package utez.edu.mx.movies.models.movies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movies, Long> {
    List<Movies> findByNameContaining(String searchTerm);
    List<Movies> findByDirector(String director);

}
