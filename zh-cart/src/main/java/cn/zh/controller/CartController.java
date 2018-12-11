package cn.zh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zh.feign.CartFeign;

@Controller
public class CartController {
	@Autowired
	private CartFeign cartFeign;
	
	@RequestMapping("/cart/index")
	public String index(ModelMap model){ 
		String query = cartFeign.query(6l);
		System.out.println(query.toString());
		
		
		model.addAttribute("order",query);  
		return "order";  
	} 
	@RequestMapping("/cart/Order")
	@ResponseBody
	public String query(){
		System.out.println(cartFeign.query(7l));
		return cartFeign.query(7l);
	}
	
}
