package com.abcd.serverImpl;

import org.springframework.stereotype.Service;

import com.abcd.config.AlipayConfig;
import com.abcd.server.AlipayServer;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

@Service
public class AlipayServerImpl implements AlipayServer{

	@Override
	public String alipayServerImpl(String WIDout_trade_no, String WIDsubject, String WIDtotal_amount, String WIDbody) {
		
		
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,
				AlipayConfig.merchant_private_key , AlipayConfig.FORMAT, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
//		System.out.println("appid"+AlipayConfig.app_id);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDout_trade_no +"\"," 
				+ "\"total_amount\":\""+ WIDtotal_amount +"\"," 
				+ "\"subject\":\""+ WIDsubject +"\"," 
				+ "\"body\":\""+ WIDbody +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		String result = new String();
		//请求
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(result);
		return result;
	}
	
	
}
