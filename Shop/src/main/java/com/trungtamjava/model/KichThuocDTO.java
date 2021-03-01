package com.trungtamjava.model;

public class KichThuocDTO {
	private Long id;
	private String name;

	public KichThuocDTO() {
		super();
	}

	public KichThuocDTO(String name) {
		super();
		this.name = name;
	}

	public KichThuocDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
