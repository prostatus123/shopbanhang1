package com.trungtamjava.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.GioiTinhDTO;
import com.trungtamjava.model.KichThuocDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.ThuongHieuDTO;
import com.trungtamjava.service.CategoryService;
import com.trungtamjava.service.GioiTinhService;
import com.trungtamjava.service.KichThuocService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.ThuongHieuService;

@Controller
public class AdminProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ThuongHieuService thuongHieuService;
	@Autowired
	private KichThuocService kichThuocService;
	@Autowired
	private GioiTinhService gioiTinhService;

	@GetMapping(value = "/admin/product/add")
	public String AdminProductAddGet(HttpServletRequest request, Model model) {
		model.addAttribute("product", new ProductDTO());
		List<CategoryDTO> list = categoryService.search("", 0, 100);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 100);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 100);
		List<GioiTinhDTO> gioiTinhDTOs = gioiTinhService.search("", 0, 100);

		request.setAttribute("gioiTinhList", gioiTinhDTOs);
		request.setAttribute("categoryList", list);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("kichThuocList", kichThuocDTOs);

		return "admin/product/add";
	}

	@PostMapping(value = "/admin/product/add")
	public String AdminProductAddPost(@ModelAttribute(name = "product") ProductDTO product,
			@RequestParam(name = "imageFile") MultipartFile imagefile) {
		String originalFilename = imagefile.getOriginalFilename();
		int lastIndex = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(lastIndex);

		String avatarFilename = System.currentTimeMillis() + ext;
		File newfile = new File("C:\\test\\" + avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(imagefile.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		product.setImage(avatarFilename);
		productService.insert(product);
		return "redirect:/admin/product/search";
	}

	@GetMapping(value = "/admin/product/update")
	public String AdminProductUpdate(HttpServletRequest request, Model model, Long id) {
		ProductDTO product = productService.get(id);
		List<CategoryDTO> list = categoryService.search("", 0, 100);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 100);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 100);
		List<GioiTinhDTO> gioiTinhDTOs = gioiTinhService.search("", 0, 100);

		model.addAttribute("product", product);
		request.setAttribute("gioiTinhList", gioiTinhDTOs);
		request.setAttribute("categoryList", list);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("kichThuocList", kichThuocDTOs);
		return "admin/product/update";
	}

	@PostMapping(value = "/admin/product/update")
	public String AdminProductUpdate(@ModelAttribute(name = "product") ProductDTO product,
			@RequestParam(name = "imageFile") MultipartFile imagefile) {
		String originalFilename = imagefile.getOriginalFilename();
		int lastIndex = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(lastIndex);

		String avatarFilename = System.currentTimeMillis() + ext;
		File newfile = new File("C:\\test\\" + avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(imagefile.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		product.setImage(avatarFilename);
		productService.update(product);
		return "redirect:/admin/product/search";
	}

	@GetMapping(value = "/admin/product/delete")
	public String AdminDeleteProduct(@RequestParam(name = "id") Long id) {
		productService.delete(id);
		return "redirect:/admin/product/search";
	}

	@GetMapping(value = "/admin/product/search")
	public String AdminProductSearch(HttpServletRequest request) {

		String thuongHieuName = request.getParameter("thuongHieuName") == null ? ""
				: request.getParameter("thuongHieuName");

		String gioiTinhName = request.getParameter("gioiTinhName") == null ? "" : request.getParameter("gioiTinhName");

		String categoryName = request.getParameter("categoryName") == null ? "" : request.getParameter("categoryName");

		String kichThuocName = request.getParameter("kichThuocName") == null ? ""
				: request.getParameter("kichThuocName");

		String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

		Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
				: Long.valueOf(request.getParameter("priceStart"));

		Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
				: Long.valueOf(request.getParameter("priceEnd"));

		List<ProductDTO> listPro = productService.search(keyword,categoryName, thuongHieuName, kichThuocName,gioiTinhName,
			priceStart, priceEnd, 0, page * 10);
		List<CategoryDTO> categoryDTOs = categoryService.search("", 0, page * 10);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, page * 10);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, page * 10);
		List<GioiTinhDTO> gioiTinhDTOs = gioiTinhService.search("", 0, page * 10);

		request.setAttribute("gioiTinhList", gioiTinhDTOs);
		request.setAttribute("kichThuocList", kichThuocDTOs);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("productList", listPro);
		request.setAttribute("categoryList", categoryDTOs);

		request.setAttribute("gioiTinhName", gioiTinhName);
		request.setAttribute("kichThuocName", kichThuocName);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("thuongHieuName", thuongHieuName);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "admin/product/search";
	}
}
