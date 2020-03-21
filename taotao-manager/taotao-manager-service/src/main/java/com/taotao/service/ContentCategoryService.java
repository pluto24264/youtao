package com.taotao.service;

import java.util.List;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;

/**
 * 内容分类服务
 * @author xcy
 *
 */
public interface ContentCategoryService {

	List<EUTreeNode> getCategoryList(long parentId);   //获取节点列表
	TaotaoResult insertContentCategory(long parentId,String name);   //插入节点
	TaotaoResult deleteContentCategory(long id);   //删除节点
	TaotaoResult updateContentCategory(long id,String name);  //重命名节点
}
