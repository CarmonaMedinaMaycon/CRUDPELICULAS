package utez.edu.mx.movies.controllers.movies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.movies.models.genres.Genres;
import utez.edu.mx.movies.models.movies.Movies;
import utez.edu.mx.movies.services.movies.MoviesService;
import utez.edu.mx.movies.utils.CustomResponse;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = {"*"})
public class MoviesController {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MoviesService service;

    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Movies>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Movies>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //insert
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Movies>> insert(@RequestBody Movies movies) {
        CustomResponse<Movies> response = service.insert(movies);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    //update
    @PutMapping("/update")
    public ResponseEntity<CustomResponse<Movies>> update(@RequestBody Movies movies) {
        CustomResponse<Movies> response = service.update(movies);
        if (response.getError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteMovieById(@PathVariable Long id) {
        CustomResponse<Boolean> response = service.deleteById(id);
        if (response.getError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @GetMapping("/search")
    public ResponseEntity<CustomResponse<List<Movies>>> searchMoviesByName(@RequestParam String term) {
        CustomResponse<List<Movies>> responseByName = service.findMoviesByTermInName(term);
        CustomResponse<List<Movies>> responseByDirector = service.findMoviesByDirector(term);

        if (responseByName.getData() != null && !responseByName.getData().isEmpty()) {
            return ResponseEntity.ok(responseByName);
        } else if (responseByDirector.getData() != null && !responseByDirector.getData().isEmpty()) {
            return ResponseEntity.ok(responseByDirector);
        } else {
            CustomResponse<List<Movies>> combinedResponse = new CustomResponse<>();
            combinedResponse.setError(true);
            combinedResponse.setMessage("No se encontraron películas para el término o director proporcionado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(combinedResponse);
        }
    }

    @GetMapping("/searchD")
    public ResponseEntity<CustomResponse<List<Movies>>> searchMoviesByDirector(@RequestParam String director) {
        if (director == null || director.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CustomResponse<>(null, true, 400, "Debe proporcionar el nombre del director"));
        }

        CustomResponse<List<Movies>> response = service.findMoviesByDirector(director);

        return ResponseEntity
                .status(response.getError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(response);
    }


}
