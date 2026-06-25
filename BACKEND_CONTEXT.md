# Backend — Contexto Técnico

## Stack

| Componente | Tecnología |
|---|---|
| Framework | Spring Boot 4.1.0 |
| Java | 21 |
| Build | Maven |
| ORM | Hibernate 7 (JPA) |
| DB prod | PostgreSQL (Render) |
| DB dev | H2 in-memory (opcional) |
| Auth | HTTP Basic Auth |
| Docs | springdoc-openapi (Swagger UI + OpenAPI JSON) |
| Validation | jakarta.validation (`@NotBlank`, `@Email`, `@NotNull`, `@Positive`) |
| Lombok | `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` |
| DTOs | Clases planas manuales (no records) en `dto/` |

---

## Arquitectura

```
controller/       → Reciben HTTP, delegan a services
service/          → Lógica de negocio, orquestan repos
repository/       → JPA repositories
entity/           → JPA entities (tablas)
dto/              → Request objects (sin response DTOs, se devuelven entities directo)
enums/            → Enumeraciones
config/           → SecurityConfig, CorsConfig, DataInitializer
```

No hay response DTOs: los controllers devuelven las entidades JPA directamente (Solicitud, Guisado, MenuItem, etc.), exponiendo así todos sus campos al cliente.

---

## Board de Enums

### `ServiceType`
| Valor | Servicio |
|---|---|
| `TAQUIZA` | Taquiza (guisados + tortillas + salsas) |
| `TRES_TIEMPOS` | 3 tiempos (entrada + plato fuerte + postre) |
| `PLATO_VOLADO` | Plato volado (similar a 3 tiempos pero sin mantelería) |

### `SolicitudStatus`
| Valor | Significado |
|---|---|
| `NUEVA` | Recién llegada, sin acción |
| `CONTACTADA` | El admin contactó al cliente |
| `CONCRETADA` | Se cerró el trato |
| `DESCARTADA` | No procede |

### `MenuCategory`
| Valor | Uso |
|---|---|
| `CREMA` | Cremas (entradas para 3T/PlatoVolado) |
| `PASTA` | Pastas (entrada para 3T/PlatoVolado) |
| `PLATO_FUERTE_POLLO` | Plato fuerte pollo |
| `PLATO_FUERTE_CERDO` | Plato fuerte cerdo |
| `ENSALADA` | Ensaladas (guarnición para 3T/PlatoVolado) |
| `GUARNICION` | Guarniciones calientes |

### `GuisadoCategory`
| Valor | Descripción |
|---|---|
| `POLLO` | Guisados de pollo |
| `RES` | Guisados de res |
| `PUERCO` | Guisados de puerco |
| `VEGETARIANO` | Guisados vegetarianos |

---

## Entidades JPA

### `Solicitud`
| Campo | Tipo | Requerido | Notas |
|---|---|---|---|
| id | Long (PK, auto) | — | Identity |
| nombreContacto | String | Sí | |
| telefono | String | No | |
| email | String | Sí | |
| direccion | String | No | |
| password | String | No | Se usa SOLO para auto-crear User si se proporciona |
| serviceType | ServiceType (enum string) | Sí | TAQUIZA / TRES_TIEMPOS / PLATO_VOLADO |
| eventDate | LocalDate | No | Fecha del evento |
| numberOfPeople | Integer | Sí | Cantidad de comensales |
| selectedGuisados | List\<Guisado\> (MTM) | No | Solo para TAQUIZA. JoinTable: solicitud_guisados |
| selectedCrema | MenuItem (MTO, LAZY) | No | FK a menu_items |
| selectedPasta | MenuItem (MTO, LAZY) | No | FK a menu_items |
| selectedPlatoFuerte | MenuItem (MTO, LAZY) | No | FK a menu_items |
| selectedSalsa | Salsa (MTO, LAZY) | No | FK a salsa |
| selectedEnsalada | MenuItem (MTO, LAZY) | No | FK a menu_items |
| selectedGuarnicion | MenuItem (MTO, LAZY) | No | FK a menu_items |
| includesTableware | Boolean | No | ¿Incluye loza/vasos/cubiertos? (Solo 3T/PlatoVolado) |
| aguaFrescaVariety | String | No | Sabor de agua fresca |
| comentarios | String (max 2000) | No | Notas adicionales |
| status | SolicitudStatus (enum string) | Default NUEVA | Se setea en @PrePersist si es null |
| createdAt | LocalDateTime | Default now | Se setea en @PrePersist |

