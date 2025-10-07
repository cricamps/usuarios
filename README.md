# Microservicio de Gestión de Usuarios
## DSY2201 - Desarrollo Full Stack I - Experiencia 3

---

## 📋 Descripción

Microservicio RESTful para la gestión de usuarios desarrollado con Spring Boot 3.5.6, que incluye:
- ✅ CRUD completo de usuarios
- ✅ Conexión a Oracle Cloud Database
- ✅ Documentación con HATEOAS
- ✅ Pruebas unitarias con JUnit 5 y Mockito
- ✅ Contenerización con Docker
- ✅ Validaciones de datos
- ✅ Manejo de excepciones personalizado

---

## 🚀 Tecnologías Utilizadas

- **Spring Boot 3.5.6**
- **Java 17**
- **Oracle Database 19c** (Cloud)
- **JPA/Hibernate**
- **HATEOAS** para documentación de API
- **JUnit 5** para pruebas unitarias
- **Mockito** para mocks en pruebas
- **Docker** para contenerización
- **Maven** como herramienta de construcción

---

## 📦 Estructura del Proyecto

```
usuarios/
├── src/
│   ├── main/
│   │   ├── java/usuarios/usuarios/
│   │   │   ├── model/          # Entidades JPA
│   │   │   ├── repository/     # Interfaces JPA Repository
│   │   │   ├── service/        # Lógica de negocio
│   │   │   ├── controller/     # Controladores REST con HATEOAS
│   │   │   └── exception/      # Excepciones personalizadas
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-test.properties
│   └── test/
│       └── java/usuarios/usuarios/
│           ├── repository/     # Tests del repositorio
│           ├── service/        # Tests del servicio
│           └── controller/     # Tests del controlador
├── Wallet/                     # Wallet Oracle Cloud
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

## 🔧 Configuración Previa

### 1. Requisitos
- Java JDK 17 o superior
- Maven 3.8+
- Docker Desktop
- Oracle Cloud Account (con database configurada)
- Git

### 2. Configurar Oracle Cloud Database

1. Acceder a Oracle Cloud Console
2. Crear una Autonomous Database
3. Descargar el Wallet de conexión
4. Descomprimir el Wallet en la carpeta raíz del proyecto

### 3. Actualizar application.properties

```properties
spring.datasource.url=jdbc:oracle:thin:@[TU_SERVICE_NAME]_high?TNS_ADMIN=./Wallet
spring.datasource.username=[TU_USUARIO]
spring.datasource.password=[TU_PASSWORD]
```

### 4. Actualizar docker-compose.yml

Modificar las variables de entorno con tus credenciales:
```yaml
SPRING_DATASOURCE_URL: jdbc:oracle:thin:@[TU_SERVICE_NAME]_high?TNS_ADMIN=./Wallet
SPRING_DATASOURCE_USERNAME: [TU_USUARIO]
SPRING_DATASOURCE_PASSWORD: [TU_PASSWORD]
```

---

## 🏃 Ejecutar Localmente

### Opción 1: Con Maven

```bash
# Compilar el proyecto
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run
```

### Opción 2: Con JAR

```bash
# Generar el JAR
./mvnw clean package -DskipTests

# Ejecutar el JAR
java -jar target/usuarios-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 🐳 Ejecutar con Docker

### Construir y ejecutar localmente

```bash
# Construir la imagen
docker-compose build

# Ejecutar el contenedor
docker-compose up

# Ejecutar en segundo plano
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener el contenedor
docker-compose down
```

---

## ☁️ Desplegar en Docker Lab (Cloud)

### Paso 1: Acceder a Docker Lab
1. Ir a https://www.docker.com/play-with-docker/
2. Hacer clic en "Login" e iniciar sesión con GitHub
3. Hacer clic en "Start" y luego "ADD NEW INSTANCE"

### Paso 2: Clonar el repositorio

```bash
git clone [URL_DE_TU_REPOSITORIO]
cd usuarios
```

### Paso 3: Construir y ejecutar

```bash
# Construir y ejecutar
docker-compose up -d

# Verificar que está corriendo
docker ps

# Ver logs
docker logs usuarios-app
```

### Paso 4: Obtener URL pública

1. En Docker Lab, buscar el puerto 8080
2. Hacer clic en el botón "OPEN PORT"
3. Se abrirá una URL pública como: `http://ip172-18-0-X-XXXXX.direct.labs.play-with-docker.com`

---

## 🧪 Ejecutar Pruebas Unitarias

```bash
# Ejecutar todas las pruebas
./mvnw test

# Ejecutar pruebas con reporte de cobertura
./mvnw test jacoco:report

# Ver solo las pruebas del repositorio
./mvnw test -Dtest=UsuarioRepositoryTest

# Ver solo las pruebas del servicio
./mvnw test -Dtest=UsuarioServiceTest

# Ver solo las pruebas del controlador
./mvnw test -Dtest=UsuarioControllerTest
```

---

## 📡 Endpoints de la API

### Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/usuarios` | Obtener todos los usuarios |
| GET | `/usuarios/{id}` | Obtener usuario por ID |
| GET | `/usuarios/activos` | Obtener usuarios activos |
| GET | `/usuarios/rol/{rol}` | Obtener usuarios por rol |
| POST | `/usuarios` | Crear nuevo usuario |
| PUT | `/usuarios/{id}` | Actualizar usuario |
| DELETE | `/usuarios/{id}` | Eliminar usuario |

### Ejemplos con POSTMAN

#### 1. GET - Obtener todos los usuarios
```
GET http://localhost:8080/usuarios
```

**Respuesta HATEOAS:**
```json
{
  "_embedded": {
    "usuarioList": [
      {
        "id": 1,
        "nombre": "María González",
        "email": "maria@example.com",
        "rol": "ADMIN",
        "_links": {
          "self": { "href": "http://localhost:8080/usuarios/1" },
          "todos-usuarios": { "href": "http://localhost:8080/usuarios" },
          "actualizar": { "href": "http://localhost:8080/usuarios/1" },
          "eliminar": { "href": "http://localhost:8080/usuarios/1" }
        }
      }
    ]
  },
  "_links": {
    "self": { "href": "http://localhost:8080/usuarios" }
  }
}
```

#### 2. POST - Crear usuario
```
POST http://localhost:8080/usuarios
Content-Type: application/json

{
  "nombre": "Pedro Ramírez",
  "email": "pedro.ramirez@example.com",
  "telefono": "+56912345678",
  "rol": "USER"
}
```

#### 3. PUT - Actualizar usuario
```
PUT http://localhost:8080/usuarios/1
Content-Type: application/json

{
  "nombre": "María González Actualizada",
  "email": "maria.nueva@example.com",
  "telefono": "+56987654321",
  "rol": "ADMIN"
}
```

#### 4. DELETE - Eliminar usuario
```
DELETE http://localhost:8080/usuarios/1
```

---

## 📝 Pruebas Unitarias Implementadas

### UsuarioRepositoryTest
1. ✅ Guardar usuario correctamente
2. ✅ Buscar usuarios activos

### UsuarioServiceTest
1. ✅ Crear usuario correctamente
2. ✅ Obtener todos los usuarios
3. ✅ Actualizar usuario existente
4. ✅ Lanzar excepción al actualizar usuario no existente

### UsuarioControllerTest
1. ✅ GET /usuarios - Obtener todos los usuarios
2. ✅ GET /usuarios/{id} - Obtener usuario por ID
3. ✅ POST /usuarios - Crear usuario
4. ✅ PUT /usuarios/{id} - Actualizar usuario
5. ✅ DELETE /usuarios/{id} - Eliminar usuario
6. ✅ GET /usuarios/{id} - Error 404 cuando no existe

---


## 📊 Base de Datos

### Script de creación de tabla

```sql
-- Ver archivo: crear_tabla_usuarios.sql

CREATE TABLE USUARIOS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NOMBRE VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(100) NOT NULL UNIQUE,
    TELEFONO VARCHAR2(20),
    ROL VARCHAR2(50),
    FECHA_REGISTRO TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ACTIVO NUMBER(1) DEFAULT 1
);

-- Crear secuencia
CREATE SEQUENCE USUARIO_SEQ START WITH 1 INCREMENT BY 1;
```

### Datos de prueba

```sql
INSERT INTO USUARIOS (NOMBRE, EMAIL, TELEFONO, ROL, ACTIVO) 
VALUES ('María González', 'maria@example.com', '+56912345678', 'ADMIN', 1);

INSERT INTO USUARIOS (NOMBRE, EMAIL, TELEFONO, ROL, ACTIVO) 
VALUES ('Pedro Ramírez', 'pedro@example.com', '+56987654321', 'USER', 1);

INSERT INTO USUARIOS (NOMBRE, EMAIL, TELEFONO, ROL, ACTIVO) 
VALUES ('Ana Torres', 'ana@example.com', '+56998877665', 'USER', 1);

COMMIT;
```

---

## 🔍 Solución de Problemas

### Error de conexión a Oracle Cloud
- Verificar que el Wallet esté en la carpeta correcta
- Verificar credenciales en application.properties
- Verificar que el servicio de BD esté activo en Oracle Cloud

### Error al construir Docker
- Verificar que Docker Desktop esté corriendo
- Limpiar caché: `docker system prune -a`
- Verificar que el Wallet esté incluido

### Tests fallan
- Verificar que application-test.properties exista
- Ejecutar: `./mvnw clean test`

---

## 👥 Autor

**[Tu Nombre]**
- Curso: DSY2201 - Desarrollo Full Stack I
- Institución: Duoc UC
- Fecha: Octubre 2025

---

## 📄 Licencia

Este proyecto es parte de una actividad académica de Duoc UC.

---

## 🔗 Enlaces de Interés

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring HATEOAS](https://spring.io/projects/spring-hateoas)
- [Oracle Cloud](https://www.oracle.com/cloud/)
- [Docker Documentation](https://docs.docker.com/)
- [JUnit 5](https://junit.org/junit5/)
