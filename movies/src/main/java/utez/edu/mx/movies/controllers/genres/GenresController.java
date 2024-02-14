package utez.edu.mx.movies.controllers.genres;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.movies.models.genres.Genres;
import utez.edu.mx.movies.models.movies.Movies;
import utez.edu.mx.movies.services.genres.GenresService;
import utez.edu.mx.movies.services.movies.MoviesService;
import utez.edu.mx.movies.utils.CustomResponse;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = {"*"})
public class GenresController {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private GenresService service;

    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Genres>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Genres>> getOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //insert
    @PostMapping("/insert")
    public ResponseEntity<CustomResponse<Genres>> insert(@RequestBody Genres genres) {
        CustomResponse<Genres> response = service.insert(genres);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    //update
    @PutMapping("/update")
    public ResponseEntity<CustomResponse<Genres>> updateGenre(@RequestBody Genres genres) {
        CustomResponse<Genres> response = service.update(genres);
        if (response.getError()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
