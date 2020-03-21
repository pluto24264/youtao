package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * 获取节点列表
	 */
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 根据parentId查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
		for (TbContentCategory tbContentCategory : list) {
			// 创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open"); // 如果该节点是父节点，关闭，如果是子节点，打开
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 插入节点
	 */
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//状态。可选值:1(正常),2(删除),
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent节点是否为true，如果不是true，改为true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			//更新父节点
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		
		return TaotaoResult.ok(contentCategory);
	}

	/**
	 * 删除节点
	 * 原jsp页面为：$.post("/content/category/delete/",{parentId:node.parentId,id:node.id},function()
	 * 需要穿两个参数，但是不知道为什么前台拿到的parentId一直是null,查询结果报空指针异常，
	 * 在这里我修改了原前台的jsp删除了parentId参数，只保留id，通过该id来查询它的parentId
	 */
	@Override
	public TaotaoResult deleteContentCategory(long id) {
		
		//首先查询该节点的parentId
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		Long pId = category.getParentId();
		//删除节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		//判断父节点是否还有子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(pId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//父节点没有子节点
		if(list.size() < 1) {
			//查询得到该父节点
			TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(pId);
			parentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
		return TaotaoResult.ok();
	}

	/**
	 * 重命名节点
	 */
	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);  //查出这个节点
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok();
	}

}
