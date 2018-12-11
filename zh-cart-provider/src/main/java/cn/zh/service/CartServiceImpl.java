package cn.zh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import cn.zh.common.vo.SysResult;
import cn.zh.mapper.CartMapper;
import cn.zh.pojo.Cart;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private CartMapper cartMapper;
	
	public List<Cart> query(Long userId){
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		wrapper.where("user_id={0}", userId);
		
		return cartMapper.selectList(wrapper);
	}
	
	public void updateNum(Cart cart){
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		wrapper.where("user_id={0}", cart.getUserId());
		wrapper.and("item_id={0}", cart.getItemId());
		
		cart.setUpdated(new Date());
		cartMapper.update(cart, wrapper);
	}
	
	public void delete(Long userId, Long itemId){
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		wrapper.where("user_id={0}", userId);
		wrapper.and("item_id={0}", itemId);
		
		cartMapper.delete(wrapper);
	}

	public void updateStatus(Cart cart) {
		cart.setAlipayStatus("1");
		cart.setItemId(563379l);
		cartMapper.updateAlipayStatus(cart);
	}
}
