package ar.edu.unrn.chamorro.minitwitter.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Crear usuario con username válido crea la instancia correctamente")
    void crearUsuario_valido_instanciaCorrecta() {

        var username = "usuario1";

        var usuario = new Usuario(username);

        assertEquals(username, usuario.userName(),
                "El userName del usuario no coincide con el valor esperado");
    }

    @Test
    @DisplayName("Crear usuario con username muy corto lanza excepción")
    void crearUsuario_usernameCorto_lanzaExcepcion() {

        var username = "abc";

        var ex = assertThrows(RuntimeException.class,
                () -> new Usuario(username),
                "Se esperaba una RuntimeException por username inválido");

        assertEquals(Usuario.ERROR_USERNAME_LARGO, ex.getMessage());
    }

    @Test
    @DisplayName("Crear usuario con username muy largo lanza excepción")
    void crearUsuario_usernameLargo_lanzaExcepcion() {

        var username = "este_es_un_username_demasiado_largo_para_ser_valido";

        var ex = assertThrows(RuntimeException.class,
                () -> new Usuario(username),
                "Se esperaba una RuntimeException por username inválido");

        assertEquals(Usuario.ERROR_USERNAME_LARGO, ex.getMessage());
    }

    @Test
    @DisplayName("Crear usuario con username nulo lanza excepción")
    void crearUsuario_usernameNulo_lanzaExcepcion() {

        var ex = assertThrows(RuntimeException.class,
                () -> new Usuario(null),
                "Se esperaba una RuntimeException por username nulo");

        assertEquals(Usuario.ERROR_USERNAME_OBLIGATORIO, ex.getMessage());
    }

    @Test
    @DisplayName("Crear usuario con username vacío lanza excepción")
    void crearUsuario_usernameVacio_lanzaExcepcion() {

        var username = "   ";

        var ex = assertThrows(RuntimeException.class,
                () -> new Usuario(username),
                "Se esperaba una RuntimeException por username vacío");

        assertEquals(Usuario.ERROR_USERNAME_OBLIGATORIO, ex.getMessage());
    }

    @Test
    @DisplayName("Usuario puede twittear correctamente")
    void usuario_twittear_creaTweet() {

        var usuario = new Usuario("usuario1");
        var texto = "Mi primer tweet";

        var tweet = usuario.twittear(texto);

        assertNotNull(tweet, "El tweet no debería ser nulo");
        assertEquals(texto, tweet.texto(), "El texto del tweet no coincide");
        assertEquals(usuario, tweet.autor(), "El autor del tweet no coincide");
        assertTrue(usuario.tweets().contains(tweet), "El tweet debería estar en la lista de tweets del usuario");
    }

    @Test
    @DisplayName("Usuario puede retwittear tweet de otro usuario")
    void usuario_retwittear_creaRetweet() {

        var usuario1 = new Usuario("usuario1");
        var usuario2 = new Usuario("usuario2");
        var tweetOriginal = usuario1.twittear("Tweet original");

        var retweet = usuario2.retwittear(tweetOriginal);

        assertNotNull(retweet, "El retweet no debería ser nulo");
        assertTrue(retweet.esRetweet(), "Debería ser un retweet");
        assertEquals(tweetOriginal, retweet.tweetOrigen(), "El tweet origen no coincide");
        assertEquals(usuario2, retweet.autor(), "El autor del retweet no coincide");
        assertTrue(usuario2.tweets().contains(retweet), "El retweet debería estar en la lista de tweets del usuario");
    }
}
