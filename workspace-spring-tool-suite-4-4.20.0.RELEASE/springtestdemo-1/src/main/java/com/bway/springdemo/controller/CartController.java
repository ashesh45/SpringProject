package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bway.springdemo.repository.CartRepository;

public class CartController {
	
	
	@Autowired
	private CartRepository cartRepo;
	
	
	@GetMapping("/cart")
	public String getProductGallery(Model model) {

	    model.addAttribute("clist", cartRepo.findAll());
	    return "Cart";
	}

}
