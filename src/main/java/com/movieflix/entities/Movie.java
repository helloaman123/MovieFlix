package com.movieflix.entities;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
 // we take reference type not primitive type so that we can provide validations//
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Movie(Integer movieId, @NotBlank(message = "Please provide title") String title,
			@NotBlank(message = "Please provide movie director!") String director,
			@NotBlank(message = "Please provide movie studio!") String studio, Set<String> movieCast,
			@NotBlank(message = "Please provide movie realease year!") Integer releaseYear,
			@NotBlank(message = "Please provide movie poster!") String poster) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.director = director;
		this.studio = studio;
		this.movieCast = movieCast;
		this.releaseYear = releaseYear;
		this.poster = poster;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public Set<String> getMovieCast() {
		return movieCast;
	}

	public void setMovieCast(Set<String> movieCast) {
		this.movieCast = movieCast;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer movieId;
   @Column(nullable = false , length = 200)
   @NotBlank(message = "Please provide title")
   private String title;
   
   @Column(nullable = false)
   @NotBlank(message = "Please provide movie director!")
   private String director;
   
   @Column(nullable = false)
   @NotBlank(message = "Please provide movie studio!")
   private String studio;
   
   @ElementCollection
   @CollectionTable(name = "movie_cast")
   private Set<String> movieCast;
   
   @Column(nullable = false)
   @NotNull(message = "Please provide movie realease year!")
   private Integer releaseYear;
   
   @Column(nullable = false)
   @NotBlank(message = "Please provide movie poster!")
   private String poster;
   
   
}
