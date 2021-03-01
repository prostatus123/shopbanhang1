package com.trungtamjava.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.model.ThuongHieuDTO;
import com.trungtamjava.service.ThuongHieuService;

@Controller
public class AdminThuongHieuController {

	@Autowired
	private ThuongHieuService thuongHieuService;
	
	@GetMapping(value = "/admin/thuongHieu/search")
	public String searchThuongHieu(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search(keyword, 0, page * 10);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "admin/thuonghieu/thuong-hieu-list";
	}
	
	@GetMapping(value = "/admin/thuongHieu/add")
	public String addCategory() {
		return "admin/thuonghieu/add-thuong-hieu";
	}

	@PostMapping(value = "/admin/thuongHieu/add")
	public String addCategoryPost(@ModelAttribute ThuongHieuDTO thuongHieuDTO) {
		thuongHieuService.insert(thuongHieuDTO);

		return "redirect:/admin/thuongHieu/search";
	}

	@GetMapping(value = "/admin/thuongHieu/update")
	public String updateCategoryGet(Model model, @RequestParam(value = "id") int id) {
		ThuongHieuDTO thuongHieuDTO = thuongHieuService.get(id);
		model.addAttribute("thuonghieu", thuongHieuDTO);

		return "admin/thuonghieu/update-thuong-hieu";
	}

	@PostMapping(value = "/admin/thuongHieu/update")
	public String updateCategoryPost(@ModelAttribute ThuongHieuDTO thuongHieuDTO) {
		thuongHieuService.update(thuongHieuDTO);
		return "redirect:/admin/thuongHieu/search";
	}

	@GetMapping(value = "/admin/thuongHieu/delete")
	public String deleteCategory(@RequestParam(value = "id") int id) {
		thuongHieuService.delete(id);
		return "redirect:/admin/thuongHieu/search";
	}
	
}
