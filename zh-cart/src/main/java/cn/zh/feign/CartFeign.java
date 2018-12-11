package cn.zh.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("zh-cart-provider")
public interface CartFeign {
	@RequestMapping("/cart/query/{userId}")
	public String query(@PathVariable("userId") Long userId);
}
