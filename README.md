# PGT USACH

Prototipo funcional de la **Plataforma de Gestión de Tesistas** para el caso de uso CU04: **Postular a tema de tesis**.

## Funcionalidades

- Inicio de sesión institucional de demostración.
- Panel principal del estudiante.
- Catálogo de temas con búsqueda y filtros.
- Detalle completo de cada propuesta.
- Postulación individual o grupal.
- Validación de cupos, composición y estado académico.
- Confirmación, reserva del cupo y notificación al profesor.
- Seguimiento desde “Mis postulaciones”.
- Accesos a los demás casos de uso con aviso “Próximamente”.

## Tecnologías

- Vue 3, Vue Router y Vite.
- Spring Boot 4.1 y Java 21.
- PostgreSQL 17.

## Ejecución local

### 1. Base de datos

Con PostgreSQL instalado y ejecutándose, abre `psql` con un usuario administrador y crea la base de datos local:

```sql
CREATE USER pgt WITH PASSWORD 'pgt2026';
CREATE DATABASE pgt_usach OWNER pgt;
```

Si ya tienes un usuario o una base de datos, configura sus datos mediante las variables `DB_URL`, `DB_USER` y `DB_PASSWORD`.

### 2. Backend

```powershell
cd backend
mvn spring-boot:run
```

El backend queda disponible en `http://localhost:8080` y crea las tablas y datos demostrativos al iniciar.

### 3. Frontend

En otra terminal:

```powershell
cd frontend
pnpm install
Copy-Item .env.example .env
pnpm dev
```

Abre `http://localhost:5173`. Si no existe `frontend/.env`, el frontend funciona con sus datos locales de demostración.

## Credenciales de demostración

- Correo: `estudiante@usach.cl`
- Clave: `usach2026`

## Endpoints principales

- `POST /api/auth/login`
- `GET /api/topics`
- `GET /api/topics/{id}`
- `POST /api/applications`
- `GET /api/applications/student/{studentId}`
- `GET /api/health`

## Estructura

```text
frontend/
  src/
    main.js       Inicia Vue
    router.js     Define las páginas y sus rutas
    api.js        Comunica el frontend con el backend o los datos de prueba
    session.js    Guarda el usuario que inició sesión
    toast.js      Muestra las notificaciones breves
    topics.js     Temas de tesis de demostración
    modules.js    Menú y accesos del panel principal
    styles.css    Diseño visual completo
    components/   Piezas visuales reutilizables
    views/        Pantallas completas

backend/
  src/pgt/
    controller/   Recibe las peticiones HTTP
    service/      Contiene las reglas del caso de uso
    domain/       Representa los datos guardados
    repository/   Accede a PostgreSQL
    dto/          Datos que entran y salen de la API
    config/       Carga los datos de demostración
  resources/      Configuración local y conexión a PostgreSQL
```

La información incluida es demostrativa y se carga automáticamente cuando la base de datos está vacía.
