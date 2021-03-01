package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.BillDTO;

public interface BillService {
	void insert(BillDTO billDTO);

	void update(BillDTO billDTO);

	void delete(Long id);

	BillDTO get(Long id);

	List<BillDTO> search(String findName, int start, int length);
	
	List<BillDTO> searchByBuyerId(Long buyerId, int start, int length);
}
