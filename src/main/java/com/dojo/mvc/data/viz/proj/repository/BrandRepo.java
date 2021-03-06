package com.dojo.mvc.data.viz.proj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dojo.mvc.data.viz.proj.models.Brand;

@Repository
public interface BrandRepo extends CrudRepository<Brand,Long>{
	List<Brand> findAll();
	
	@Query(value="select * from brands join subdepts_brands on brands.id = subdepts_brands.brand_id where subdepts_brands.subdepartment_id = ?1 " , nativeQuery=true)
	List<Brand> findAllWithBrandsItemsNotNull(Long subid,Long deptid);
	
	
}
