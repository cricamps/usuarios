package usuarios.usuarios.service;

import usuarios.usuarios.model.Usuario;
import usuarios.usuarios.repository.UsuarioRepository;
import usuarios.usuarios.exception.UsuarioNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Servicio de Usuarios")
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    public void setUp() {
        // Preparar datos de prueba
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Javiera Aguilar");
        usuario1.setEmail("javiera.aguilar@test.com");
        usuario1.setTelefono("+56987654321");
        usuario1.setRol("ADMIN");
        usuario1.setActivo(true);

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("José Rondón");
        usuario2.setEmail("jose.rondon@test.com");
        usuario2.setTelefono("+56912345678");
        usuario2.setRol("USER");
        usuario2.setActivo(true);
    }

    @Test
    @DisplayName("Test 1: Crear usuario correctamente")
    public void testCrearUsuario() {
        // Arrange - Configurar el comportamiento del mock
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // Act - Ejecutar el método a probar
        Usuario resultado = usuarioService.crearUsuario(usuario1);

        // Assert - Verificar resultados
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Javiera Aguilar");
        assertThat(resultado.getEmail()).isEqualTo("javiera.aguilar@test.com");
        assertThat(resultado.getRol()).isEqualTo("ADMIN");
        
        // Verificar que el repositorio fue llamado una vez
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test 2: Obtener todos los usuarios")
    public void testObtenerTodosLosUsuarios() {
        // Arrange
        List<Usuario> listaUsuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        // Act
        List<Usuario> resultado = usuarioService.obtenerTodosLosUsuarios();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).contains(usuario1, usuario2);
        
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test 3: Actualizar usuario existente")
    public void testActualizarUsuarioExistente() {
        // Arrange
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Javiera Aguilar Actualizada");
        usuarioActualizado.setEmail("javiera.nueva@test.com");
        usuarioActualizado.setTelefono("+56911111111");
        usuarioActualizado.setRol("SUPER_ADMIN");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // Act
        Usuario resultado = usuarioService.actualizarUsuario(1L, usuarioActualizado);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Javiera Aguilar Actualizada");
        
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test 4: Lanzar excepción al actualizar usuario no existente")
    public void testActualizarUsuarioNoExistente() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Usuario No Existe");

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.actualizarUsuario(999L, usuarioActualizado))
                .isInstanceOf(UsuarioNotFoundException.class)
                .hasMessageContaining("999");
        
        verify(usuarioRepository, times(1)).findById(999L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
