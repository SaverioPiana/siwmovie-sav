package it.uniroma3.siw.siwmoviesav.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String url_of_picture;
    private LocalDate dateOfBirth;
    private String picFilename;
    @OneToMany(mappedBy = "director")
    private Set<Movie> directedMovies;
    @ManyToMany(mappedBy = "actors")
    private List<Movie> starredMovies;
    public Artist(){
        directedMovies = new HashSet<>();
        starredMovies = new ArrayList<>();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name, artist.name) && Objects.equals(surname, artist.surname);
    }
    public String getPicPath(){
        if(picFilename != null) return "/upload/images/artist_pics/" + this.getId() + "/"
                +this.getPicFilename();
        return "/images/default_profile_pic.png";
    }

    public String getPicFilename() {
        return picFilename;
    }

    public void setPicFilename(String picFilename) {
        this.picFilename = picFilename;
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
    public String getDateOfBirthFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateOfBirth.format(formatter);
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(Set<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public List<Movie> getStarredMovies() {
        return starredMovies;
    }

    public void setStarredMovies(List<Movie> starredMovies) {
        this.starredMovies = starredMovies;
    }
}
