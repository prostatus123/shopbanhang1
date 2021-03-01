package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.entity.Product;
import com.trungtamjava.dao.CommentDao;
import com.trungtamjava.entity.Comment;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.CommentDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.CommentService;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Override
	public void insert(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setComment(commentDTO.getComment());
		Product product= new Product();
		product.setId(commentDTO.getProductDTO().getId());
		comment.setProduct(product);
		
		User user = new User();
		user.setId(commentDTO.getUserDTO().getId());
		comment.setUser(user);
		comment.setCommentDate(new Date());
		commentDao.insert(comment);

	}

	@Override
	public void update(CommentDTO commentDTO) {
		Comment comment = commentDao.get(commentDTO.getId());
		if (comment != null) {
			comment.setComment(commentDTO.getComment());
			commentDao.update(comment);
		}

	}

	@Override
	public void delete(Long id) {
		Comment comment = commentDao.get(id);
		if (comment != null) {
			commentDao.delete(id);
		}
	}

	@Override
	public CommentDTO get(Long id) {
		return null;
	}

	@Override
	public List<CommentDTO> searchByProduct(Long id) {
		List<Comment> listComments= commentDao.searchByProduct(id);
		List<CommentDTO> commentDTOs= new ArrayList<>();
		for(Comment comment:listComments) {
			CommentDTO commentDTO= new CommentDTO();
			commentDTO.setComment(comment.getComment());
			commentDTO.setCommentDate(String.valueOf(comment.getCommentDate()));
			
			ProductDTO productDTO= new ProductDTO();
			productDTO.setId(comment.getProduct().getId());
			commentDTO.setProductDTO(productDTO);
			
			UserDTO userDTO = new UserDTO();
			userDTO.setName(comment.getUser().getName());
			commentDTO.setUserDTO(userDTO);
			
			commentDTOs.add(commentDTO);
		}
		return commentDTOs;
	}
	}

