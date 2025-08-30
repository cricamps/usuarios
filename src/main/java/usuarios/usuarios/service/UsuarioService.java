package usuarios.usuarios.service;

import usuarios.usuarios.model.Usuario;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UsuarioService {
    
    private List<Usuario> usuarios;

    public UsuarioService() {
        inicializarDatos();
    }

    private void inicializarDatos() {
        // Inicializar usuarios
        usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1L, "Juan Pérez", "juan.perez@email.com", "987654321", "ADMIN"));
        usuarios.add(new Usuario(2L, "María González", "maria.gonzalez@email.com", "987654322", "CLIENTE"));
        usuarios.add(new Usuario(3L, "Carlos Rodríguez", "carlos.rodriguez@email.com", "987654323", "VENDEDOR"));
        usuarios.add(new Usuario(4L, "Ana López", "ana.lopez@email.com", "987654324", "CLIENTE"));
        usuarios.add(new Usuario(5L, "Pedro Martínez", "pedro.martinez@email.com", "987654325", "CLIENTE"));
        usuarios.add(new Usuario(6L, "Laura Fernández", "laura.fernandez@email.com", "987654326", "VENDEDOR"));
        usuarios.add(new Usuario(7L, "Miguel Torres", "miguel.torres@email.com", "987654327", "CLIENTE"));
        usuarios.add(new Usuario(8L, "Carmen Silva", "carmen.silva@email.com", "987654328", "ADMIN"));

        // Configurar algunas fechas diferentes y estados
        usuarios.get(1).setFechaRegistro(LocalDateTime.now().minusDays(30));
        usuarios.get(2).setFechaRegistro(LocalDateTime.now().minusDays(15));
        usuarios.get(7).setActivo(false); // Un usuario inactivo para demostrar el filtro
    }

    // Métodos para usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> obtenerUsuariosActivos() {
        return usuarios.stream()
                .filter(Usuario::getActivo)
                .toList();
    }
}
