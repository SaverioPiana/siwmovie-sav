package it.uniroma3.siw.siwmoviesav.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String nationality;
    @OneToMany
    private List<Movie> directedMovies;
    public Artist(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name, artist.name) && Objects.equals(surname, artist.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
