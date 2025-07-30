package com.movieflix.dto;

import java.util.List;

public record MoviePageResponse(List<Moviedto> movieDtos,
		Integer pageNumber,Integer pageSize,int totalElements,int totalPages
		,boolean isLast) {

	public MoviePageResponse(
	        List<Moviedto> movieDtos2,
	        Integer pageNumber2,
	        Integer pageSize2,
	        long totalElements2,
	        int totalPages2,
	        boolean last
	) {
	    this(movieDtos2, pageNumber2, pageSize2, (int) totalElements2, totalPages2, last);
	}
	

}
