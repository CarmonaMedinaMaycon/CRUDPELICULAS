package utez.edu.mx.movies.models.genres;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.movies.models.movies.Movies;

import java.util.List;

@Entity
@Table(name = "generos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;


    @OneToMany(mappedBy="genres")
    @JsonIgnore
    private List<Movies> movies;
}
