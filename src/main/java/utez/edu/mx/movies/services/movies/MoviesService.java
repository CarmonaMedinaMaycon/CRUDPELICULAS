package utez.edu.mx.movies.services.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.movies.models.genres.Genres;
import utez.edu.mx.movies.models.movies.Movies;
import utez.edu.mx.movies.models.movies.MoviesRepository;
import utez.edu.mx.movies.utils.CustomResponse;

import java.util.List;
import java.util.Optional;



@Service
public class MoviesService  {


    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MoviesRepository repository;


    //insert
    public CustomResponse<Movies> insert(Movies movies) {
        return new CustomResponse<>(
                this.repository.saveAndFlush(movies),
                false,
                200,
                "Película registrada"
        );
    }



    //get all
    public CustomResponse<List<Movies>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    //get one
    public CustomResponse<Movies> getOne(Long id) {
        Optional<Movies> movies = repository.findById(id);
        if (movies.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La película no existe."
            );
        }
        return new CustomResponse<>(
                movies.get(),
                false,
                200,
                "Ok"
        );
    }

    //update
    public CustomResponse<Movies> update(Movies movies) {
        if (!this.repository.existsById(movies.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La película no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(movies),
                false,
                200,
                "Película actualizada"
        );
    }


    //delete by id

    public CustomResponse<Boolean> deleteById(Long id) {
        if (!this.repository.existsById(id)) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La película no existe"
            );
        }

        this.repository.deleteById(id);

        return new CustomResponse<>(
                true,
                false,
                200,
                "Película eliminada exitosamente"
        );
    }




    public CustomResponse findMoviesByTermInName(String searchTerm) {
        List<Movies> movies = repository.findByNameContaining(searchTerm);
        if (movies.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La o las peliculas no existen"
            );
        } else {
            return new CustomResponse<>(
                    true,
                    false,
                    200,
                    "Pelicula o peliculas encontradas"
            );
        }
    }





}
