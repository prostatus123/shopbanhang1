package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.GioiTinh;

public interface GioiTinhDao {

	void add(GioiTinh gioiTinh);

	void update(GioiTinh gioiTinh);

	void delete(Long id);

	GioiTinh getById(Long id);

	List<GioiTinh> Search(String name, int start, int length);

}
