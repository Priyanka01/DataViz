package com.dojo.mvc.data.viz.proj.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.dojo.mvc.data.viz.proj.models.Brand;
import com.dojo.mvc.data.viz.proj.models.Department;
import com.dojo.mvc.data.viz.proj.models.Sale;
import com.dojo.mvc.data.viz.proj.models.SubDepartment;
import com.dojo.mvc.data.viz.proj.repository.BrandRepo;
import com.dojo.mvc.data.viz.proj.repository.DepartmentRepo;
import com.dojo.mvc.data.viz.proj.repository.SaleRepo;
import com.dojo.mvc.data.viz.proj.repository.SubDepartmentRepo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;



@Service
public class UniversalService {
	private final BrandRepo brandRepo;
	private final DepartmentRepo deptRepo;
	private final SubDepartmentRepo subRepo;
	private final SaleRepo saleRepo;
	
	public UniversalService(BrandRepo brandRepo, DepartmentRepo deptRepo, SubDepartmentRepo subRepo, SaleRepo saleRepo) {
		this.brandRepo = brandRepo;
		this.deptRepo = deptRepo;
		this.subRepo = subRepo;
		this.saleRepo = saleRepo;
		
	}

	
	//CSV PARSE TO DATABASE
	public List<Sale> CSVtoDB(File file) {
		List<Sale> notMapped = new ArrayList<Sale>();
		try {
			Scanner inputStream = new Scanner(file);
			String splitter = ",";
			//Initializes csv array list
			ArrayList<String[]> items = new ArrayList<String[]>();
			//goes through csv 
			while(inputStream.hasNext()) {
				//while loop incrementer
				String data = inputStream.nextLine();
				//Skips SKU, SKU Description line
				if(data.contains("SKU")) {
					continue;	
				}else {
					//makes the sub arrays in items
					String[] data2= data.split(splitter);
					items.add(data2);
				}
			}
			inputStream.close();
			
			// to break down into sub cats			
			List<Brand> brandList = brandRepo.findAll();
			String depo = null;
			String subDepo = null;
			Department department = null;
			SubDepartment sub = null;
			SubDepartment subDept = null;
			Sale sale = null;
			Integer count = 0;
			for(int i = 0; i < items.size(); i++) {
				count ++;
//				System.out.println(count);
				if(items.get(i).length == 1) {
					//Get Department ID
					depo = items.get(i)[0].substring(0, 2);
					department = deptRepo.findByDepartmentNumber(depo);

					//Get SubDepartment ID
					subDepo = items.get(i)[0].substring(3, 5);
					//find if sub department exists
					sub = subRepo.findBySubNo(subDepo);
					//create a new subDepartment if it doesnt
					if (sub == null) {
						SubDepartment subDepartment = new SubDepartment(subDepo, items.get(i)[0].substring(6), department);
						subDept = subRepo.save(subDepartment);
						//add new subDepartment to department
						department.addSubDept(subDept);
						deptRepo.save(department);
					}else {
						subDept = sub;
						//add new subDepartment to department
						if(department.getSubDepts().contains(subDept) == false) {
							department.addSubDept(subDept);
							deptRepo.save(department);
					}
				}
			
				}else {
					if(items.get(i).length >= 6){
						String edit = items.get(i)[3].concat(items.get(i)[4]);
						edit = edit.replaceAll("\"", "");
						edit = edit.replace("$", "");
						Integer totalSale = Integer.parseInt(edit);
						Sale item = new Sale(items.get(i)[0], items.get(i)[1] , Integer.parseInt(items.get(i)[05]), totalSale);
						sale = saleRepo.save(item);
					}if(items.get(i).length == 5) {
						String edit = items.get(i)[3];
						edit = edit.replaceAll("\"", "");
						edit = edit.replace("$", "");
						Integer totalSale = Integer.parseInt(edit);
							Sale item = new Sale(items.get(i)[0], items.get(i)[1] , Integer.parseInt(items.get(i)[04]), totalSale);
							sale = saleRepo.save(item);
					}
					String check = items.get(i)[1];
					Boolean flag = false;
					
					
					for(int k = 0; k < brandList.size(); k++) {
						String name = brandList.get(k).getName().toUpperCase();
						String test = "BLAKE";
						name = name.replaceAll("\\s","");
						if(check.contains(name) == true) {
							sale.setBrand(brandList.get(k));
							brandRepo.save(brandList.get(k));
							if(subDept.getBrands().contains(brandList.get(k)) == false) {
								subDept.addBrand(brandList.get(k));
								subRepo.save(subDept);
							}
							flag = true;
						}
						if (flag = false) {
							notMapped.add(sale);
						}
					}
				}	
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notMapped;
		
	}
	public List<Brand> findBrands() {
		return brandRepo.findAll();
	}
	

	public void createJSON(boolean prettyPrint) {	
		JsonWriter jsonWriter = null;
		
		try {
		    jsonWriter = new JsonWriter(new FileWriter("test.json"));
		    jsonWriter.beginObject();
		    Department department = this.deptRepo.findByDepartmentNumber("50");
		    
		    jsonWriter.name("name").value(department.getDepartmentNumber()+" "+department.getName());
		    jsonWriter.name("children");
		    jsonWriter.beginArray();
		     
		    
//		    For each sub-dept create an array of objects
		    
		    List<SubDepartment> sub = this.subRepo.findAll();
		    
		    for(SubDepartment s : sub) {
		    	jsonWriter.beginObject();
		    	jsonWriter.name("name").value(s.getName());
		    	jsonWriter.name("children");
		    	 jsonWriter.beginArray();
		    	 
		    	 List<Brand> brand = this.brandRepo.findAllWithBrandsItemsNotNull(s.getId(),department.getId());
		    	 for(Brand b : brand) {
		    		 jsonWriter.beginObject();
		    		 jsonWriter.name("name").value(b.getName());
		    		 jsonWriter.name("children");
		    		 jsonWriter.beginArray();
		    		 
		    		 List<Sale> salesList = this.saleRepo.findAllByBrandAndSubDept(b.getId(),s.getId(),department.getId());
		    		 
		    		 for(Sale sale : salesList) {
		    			 jsonWriter.beginObject();
		    			 jsonWriter.name("sku").value(sale.getSku_number());
		    			 jsonWriter.name("name").value(sale.getName());
		    			 jsonWriter.name("sales").value(sale.getAmount());
		    			 jsonWriter.name("units").value(sale.getQuantity());
		    			 jsonWriter.endObject();
		    		 }
		    		 jsonWriter.endArray();
		    		 jsonWriter.endObject();
		    		 
		    	 }
		    	 jsonWriter.endArray();
		    	 jsonWriter.endObject();
		    }
		    jsonWriter.endArray();
		    jsonWriter.endObject();
	
		    
		} catch (IOException e) {

		}finally{
		    try {
		        jsonWriter.close();
		    } catch (IOException e) {

		    }
		}
		
		
//		System.out.println("salesList"+salesList.size());
	}
}

