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
	private String dept_no;
	
	@OneToMany(mappedBy="department", fetch = FetchType.LAZY)
    private List<SubDepartment> sub_depts;
	
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

	public String getDeptNo() {
		return dept_no;
	}

	public void setDeptNo(String dept_no) {
		this.dept_no = dept_no;
	}

	public List<SubDepartment> getSub_depts() {
		return sub_depts;
	}

	public void setSub_depts(List<SubDepartment> sub_depts) {
		this.sub_depts = sub_depts;
	}
	
	public void addSubDept(SubDepartment sub) {
		this.sub_depts.add(sub);
	}

}
