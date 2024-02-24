package utez.edu.mx.movies.models.movies;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.movies.models.genres.Genres;

import java.util.Date;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
    public class Movies {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @Column(nullable = false)
        private String name;
    
        @Column(nullable = false)
        private String director;

        @Column(nullable = false)
        @Temporal(TemporalType.DATE) // solo guarda año/mes/dia yyyy-mm-dd
        private Date releaseDate; //
    
    
        @Column(nullable = false)
        private String description;
    
    
        @ManyToOne
        @JoinColumn(name = "genres_id", nullable = false)
        private Genres genres;
    
    
    
    }
