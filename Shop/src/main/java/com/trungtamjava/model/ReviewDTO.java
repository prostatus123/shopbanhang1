package com.trungtamjava.model;

public class ReviewDTO {

	private Long id;
	private int starNumber;
	private String reviewDate;
	private UserDTO userDTO;

	private ProductDTO productDTO;

	public ReviewDTO() {
		super();
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStarNumber() {
		return starNumber;
	}

	public void setStarNumber(int starNumber) {
		this.starNumber = starNumber;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

}
