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

import com.trungtamjava.model.KichThuocDTO;
import com.trungtamjava.service.KichThuocService;

@Controller
public class AdminKichThuocController {

	@Autowired
	private KichThuocService kichThuocService;

	@GetMapping(value = "/admin/kichThuoc/search")
	public String searchKichThuoc(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<KichThuocDTO> kichThuocList = kichThuocService.search(keyword, 0, page * 10);
		request.setAttribute("kichThuocList", kichThuocList);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "admin/kichthuoc/kich-thuoc-list";
	}

	@GetMapping(value = "/admin/kichThuoc/add")
	public String addKichThuoc() {
		return "admin/kichthuoc/add-kich-thuoc";
	}

	@PostMapping(value = "/admin/kichThuoc/add")
	public String addKichThuocPost(@ModelAttribute KichThuocDTO kichThuocDTO) {
		kichThuocService.insert(kichThuocDTO);

		return "redirect:/admin/kichThuoc/search";
	}

	@GetMapping(value = "/admin/kichThuoc/update")
	public String updateKichThuocGet(Model model, @RequestParam(value = "id") Long id) {
		KichThuocDTO kichThuocDTO = kichThuocService.get(id);
		model.addAttribute("kichThuocDTO", kichThuocDTO);

		return "admin/kichthuoc/update-kich-thuoc";
	}

	@PostMapping(value = "/admin/kichThuoc/update")
	public String updateKichThuocPost(@ModelAttribute KichThuocDTO kichThuocDTO) {
		kichThuocService.update(kichThuocDTO);
		return "redirect:/admin/kichThuoc/search";
	}

	@GetMapping(value = "/admin/kichThuoc/delete")
	public String deleteKichThuoc(@RequestParam(value = "id") Long id) {
		kichThuocService.delete(id);
		return "redirect:/admin/kichThuoc/search";
	}

}