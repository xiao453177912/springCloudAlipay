package com.abcd.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abcd.config.AlipayConfig;
import com.abcd.server.AlipayServer;
import com.abcd.server.CartIndexServer;
import com.abcd.server.CartServer;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import groovy.util.logging.Slf4j;
@Controller
public class HomeController {
	@Autowired
	private AlipayServer alipayServer;
	@Autowired
	private CartServer cartServer;
	@Autowired
	private CartIndexServer cartIndexServer;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final AtomicInteger atomicInteger = new AtomicInteger(1000000);

	@RequestMapping(value = "/alipay/index")
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/alipay/alipay.trade.page.pay",method=RequestMethod.POST)
	@ResponseBody
	public String home1(HttpServletResponse response,@RequestParam("WIDout_trade_no") String WIDout_trade_no,
			@RequestParam("WIDsubject") String WIDsubject,
			@RequestParam("WIDtotal_amount") String WIDtotal_amount,
			@RequestParam("WIDbody") String WIDbody) {
		
		Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
		if (uuidHashCode < 0) {
		uuidHashCode = uuidHashCode * (-1);
		}
		String date = simpleDateFormat.format(new Date());
		WIDout_trade_no = date + uuidHashCode;
		System.out.println("WIDout_trade_no===="+WIDout_trade_no);
		String alipayServerImpl = alipayServer.alipayServerImpl(WIDout_trade_no, WIDsubject, WIDtotal_amount, WIDbody);
		return alipayServerImpl;
	}

	@RequestMapping(value = "/alipay/return_url", method = RequestMethod.GET)
	@ResponseBody
	public String return_url(@RequestParam Map<String, String> requestParams) throws AlipayApiException {
		boolean signVerified = AlipaySignature.rsaCheckV1(requestParams, AlipayConfig.alipay_public_key,
				AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
		if (signVerified) {
			return cartIndexServer.index();
		} else {
			return "验签失败";
		}
	}

	@RequestMapping(value = "/alipay/notify_url", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String notify_url(HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		System.out.println("notify_urlMap==="+params);
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
		System.out.println("signVerified==="+signVerified);
		if (signVerified) {// 验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			System.out.println("trade_status="+trade_status);
			if (trade_status.equals("TRADE_FINISHED")) {
				System.out.println(params);
				
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				String[] alipayStatus = requestParams.get("out_trade_no");
				System.out.println("alipayStatus=="+alipayStatus[0]);
				cartServer.updateAlipayStatus(alipayStatus[0]);
				System.out.println(params);
			}
			System.out.println("验签成功");
			return "success";

		} else {// 验证失败
			System.out.println("验签失败");
			return "fail";
		}

	}

}