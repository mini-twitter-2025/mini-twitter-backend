package ar.edu.unrn.chamorro.minitwitter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unrn.chamorro.minitwitter.model.Tweet;
import ar.edu.unrn.chamorro.minitwitter.repository.TweetRepository;
import ar.edu.unrn.chamorro.minitwitter.repository.UsuarioRepository;

@Service
@Transactional
public class TweetService {

    static final String ERROR_TWEET_NO_ENCONTRADO = "Tweet no encontrado";

    private final TweetRepository tweetRepository;
    private final UsuarioRepository usuarioRepository;

    public TweetService(TweetRepository tweetRepository,
                        UsuarioRepository usuarioRepository) {
        this.tweetRepository = tweetRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Tweet crearTweet(Long usuarioId, String texto) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException(UsuarioService.ERROR_USUARIO_NO_ENCONTRADO));

        var tweet = usuario.twittear(texto);
        return tweetRepository.save(tweet);
    }

    public Tweet crearRetweet(Long usuarioId, Long tweetOriginalId) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException(UsuarioService.ERROR_USUARIO_NO_ENCONTRADO));

        var tweetOriginal = tweetRepository.findById(tweetOriginalId)
                .orElseThrow(() -> new RuntimeException(ERROR_TWEET_NO_ENCONTRADO));

        var retweet = usuario.retwittear(tweetOriginal);
        return tweetRepository.save(retweet);
    }

    @Transactional(readOnly = true)
    public Page<Tweet> listarTweetsOriginales(Pageable pageable) {
        return tweetRepository.findByTweetOrigenIsNullOrderByFechaCreacionDesc(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Tweet> listarTweetsPorUsuario(Long usuarioId, Pageable pageable) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException(UsuarioService.ERROR_USUARIO_NO_ENCONTRADO));
        return tweetRepository.findByAutorOrderByFechaCreacionDesc(usuario, pageable);
    }
}
