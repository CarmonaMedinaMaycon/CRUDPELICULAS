package utez.edu.mx.movies.models.movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movies, Long> {
    List<Movies> findByNameContaining(String searchTerm);
    List<Movies> findByDirector(String director);

    @Query("SELECT m FROM Movies m WHERE m.genres.name = :genreName")
    List<Movies> findByGenreName(String genreName);


    List<Movies> findByOrderByReleaseDateDesc();

}
