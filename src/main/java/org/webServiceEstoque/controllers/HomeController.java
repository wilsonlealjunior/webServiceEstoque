package org.webServiceEstoque.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.GeradorRelatorio;



@Controller
public class HomeController {
	
	
	@RequestMapping("/")
	public String index() {
		
		

		System.out.println("HomeController.index()");
		return "index";
	}


	
	

	
	

}
