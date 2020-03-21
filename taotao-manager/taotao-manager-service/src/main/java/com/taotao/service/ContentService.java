package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 内容管理service
 * @author xcy
 *
 */
public interface ContentService {

	//内容列表的展示
	EUDataGridResult getContentList(Integer page,Integer rows,Long categoryId);
	
	//内容分类添加
	TaotaoResult insertContent(TbContent content);
}
