package ar.edu.unrn.chamorro.minitwitter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unrn.chamorro.minitwitter.model.Tweet;
import ar.edu.unrn.chamorro.minitwitter.model.Usuario;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Page<Tweet> findByTweetOrigenIsNullOrderByFechaCreacionDesc(Pageable pageable);

    Page<Tweet> findByAutorOrderByFechaCreacionDesc(Usuario autor, Pageable pageable);
}
