# INICIO RAPIDO - 5 MINUTOS

## 1. Actualizar Credenciales Oracle (2 min)

### Archivo: application.properties
Linea 7-9: Actualizar con tus datos
```properties
spring.datasource.url=jdbc:oracle:thin:@[TU_SERVICE]_high?TNS_ADMIN=./Wallet
spring.datasource.username=[TU_USUARIO]
spring.datasource.password=[TU_PASSWORD]
```

### Archivo: docker-compose.yml
Linea 11-13: Actualizar con los mismos datos
```yaml
SPRING_DATASOURCE_URL: jdbc:oracle:thin:@[TU_SERVICE]_high?TNS_ADMIN=./Wallet
SPRING_DATASOURCE_USERNAME: [TU_USUARIO]
SPRING_DATASOURCE_PASSWORD: [TU_PASSWORD]
```

Donde encontrar TU_SERVICE: abre Wallet/tnsnames.ora

## 2. Probar que todo funciona (3 min)

### Abrir terminal en la carpeta del proyecto:
```bash
cd "G:\Mi unidad\Proyectos\usuarios"
```

### Ejecutar pruebas:
```bash
./mvnw clean test
```

Debes ver: Tests run: 12, Failures: 0

### Ejecutar aplicacion:
```bash
./mvnw spring-boot:run
```

### Probar en POSTMAN:
```
GET http://localhost:8080/usuarios
```

Debes ver enlaces _links en la respuesta

## 3. Siguientes pasos

Lee en orden:
1. CHECKLIST_ACTIVIDAD.md - Pasos completos
2. README.md - Documentacion detallada

## Archivos que debes revisar

NUEVOS:
- CHECKLIST_ACTIVIDAD.md (LO MAS IMPORTANTE)
- README.md
- RESUMEN_EJECUTIVO.md
- src/test/ (carpeta completa con pruebas)
- Dockerfile
- docker-compose.yml

MODIFICADOS:
- pom.xml (agregada HATEOAS y H2)
- src/main/java/usuarios/usuarios/model/Usuario.java (HATEOAS)
- src/main/java/usuarios/usuarios/controller/UsuarioController.java (HATEOAS)
- src/main/java/usuarios/usuarios/service/UsuarioService.java (excepciones)

CREADOS:
- src/main/java/usuarios/usuarios/exception/UsuarioNotFoundException.java

COMIENZA AQUI:
./mvnw clean test
