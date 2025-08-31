package usuarios.usuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    // GET / - Página de inicio con información de endpoints
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> inicio() {
        Map<String, Object> respuesta = Map.of(
            "mensaje", "¡Microservicio de Usuarios funcionando!",
            "version", "1.0.0",
            "endpoints", List.of(
                Map.of(
                    "url", "/usuarios",
                    "metodo", "GET",
                    "descripcion", "Obtiene todos los usuarios (8 registros)"
                ),
                Map.of(
                    "url", "/usuarios/{id}",
                    "metodo", "GET", 
                    "descripcion", "Obtiene un usuario específico por ID",
                    "ejemplo", "/usuarios/1"
                ),
                Map.of(
                    "url", "/usuarios/activos",
                    "metodo", "GET",
                    "descripcion", "Obtiene solo los usuarios activos"
                )
            ),
            "ejemplos", List.of(
                "http://localhost:8080/usuarios",
                "http://localhost:8080/usuarios/1", 
                "http://localhost:8080/usuarios/activos"
            ),
            "estado", "Funcionando correctamente"
        );
        
        return ResponseEntity.ok(respuesta);
    }
}
