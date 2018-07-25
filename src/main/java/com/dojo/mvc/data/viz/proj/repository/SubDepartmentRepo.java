package com.dojo.mvc.data.viz.proj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dojo.mvc.data.viz.proj.models.SubDepartment;

@Repository
public interface SubDepartmentRepo extends CrudRepository<SubDepartment,Long>{
	List<SubDepartment> findAll();
	SubDepartment findBySubNo(String sId);
}
