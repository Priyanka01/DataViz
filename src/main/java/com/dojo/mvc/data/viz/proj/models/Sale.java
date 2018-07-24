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
	private Double amount;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "brands_sales", 
        joinColumns = @JoinColumn(name = "sale_id"), 
        inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
	private List<Brand> brands;
	
	public Sale() {
		
	}
	
	public Sale(String sku_number, String name, Integer quantity, Double amount) {
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


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public List<Brand> getBrands() {
		return brands;
	}


	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
	public void addBrand(Brand brand) {
		this.brands.add(brand);
	}



}
