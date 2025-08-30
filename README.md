# Microservicio de Usuarios

Sistema básico de gestión de usuarios.

## Información del Proyecto
- **Framework**: Spring Boot 3.5.5
- **Lenguaje**: Java 17
- **Puerto**: 8080 (por defecto)
- **Tipo**: Microservicio REST con datos en memoria


## Datos en Memoria

### Usuarios
1. Juan Pérez (ADMIN)
2. María González (CLIENTE) 
3. Carlos Rodríguez (VENDEDOR)
4. Ana López (CLIENTE)
5. Pedro Martínez (CLIENTE)
6. Laura Fernández (VENDEDOR)
7. Miguel Torres (CLIENTE) 
8. Carmen Silva (ADMIN)


### Usuarios (`/usuarios`)
- `GET /usuarios` - Listar todos los usuarios
- `GET /usuarios/{id}` - Usuario por ID
- `GET /usuarios/activos` - Solo usuarios activos

## Modelo de Datos

### Usuario
- **id**: Long - Identificador único
- **nombre**: String - Nombre completo
- **email**: String - Correo electrónico
- **telefono**: String - Número de teléfono
- **rol**: String - Rol del usuario (ADMIN, CLIENTE, VENDEDOR)
- **fechaRegistro**: LocalDateTime - Fecha de registro
- **activo**: Boolean - Estado del usuario

## Ejecutar el Microservicio

1. Ejecutar: `mvn spring-boot:run`
2. Acceder: `http://localhost:8080`

## Ejemplos de Uso

### Listar todos los usuarios
```
GET http://localhost:8080/usuarios
```

### Usuario específico
```
GET http://localhost:8080/usuarios/1
```

### Solo usuarios activos
```
GET http://localhost:8080/usuarios/activos
```


