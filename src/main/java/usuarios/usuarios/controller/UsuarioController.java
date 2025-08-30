package usuarios.usuarios.controller;

import usuarios.usuarios.model.Usuario;
import usuarios.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /usuarios - mostrar todos los usuarios
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        
        Map<String, Object> respuesta = Map.of(
            "mensaje", "Lista de usuarios obtenida exitosamente",
            "total", usuarios.size(),
            "datos", usuarios
        );
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /usuarios/{id} - mostrar usuario específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> respuesta = Map.of(
            "mensaje", "Usuario encontrado",
            "datos", usuario
        );
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /usuarios/activos - mostrar solo usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<Map<String, Object>> obtenerUsuariosActivos() {
        List<Usuario> usuariosActivos = usuarioService.obtenerUsuariosActivos();
        
        Map<String, Object> respuesta = Map.of(
            "mensaje", "Lista de usuarios activos",
            "total", usuariosActivos.size(),
            "datos", usuariosActivos
        );
        
        return ResponseEntity.ok(respuesta);
    }
}
