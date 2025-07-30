package com.movieflix.service;

import java.beans.JavaBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieflix.dto.MoviePageResponse;
import com.movieflix.dto.Moviedto;
import com.movieflix.entities.Movie;
import com.movieflix.exceptions.FileExistsException;
import com.movieflix.exceptions.MovieNotFoundException;
import com.movieflix.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
   private final MovieRepository movieRepository;
  
   public MovieServiceImpl(MovieRepository movieRepository) {
	   this.movieRepository=movieRepository;
   }
   @Autowired
   private FileService fileService;
   @Value ("${project.poster}")
	private String path;
   @Value ("${base.url}")
   private String baseUrl;
	
	@Override
	public Moviedto addMovie(Moviedto movieDto, MultipartFile file) throws IOException {
		//1.upload the file 
		if(Files.exists(Paths.get(path + File.separator+file.getOriginalFilename()))) {
		throw new FileExistsException("File already Exists!, please entor another filename");	
		}
		String uplodedFileName = fileService.uploadFile(path, file);
		
		//2.Set the value of field 'Poster' as filename;
	   movieDto.setPoster(uplodedFileName);
	   if (movieDto.getMovieId() != null) {
		    throw new IllegalArgumentException("Movie ID must be null when creating a new movie.");
		}
		//3.map DTO to movie object
	   Movie movie = new Movie(
	    		 movieDto.getMovieId(),
	    		 movieDto.getTitle(),
	    		 movieDto.getDirector(),
	    		 movieDto.getStudio(),
	    		 movieDto.getMovieCast(),
	    		 movieDto.getReleaseYear(),
	    		 movieDto.getPoster()
	    		 );
		// 4. save the movie object -> saved movie object
	    Movie savedMovie = movieRepository.save(movie);
	   
		// 5 . generate the posterUrl
	    
	    String posterUrl = baseUrl +"/file"+"/" + uplodedFileName;
	    
		// 6 . map movie object to DTO object and return it 
		  Moviedto response = new Moviedto(
				  savedMovie.getMovieId(),
				  savedMovie.getTitle(),
				  savedMovie.getDirector(),
				  savedMovie.getStudio(),
				  savedMovie.getMovieCast(),
				  savedMovie.getReleaseYear(),
				  savedMovie.getPoster(),
				  posterUrl
				  );
		return response;
	}

	@Override
	public Moviedto getmovie(Integer movieId) {
		//1 .check the data in DB and if exists,fetch the data of given ID 
		Movie movie = movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie Not Found"));
	    //2 generate PosterUrl
		String posterUrl = baseUrl +"/file/" + movie.getPoster();
		//3 map to MovieDto object and return it 
		Moviedto response = new Moviedto(
				movie.getMovieId(),
				  movie.getTitle(),
				  movie.getDirector(),
				  movie.getStudio(),
				  movie.getMovieCast(),
				  movie.getReleaseYear(),
				  movie.getPoster(),
				  posterUrl);
		
		
		return response;
	
	}

	@Override
	public List<Moviedto> getAllMovies() {
		 //1 . fetch all data from DB
		List<Movie> movies = movieRepository.findAll();
		//2 . iterate through the list , generate posterUrl for each movie//
		//and map to movie DTO 
		List<Moviedto> moviesDtos = new ArrayList<>();
		
		for(Movie movie:movies) {
			String posterUrl = baseUrl + "/file/"+movie.getPoster(); 
			Moviedto response = new Moviedto(
					movie.getMovieId(),
					  movie.getTitle(),
					  movie.getDirector(),
					  movie.getStudio(),
					  movie.getMovieCast(),
					  movie.getReleaseYear(),
					  movie.getPoster(),
					  posterUrl);
			moviesDtos.add(response);
		}
		return moviesDtos;
	}

	@Override
	public Moviedto updateMovie(Integer movieId, Moviedto movieDto, MultipartFile file) throws IOException {
		 //1 . check if movie object exists with given movie id 
		  
		 Movie mv = movieRepository.findById(movieId).orElseThrow(()-> new MovieNotFoundException("Movie not found"));
		
		//2 . if file is null , do nothing 
		// if file is not null , then delete existing file associated with record
		//and upload new file
		String fileName = mv.getPoster();
		if(file!=null) {
			Files.deleteIfExists(Paths.get(path + File.separator+fileName));
			 fileName = fileService.uploadFile(path, file);
		}
	   //3 set movieDto's poster value
		movieDto.setPoster(fileName);
		
		//4 map it to movie object
		Movie movie = new Movie(
				mv.getMovieId(),
				 movieDto.getTitle(),
				  movieDto.getDirector(),
				  movieDto.getStudio(),
				  movieDto.getMovieCast(),
				  movieDto.getReleaseYear(),
				  movieDto.getPoster()
				);
		//5 save the movie object -> return saved movie object
		Movie updatedMovie = movieRepository.save(movie);
		
		// 6 generate poster url
		
		String posterUrl = baseUrl +"/file/" + fileName;
		
		// map movie to Dt
		Moviedto response = new Moviedto(
				movie.getMovieId(),
				  movie.getTitle(),
				  movie.getDirector(),
				  movie.getStudio(),
				  movie.getMovieCast(),
				  movie.getReleaseYear(),
				  movie.getPoster(),
				  posterUrl);
		
		
		return response;
	}

	@Override
	public String deleteMovie(Integer movieId) throws IOException {
		Movie mv = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Not found with this particular id"));
		int id = mv.getMovieId();
		
		Files.deleteIfExists(Paths.get(path + File.separator+mv.getPoster()));
		 movieRepository.delete(mv); 
		
		return "deleted successfull" + id ;
	}

	@Override
	public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
	 Pageable pageable = PageRequest.of(pageNumber, pageSize);
	 Page<Movie> moviePages = movieRepository.findAll(pageable);
	 List<Movie> movies = moviePages.getContent();
	 List<Moviedto> movieDtos = new ArrayList<>();
	 
	 for(Movie movie:movies) {
			String posterUrl = baseUrl + "/file/"+movie.getPoster(); 
			Moviedto response = new Moviedto(
					movie.getMovieId(),
					  movie.getTitle(),
					  movie.getDirector(),
					  movie.getStudio(),
					  movie.getMovieCast(),
					  movie.getReleaseYear(),
					  movie.getPoster(),
					  posterUrl);
			movieDtos.add(response);
		}
	 
		return  new MoviePageResponse(movieDtos,pageNumber,pageSize,
				moviePages.getTotalElements(),moviePages.getTotalPages(),moviePages.isLast());
	}

	@Override
	public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy,
			String dir) {
		//Sorting
		Sort sort = dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		 Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		 Page<Movie> moviePages = movieRepository.findAll(pageable);
		 List<Movie> movies = moviePages.getContent();
		 List<Moviedto> movieDtos = new ArrayList<>();
		 
		 for(Movie movie:movies) {
				String posterUrl = baseUrl + "/file/"+movie.getPoster(); 
				Moviedto response = new Moviedto(
						movie.getMovieId(),
						  movie.getTitle(),
						  movie.getDirector(),
						  movie.getStudio(),
						  movie.getMovieCast(),
						  movie.getReleaseYear(),
						  movie.getPoster(),
						  posterUrl);
				movieDtos.add(response);
			}
		 
		return new MoviePageResponse(movieDtos,pageNumber,pageSize,
				moviePages.getTotalElements(),moviePages.getTotalPages(),moviePages.isLast());
	}
	

	
	

}
