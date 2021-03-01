package com.trungtamjava.model;

public class ProductDTO {
	private Long id;
	private String name;
	private Long price;
	private String image;
	private String description;
	private CategoryDTO category;
	private ThuongHieuDTO thuongHieuDTO;
	private KichThuocDTO kichThuocDTO;
	private GioiTinhDTO gioiTinhDTO;
	private Long soLuong;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public GioiTinhDTO getGioiTinhDTO() {
		return gioiTinhDTO;
	}

	public void setGioiTinhDTO(GioiTinhDTO gioiTinhDTO) {
		this.gioiTinhDTO = gioiTinhDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ThuongHieuDTO getThuongHieuDTO() {
		return thuongHieuDTO;
	}

	public void setThuongHieuDTO(ThuongHieuDTO thuongHieuDTO) {
		this.thuongHieuDTO = thuongHieuDTO;
	}

	public KichThuocDTO getKichThuocDTO() {
		return kichThuocDTO;
	}

	public void setKichThuocDTO(KichThuocDTO kichThuocDTO) {
		this.kichThuocDTO = kichThuocDTO;
	}

	public Long getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Long soLuong) {
		this.soLuong = soLuong;
	}

}
