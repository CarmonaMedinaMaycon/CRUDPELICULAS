package utez.edu.mx.movies.services.genres;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.ietf.jgss.GSSName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import utez.edu.mx.movies.models.genres.Genres;
import utez.edu.mx.movies.models.genres.GenresRepository;
import utez.edu.mx.movies.models.movies.Movies;
import utez.edu.mx.movies.models.movies.MoviesRepository;
import utez.edu.mx.movies.utils.CustomResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



@Service

public class GenresService {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private GenresRepository repository;


    //insert
    public CustomResponse<Genres> insert(Genres genres) {
        return new CustomResponse<>(
                this.repository.saveAndFlush(genres),
                false,
                200,
                "Género registrado"
        );
    }

    //get all
    public CustomResponse<List<Genres>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    //get one
    public CustomResponse<Genres> getOne(Long id) {
        Optional<Genres> genres = repository.findById(id);
        if (genres.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El género no existe."
            );
        }
        return new CustomResponse<>(
                genres.get(),
                false,
                200,
                "Ok"
        );
    }

    //update
    public CustomResponse<Genres> update(Genres genres) {
        if (!this.repository.existsById(genres.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El género no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(genres),
                false,
                200,
                "Categoria Actualizada!"
        );
    }

}
