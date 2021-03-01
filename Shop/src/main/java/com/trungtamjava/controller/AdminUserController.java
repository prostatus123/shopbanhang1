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

import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.UserService;

@Controller
public class AdminUserController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/admin/user/search")
	public String searchUser(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<UserDTO> userList = userService.search(keyword, 0, page * 10);
		request.setAttribute("userList", userList);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "admin/user/users";
	}

	@GetMapping(value = "/admin/user/add")
	public String AdminAddUserGet() {
		return "admin/user/add-user";
	}

	@PostMapping(value = "/admin/user/add")
	public String AdminAddUserPost(@ModelAttribute(name = "adduser") UserDTO user) {
		user.setEnabled(true);
		userService.insert(user);
		return "redirect:/admin/user/search";

	}

	@GetMapping(value = "/admin/user/update")
	public String AdminUpdateUserGet(Model model, @RequestParam(name = "id") Long id) {
		UserDTO user = userService.get(id);
		model.addAttribute("user", user);
		return "admin/user/update-user";
	}

	@PostMapping(value = "/admin/user/update")
	public String changePassword(@ModelAttribute(name = "user") UserDTO user) {
		userService.update(user);
		return "redirect:/admin/user/search";
	}
	
	@GetMapping(value = "/admin/user/change-password")
	public String changePassword(Model model, @RequestParam(name = "id") Long id) {
		UserDTO user = userService.get(id);
		model.addAttribute("user", user);
		return "admin/user/change-password";
	}

	@PostMapping(value = "/admin/user/change-password")
	public String AdminUpdateUserPost(@ModelAttribute(name = "user") UserDTO user) {
		userService.setupUserPassword(user);
		return "redirect:/admin/user/search";
	}

	@GetMapping(value = "/admin/user/delete")
	public String deleteUser(Long id) {
		userService.delete(id);
		return "redirect:/admin/user/search";
	}

}