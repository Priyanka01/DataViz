package com.dojo.mvc.data.viz.proj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dojo.mvc.data.viz.proj.models.Department;

@Repository
public interface DepartmentRepo extends CrudRepository<Department,Long>{
	List<Department> findAll();
	Department findByDepartmentNumber(String dId);
}
