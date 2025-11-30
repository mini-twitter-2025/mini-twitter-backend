package ar.edu.unrn.chamorro.minitwitter.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_username", columnNames = "username")
})
public class Usuario {

    static final String ERROR_USERNAME_OBLIGATORIO = "El userName es obligatorio";
    static final String ERROR_USERNAME_LARGO = "El userName debe tener entre 5 y 25 caracteres";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 25)
    private String userName;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tweet> tweets = new ArrayList<>();

    protected Usuario() {
        // Requerido por JPA
    }

    public Usuario(String userName) {
        assertUserNameValido(userName);
        this.userName = userName.trim();
    }

    private void assertUserNameValido(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new RuntimeException(ERROR_USERNAME_OBLIGATORIO);
        }
        var trimmed = userName.trim();
        if (trimmed.length() < 5 || trimmed.length() > 25) {
            throw new RuntimeException(ERROR_USERNAME_LARGO);
        }
    }

    // Métodos de dominio (tell, don't ask)

    public Tweet twittear(String texto) {
        var tweet = new Tweet(this, texto);
        agregarTweet(tweet);
        return tweet;
    }

    public Tweet retwittear(Tweet tweetOriginal) {
        var retweet = Tweet.retweet(this, tweetOriginal);
        agregarTweet(retweet);
        return retweet;
    }

    void agregarTweet(Tweet tweet) {
        if (!tweets.contains(tweet)) {
            tweets.add(tweet);
        }
    }

    void eliminarTweet(Tweet tweet) {
        tweets.remove(tweet);
    }

    // Accesores no "getX": menos anémicos
    public Long id() {
        return id;
    }

    public String userName() {
        return userName;
    }

    public List<Tweet> tweets() {
        return Collections.unmodifiableList(tweets);
    }
}
