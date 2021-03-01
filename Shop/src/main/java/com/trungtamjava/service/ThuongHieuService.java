package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.ThuongHieuDTO;

public interface ThuongHieuService {

	void insert(ThuongHieuDTO categoryDTO);

	void update(ThuongHieuDTO categoryDTO);

	void delete(long id);

	ThuongHieuDTO get(long id);

	List<ThuongHieuDTO> search(String name, int start, int length);
}
