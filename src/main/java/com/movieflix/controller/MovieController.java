package com.movieflix.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dto.MoviePageResponse;
import com.movieflix.dto.Moviedto;
import com.movieflix.service.FileService;
import com.movieflix.service.FileServiceImpl;
import com.movieflix.service.MovieService;
import com.movieflix.utils.AppConstants;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService=movieService;
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/add-movie")
	public ResponseEntity<Moviedto> addMovieHandler(@RequestPart("file") MultipartFile file
			  ,@RequestPart("movieDto") String movieDto) throws IOException{
		Moviedto movieDtobj = convertToMovieDto(movieDto);
		return new ResponseEntity<>(movieService.addMovie(movieDtobj, file),HttpStatus.CREATED);
	}
	// WhenEver you are dealing with file object as well as JSON object we need to have this 
	private Moviedto convertToMovieDto(String movieDtoObj) throws JsonProcessingException, JsonProcessingException {
		Moviedto movieDto;
		ObjectMapper objectMapper = new ObjectMapper();
		movieDto =objectMapper.readValue(movieDtoObj, Moviedto.class);
		return movieDto;
	}
	@GetMapping("/{movieId}")
	public ResponseEntity<Moviedto> getMovieById(@PathVariable("movieId") Integer movieId){
		Moviedto movieDto = movieService.getmovie(movieId);
		return new ResponseEntity<>(movieDto,HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<List<Moviedto>> getAllMoviesHandler(){
		return ResponseEntity.ok(movieService.getAllMovies());
	}
	@PutMapping("/update/{movieId}")
	public ResponseEntity<Moviedto> updateHandler(@PathVariable("movieId") Integer movieId
			                       ,@RequestPart("file") MultipartFile file, @RequestPart("movieDtoObj") String movieDtoObj) throws IOException{
	   if(file.isEmpty()) file = null;
	   Moviedto movieDto =convertToMovieDto(movieDtoObj);
		
		return ResponseEntity.ok(movieService.updateMovie(movieId, movieDto, file));
	}
	
	@DeleteMapping("/delete/{movieId}")
	public ResponseEntity<String> deleteHandler(@PathVariable ("movieId") Integer movieId) throws IOException{
		
		return ResponseEntity.ok(movieService.deleteMovie(movieId));
		
	}
	
	@GetMapping("/allMoviePage")
	public ResponseEntity<MoviePageResponse> getMoviesWithPagination(
	    @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
	    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
	) {
	    return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize));
	}
	@GetMapping("/allMoviePageSort")
	public ResponseEntity<MoviePageResponse> getMoviesWithPaginationAndSorting(
			@RequestParam(value = "pageNumber", defaultValue =AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "dir",defaultValue =AppConstants.SORT_DIR,required = false)String dir){
		
		
	
	return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize,sortBy,dir));
	}
	
}
