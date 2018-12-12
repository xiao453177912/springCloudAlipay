package cn.zh.service;

import java.util.List;

import cn.zh.common.vo.SysResult;
import cn.zh.pojo.Cart;

public interface CartService {
	public List<Cart> query(Long userId);
	public void delete(Long userId, Long itemId);
	public void updateNum(Cart cart);
	public void updateStatus(Cart cart);
	public List<Cart> queryCartList();
}
