package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TaotaoResult;


public interface ItemService {
	
	TbItem getItemById(Long itemId);
	EUDataGridResult getItemList(Integer page,Integer rows);
	TaotaoResult creatItem(TbItem item,String desc,String itemParam) throws Exception;
}
