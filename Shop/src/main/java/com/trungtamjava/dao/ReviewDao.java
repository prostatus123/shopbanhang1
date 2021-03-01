package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Review;
import com.trungtamjava.model.SearchReviewDTO;

public interface ReviewDao {

	void add(Review review);

	void delete(Review review);

	void edit(Review review);

	Review getById(Long id);

	List<Review> find(Long productId);

	Long count(SearchReviewDTO searchReviewDTO);

	Long coutTotal(SearchReviewDTO searchReviewDTO);
}
