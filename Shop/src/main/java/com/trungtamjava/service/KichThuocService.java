package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.KichThuocDTO;

public interface KichThuocService {
	void insert(KichThuocDTO kichThuocDTO);

	void update(KichThuocDTO kichThuocDTO);

	void delete(Long id);

	List<KichThuocDTO> search(String name, int start, int length);

	KichThuocDTO get(Long id);

}
