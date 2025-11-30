package ar.edu.unrn.chamorro.minitwitter.web.dto;

import ar.edu.unrn.chamorro.minitwitter.model.Tweet;

public class TweetMapper {

    public static TweetResponse toResponse(Tweet tweet) {
        if (tweet.esRetweet()) {
            var origen = tweet.tweetOrigen();
            return new TweetResponse(
                    tweet.id(),
                    tweet.autor().userName(),
                    tweet.texto(),
                    tweet.fechaCreacion(),
                    true,
                    origen.id(),
                    origen.autor().userName(),
                    origen.fechaCreacion()
            );
        }
        return new TweetResponse(
                tweet.id(),
                tweet.autor().userName(),
                tweet.texto(),
                tweet.fechaCreacion(),
                false,
                null,
                null,
                null
        );
    }
}
