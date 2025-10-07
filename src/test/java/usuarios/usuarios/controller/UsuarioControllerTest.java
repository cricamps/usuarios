package usuarios.usuarios.controller;

import usuarios.usuarios.model.Usuario;
import usuarios.usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UsuarioController.class)
@DisplayName("Tests del Controlador de Usuarios")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    public void setUp() {
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("María Elvisa");
        usuario1.setEmail("maria.elvisa@test.com");
        usuario1.setTelefono("+56987654321");
        usuario1.setRol("ADMIN");
        usuario1.setActivo(true);

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Carlos Pérez");
        usuario2.setEmail("carlos.perez@test.com");
        usuario2.setTelefono("+56912345678");
        usuario2.setRol("USER");
        usuario2.setActivo(true);
    }

    @Test
    @DisplayName("Test 1: GET /usuarios - Obtener todos los usuarios")
    public void testObtenerTodosLosUsuarios() throws Exception {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(usuarios);

        // Act & Assert
        mockMvc.perform(get("/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", containsString("/usuarios")));

        verify(usuarioService, times(1)).obtenerTodosLosUsuarios();
    }

    @Test
    @DisplayName("Test 2: GET /usuarios/{id} - Obtener usuario por ID existente")
    public void testObtenerUsuarioPorId() throws Exception {
        // Arrange
        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(Optional.of(usuario1));

        // Act & Assert
        mockMvc.perform(get("/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("María Elvisa")))
                .andExpect(jsonPath("$.email", is("maria.elvisa@test.com")))
                .andExpect(jsonPath("$._links.self.href", containsString("/usuarios/1")))
                .andExpect(jsonPath("$._links.todos-usuarios.href", containsString("/usuarios")));

        verify(usuarioService, times(1)).buscarUsuarioPorId(1L);
    }

    @Test
    @DisplayName("Test 3: POST /usuarios - Crear usuario con datos válidos")
    public void testCrearUsuario() throws Exception {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Diego Fernández");
        nuevoUsuario.setEmail("diego.fernandez@test.com");
        nuevoUsuario.setRol("USER");

        Usuario usuarioCreado = new Usuario();
        usuarioCreado.setId(3L);
        usuarioCreado.setNombre("Diego Fernández");
        usuarioCreado.setEmail("diego.fernandez@test.com");
        usuarioCreado.setRol("USER");
        usuarioCreado.setActivo(true);

        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuarioCreado);

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nombre", is("Diego Fernández")))
                .andExpect(jsonPath("$._links.self.href", containsString("/usuarios/3")));

        verify(usuarioService, times(1)).crearUsuario(any(Usuario.class));
    }

    @Test
    @DisplayName("Test 4: PUT /usuarios/{id} - Actualizar usuario existente")
    public void testActualizarUsuario() throws Exception {
        // Arrange
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("María Elvisa Actualizada");
        usuarioActualizado.setEmail("maria.elvisa.nueva@test.com");
        usuarioActualizado.setRol("SUPER_ADMIN");

        Usuario usuarioResultado = new Usuario();
        usuarioResultado.setId(1L);
        usuarioResultado.setNombre("María Elvisa Actualizada");
        usuarioResultado.setEmail("maria.elvisa.nueva@test.com");
        usuarioResultado.setRol("SUPER_ADMIN");
        usuarioResultado.setActivo(true);

        when(usuarioService.actualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(usuarioResultado);

        // Act & Assert
        mockMvc.perform(put("/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("María Elvisa Actualizada")))
                .andExpect(jsonPath("$.rol", is("SUPER_ADMIN")))
                .andExpect(jsonPath("$._links.self.href", containsString("/usuarios/1")));

        verify(usuarioService, times(1)).actualizarUsuario(eq(1L), any(Usuario.class));
    }

    @Test
    @DisplayName("Test 5: DELETE /usuarios/{id} - Eliminar usuario")
    public void testEliminarUsuario() throws Exception {
        // Arrange
        doNothing().when(usuarioService).eliminarUsuario(1L);

        // Act & Assert
        mockMvc.perform(delete("/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }

    @Test
    @DisplayName("Test 6: GET /usuarios/{id} - Error 404 cuando usuario no existe")
    public void testObtenerUsuarioNoExistente() throws Exception {
        // Arrange
        when(usuarioService.buscarUsuarioPorId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/usuarios/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(usuarioService, times(1)).buscarUsuarioPorId(999L);
    }
}
