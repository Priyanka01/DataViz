package com.dojo.mvc.data.viz.proj.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sales")
public class Sale {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	

	private String sku_number;
	private String name;
	private Integer quantity;
	private Integer amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
	private Brand brand;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subdepartment_id")
	private SubDepartment subdepartment;
	
	public Sale() {
		
	}
	
	public Sale(String sku_number, String name, Integer quantity, Integer amount) {
		this.sku_number = sku_number;
		this.name = name;
		this.quantity = quantity;
		this.amount = amount;

	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSku_number() {
		return sku_number;
	}


	public void setSku_number(String sku_number) {
		this.sku_number = sku_number;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	public Brand getBrand() {
		return brand;
	}


	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public SubDepartment getSubdepartment() {
		return subdepartment;
	}

	public void setSubdepartment(SubDepartment subdepartment) {
		this.subdepartment = subdepartment;
	}
}
