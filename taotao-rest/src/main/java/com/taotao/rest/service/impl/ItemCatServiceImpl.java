package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.utils.JsonUtils;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CAT_REDIS_KEY}")
	private String INDEX_CAT_REDIS_KEY;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		return catResult;
	}

	/**
	 * 查询分类列表的方法
	 * 
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {

		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList();
		// 为保证版面有序，限制取出14个
		int count = 0;
		for (TbItemCat itemCat : list) {
			// 叶子结点的i是一个字符串，判断是否为叶子结点
			// 如果是父节点
			if (itemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				// 如果是第一层
				if (parentId == 0) {
					catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				} else { // 其它层
					catNode.setName(itemCat.getName());
				}
				catNode.setUrl("/products/" + itemCat.getId() + ".html");
				catNode.setItem(getCatList(itemCat.getId()));
				resultList.add(catNode);
				count++;
				// 第一层只取14个记录77
				if (parentId == 0 && count >= 14) {
					break;
				}
				// 如果是叶子结点，直接添加字符串
			} else {
				resultList.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
			}
		}
		return resultList;
	}

}
