package it.uniroma3.siw.siwmoviesav.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    @Min(1900)
    @Max(2023)
    private Integer year;
    private String picFilename;
    @ManyToOne
    private Artist director;
    @ManyToMany
    private Set<Artist> actors;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reviewedMovie")
    private Set<Review> reviews;
    public Movie(){
    actors = new HashSet<>();
    reviews = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(year, movie.year);
    }
    public String getPicPath(){
        if(picFilename != null) return "/upload/images/movie_pics/" + this.getId() + "/"
                +this.getPicFilename();
        return "/images/default_profile_pic.png";
    }
    public int getAvgReviews(){
        int sum=0;
        if (reviews.size()==0) return 0;
        for (Review r:
             reviews) {
            sum += r.getScore();
        }
        return sum/reviews.size();
    }
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, year);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getDirector() {
        return director;
    }

    public void setDirector(Artist director) {
        this.director = director;
    }

    public Set<Artist> getActors() {
        return actors;
    }

    public void setActors(Set<Artist> actors) {
        this.actors = actors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPicFilename() {
        return picFilename;
    }

    public void setPicFilename(String urlImage) {
        this.picFilename = urlImage;
    }
}