### `Guisado`
| Campo | Tipo | Requerido |
|---|---|---|
| id | Long (PK, auto) | — |
| nombre | String | Sí |
| categoria | GuisadoCategory (enum string) | Sí |

### `MenuItem`
| Campo | Tipo | Requerido |
|---|---|---|
| id | Long (PK, auto) | — |
| nombre | String | Sí |
| categoria | MenuCategory (enum string) | Sí |
| descripcion | String (max 500) | No |

### `Salsa`
| Campo | Tipo | Requerido |
|---|---|---|
| id | Long (PK, auto) | — |
| nombre | String | Sí |
| tipo | String | No (valores: "POLLO", "CERDO", "AMBOS") |
| descripcion | String (max 500) | No |

### `Client`
| Campo | Tipo | Requerido |
|---|---|---|
| id | Long (PK, auto) | — |
| nombre | String | Sí |
| telefono | String | No |
| email | String (unique) | No |
| direccion | String | No |

### `User` (tabla: `users`)
| Campo | Tipo | Requerido |
|---|---|---|
| id | Long (PK, auto) | — |
| username | String (unique) | Sí |
| password | String (BCrypt) | Sí |
| role | String | Sí ("ADMIN", "CLIENTE", "USER") |
| enabled | boolean | Default true |

---

## API Contract

### Base URL: `http://localhost:8080/api`

### Autenticación
- **Scheme:** HTTP Basic Auth
- **Header:** `Authorization: Basic <base64(username:password)>`
- Usuarios pre-cargados: `admin/admin123` (ADMIN), `user/user123` (USER)
- Clientes se auto-crean al enviar solicitud con password (role=CLIENTE)
- Endpoints privados devuelven **401** si no hay auth, **403** si el rol no es suficiente

---

### Publicos (sin autenticación)

#### `GET /api/public/catalogo`
Obtiene el catálogo completo del menú.

**Response 200:**

```json
{
  "servicios": [
    {
      "tipo": "TAQUIZA",
      "descripcion": "Guisados, tortillas, salsas y aguas frescas.",
      "precioReferencia": "$110 por persona",
      "incluye": ["Guisados", "Tortillas", "Salsas", "Aguas frescas"],
      "categorias": [
        {
          "nombre": "POLLO",
          "items": [
            { "id": 1, "nombre": "En salsa guajillo", "categoria": "POLLO" }
          ]
        }
      ]
    },
    {
      "tipo": "TRES_TIEMPOS",
      "descripcion": "Entrada, plato fuerte con guarniciones y postre.",
      "precioReferencia": "A partir de $500 por persona",
      "incluye": ["Entrada", "Plato fuerte con guarnicion", "Postre", "Aguas frescas"],
      "categorias": [
        {
          "nombre": "CREMA",
          "items": [
            { "id": 21, "nombre": "Crema de elote", "categoria": "CREMA", "descripcion": null }
          ]
        }
      ]
    },
    {
      "tipo": "PLATO_VOLADO",
      "descripcion": "Entrada, plato fuerte y postre en empaque biodegradables.",
      "precioReferencia": "A partir de $500 por persona",
      "incluye": ["Entrada", "Plato fuerte con guarnicion", "Postre", "Aguas frescas"],
      "categorias": [
        {
          "nombre": "CREMA",
          "items": [
            { "id": 21, "nombre": "Crema de elote", "categoria": "CREMA", "descripcion": null }
          ]
        }
      ]
    }
  ],
  "salsas": [
    { "id": 1, "nombre": "Crema de champinon", "tipo": "POLLO", "descripcion": null },
    { "id": 2, "nombre": "Salsa de tres chiles", "tipo": "POLLO", "descripcion": null },
    { "id": 3, "nombre": "Salsa pasilla", "tipo": "POLLO", "descripcion": null },
    { "id": 4, "nombre": "Ciruela", "tipo": "CERDO", "descripcion": null },
    { "id": 5, "nombre": "Enchilado", "tipo": "CERDO", "descripcion": null },
    { "id": 6, "nombre": "Tres chiles", "tipo": "CERDO", "descripcion": null },
    { "id": 7, "nombre": "A la naranja", "tipo": "CERDO", "descripcion": null }
  ]
}
```

