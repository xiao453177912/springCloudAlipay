package com.abcd.server;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("zh-cart-provider")
public interface CartServer {
	@RequestMapping("/cart/update/{num}")
	public String updateNum(@PathVariable("num") String num);
	
	@RequestMapping("/cart/updateAlipayStatus/{alipayStatus}")
	public String updateAlipayStatus(@PathVariable("alipayStatus") String alipayStatus);
}
