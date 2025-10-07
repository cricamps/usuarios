# ✅ CHECKLIST ACTIVIDAD SUMATIVA - EXPERIENCIA 3

## 📦 Archivos Creados Automáticamente

### ✅ Código Fuente Actualizado
- [x] **pom.xml** - Agregada dependencia HATEOAS y H2 para tests
- [x] **Usuario.java** - Extendido de RepresentationModel para HATEOAS
- [x] **UsuarioNotFoundException.java** - Excepción personalizada creada
- [x] **UsuarioController.java** - Actualizado con enlaces HATEOAS completos
- [x] **UsuarioService.java** - Actualizado con manejo de excepciones

### ✅ Pruebas Unitarias (12 tests en total)
- [x] **UsuarioRepositoryTest.java** - 2 pruebas
  - Test 1: Guardar usuario correctamente
  - Test 2: Buscar usuarios activos
- [x] **UsuarioServiceTest.java** - 4 pruebas
  - Test 1: Crear usuario correctamente
  - Test 2: Obtener todos los usuarios
  - Test 3: Actualizar usuario existente
  - Test 4: Lanzar excepción al actualizar usuario no existente
- [x] **UsuarioControllerTest.java** - 6 pruebas
  - Test 1: GET todos los usuarios
  - Test 2: GET usuario por ID
  - Test 3: POST crear usuario
  - Test 4: PUT actualizar usuario
  - Test 5: DELETE eliminar usuario
  - Test 6: Error 404 cuando no existe

### ✅ Docker
- [x] **Dockerfile** - Archivo de construcción de imagen Docker
- [x] **docker-compose.yml** - Orquestación del contenedor
- [x] **.dockerignore** - Archivos a excluir en la imagen

### ✅ Documentación
- [x] **README.md** - Guía completa del proyecto
- [x] **CHECKLIST_ACTIVIDAD.md** - Este archivo

---

## 🔧 PASOS QUE DEBES COMPLETAR

### PASO 1: Actualizar Credenciales de Oracle Cloud ⚠️

#### En `application.properties`:
```properties
# ACTUALIZAR ESTAS LÍNEAS:
spring.datasource.url=jdbc:oracle:thin:@[TU_SERVICE_NAME]_high?TNS_ADMIN=./Wallet
spring.datasource.username=[TU_USUARIO]
spring.datasource.password=[TU_CONTRASEÑA]
```

#### En `docker-compose.yml`:
```yaml
# ACTUALIZAR ESTAS VARIABLES:
SPRING_DATASOURCE_URL: jdbc:oracle:thin:@[TU_SERVICE_NAME]_high?TNS_ADMIN=./Wallet
SPRING_DATASOURCE_USERNAME: [TU_USUARIO]
SPRING_DATASOURCE_PASSWORD: [TU_CONTRASEÑA]
```

**📝 Nota:** El SERVICE_NAME lo encuentras en el archivo `tnsnames.ora` dentro de tu carpeta Wallet

---

### PASO 2: Verificar que el Wallet esté en la raíz del proyecto

```
usuarios/
├── Wallet/
│   ├── tnsnames.ora
│   ├── cwallet.sso
│   ├── ewallet.p12
│   └── ... (otros archivos)
```

✅ Si está en otro lugar, muévelo a la raíz del proyecto

---

### PASO 3: Actualizar dependencias Maven

```bash
# Abrir terminal en la raíz del proyecto
cd "G:\Mi unidad\Proyectos\usuarios"

# Actualizar dependencias
./mvnw clean install

# O si estás en Windows:
mvnw.cmd clean install
```

---

### PASO 4: Ejecutar Pruebas Unitarias

```bash
# Ejecutar todas las pruebas
./mvnw test

# Deberías ver algo como:
# [INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
```

**Si hay errores:**
- Verificar que H2 esté en el pom.xml
- Verificar que application-test.properties exista
- Revisar los logs para ver qué test falló

---

### PASO 5: Probar Localmente con Spring Boot

```bash
# Ejecutar la aplicación
./mvnw spring-boot:run
```

**Luego en POSTMAN:**

1. **GET** `http://localhost:8080/usuarios` 
   - Deberías ver la lista con enlaces `_links`

2. **POST** `http://localhost:8080/usuarios`
   ```json
   {
     "nombre": "María González",
     "email": "maria.gonzalez@test.com",
     "telefono": "+56912345678",
     "rol": "ADMIN"
   }
   ```

3. **GET** `http://localhost:8080/usuarios/1`
   - Verifica que tenga enlaces HATEOAS

---

### PASO 6: Crear el JAR para Docker

