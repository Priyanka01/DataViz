package com.dojo.mvc.data.viz.proj.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="brands")
public class Brand {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double sale;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "subdepts_brands", 
        joinColumns = @JoinColumn(name = "brand_id"), 
        inverseJoinColumns = @JoinColumn(name = "subdepartment_id")
    )
	private List<SubDepartment> subDepts;
	

	 @OneToMany(mappedBy="brand", fetch = FetchType.LAZY)
	    private List<Sale> sales;
	
	public Brand() {
		this.subDepts = new ArrayList<SubDepartment>();
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

	public Double getSale() {
		return sale;
	}

	public void setSale(Double sale) {
		this.sale = sale;
	}

	public List<SubDepartment> getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(List<SubDepartment> subDepts) {
		this.subDepts = subDepts;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public void addSale(Sale e) {
		this.sales.add(e);		
	}
}
