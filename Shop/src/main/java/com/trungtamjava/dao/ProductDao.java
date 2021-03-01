package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Product;

public interface ProductDao {
	void insert(Product product);

	void update(Product product);

	void delete(Product product);

	Product get(Long id);

	List<Product> search(String findName, String categoryName, String thuongHieuName, String kichThuocName,
			String gioiTinhName, Long priceStart, Long priceEnd, int start, int length);

	List<Product> searchByCategory(String findName, String thuongHieuName, String kichThuocName, String gioiTinhName,
			Long priceStart, Long priceEnd, Long categoryId, int start, int length);


}