```bash
# Limpiar y crear JAR (saltando tests para ir más rápido)
./mvnw clean package -DskipTests

# Verificar que se creó el JAR
ls target/*.jar

# Deberías ver: usuarios-0.0.1-SNAPSHOT.jar
```

---

### PASO 7: Copiar Wallet al target/classes

```bash
# En Windows:
xcopy /E /I Wallet target\classes\Wallet

# En Mac/Linux:
cp -r Wallet target/classes/Wallet
```

⚠️ **IMPORTANTE:** Este paso es necesario cada vez que ejecutes `mvn clean package`

---

### PASO 8: Construir Imagen Docker

```bash
# Verificar que Docker Desktop esté corriendo

# Construir la imagen
docker-compose build

# Verificar que se creó
docker images

# Deberías ver: usuarios-usuarios-microservicio-usuarios
```

---

### PASO 9: Ejecutar Docker Localmente

```bash
# Ejecutar el contenedor
docker-compose up

# O en segundo plano:
docker-compose up -d

# Ver logs:
docker logs usuarios-app -f

# Esperar a que diga: "Started UsuariosApplication in X.XXX seconds"
```

**Probar en POSTMAN:**
- Igual que en el PASO 5 pero con `http://localhost:8080`

---

### PASO 10: Preparar para Despliegue en Cloud

#### A. Subir a GitHub

```bash
# Agregar todos los cambios
git add .

# Commit
git commit -m "Implementación completa: HATEOAS, Tests, Docker"

# Push
git push origin main
```

#### B. Verificar que estos archivos ESTÉN en el repositorio:
- ✅ pom.xml (actualizado)
- ✅ src/ (todo el código)
- ✅ Wallet/ (carpeta completa)
- ✅ Dockerfile
- ✅ docker-compose.yml
- ✅ README.md

#### C. Verificar que estos archivos NO estén (están en .gitignore):
- ❌ target/ (excepto si lo necesitas para Docker Lab)
- ❌ .idea/
- ❌ .vscode/

---

### PASO 11: Desplegar en Docker Lab

#### A. Acceder a Docker Lab
1. Ir a: https://labs.play-with-docker.com
2. Login con Docker Hub (puedes crear cuenta gratis)
3. Click en "Start"
4. Click en "ADD NEW INSTANCE"

#### B. Clonar y ejecutar

```bash
# Clonar tu repositorio
git clone [URL_DE_TU_REPO]
cd usuarios

# Construir y ejecutar
docker-compose up -d

# Ver logs
docker logs usuarios-app

# Esperar mensaje: "Started UsuariosApplication"
```

#### C. Obtener URL pública

1. En Docker Lab, buscar el puerto **8080**
2. Click en el botón **"8080"** o **"OPEN PORT"**
3. Se abrirá una URL como:
   ```
   http://ip172-18-0-X-XXXXX.direct.labs.play-with-docker.com
   ```

#### D. Probar con POSTMAN usando la URL pública

```
GET http://ip172-18-0-X-XXXXX.direct.labs.play-with-docker.com/usuarios
```

---

### PASO 12: Grabar Video con Kaltura

#### Estructura del video (15 puntos):

**1. Introducción (30 seg)**
- Presentarte
- Decir qué microservicio es (Usuarios)

**2. Mostrar Despliegue en Cloud (3 min)**
- Pantalla de Docker Lab con el contenedor corriendo
- Ejecutar `docker ps` para mostrar el contenedor
- Ejecutar `docker logs usuarios-app` para ver logs
- Mostrar la URL pública

**3. Pruebas con POSTMAN usando URL pública (5 min)**
- **GET /usuarios** - Mostrar enlaces HATEOAS
- **GET /usuarios/1** - Mostrar enlaces HATEOAS de un usuario
- **POST /usuarios** - Crear nuevo usuario
  ```json
  {
    "nombre": "Pedro Ramírez",
    "email": "pedro@test.com",
    "telefono": "+56912345678",
    "rol": "USER"
  }
  ```
- **PUT /usuarios/[id]** - Actualizar usuario
- **DELETE /usuarios/[id]** - Eliminar usuario
- **GET /usuarios/activos** - Mostrar usuarios activos
- **GET /usuarios/rol/ADMIN** - Mostrar por rol

**4. Conexión Oracle Cloud (2 min)**
- Abrir SQL Developer
- Conectarse a la BD Cloud
- Ejecutar: `SELECT * FROM USUARIOS;`
- Mostrar que los datos de POSTMAN están en la BD

**5. Pruebas Unitarias (2 min)**
- Abrir terminal
- Ejecutar: `./mvnw test`
- Mostrar que pasan los 12 tests
- Mostrar resumen: "Tests run: 12, Failures: 0"

