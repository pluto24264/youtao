package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	
	//商品参数规格列表展示
	EUDataGridResult getItemParamList(Integer page, Integer rows);
	
	//通过id查找商品规格
	TaotaoResult getItemParamByCid(Long cid);
	
	//添加商品规格
	TaotaoResult insertItemParam(TbItemParam itemParam);

}
