package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.ReviewDTO;
import com.trungtamjava.model.SearchReviewDTO;

public interface ReviewService {
	
	void add(ReviewDTO reviewDTO);

	void delete(Long id);

	void edit(ReviewDTO reviewDTO);

	ReviewDTO getById(Long id);

	List<ReviewDTO> find(Long id);

	Long count(SearchReviewDTO searchReviewDTO);

	Long coutTotal(SearchReviewDTO searchReviewDTO);

}