**6. Documentación HATEOAS (2 min)**
- En POSTMAN, mostrar respuesta de GET /usuarios/1
- Explicar los enlaces `_links`:
  - `self` - enlace a sí mismo
  - `todos-usuarios` - enlace a la colección
  - `actualizar` - enlace para actualizar
  - `eliminar` - enlace para eliminar
  - `usuarios-mismo-rol` - enlace a usuarios del mismo rol

**7. Cierre (30 seg)**
- Resumir lo mostrado
- Agradecer

**Duración total: ~15 minutos**

---

### PASO 13: Preparar Entrega Final

#### A. Crear archivo ZIP con:
```
usuarios.zip
├── src/
├── Wallet/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── README.md
├── crear_tabla_usuarios.sql
└── (otros archivos necesarios)
```

**NO incluir:**
- target/
- .git/
- .idea/
- .vscode/

#### B. Preparar Links:

**1. Link GitHub:**
```
https://github.com/[tu-usuario]/[tu-repo]
```

**2. Link Trello:**
```
https://trello.com/b/[tu-board-id]
```

**3. Link Video Kaltura:**
- Subir video a Kaltura
- Copiar link de compartir

#### C. Subir al AVA:
1. Archivo ZIP
2. En comentarios/descripción:
   - Link GitHub
   - Link Trello
   - Link Video Kaltura

---

## ⚠️ PROBLEMAS COMUNES Y SOLUCIONES

### Error: "Failed to load driver class oracle.jdbc.OracleDriver"
**Solución:** Verificar que el Wallet esté en la raíz del proyecto

### Error: "Tests failing"
**Solución:** Ejecutar `./mvnw clean test` y revisar logs

### Error: "Cannot connect to Oracle Cloud"
**Solución:**
- Verificar credenciales en application.properties
- Verificar que la BD esté activa en Oracle Cloud
- Verificar que el TNS_ADMIN apunte al Wallet correcto

### Error: "Docker build failed"
**Solución:**
- Verificar que Docker Desktop esté corriendo
- Ejecutar `docker system prune -a` para limpiar
- Verificar que el Wallet esté en target/classes

### Error: "Port 8080 already in use"
**Solución:**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID [PID_NUMBER] /F

# Mac/Linux
lsof -i :8080
kill -9 [PID]
```

---

## 📊 RUBRICA - AUTOEVALUACIÓN

Antes de entregar, verifica que cumples con:

| Criterio | Puntos | ✅ |
|----------|--------|---|
| **1. Uso de Git y Docker** | 20 | [ ] |
| - Repositorio Git actualizado | | [ ] |
| - Contenedor desplegado en Cloud | | [ ] |
| **2. Documentación HATEOAS** | 10 | [ ] |
| - Enlaces _links en todos los endpoints | | [ ] |
| - Implementado en ambos microservicios | | [ ] |
| **3. Microservicios funcionando** | 20 | [ ] |
| - CRUD completo | | [ ] |
| - Conexión Oracle Cloud | | [ ] |
| - Al menos 3 registros por tabla | | [ ] |
| - Validación con POSTMAN | | [ ] |
| **4. Configuración JUnit** | 5 | [ ] |
| - Anotaciones correctas | | [ ] |
| - Configuración adecuada | | [ ] |
| **5. Pruebas Unitarias** | 30 | [ ] |
| - Al menos 2 pruebas por microservicio | | [ ] |
| - Todas las pruebas pasan | | [ ] |
| **6. Video presentación** | 15 | [ ] |
| - Despliegue en Cloud | | [ ] |
| - Ejecución POSTMAN | | [ ] |
| - Conexión Oracle Cloud | | [ ] |
| - Pruebas unitarias | | [ ] |
| - Documentación HATEOAS | | [ ] |

**TOTAL: 100 puntos**

---

## 🎯 CHECKLIST FINAL ANTES DE ENTREGAR

- [ ] Código actualizado en GitHub
- [ ] Trello actualizado con tareas
- [ ] Pruebas unitarias pasan (./mvnw test)
- [ ] Microservicio funciona localmente
- [ ] Docker construye sin errores
- [ ] Microservicio desplegado en Docker Lab
- [ ] URL pública funciona
- [ ] POSTMAN muestra enlaces HATEOAS
- [ ] Video grabado con Kaltura
- [ ] ZIP creado sin carpeta target
- [ ] Links preparados (Git, Trello, Video)
- [ ] Script SQL de BD incluido
- [ ] README.md completado

---

## 📞 AYUDA ADICIONAL

Si tienes problemas:
1. Revisar logs: `docker logs usuarios-app`
2. Revisar README.md sección "Solución de Problemas"
3. Consultar con el profesor
4. Revisar las guías de aprendizaje de las semanas 7 y 8

---

**¡ÉXITO EN TU ACTIVIDAD!** 🚀
