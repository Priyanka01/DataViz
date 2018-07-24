package com.dojo.mvc.data.viz.proj.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dojo.mvc.data.viz.proj.services.UniversalService;


@Controller
public class UploadFile {
	
	private final UniversalService uservice; 
	
	public UploadFile(UniversalService uservice) {
		this.uservice = uservice;
	}
	
	@RequestMapping("/load")
	public String uploadOneFile() {
		return "/views/index.jsp";
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String receiveFile(@RequestParam("file") MultipartFile file,@RequestParam("description")String description) throws IOException {
		if (!file.isEmpty()) {
           System.out.println("File"+description);
           UploadFile.parseToCSV(file);
		}
		return "redirect:/load";
	}
	
	
	
	public static void parseToCSV(MultipartFile file) {
		
		//	CSVTODB	

	}
}


