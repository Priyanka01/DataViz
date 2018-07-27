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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="subdepartments")
public class SubDepartment {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String subNo;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "subdepts_brands", 
        joinColumns = @JoinColumn(name = "subdepartment_id"), 
        inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
	private List<Brand> brands;
	
	@OneToMany(mappedBy="subdepartment", fetch = FetchType.LAZY)
	private List <Sale> sales;
	
	public SubDepartment() {}
	public SubDepartment(String sub, String string, Department depo) {
		this.brands = new ArrayList<Brand>();
		this.name = string;
		this.subNo = sub;
		this.department = depo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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
	public List<Sale> getSales() {
		return sales;
	}
	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
}
