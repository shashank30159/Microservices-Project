package com.vm.springboot.ms.moviecatalog.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vm.springboot.ms.moviecatalog.model.CatalogItem;
import com.vm.springboot.ms.moviecatalog.model.Rating;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		//1. get all the rated movie ids freom the ratings service
		List<Rating> ratingsBag = Arrays.asList(  //mocking call to ratings ms
				new Rating("movieOne",1),
				new Rating("movieTwo",2),
				new Rating("movieThree",3)
				);
		
		
		//for each movie id retrieved from the previous MS call send it to MovieInfo and get movie details
		return ratingsBag.stream()                                                  //putting the list of items [emptying the bag on a] conveyor belt
				.map(rating -> new CatalogItem(rating.getMovieId(), "description of "+ rating.getMovieId(), rating.getRating()))   //converting each rating item moving on the conveyor belt TO  catalogitem
				.collect(Collectors.toList());                                                                                     //after all the items have been converted put it back into the bag[list]
		
		//put them together and serve the user
		
		/*
		 * return Arrays.asList( new CatalogItem("firstmovie", "first movie desc", 1),
		 * new CatalogItem("secondmovie", "second movie desc", 2)
		 * 
		 * );
		 */

	}

}