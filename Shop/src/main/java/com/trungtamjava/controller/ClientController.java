package com.trungtamjava.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.model.BillProductDTO;
import com.trungtamjava.model.CategoryDTO;
import com.trungtamjava.model.CommentDTO;
import com.trungtamjava.model.GioiTinhDTO;
import com.trungtamjava.model.KichThuocDTO;
import com.trungtamjava.model.ProductDTO;
import com.trungtamjava.model.ReviewDTO;
import com.trungtamjava.model.ThuongHieuDTO;
import com.trungtamjava.model.UserDTO;
import com.trungtamjava.service.CategoryService;
import com.trungtamjava.service.CommentService;
import com.trungtamjava.service.GioiTinhService;
import com.trungtamjava.service.KichThuocService;
import com.trungtamjava.service.ProductService;
import com.trungtamjava.service.ReviewService;
import com.trungtamjava.service.ThuongHieuService;
import com.trungtamjava.service.UserService;
import com.trungtamjava.utils.RoleEnum;

@Controller
public class ClientController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ThuongHieuService thuongHieuService;

	@Autowired
	private KichThuocService kichThuocService;

	@Autowired
	private GioiTinhService gioiTinhService;

	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, 
			@RequestParam(name = "err", required = false) String error) {
		if (error != null) {
			request.setAttribute("err", error);
		}
		return "client/login";
	}

	@GetMapping(value = "/register")
	public String register(HttpServletRequest request) {
		return "client/register";
	}

	@PostMapping(value = "/register")
	public String register(@ModelAttribute UserDTO userDTO) {
		userDTO.setEnabled(true);
		userDTO.setRole(RoleEnum.MEMBER.getRoleName());
		userService.insert(userDTO);
		return "redirect:/login";
	}

	@GetMapping(value = "/products")
	public String findProducts(HttpServletRequest request) {

		String gioiTinhName = request.getParameter("gioiTinhName") == null ? "" : request.getParameter("gioiTinhName");
		String thuongHieuName = request.getParameter("thuongHieuName") == null ? ""
				: request.getParameter("thuongHieuName");
		String categoryName = request.getParameter("categoryName") == null ? "" : request.getParameter("categoryName");

		String kichThuocName = request.getParameter("kichThuocName") == null ? ""
				: request.getParameter("kichThuocName");

		String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

		Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
				: Long.valueOf(request.getParameter("priceStart"));

		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

		Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
				: Long.valueOf(request.getParameter("priceEnd"));

		List<ProductDTO> listPro = productService.search(keyword, categoryName, thuongHieuName,kichThuocName, gioiTinhName, priceStart, priceEnd, 0, page * 10);

		List<CategoryDTO> list = categoryService.search("", 0, page * 10);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, page * 10);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, page * 10);
		List<GioiTinhDTO> gioiTinhDTOs = gioiTinhService.search("", 0, page * 10);

		request.setAttribute("gioiTinhList", gioiTinhDTOs);
		request.setAttribute("kichThuocList", kichThuocDTOs);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("productList", listPro);
		request.setAttribute("categoryList", list);

		request.setAttribute("kichThuocName", kichThuocDTOs);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("thuongHieuName", thuongHieuName);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "client/products";
	}

	@GetMapping(value = "/product")
	public String oneProduct(HttpServletRequest request, @RequestParam(name = "id", required = true) Long id) {
		ProductDTO product = productService.get(id);
		List<CommentDTO> commentDTOs = commentService.searchByProduct(id);
		List<ReviewDTO> reviews = reviewService.find(id);
		float sum=0;
		for(ReviewDTO reviewDTO:reviews) {
			int star=reviewDTO.getStarNumber();
			sum=sum+star;
		}
		int dem=reviews.size();
		float totalStar= sum/dem;
		request.setAttribute("dem", dem);
		request.setAttribute("totalStar", totalStar);
		request.setAttribute("product", product);
		request.setAttribute("commentProduct", commentDTOs);
		request.setAttribute("reviews", reviews);
		return "client/product";
	}

	@GetMapping(value = "/member/add-to-cart")
	public String AddToCart(@RequestParam(name = "pid") Long pId, HttpSession session) throws IOException {
//		ProductDTO product = productService.get(pId);
//
//		Object object = session.getAttribute("cart");
//		if (object == null) {
//			BillProductDTO billProduct = new BillProductDTO();
//			billProduct.setProduct(product);
//			billProduct.setQuantity(1);
//			billProduct.setUnitPrice(product.getPrice());
//			Map<Long, BillProductDTO> map = new HashMap<>();
//			map.put(pId, billProduct);
//			session.setAttribute("cart", map);
//		} else {
//			Map<Long, BillProductDTO> map = (Map<Long, BillProductDTO>) object;
//			BillProductDTO billProduct = map.get(pId);
//			if (billProduct == null) {
//				billProduct = new BillProductDTO();
//				billProduct.setProduct(product);
//				billProduct.setQuantity(1);
//				billProduct.setUnitPrice(product.getPrice());
//				map.put(pId, billProduct);
//			} else {
//				billProduct.setQuantity(billProduct.getQuantity() + 1);
//
//			}
//			session.setAttribute("cart", map);
//
//		}
//		return "redirect:/cart";
//	
		ProductDTO product = productService.get(pId);// lay thong tin sp tu trang products , product
	
		Object object = session.getAttribute("cart");// lay session neu co , neu chua co tao 1 session moi la cart
		if (object == null) {// neu cart rong 
			BillProductDTO billProduct = new BillProductDTO();
			billProduct.setProduct(product);// them sp vao cart
			billProduct.setQuantity(1);// sl sp =1
			billProduct.setUnitPrice(product.getPrice());
			Map<Long, BillProductDTO> map = new HashMap<>();// luu tt sp vao map
			map.put(pId, billProduct);
			session.setAttribute("cart", map);// set cart bang map
		} else {// neu cart da co sp
			Map<Long, BillProductDTO> map = (Map<Long, BillProductDTO>) object;// lay ra map 
			BillProductDTO billProduct = map.get(pId);// tim danh sach sp co trong map
			if (billProduct == null) {// neu chua co sp trong map thi lay tt sp va sl sp =1
				billProduct = new BillProductDTO();
				billProduct.setProduct(product);
				billProduct.setQuantity(1);
				billProduct.setUnitPrice(product.getPrice());
				map.put(pId, billProduct);
			} else {// neu co sp trong map roi thi tang sl cua sp len +1
				
				if (billProduct.getQuantity() < product.getSoLuong()) {
					billProduct.setQuantity(billProduct.getQuantity() + 1);
				} else {
					billProduct.setQuantity(billProduct.getQuantity());
				}
			}
			session.setAttribute("cart", map);
		}
		return "redirect:/cart";
	}
	@GetMapping(value = "/delete-from-cart")
	public String Deletefromtocart(HttpServletRequest req, @RequestParam(name = "key", required = true) Long key) {
		HttpSession session = req.getSession();
		Object object = session.getAttribute("cart");
		if (object != null) {
			Map<Long, BillProductDTO> map = (Map<Long, BillProductDTO>) object;
			map.remove(key);
			session.setAttribute("cart", map);
		}
		return "redirect:/cart";
	}

	@GetMapping(value = "/cart")
	public String cart() {
		return "client/cart";
	}

	@GetMapping(value = "/category/search")
	public String searchCategory(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<CategoryDTO> categoryList = categoryService.search(keyword, 0, page * 10);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "client/categories";
	}

	@GetMapping(value = "/productsByCategory")
	public String AdminProductSearch(HttpServletRequest request,
			@RequestParam(name = "id", required = true) Long categoryId) {

		String thuongHieuName = request.getParameter("thuongHieuName") == null ? ""
				: request.getParameter("thuongHieuName");

		String gioiTinhName = request.getParameter("gioiTinhName") == null ? "" : request.getParameter("gioiTinhName");
		
		String kichThuocName = request.getParameter("kichThuocName") == null ? ""
				: request.getParameter("kichThuocName");
		String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");
		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

		Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
				: Long.valueOf(request.getParameter("priceStart"));
		Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000
				: Long.valueOf(request.getParameter("priceEnd"));

		List<ProductDTO> listPro = productService.searchByCategory(keyword, thuongHieuName,
				kichThuocName, gioiTinhName, priceStart, priceEnd, categoryId, 0, page*10);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, page * 10);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, page * 10);
		List<GioiTinhDTO> gioiTinhDTOs = gioiTinhService.search("", 0, page * 10);

		request.setAttribute("gioiTinhList", gioiTinhDTOs);
		request.setAttribute("kichThuocList", kichThuocDTOs);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("productList", listPro);

		request.setAttribute("kichThuocName", kichThuocDTOs);
		request.setAttribute("thuongHieuName", thuongHieuName);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "client/products-by-category";
	}
}