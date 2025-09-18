package usuarios.usuarios.controller;

import usuarios.usuarios.model.Usuario;
import usuarios.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /usuarios - Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de usuarios obtenida exitosamente");
        respuesta.put("total", usuarios.size());
        respuesta.put("datos", usuarios);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /usuarios/{id} - Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        
        Map<String, Object> respuesta = new HashMap<>();
        if (usuario.isPresent()) {
            respuesta.put("mensaje", "Usuario encontrado");
            respuesta.put("datos", usuario.get());
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("mensaje", "Usuario no encontrado");
            respuesta.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // GET /usuarios/activos - Obtener usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<Map<String, Object>> obtenerUsuariosActivos() {
        List<Usuario> usuariosActivos = usuarioService.obtenerUsuariosActivos();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de usuarios activos");
        respuesta.put("total", usuariosActivos.size());
        respuesta.put("datos", usuariosActivos);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /usuarios/rol/{rol} - Obtener usuarios por rol
    @GetMapping("/rol/{rol}")
    public ResponseEntity<Map<String, Object>> obtenerUsuariosPorRol(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol(rol);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuarios con rol: " + rol);
        respuesta.put("total", usuarios.size());
        respuesta.put("datos", usuarios);
        
        return ResponseEntity.ok(respuesta);
    }

    // POST /usuarios - Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario creado exitosamente");
        respuesta.put("datos", nuevoUsuario);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // PUT /usuarios/{id} - Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario actualizado exitosamente");
            respuesta.put("datos", usuarioActualizado);
            
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Error al actualizar usuario");
            respuesta.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // DELETE /usuarios/{id} - Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario eliminado exitosamente");
        respuesta.put("id", id);
        
        return ResponseEntity.ok(respuesta);
    }
}
