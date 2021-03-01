package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.ThuongHieu;

public interface ThuongHieuDao {

	void insert(ThuongHieu thuongHieu);

	void update(ThuongHieu thuongHieu);

	void delete(ThuongHieu thuongHieu);

	ThuongHieu get(long id);

	List<ThuongHieu> search(String findName);

	List<ThuongHieu> search(String findName, int offset, int maxPerPage);

	int count(String findName);
}
