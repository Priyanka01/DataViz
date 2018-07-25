package com.dojo.mvc.data.viz.proj.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="department")
public class Department {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String departmentNumber;
	
	@OneToMany(mappedBy="department", fetch = FetchType.LAZY)
    private List<SubDepartment> subDepts;
	
	public Department() {}
	
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

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String dept_no) {
		this.departmentNumber = dept_no;
	}

	public List<SubDepartment> getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(List<SubDepartment> subDepts) {
		this.subDepts = subDepts;
	}
	
	public void addSubDept(SubDepartment sub) {
		this.subDepts.add(sub);
	}

}
