package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trungtamjava.entity.Product;
import com.trungtamjava.dao.ProductDao;
import com.trungtamjava.entity.Category;
import com.trungtamjava.entity.GioiTinh;
import com.trungtamjava.entity.KichThuoc;
import com.trungtamjava.entity.ThuongHieu;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.GioiTinhDTO;
import com.trungtamjava.model.KichThuocDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.ThuongHieuDTO;
import com.trungtamjava.service.ProductService;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao;

	@Override
	public void insert(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		product.setImage(productDTO.getImage());
		product.setSoLuong(productDTO.getSoLuong());

		Category category = new Category();
		category.setId(productDTO.getCategory().getId());
		category.setName(productDTO.getCategory().getName());
		product.setCategory(category);

		ThuongHieu thuongHieu = new ThuongHieu();
		thuongHieu.setId(productDTO.getThuongHieuDTO().getId());
		thuongHieu.setName(productDTO.getThuongHieuDTO().getName());
		product.setThuongHieu(thuongHieu);

		KichThuoc kichThuoc = new KichThuoc();
		kichThuoc.setId(productDTO.getKichThuocDTO().getId());
		kichThuoc.setName(productDTO.getKichThuocDTO().getName());
		product.setKichThuoc(kichThuoc);

		GioiTinh gioiTinh = new GioiTinh();
		gioiTinh.setId(productDTO.getGioiTinhDTO().getId());
		gioiTinh.setName(productDTO.getGioiTinhDTO().getName());
		product.setGioiTinh(gioiTinh);

		productDao.insert(product);
	}

	@Override
	public void update(ProductDTO productDTO) {
		Product product = productDao.get(productDTO.getId());
		if (product != null) {
			product.setId(productDTO.getId());
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());
			product.setDescription(productDTO.getDescription());
			product.setImage(productDTO.getImage());
			product.setSoLuong(productDTO.getSoLuong());

			Category category = new Category();
			category.setId(productDTO.getCategory().getId());
			category.setName(productDTO.getCategory().getName());
			product.setCategory(category);

			ThuongHieu thuongHieu = new ThuongHieu();
			thuongHieu.setId(productDTO.getThuongHieuDTO().getId());
			thuongHieu.setName(productDTO.getThuongHieuDTO().getName());
			product.setThuongHieu(thuongHieu);

			KichThuoc kichThuoc = new KichThuoc();
			kichThuoc.setId(productDTO.getKichThuocDTO().getId());
			kichThuoc.setName(productDTO.getKichThuocDTO().getName());
			product.setKichThuoc(kichThuoc);

			GioiTinh gioiTinh = new GioiTinh();
			gioiTinh.setId(productDTO.getGioiTinhDTO().getId());
			gioiTinh.setName(productDTO.getGioiTinhDTO().getName());
			product.setGioiTinh(gioiTinh);

			productDao.update(product);
		}
	}

	@Override
	public void delete(Long id) {
		Product product = productDao.get(id);
		if (product != null) {
			productDao.delete(product);
		}
	}

	@Override
	public List<ProductDTO> search(String findName, String categoryName, String thuongHieuName, String kichThuocName,
			String gioiTinh, Long priceStart, Long priceEnd, int start, int length) {

		List<Product> listProducts = productDao.search(findName, categoryName, thuongHieuName, kichThuocName, gioiTinh,
				priceStart, priceEnd, start, length);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(product.getId());
			productDTO.setName(product.getName());
			productDTO.setPrice(product.getPrice());
			productDTO.setImage(product.getImage());
			productDTO.setDescription(product.getDescription());
			productDTO.setSoLuong(product.getSoLuong());

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());
			productDTO.setCategory(categoryDTO);

			ThuongHieuDTO thuongHieu = new ThuongHieuDTO();
			thuongHieu.setId(product.getThuongHieu().getId());
			thuongHieu.setName(product.getThuongHieu().getName());
			productDTO.setThuongHieuDTO(thuongHieu);

			KichThuocDTO kichThuocDTO = new KichThuocDTO();
			kichThuocDTO.setId(product.getKichThuoc().getId());
			kichThuocDTO.setName(product.getKichThuoc().getName());
			productDTO.setKichThuocDTO(kichThuocDTO);

			GioiTinhDTO gioiTinhDTO = new GioiTinhDTO();
			gioiTinhDTO.setId(product.getGioiTinh().getId());
			gioiTinhDTO.setName(product.getGioiTinh().getName());
			productDTO.setGioiTinhDTO(gioiTinhDTO);

			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	@Override
	public ProductDTO get(Long id) {
		Product product = productDao.get(id);
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setImage(product.getImage());
		dto.setPrice(product.getPrice());
		dto.setSoLuong(product.getSoLuong());

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(product.getCategory().getId());
		categoryDTO.setName(product.getCategory().getName());
		dto.setCategory(categoryDTO);

		ThuongHieuDTO thuongHieuDTO = new ThuongHieuDTO();
		thuongHieuDTO.setId(product.getThuongHieu().getId());
		thuongHieuDTO.setName(product.getThuongHieu().getName());
		dto.setThuongHieuDTO(thuongHieuDTO);

		KichThuocDTO kichThuocDTO = new KichThuocDTO();
		kichThuocDTO.setId(product.getKichThuoc().getId());
		kichThuocDTO.setName(product.getKichThuoc().getName());
		dto.setKichThuocDTO(kichThuocDTO);

		GioiTinhDTO gioiTinhDTO = new GioiTinhDTO();
		gioiTinhDTO.setId(product.getGioiTinh().getId());
		gioiTinhDTO.setName(product.getGioiTinh().getName());
		dto.setGioiTinhDTO(gioiTinhDTO);

		return dto;
	}

	@Override
	public List<ProductDTO> searchByCategory(String findName, String thuongHieuName, String kichThuocName,
			String gioiTinhName, Long priceStart, Long priceEnd, Long categoryId, int start, int length) {

		List<Product> listProducts = productDao.searchByCategory(findName, thuongHieuName, kichThuocName, gioiTinhName,
				priceStart, priceEnd, categoryId, start, length);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			ProductDTO ProductDTO = new ProductDTO();
			ProductDTO.setId(product.getId());
			ProductDTO.setName(product.getName());
			ProductDTO.setPrice(product.getPrice());
			ProductDTO.setImage(product.getImage());
			ProductDTO.setDescription(product.getDescription());

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());
			ProductDTO.setCategory(categoryDTO);

			ThuongHieuDTO thuongHieu = new ThuongHieuDTO();
			thuongHieu.setId(product.getThuongHieu().getId());
			thuongHieu.setName(product.getThuongHieu().getName());
			ProductDTO.setThuongHieuDTO(thuongHieu);

			KichThuocDTO kichThuocDTO = new KichThuocDTO();
			kichThuocDTO.setId(product.getKichThuoc().getId());
			kichThuocDTO.setName(product.getKichThuoc().getName());
			ProductDTO.setKichThuocDTO(kichThuocDTO);

			GioiTinhDTO gioiTinhDTO = new GioiTinhDTO();
			gioiTinhDTO.setId(product.getGioiTinh().getId());
			gioiTinhDTO.setName(product.getGioiTinh().getName());
			ProductDTO.setGioiTinhDTO(gioiTinhDTO);

			productDTOs.add(ProductDTO);
		}
		return productDTOs;
	}

}
