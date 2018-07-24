package com.dojo.mvc.data.viz.proj.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dojo.mvc.data.viz.proj.models.Sale;

@Repository
public interface SaleRepo extends CrudRepository<Sale,Long>{

}
