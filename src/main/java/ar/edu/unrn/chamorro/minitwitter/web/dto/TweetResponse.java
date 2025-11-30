package ar.edu.unrn.chamorro.minitwitter.web.dto;

import java.time.LocalDateTime;

public record TweetResponse(
        Long id,
        String autorUserName,
        String texto,
        LocalDateTime fechaCreacion,
        boolean esRetweet,
        Long tweetOrigenId,
        String tweetOrigenAutor,
        LocalDateTime fechaTweetOrigen
) {
}
