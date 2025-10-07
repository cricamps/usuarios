package usuarios.usuarios;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests Basicos de la Aplicacion")
class UsuariosApplicationTests {

	@Test
	@DisplayName("Test: Verificar que la clase principal existe")
	void applicationClassExists() {
		assertThat(UsuariosApplication.class).isNotNull();
	}

	@Test
	@DisplayName("Test: Verificar que el metodo main existe")
	void mainMethodExists() throws NoSuchMethodException {
		assertThat(UsuariosApplication.class.getMethod("main", String[].class)).isNotNull();
	}
}
