package com.dojo.mvc.data.viz.proj.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dojo.mvc.data.viz.proj.models.Brand;

@Repository
public interface BrandRepo extends CrudRepository<Brand,Long>{
	
}
