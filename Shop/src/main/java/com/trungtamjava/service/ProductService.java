package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.ProductDTO;

public interface ProductService {
	void insert(ProductDTO productDTO);

	void update(ProductDTO productDTO);

	void delete(Long id);

	ProductDTO get(Long id);

	List<ProductDTO> search(String findName, String categoryName, String thuongHieuName, String kichThuocName,
			String gioiTinhName, Long priceStart, Long priceEnd, int start, int length);

	List<ProductDTO> searchByCategory(String findName, String thuongHieuName, String kichThuocName,
			String gioiTinhName, Long priceStart, Long priceEnd, Long categoryId, int start, int length);

}
