package ar.edu.unrn.chamorro.minitwitter.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ar.edu.unrn.chamorro.minitwitter.model.Tweet;
import ar.edu.unrn.chamorro.minitwitter.service.TweetService;
import ar.edu.unrn.chamorro.minitwitter.web.dto.CrearRetweetRequest;
import ar.edu.unrn.chamorro.minitwitter.web.dto.CrearTweetRequest;
import ar.edu.unrn.chamorro.minitwitter.web.dto.TweetMapper;
import ar.edu.unrn.chamorro.minitwitter.web.dto.TweetResponse;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public TweetResponse crear(@RequestBody CrearTweetRequest request) {
        Tweet tweet = tweetService.crearTweet(request.usuarioId(), request.texto());
        return TweetMapper.toResponse(tweet);
    }

    @PostMapping("/{id}/retweet")
    public TweetResponse retweet(@PathVariable Long id,
                                 @RequestBody CrearRetweetRequest request) {
        Tweet retweet = tweetService.crearRetweet(request.usuarioId(), id);
        return TweetMapper.toResponse(retweet);
    }

    // Home: tweets originales paginados de a 10
    @GetMapping("/home")
    public Page<TweetResponse> listarHome(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return tweetService
                .listarTweetsOriginales(PageRequest.of(page, size))
                .map(TweetMapper::toResponse);
    }

    // Tweets de un usuario (últimos 15, con "Mostrar más" por página)
    @GetMapping("/usuario/{usuarioId}")
    public Page<TweetResponse> listarPorUsuario(@PathVariable Long usuarioId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "15") int size) {
        return tweetService
                .listarTweetsPorUsuario(usuarioId, PageRequest.of(page, size))
                .map(TweetMapper::toResponse);
    }
}