#### `GET /api/public/guisados`
Lista todos los guisados.

**Response 200:** `Guisado[]`

#### `GET /api/public/menu-items`
Lista todos los items del menú.

**Response 200:** `MenuItem[]`

#### `GET /api/public/salsas`
Lista todas las salsas.

**Response 200:** `Salsa[]`

#### `POST /api/public/solicitudes`
Envía una solicitud de cotización.

**Request body:**

```json
{
  "nombreContacto": "Juan Pérez",
  "telefono": "555-1234",
  "email": "juan@example.com",
  "password": "mi-clave-opcional",
  "direccion": "Calle 123",
  "serviceType": "TAQUIZA",
  "eventDate": "2026-07-15",
  "numberOfPeople": 50,
  "selectedGuisadoIds": [1, 2, 3],
  "selectedCremaId": null,
  "selectedPastaId": null,
  "selectedPlatoFuerteId": null,
  "selectedSalsaId": null,
  "selectedEnsaladaId": null,
  "selectedGuarnicionId": null,
  "includesTableware": false,
  "aguaFrescaVariety": "Jamaica",
  "comentarios": "Evento corporativo"
}
```

**Notas:**
- Si `serviceType` = `TAQUIZA`, se ignoran `selectedCremaId`, `selectedPastaId`, etc. Solo se usan `selectedGuisadoIds`.
- Si `serviceType` = `TRES_TIEMPOS` o `PLATO_VOLADO`, se ignoran `selectedGuisadoIds`. Se usan los `selected*Id` individuales.
- Si se proporciona `password`, se auto-crea un `User` con `username=email` y `role=CLIENTE` (BCrypt) si no existe ya.

**Response 201:** `Solicitud` (con id, status="NUEVA", createdAt)

**Errores:**
- `400 Bad Request` si falla validación (`@NotBlank`, `@Email`, `@Positive`, etc.)
- `400 Bad Request` si se referencian IDs de guisados/menu items/salsas que no existen (lanza RuntimeException)

---

### Autenticación

#### `GET /api/auth/me`
Devuelve información del usuario autenticado.

**Headers:** `Authorization: Basic <base64>`

**Response 200:**

```json
{
  "authenticated": true,
  "username": "admin",
  "role": "ADMIN"
}
```

**Response 401:** si no hay credenciales o son inválidas.

---

### Cliente (requiere Basic Auth)

#### `GET /api/cliente/solicitudes`
Lista las solicitudes asociadas al email del cliente autenticado.

**Lógica:** El backend obtiene el `username` del `Authentication` principal (que para clientes es su email) y filtra `Solicitud` por ese email.

**Response 200:** `Solicitud[]`

**Response 401:** sin auth.

---

### Admin (requiere Basic Auth)

#### Solicitudes

| Método | Path | Descripción |
|---|---|---|
| `GET` | `/api/admin/solicitudes` | Lista TODAS las solicitudes (sin filtro) |
| `GET` | `/api/admin/solicitudes/{id}` | Detalle de una solicitud |
| `PATCH` | `/api/admin/solicitudes/{id}/status` | Actualiza el estado |
| `DELETE` | `/api/admin/solicitudes/{id}` | Elimina una solicitud |

**`PATCH` body:**

```json
"CONCRETADA"
```

(tipo: `SolicitudStatus` — string literal en JSON, no objeto anidado)

#### Guisados (`/api/guisados`)

| Método | Path | Descripción |
|---|---|---|
| `GET` | `/api/guisados` | Lista todos |
| `GET` | `/api/guisados/{id}` | Get by ID |
| `GET` | `/api/guisados/categoria/{categoria}` | Filtrar por categoría (POLLO, RES, PUERCO, VEGETARIANO) |
| `POST` | `/api/guisados` | Crear |
| `PUT` | `/api/guisados/{id}` | Actualizar |
| `DELETE` | `/api/guisados/{id}` | Eliminar (204) |

**`POST/PUT /api/guisados` body:**

```json
{
  "nombre": "Guisado nuevo",
  "categoria": "POLLO"
}
```

#### Menu Items (`/api/menu-items`)

