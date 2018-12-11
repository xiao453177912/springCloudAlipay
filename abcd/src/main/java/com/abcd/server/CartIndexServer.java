package com.abcd.server;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("zh-cart")
public interface CartIndexServer {
	@RequestMapping("/cart/index")
	public String index();
}
