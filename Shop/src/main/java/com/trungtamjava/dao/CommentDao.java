package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Comment;

public interface CommentDao {

	void insert(Comment comment);

	void update(Comment comment);

	void delete(Long id);

	Comment get(Long id);

	List<Comment> searchByProduct(Long id);

}
