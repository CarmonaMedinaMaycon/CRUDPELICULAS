package utez.edu.mx.movies.models.genres;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.movies.models.movies.Movies;

public interface GenresRepository extends JpaRepository<Genres, Long> {

}
