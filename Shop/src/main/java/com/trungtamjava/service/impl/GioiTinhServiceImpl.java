package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trungtamjava.dao.GioiTinhDao;
import com.trungtamjava.entity.GioiTinh;
import com.trungtamjava.model.GioiTinhDTO;
import com.trungtamjava.service.GioiTinhService;

@Service
@Transactional
public class GioiTinhServiceImpl implements GioiTinhService {

	@Autowired
	private GioiTinhDao gioiTinhDao;

	@Override
	public void add(GioiTinhDTO gioiTinhDTO) {
		GioiTinh gioiTinh = new GioiTinh();
		gioiTinh.setName(gioiTinhDTO.getName());
		gioiTinhDao.add(gioiTinh);
		gioiTinhDTO.setId(gioiTinh.getId());
	}

	@Override
	public void update(GioiTinhDTO gioiTinhDTO) {
		GioiTinh gioiTinh = gioiTinhDao.getById(gioiTinhDTO.getId());
		if (gioiTinh != null) {
			gioiTinh.setName(gioiTinhDTO.getName());

			gioiTinhDao.update(gioiTinh);
		}
	}

	@Override
	public void delete(Long id) {
		GioiTinh gioiTinh = gioiTinhDao.getById(id);
		if (gioiTinh != null) {
			gioiTinhDao.delete(id);
		}
	}

	@Override
	public GioiTinhDTO getById(Long id) {
		GioiTinh gioiTinh = gioiTinhDao.getById(id);
		GioiTinhDTO gioiTinhDTO = new GioiTinhDTO();
		gioiTinhDTO.setId(gioiTinh.getId());
		gioiTinhDTO.setName(gioiTinh.getName());
		return gioiTinhDTO;
	}

	@Override
	public List<GioiTinhDTO> search(String name, int start, int length) {
		List<GioiTinh> gioiTinhs = gioiTinhDao.Search(name, start, length);
		List<GioiTinhDTO> gioiTinhDTOs = new ArrayList<GioiTinhDTO>();
		for (GioiTinh gioiTinh : gioiTinhs) {
			GioiTinhDTO gioiTinhDTO = new GioiTinhDTO();
			gioiTinhDTO.setId(gioiTinh.getId());
			gioiTinhDTO.setName(gioiTinh.getName());
			gioiTinhDTOs.add(gioiTinhDTO);
		}
		return gioiTinhDTOs  ;
	}

}
