package com.dojo.mvc.data.viz.proj.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="department")
public class Department {
	
	String name;
	String dept_no;

}
