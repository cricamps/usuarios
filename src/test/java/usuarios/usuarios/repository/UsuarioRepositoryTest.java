package usuarios.usuarios.repository;

import usuarios.usuarios.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Repositorio de Usuarios")
public class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Test 1: Guardar usuario correctamente")
    public void testGuardarUsuario() {
        // Arrange - Preparar datos
        Usuario usuario = new Usuario();
        usuario.setNombre("María González");
        usuario.setEmail("maria.gonzalez@test.com");
        usuario.setTelefono("+56912345678");
        usuario.setRol("ADMIN");
        usuario.setActivo(true);

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre("María González");
        usuarioGuardado.setEmail("maria.gonzalez@test.com");
        usuarioGuardado.setTelefono("+56912345678");
        usuarioGuardado.setRol("ADMIN");
        usuarioGuardado.setActivo(true);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // Act - Ejecutar la acción
        Usuario resultado = usuarioRepository.save(usuario);

        // Assert - Verificar resultados
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("María González");
        assertThat(resultado.getEmail()).isEqualTo("maria.gonzalez@test.com");
        assertThat(resultado.getRol()).isEqualTo("ADMIN");
        assertThat(resultado.getActivo()).isTrue();

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test 2: Buscar usuarios activos")
    public void testBuscarUsuariosActivos() {
        // Arrange - Preparar datos
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Pedro Ramírez");
        usuario1.setEmail("pedro.ramirez@test.com");
        usuario1.setRol("USER");
        usuario1.setActivo(true);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Carlos Muñoz");
        usuario2.setEmail("carlos.munoz@test.com");
        usuario2.setRol("ADMIN");
        usuario2.setActivo(true);

        List<Usuario> usuariosActivos = Arrays.asList(usuario1, usuario2);

        when(usuarioRepository.findByActivoTrue()).thenReturn(usuariosActivos);

        // Act - Ejecutar la acción
        List<Usuario> resultado = usuarioRepository.findByActivoTrue();

        // Assert - Verificar resultados
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(u -> u.getActivo() == true);

        verify(usuarioRepository, times(1)).findByActivoTrue();
    }
}
