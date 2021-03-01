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

import com.trungtamjava.model.GioiTinhDTO;
import com.trungtamjava.service.GioiTinhService;

@Controller
public class GioiTinhController {

	@Autowired
	private GioiTinhService gioiTinhService;

	@GetMapping(value = "/admin/gioiTinh/search")
	public String search(HttpServletRequest request, @RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang

		List<GioiTinhDTO> gioiTinhList = gioiTinhService.search(keyword, 0, page * 10);
		request.setAttribute("gioiTinhList", gioiTinhList);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "admin/gioi-tinh/gioi-tinh-list";
	}

	@GetMapping(value = "/admin/gioiTinh/add")
	public String addCategory() {
		return "admin/gioi-tinh/add-gioi-tinh";
	}

	@PostMapping(value = "/admin/gioiTinh/add")
	public String addPost(@ModelAttribute GioiTinhDTO gioiTinhDTO) {
		gioiTinhService.add(gioiTinhDTO);

		return "redirect:/admin/gioiTinh/search";
	}

	@GetMapping(value = "/admin/gioiTinh/update")
	public String updateGet(Model model, @RequestParam(value = "id") Long id) {
		GioiTinhDTO gioiTinh = gioiTinhService.getById(id);
		model.addAttribute("gioiTinh", gioiTinh);
		return "admin/gioi-tinh/update-gioi-tinh";
	}

	@PostMapping(value = "/admin/gioiTinh/update")
	public String updatea(@ModelAttribute GioiTinhDTO gioiTinhDTO) {
		gioiTinhService.update(gioiTinhDTO);
		return "redirect:/admin/gioiTinh/search";
	}

	@GetMapping(value = "/admin/gioiTinh/delete")
	public String delete(@RequestParam(value = "id") Long id) {
		gioiTinhService.delete(id);
		return "redirect:/admin/gioiTinh/search";
	}
}
