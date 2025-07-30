package com.movieflix.dto;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Moviedto {
	 public Moviedto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Moviedto(Integer movieId, @NotBlank(message = "Please provide title") String title,
			@NotBlank(message = "Please provide movie director!") String director,
			@NotBlank(message = "Please provide movie studio!") String studio, Set<String> movieCast,
			Integer releaseYear, @NotBlank(message = "Please provide movie poster!") String poster,
			@NotBlank(message = "Please provide movie poster url!") String posterUrl) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.director = director;
		this.studio = studio;
		this.movieCast = movieCast;
		this.releaseYear = releaseYear;
		this.poster = poster;
		this.posterUrl = posterUrl;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
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

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	
	private Integer movieId;
	   
	   @NotBlank(message = "Please provide title")
	   private String title;
	   
	   
	   @NotBlank(message = "Please provide movie director!")
	   private String director;
	   
	   
	   @NotBlank(message = "Please provide movie studio!")
	   private String studio;
	   
	   
	   
	   private Set<String> movieCast;
	   
	   
	   
	   private Integer releaseYear;
	   
	
	   @NotBlank(message = "Please provide movie poster!")
	   private String poster;
	   
	   @NotBlank(message = "Please provide movie poster url!")
	   private String posterUrl;
	   
}
