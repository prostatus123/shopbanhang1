package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.KichThuoc;

public interface KichThuocDao {
	
	void insert(KichThuoc kichThuoc);

	void update(KichThuoc kichThuoc);

	void delete(KichThuoc kichThuoc);

	KichThuoc get(Long id);

	List<KichThuoc> search(String findName);

	List<KichThuoc> search(String findName, int offset, int maxPerPage);

	int count(String findName);
}
