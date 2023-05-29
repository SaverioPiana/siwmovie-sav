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
    private String url_of_picture;
    private LocalDate dateOfBirth;
    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovies;
    @ManyToMany(mappedBy = "actors")
    private List<Movie> starredMovies;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUrl_of_picture() {
        return url_of_picture;
    }

    public void setUrl_of_picture(String url_of_picture) {
        this.url_of_picture = url_of_picture;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(List<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public List<Movie> getStarredMovies() {
        return starredMovies;
    }

    public void setStarredMovies(List<Movie> starredMovies) {
        this.starredMovies = starredMovies;
    }
}
