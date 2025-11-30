package ar.edu.unrn.chamorro.minitwitter.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TweetTest {

    @Test
    @DisplayName("Crear tweet con texto válido genera instancia correcta")
    void crearTweet_valido_instanciaCorrecta() {

        var usuario = new Usuario("usuario1");
        var texto = "Hola mundo";

        var tweet = new Tweet(usuario, texto);

        assertEquals(texto, tweet.texto(), "El texto del tweet no coincide");
        assertEquals(usuario, tweet.autor(), "El autor del tweet no coincide");
        assertFalse(tweet.esRetweet(), "Un tweet nuevo no debería ser retweet");
        assertNotNull(tweet.fechaCreacion(), "La fecha de creación no debería ser nula");
    }

    @Test
    @DisplayName("Crear tweet con texto nulo lanza excepción")
    void crearTweet_textoNulo_lanzaExcepcion() {
        var usuario = new Usuario("usuario1");

        var ex = assertThrows(RuntimeException.class,
                () -> new Tweet(usuario, null),
                "Se esperaba una RuntimeException por texto nulo");

        assertEquals(Tweet.ERROR_TEXTO_OBLIGATORIO, ex.getMessage());
    }

    @Test
    @DisplayName("Crear tweet con texto vacío lanza excepción")
    void crearTweet_textoVacio_lanzaExcepcion() {

        var usuario = new Usuario("usuario1");
        var texto = "   ";

        var ex = assertThrows(RuntimeException.class,
                () -> new Tweet(usuario, texto),
                "Se esperaba una RuntimeException por texto vacío");

        assertEquals(Tweet.ERROR_TEXTO_OBLIGATORIO, ex.getMessage());
    }

    @Test
    @DisplayName("Crear tweet con texto muy largo lanza excepción")
    void crearTweet_textoLargo_lanzaExcepcion() {

        var usuario = new Usuario("usuario1");
        var texto = "a".repeat(281);

        var ex = assertThrows(RuntimeException.class,
                () -> new Tweet(usuario, texto),
                "Se esperaba una RuntimeException por texto muy largo");

        assertEquals(Tweet.ERROR_TEXTO_LARGO, ex.getMessage());
    }

    @Test
    @DisplayName("Crear retweet de tweet propio lanza excepción")
    void retweet_mismoAutor_lanzaExcepcion() {

        var usuario = new Usuario("usuario1");
        var tweetOriginal = new Tweet(usuario, "Texto original");

        var ex = assertThrows(RuntimeException.class,
                () -> Tweet.retweet(usuario, tweetOriginal),
                "Se esperaba excepción al retwittear un tweet propio");

        assertEquals(Tweet.ERROR_RETWEET_MISMO_AUTOR, ex.getMessage());
    }

    @Test
    @DisplayName("Crear retweet de tweet de otro usuario funciona correctamente")
    void retweet_otroAutor_creaRetweet() {

        var usuario1 = new Usuario("usuario1");
        var usuario2 = new Usuario("usuario2");
        var tweetOriginal = new Tweet(usuario1, "Tweet original");

        var retweet = Tweet.retweet(usuario2, tweetOriginal);

        assertNotNull(retweet, "El retweet no debería ser nulo");
        assertTrue(retweet.esRetweet(), "Debería ser un retweet");
        assertEquals(tweetOriginal, retweet.tweetOrigen(), "El tweet origen no coincide");
        assertEquals(usuario2, retweet.autor(), "El autor del retweet no coincide");
        assertEquals(tweetOriginal.texto(), retweet.texto(), "El texto debería ser el mismo que el original");
    }

    @Test
    @DisplayName("Crear retweet con tweet original nulo lanza excepción")
    void retweet_tweetOriginalNulo_lanzaExcepcion() {

        var usuario = new Usuario("usuario1");

        var ex = assertThrows(RuntimeException.class,
                () -> Tweet.retweet(usuario, null),
                "Se esperaba excepción al retwittear un tweet nulo");

        assertEquals(Tweet.ERROR_TWEET_ORIGINAL_OBLIGATORIO, ex.getMessage());
    }
}
