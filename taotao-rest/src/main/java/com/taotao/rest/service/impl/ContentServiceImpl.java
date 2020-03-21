package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	String INDEX_CONTENT_REDIS_KEY;

	@Override
	public List<TbContent> getContentLsit(long contentId) {
		// 第二次查询时去缓冲中查询
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentId + "");
			if (!StringUtils.isBlank(result)) {
				// 把字符串转换成list
				List<TbContent> list = JsonUtils.jsonToList(result, TbContent.class);
				System.out.println("这是从缓存中查找的内容");
				System.out.println("--------------------");
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentId);
		List<TbContent> list = contentMapper.selectByExample(example);
		// 将查询的内容添加到缓存
		try {
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentId + "", cacheString);
			System.out.println("存入缓存");
			System.out.println("-----------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
