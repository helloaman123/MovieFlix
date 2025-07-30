package com.movieflix.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.movieflix.dto.MoviePageResponse;
import com.movieflix.dto.Moviedto;

public interface MovieService {
Moviedto addMovie(Moviedto movieDto,MultipartFile file) throws IOException;

Moviedto getmovie(Integer movieId);

 List<Moviedto> getAllMovies();
 
 Moviedto updateMovie(Integer movieId , Moviedto movieDto,MultipartFile file) throws IOException;

 String deleteMovie(Integer movieId) throws IOException;
 
 MoviePageResponse getAllMoviesWithPagination(Integer pageNumber,Integer pageSize);

 MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber,Integer pageSize,String sortBy,String dir);
  
}
