package com.abcd.server;

import org.springframework.web.bind.annotation.RequestParam;

public interface AlipayServer {
	
	String alipayServerImpl(String WIDout_trade_no, String WIDsubject,String WIDtotal_amount,String WIDbody);
}
