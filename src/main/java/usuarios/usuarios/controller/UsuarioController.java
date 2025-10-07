package usuarios.usuarios.controller;

import usuarios.usuarios.model.Usuario;
import usuarios.usuarios.service.UsuarioService;
import usuarios.usuarios.exception.UsuarioNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /usuarios - Obtener todos los usuarios con HATEOAS
    @GetMapping
    public ResponseEntity<CollectionModel<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        
        // Agregar enlaces HATEOAS a cada usuario
        for (Usuario usuario : usuarios) {
            agregarEnlacesUsuario(usuario);
        }
        
        // Crear enlace para la colección
        Link selfLink = linkTo(methodOn(UsuarioController.class).obtenerTodosLosUsuarios()).withSelfRel();
        CollectionModel<Usuario> collectionModel = CollectionModel.of(usuarios, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    // GET /usuarios/{id} - Obtener usuario por ID con HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        
        if (usuario.isPresent()) {
            Usuario usuarioEncontrado = usuario.get();
            agregarEnlacesUsuario(usuarioEncontrado);
            return ResponseEntity.ok(usuarioEncontrado);
        } else {
            throw new UsuarioNotFoundException(id);
        }
    }

    // GET /usuarios/activos - Obtener usuarios activos con HATEOAS
    @GetMapping("/activos")
    public ResponseEntity<CollectionModel<Usuario>> obtenerUsuariosActivos() {
        List<Usuario> usuariosActivos = usuarioService.obtenerUsuariosActivos();
        
        // Agregar enlaces HATEOAS a cada usuario
        for (Usuario usuario : usuariosActivos) {
            agregarEnlacesUsuario(usuario);
        }
        
        Link selfLink = linkTo(methodOn(UsuarioController.class).obtenerUsuariosActivos()).withSelfRel();
        Link allLink = linkTo(methodOn(UsuarioController.class).obtenerTodosLosUsuarios()).withRel("todos-usuarios");
        
        CollectionModel<Usuario> collectionModel = CollectionModel.of(usuariosActivos, selfLink, allLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    // GET /usuarios/rol/{rol} - Obtener usuarios por rol con HATEOAS
    @GetMapping("/rol/{rol}")
    public ResponseEntity<CollectionModel<Usuario>> obtenerUsuariosPorRol(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol(rol);
        
        // Agregar enlaces HATEOAS a cada usuario
        for (Usuario usuario : usuarios) {
            agregarEnlacesUsuario(usuario);
        }
        
        Link selfLink = linkTo(methodOn(UsuarioController.class).obtenerUsuariosPorRol(rol)).withSelfRel();
        Link allLink = linkTo(methodOn(UsuarioController.class).obtenerTodosLosUsuarios()).withRel("todos-usuarios");
        
        CollectionModel<Usuario> collectionModel = CollectionModel.of(usuarios, selfLink, allLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    // POST /usuarios - Crear nuevo usuario con HATEOAS
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        agregarEnlacesUsuario(nuevoUsuario);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    // PUT /usuarios/{id} - Actualizar usuario con HATEOAS
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        agregarEnlacesUsuario(usuarioActualizado);
        
        return ResponseEntity.ok(usuarioActualizado);
    }

    // DELETE /usuarios/{id} - Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Método privado para agregar enlaces HATEOAS a un usuario
    private void agregarEnlacesUsuario(Usuario usuario) {
        // Enlace a sí mismo
        Link selfLink = linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel();
        usuario.add(selfLink);
        
        // Enlace a todos los usuarios
        Link allUsuariosLink = linkTo(methodOn(UsuarioController.class).obtenerTodosLosUsuarios()).withRel("todos-usuarios");
        usuario.add(allUsuariosLink);
        
        // Enlace para actualizar
        Link updateLink = linkTo(methodOn(UsuarioController.class).actualizarUsuario(usuario.getId(), usuario)).withRel("actualizar");
        usuario.add(updateLink);
        
        // Enlace para eliminar
        Link deleteLink = linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getId())).withRel("eliminar");
        usuario.add(deleteLink);
        
        // Si el usuario tiene un rol, agregar enlace a usuarios del mismo rol
        if (usuario.getRol() != null && !usuario.getRol().isEmpty()) {
            Link rolLink = linkTo(methodOn(UsuarioController.class).obtenerUsuariosPorRol(usuario.getRol())).withRel("usuarios-mismo-rol");
            usuario.add(rolLink);
        }
    }

    // Manejador de excepción para UsuarioNotFoundException
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
