package com.trungtamjava.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.trungtamjava.dao.GioiTinhDao;
import com.trungtamjava.entity.GioiTinh;

@Transactional
@Repository
public class GioiTinhDaoImpl implements GioiTinhDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(GioiTinh gioiTinh) {
		entityManager.persist(gioiTinh);
	}

	@Override
	public void update(GioiTinh gioiTinh) {
		entityManager.merge(gioiTinh);
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(getById(id));
	}

	@Override
	public GioiTinh getById(Long id) {
		return entityManager.find(GioiTinh.class, id);
	}

	@Override
	public List<GioiTinh> Search(String name, int start, int length) {
		String jql = "select gt from GioiTinh gt where gt.name like :name";
		return entityManager.createQuery(jql, GioiTinh.class).setParameter("name", "%" + name + "%")
				.setFirstResult(start).setMaxResults(length).getResultList();
	}
}
