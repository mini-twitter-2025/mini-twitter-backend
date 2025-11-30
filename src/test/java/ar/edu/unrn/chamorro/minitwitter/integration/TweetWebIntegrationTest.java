package ar.edu.unrn.chamorro.minitwitter.integration;

import ar.edu.unrn.chamorro.minitwitter.model.Usuario;
import ar.edu.unrn.chamorro.minitwitter.repository.UsuarioRepository;
import ar.edu.unrn.chamorro.minitwitter.service.TweetService;
import ar.edu.unrn.chamorro.minitwitter.web.dto.TweetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

class TweetWebIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Crear tweet y listar en home funciona correctamente")
    void crearTweet_listarHome_funcionaCorrectamente() {

        var usuario = new Usuario("testuser");
        usuarioRepository.save(usuario);

        var tweet = tweetService.crearTweet(usuario.id(), "Mi primer tweet de integración");
        var homePage = tweetService.listarTweetsOriginales(PageRequest.of(0, 10));

        assertNotNull(tweet, "El tweet no debería ser nulo");
        assertEquals("Mi primer tweet de integración", tweet.texto());
        assertEquals(1, homePage.getTotalElements(), "Debería haber 1 tweet en el home");
        assertEquals(tweet.id(), homePage.getContent().get(0).id());
    }

    @Test
    @DisplayName("Crear retweet no aparece en home pero sí en tweets del usuario")
    void crearRetweet_noApareceEnHome_apareceEnUsuario() {

        var usuario1 = new Usuario("usuario1");
        var usuario2 = new Usuario("usuario2");
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        var tweetOriginal = tweetService.crearTweet(usuario1.id(), "Tweet original");

        var retweet = tweetService.crearRetweet(usuario2.id(), tweetOriginal.id());
        var homePage = tweetService.listarTweetsOriginales(PageRequest.of(0, 10));
        var usuario2Tweets = tweetService.listarTweetsPorUsuario(usuario2.id(), PageRequest.of(0, 15));

        assertTrue(retweet.esRetweet(), "Debería ser un retweet");
        assertEquals(1, homePage.getTotalElements(), "Solo debería haber 1 tweet original en el home");
        assertEquals(tweetOriginal.id(), homePage.getContent().get(0).id(), "El tweet en home debería ser el original");
        assertEquals(1, usuario2Tweets.getTotalElements(), "El usuario2 debería tener 1 tweet (el retweet)");
        assertEquals(retweet.id(), usuario2Tweets.getContent().get(0).id());
    }

    @Test
    @DisplayName("Paginación funciona correctamente")
    void paginacion_funcionaCorrectamente() {

        var usuario = new Usuario("testuser");
        usuarioRepository.save(usuario);

        for (int i = 1; i <= 15; i++) {
            tweetService.crearTweet(usuario.id(), "Tweet número " + i);
        }

        var pagina1 = tweetService.listarTweetsOriginales(PageRequest.of(0, 10));
        var pagina2 = tweetService.listarTweetsOriginales(PageRequest.of(1, 10));

        assertEquals(15, pagina1.getTotalElements(), "Debería haber 15 tweets en total");
        assertEquals(10, pagina1.getContent().size(), "La primera página debería tener 10 tweets");
        assertEquals(5, pagina2.getContent().size(), "La segunda página debería tener 5 tweets");
        assertEquals(2, pagina1.getTotalPages(), "Deberían haber 2 páginas en total");
    }
}
