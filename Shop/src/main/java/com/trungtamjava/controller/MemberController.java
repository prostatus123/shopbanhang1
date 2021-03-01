package com.trungtamjava.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.trungtamjava.model.BillDTO;
import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.CommentDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.ReviewDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.model.UserPrincipal;
import com.trungtamjava.service.BillProductService;
import com.trungtamjava.service.BillService;
import com.trungtamjava.service.CommentService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.ReviewService;
import com.trungtamjava.service.UserService;

@Controller
public class MemberController {

	@Autowired
	CommentService commentService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	BillProductService billProductService;

	@Autowired
	BillService billService;

	@Autowired
	ReviewService reviewService;

	

	@PostMapping(value = "/member/comment")
	public String comment(HttpServletRequest request, @RequestParam(name="productId") Long productId,@RequestParam(name="comment") String comment) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal.getId();
		UserDTO userDTO= new UserDTO();
		userDTO.setName(principal.getName());
		userDTO.setId(userId);
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setUserDTO(userDTO);
		ProductDTO productDTO= new ProductDTO();
		productDTO.setId(productId);
		commentDTO.setProductDTO(productDTO);
		commentDTO.setComment(comment);
		commentService.insert(commentDTO);
		return "redirect:/product?id=" + commentDTO.getProductDTO().getId();
	}

	@PostMapping(value = "/member/review")
	public String review(HttpServletRequest request, @ModelAttribute ReviewDTO reviewDTO,
			@RequestParam(name = "productId") Long productId, @RequestParam(name = "starNumber") int starNumber) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// Long userId = principal.getId();
		int check = 0;
		List<ReviewDTO> list = reviewService.find(productId);
		if (list.isEmpty()) {
			UserDTO userDTO = new UserDTO();
			userDTO.setName(principal.getName());
			userDTO.setId(principal.getId());
			reviewDTO.setUserDTO(userDTO);
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(productId);
			reviewDTO.setProductDTO(productDTO);
			reviewDTO.setStarNumber(starNumber);
			reviewService.add(reviewDTO);
		}
		for (ReviewDTO reviewDTO2 : list) {// kiem tra de moi nguoi dung chi comment dc  1 laan
			if (reviewDTO2.getUserDTO().getId()==principal.getId()) {
				check = 1;
				break;
				
			} else {check=2;}
		}
		if (check == 2) {
			UserDTO userDTO = new UserDTO();
			userDTO.setName(principal.getName());
			userDTO.setId(principal.getId());
			reviewDTO.setUserDTO(userDTO);
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(productId);
			reviewDTO.setProductDTO(productDTO);
			reviewDTO.setStarNumber(starNumber);
			reviewService.add(reviewDTO);
		}

		return "redirect:/product?id=" + productId;
	}

	@GetMapping(value = "/member/bill/add")
	public String addOrder(HttpSession session, @RequestParam(value = "page", required = false) Integer page,
			Model model) throws IOException {
		// lay member dang nhap hien tai
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserDTO user = new UserDTO();
		user.setId(principal.getId());

		// lay sp trong gio hang
		Object object = session.getAttribute("cart");

		if (object != null) {
			Map<String, BillProductDTO> map = (Map<String, BillProductDTO>) object;

			BillDTO bill = new BillDTO();
			bill.setUser(user);
			bill.setStatus("NEW");
			billService.insert(bill);

			long totalPrice = 0L;
			for (Entry<String, BillProductDTO> entry : map.entrySet()) {
				BillProductDTO billProduct = entry.getValue();
				billProduct.setBill(bill);

				billProductService.insert(billProduct);

				totalPrice = totalPrice + (billProduct.getQuantity() * billProduct.getUnitPrice());
																									// bill
				//update so luong sp sau khi mua hang thanh cong.
				
				ProductDTO productDTO=productService.get(entry.getValue().getProduct().getId());
				productDTO.setSoLuong(productDTO.getSoLuong()-billProduct.getQuantity());
				productService.update(productDTO);
			}
			
			long finalTotalPrice = 0L;
			/// discount
			page = page == null ? 1 : page;
			List<BillDTO> list = billService.searchByBuyerId(principal.getId(), 0, page * 10); // search trong bang

			// lan dau mua
			if (list.size() == 1) { // lan dau mua
				 finalTotalPrice = totalPrice - ((totalPrice * 5 )/ 100); 
				bill.setPriceTotal(finalTotalPrice);
				bill.setDiscountPercent(5);
				bill.setPay(String.valueOf(totalPrice));
				System.out.println("khuyen mai 5% cho lan thanh toan dau tien cua ban!!!!" + bill.getPriceTotal());
				
			} else {
				bill.setPriceTotal(totalPrice);
				bill.setDiscountPercent(0);
				bill.setPay(String.valueOf(totalPrice));
			}
			billService.update(bill);// udpate lai gia
			
			// goi sms service
			// SMSDTO smsdto = new SMSDTO();
			// smsdto.setCustomerPhone(String.valueOf((principal.getPhone())));
			// smsdto.setContent("Bạn vừa thanh toán thành công đơn hàng trên hệ thống trung
			// tâm java!!!");

			// smsService.sendSMS(smsdto);

			String content = "";

			for (BillProductDTO i : map.values()) {
				System.out.println(
						"sản phẩm: " + i.getProduct().getName() + " Số tiền: " + i.getUnitPrice() * i.getQuantity());
				content += "Sản phẩm: " + i.getProduct().getName() + ", Số tiền: " + i.getUnitPrice() * i.getQuantity()
						+ " (đ); \n";
			}

			
			// xóa cart khi đã thanh toán
			session.removeAttribute("cart");

			return "redirect:/member/bills";
		}
		return "redirect:/products";
	}

	@GetMapping(value = "/member/bills")
	public String bills(HttpServletRequest request, @RequestParam(value = "keyword", required = false) Long keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		page = page == null ? 1 : page;
		keyword = principal.getId();
		List<BillDTO> listBill = billService.searchByBuyerId(keyword, 0, page * 10); // search trong bang bill
		request.setAttribute("bills", listBill);// danh sach bill cua 1 client
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "member/bills";
	}

	long billId;// lÆ°u táº¡m thá»�i giÃ¡ trá»‹ id bill taitle

	@GetMapping(value = "/member/bill")
	public String billDetail(HttpServletRequest request, @RequestParam(name = "billId", required = true) Long billId,
			@RequestParam(value = "keyword", required = false) Long keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		
		this.billId = billId;
		page = page == null ? 1 : page;
		keyword = billId;
		List<BillProductDTO> listBillProduct = billProductService.searchByBill(keyword, 0, page * 10);// danh sach san
																										// pham trong 1
		// bill
		request.setAttribute("billProducts", listBillProduct);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "member/bill";
	}

	// xÃ³a item in list bill
	@GetMapping(value = "/member/delete/bills")
	public String deleteBillsProduct(@RequestParam(name = "billId", required = true) Long billId) {
		billService.delete(billId);
		return "redirect:/member/bills";
	}

	// xÃ³a item in list bill detail
	@GetMapping(value = "/member/delete/bill")
	public String deleteBillProduct(@RequestParam(name = "billId", required = true) Long billId) {
		System.out.println(billId);
		System.out.println();
		billProductService.delete(billId);
		return "redirect:/member/bill?billId=" + this.billId;
	}
}
