package com.vm.springboot.ms.moviecatalog.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vm.springboot.ms.moviecatalog.model.CatalogItem;
import com.vm.springboot.ms.moviecatalog.model.Movie;
import com.vm.springboot.ms.moviecatalog.model.UserRating;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		//1. get all the rated movie ids freom the ratings service
		UserRating ratings = restTemplate.getForObject("http://localhost:8084/ratingsdata/users/shashank", UserRating.class);
		
				
		
		
		//for each movie id retrieved from the previous MS call send it to MovieInfo and get movie details
		return ratings.getUserRatings()
				.stream()                                                 //putting the list of items [emptying the bag on a] conveyor belt
				.map(rating -> {
					Movie movie = restTemplate.getForObject("http://localhost:8083/movies/"+rating.getMovieId(), Movie.class);
					return new CatalogItem(movie.getMovieName(), "description"+movie.getMovieId(), rating.getRating());              				   //converting each rating item moving on the conveyor belt TO  catalogitem
   
				})
				.collect(Collectors.toList());                                                                                     //after all the items have been converted put it back into the bag[list]
		
		//put them together and serve the user
		

	}

}