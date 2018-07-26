package com.dojo.mvc.data.viz.proj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dojo.mvc.data.viz.proj.models.Sale;

@Repository
public interface SaleRepo extends CrudRepository<Sale,Long>{
	
	@Query(value = "select * from sales where brand_id = ?1 and amount > 0 and brand_id != ''",nativeQuery=true)
	List<Sale> findAllByBrandAndSubDept(Long brandid,Long sub,Long dept);

}
