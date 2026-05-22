package com.bway.springdemo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bway.springdemo.model.Cart;
import com.bway.springdemo.model.CartProduct;
import com.bway.springdemo.repository.CartRepository;

@RestController
public class CartRestController {
	
	@Autowired
	private CartRepository cartRepo;
	
	
    
    @GetMapping("api/cart")
    public String loadProduct()
    {
    	RestTemplate temp = new RestTemplate();
    	Cart[] prods = temp.getForObject("https://fakestoreapi.com/carts", Cart[].class);				
    	
    	 // IMPORTANT: fix FK before saving (like we discussed)
        for (Cart cart : prods) {
            if (cart.getProducts() != null) {
                for (CartProduct p : cart.getProducts()) {
                    p.setCart(cart);
                }
            }
        }

        cartRepo.saveAll(List.of(prods));

        return "success";
    }
    

}
