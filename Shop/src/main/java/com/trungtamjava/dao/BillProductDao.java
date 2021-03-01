package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.BillProduct;

public interface BillProductDao {
	void insert(BillProduct billProduct);

	void update(BillProduct billProduct);

	void delete(BillProduct billProduct);

	BillProduct get(Long id);

	List<BillProduct> search(String findName,int start, int length);

	List<BillProduct> searchByBill(Long idBill, int start, int length);
}
