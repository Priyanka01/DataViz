package com.dojo.mvc.data.viz.proj.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@Controller
public class UploadFile {
	
	@RequestMapping("/load")
	public String uploadOneFile() {
		return "/views/index.jsp";
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String receiveFile(@RequestParam("file") MultipartFile file,@RequestParam("description")String description) throws IOException {
		if (!file.isEmpty()) {
           System.out.println("File"+description);
           UploadFile.convertToCSV(file);
		}
		return "redirect:/load";
	}
	
	
	
	public static void convertToCSV(MultipartFile file) {

        String csvFile = "CB.csv";
		String line = "";
        String cvsSplitBy = ",";
		
		try (BufferedReader br = new BufferedReader(new FileReader(file.getName()))) {
			String[] item = {};
			while ((line = br.readLine()) != null) {

			// use comma as separator
				item = line.split(cvsSplitBy);
			}
			System.out.println(item[1]);
			// for(int i = 0; i < item.length; i ++){
			// 	System.out.println(item[i]);
			// }

		
			
        } catch (IOException e) {
            e.printStackTrace();
        }

    }		
}


