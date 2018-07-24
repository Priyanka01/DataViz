package com.dojo.mvc.data.viz.proj.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.dojo.mvc.data.viz.proj.models.Brand;
import com.dojo.mvc.data.viz.proj.models.Department;
import com.dojo.mvc.data.viz.proj.models.SubDepartment;
import com.dojo.mvc.data.viz.proj.repository.BrandRepo;
import com.dojo.mvc.data.viz.proj.repository.DepartmentRepo;
import com.dojo.mvc.data.viz.proj.repository.SaleRepo;
import com.dojo.mvc.data.viz.proj.repository.SubDepartmentRepo;

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
	
	
	public void CSVtoDB(String fileName) {
//		String fileName = "doc/CB.csv";
		File file = new File(fileName);
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
			for(int i = 0; i < items.size(); i++) {
				if(items.get(i).length == 1) {
					//Get Department ID
					depo = items.get(i)[0].substring(0, 2);//find out how to change string to long or integer
					department = deptRepo.findByDeptNo(depo);
					System.out.println("Department = " + department.getName());

					//Get SubDepartment ID
					subDepo = items.get(i)[0].substring(3, 5);//find out how to change string to long or integer
					System.out.println("SubDepartment ID = "+ subDepo);
					sub = subRepo.findBySubNo(subDepo);
				}
				if(items.get(i).length >= 6){
					System.out.println("CREATE A SALE HERE");
//					Item item = new Item(items.get(i)[3].concat(items.get(i)[4]), depo2, subDepo2, items.get(i)[1]);
				}else if(items.get(i).length == 5) {
					System.out.println("CREATE A SALE HERE");
				}	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
