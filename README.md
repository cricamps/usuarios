# Microservicio de Gestión de Usuarios

Sistema de microservicios desarrollado con Spring Boot para la gestión completa de usuarios.

### Usuarios
- `GET /usuarios` - Obtener todos los usuarios
- `GET /usuarios/{id}` - Obtener usuario por ID
- `GET /usuarios/activos` - Obtener usuarios activos
- `GET /usuarios/rol/{rol}` - Obtener usuarios por rol
- `POST /usuarios` - Crear nuevo usuario
- `PUT /usuarios/{id}` - Actualizar usuario
- `DELETE /usuarios/{id}` - Eliminar usuario

### Ejecutar la aplicación
```bash
mvn clean install
mvn spring-boot:run
```

La aplicación disponible en: `http://localhost:8080`

## Testing

Se incluye una colección de POSTMAN (`postman_simple.json`) con todas las pruebas de los endpoints.


