package ar.edu.unrn.chamorro.minitwitter.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tweets")
public class Tweet {

    static final String ERROR_TEXTO_OBLIGATORIO = "El texto del tweet es obligatorio";
    static final String ERROR_TEXTO_LARGO = "El texto del tweet debe tener entre 1 y 280 caracteres";
    static final String ERROR_RETWEET_MISMO_AUTOR = "No se puede hacer retweet de un tweet propio";
    static final String ERROR_TWEET_ORIGINAL_OBLIGATORIO = "El tweet original es obligatorio para un retweet";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario autor;

    @Column(nullable = false, length = 280)
    private String texto;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "tweet_origen_id")
    private Tweet tweetOrigen;

    protected Tweet() {
        // JPA
    }

    // Tweet original
    public Tweet(Usuario autor, String texto) {
        assertAutorValido(autor);
        assertTextoValido(texto);
        this.autor = autor;
        this.texto = texto.trim();
        this.fechaCreacion = LocalDateTime.now();
        this.tweetOrigen = null;

        autor.agregarTweet(this);
    }

    // Fábrica estática para retweets
    public static Tweet retweet(Usuario autorRetweet, Tweet tweetOriginal) {
        assertTweetOriginalNoNulo(tweetOriginal);
        assertNoEsMismoAutor(autorRetweet, tweetOriginal);

        var retweet = new Tweet();
        retweet.autor = autorRetweet;
        retweet.tweetOrigen = tweetOriginal;
        // No hay texto adicional: se copia el texto original
        retweet.texto = tweetOriginal.texto;
        retweet.fechaCreacion = LocalDateTime.now();

        autorRetweet.agregarTweet(retweet);
        return retweet;
    }

    private static void assertTweetOriginalNoNulo(Tweet tweetOriginal) {
        if (tweetOriginal == null) {
            throw new RuntimeException(ERROR_TWEET_ORIGINAL_OBLIGATORIO);
        }
    }

    private static void assertNoEsMismoAutor(Usuario autorRetweet, Tweet tweetOriginal) {
        if (tweetOriginal.autor == autorRetweet) {
            throw new RuntimeException(ERROR_RETWEET_MISMO_AUTOR);
        }
    }

    private void assertAutorValido(Usuario autor) {
        if (autor == null) {
            throw new RuntimeException("El autor es obligatorio");
        }
    }

    private void assertTextoValido(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new RuntimeException(ERROR_TEXTO_OBLIGATORIO);
        }
        var t = texto.trim();
        if (t.length() < 1 || t.length() > 280) {
            throw new RuntimeException(ERROR_TEXTO_LARGO);
        }
    }

    // Métodos de consulta estilo dominio (no getX)
    public Long id() {
        return id;
    }

    public String texto() {
        return texto;
    }

    public LocalDateTime fechaCreacion() {
        return fechaCreacion;
    }

    public Usuario autor() {
        return autor;
    }

    public boolean esRetweet() {
        return tweetOrigen != null;
    }

    public Tweet tweetOrigen() {
        return tweetOrigen;
    }
}