| Método | Path | Descripción |
|---|---|---|
| `GET` | `/api/menu-items` | Lista todos |
| `GET` | `/api/menu-items/{id}` | Get by ID |
| `GET` | `/api/menu-items/categoria/{categoria}` | Filtrar por MenuCategory |
| `POST` | `/api/menu-items` | Crear |
| `PUT` | `/api/menu-items/{id}` | Actualizar |
| `DELETE` | `/api/menu-items/{id}` | Eliminar (204) |

**`POST/PUT /api/menu-items` body:**

```json
{
  "nombre": "Item nuevo",
  "categoria": "CREMA",
  "descripcion": "Opcional"
}
```

#### Salsas (`/api/salsas`)

| Método | Path | Descripción |
|---|---|---|
| `GET` | `/api/salsas` | Lista todas |
| `GET` | `/api/salsas/{id}` | Get by ID |
| `GET` | `/api/salsas/tipo/{tipo}` | Filtrar por tipo (POLLO, CERDO) |
| `POST` | `/api/salsas` | Crear |
| `PUT` | `/api/salsas/{id}` | Actualizar |
| `DELETE` | `/api/salsas/{id}` | Eliminar (204) |

**`POST/PUT /api/salsas` body:**

```json
{
  "nombre": "Salsa nueva",
  "tipo": "POLLO",
  "descripcion": "Opcional"
}
```

#### Clientes (`/api/clientes`)

| Método | Path | Descripción |
|---|---|---|
| `GET` | `/api/clientes` | Lista todos |
| `GET` | `/api/clientes/{id}` | Get by ID |
| `POST` | `/api/clientes` | Crear |
| `PUT` | `/api/clientes/{id}` | Actualizar |
| `DELETE` | `/api/clientes/{id}` | Eliminar (204) |

**`POST/PUT /api/clientes` body:**

```json
{
  "nombre": "Cliente X",
  "telefono": "555-0000",
  "email": "cliente@example.com",
  "direccion": "Dirección"
}
```

---

## Error Handling

No hay `@ControllerAdvice` global. Los errores se manejan con el comportamiento por defecto de Spring:

| Situación | HTTP | Body |
|---|---|---|
| Sin auth o credenciales inválidas | 401 | Whitelabel error page (HTML) |
| Validación falla (`@Valid`) | 400 | Errores de validación en formato Spring |
| RuntimeException (entity no encontrada) | 500 | Whitelabel + stack trace |
| ID nulo o tipo incorrecto | 400 | Whitelabel |

---

## CORS

Configurado en `CorsConfig.java` para permitir:

- **Orígenes:** `http://localhost:5173` (dev Vite), `https://<frontend-vercel>.vercel.app`
- **Methods:** GET, POST, PUT, DELETE, OPTIONS
- **Headers:** todos
- **Credentials:** true

---

## Seguridad

- CSRF deshabilitado
- HTTP Basic con BCrypt para passwords
- No hay JWT, ni sesiones, ni tokens de expiración
- El header `Authorization` se envía en cada request (stateless)
- `spring.security.user.name/password/roles` en `application.properties` es una config alternativa de Spring Boot pero NO se usa activamente porque `SecurityConfig` define su propio `UserDetailsService` desde BD

---

## Data Inicial

El `DataInitializer` corre una sola vez (cuando `guisadoRepository.count() == 0`) y precarga:
- 29 guisados (POLLO=9, RES=5, PUERCO=9, VEGETARIANO=6)
- 25 menu items (CREMA=5, PASTA=4, PLATO_FUERTE_POLLO=4, PLATO_FUERTE_CERDO=3, ENSALADA=3, GUARNICION=2)
- 7 salsas
- 2 usuarios (admin/admin123, user/user123)

---

## Despliegue

- **Dockerfile:** multi-stage (Maven build → JRE runtime)
- **Render:** Web Service con Dockerfile
- **Env vars requeridas:** DB_USER, DB_PASSWORD
- **DB:** PostgreSQL externa (URL hardcodeada en application.properties, credenciales via env vars)
- **Puerto:** 8080

---

## Swagger

- **UI:** `/swagger-ui.html`
- **OpenAPI JSON:** `/api-docs`
- **Packages escaneados:** `com.example.proyectofinal.controller`
