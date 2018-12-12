package cn.zh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.zh.pojo.Cart;

public interface CartMapper extends BaseMapper<Cart>{
	@Update("update tb_cart set alipay_status=${alipayStatus} where item_id=${itemId}")
	void updateAlipayStatus(Cart wrapper);
	@Select("select * from tb_cart")
	List<Cart> queryCartList();
}
