package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	@Value("${AD_CATEGORY_ID}")
	private String AD_CATEGORY_ID;
	
	@Override
	public String getContentByCategoryId(long categoryId) {
		//1.使用httpclient请求url
		String url = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL+categoryId);
		//2.数据类型转换
		TaotaoResult result = TaotaoResult.formatToList(url, TbContent.class);
		//获取TaotaoResult当中的data数据
		List<TbContent> contentlist = (List<TbContent>) result.getData();
		List list = new ArrayList<>();
		//遍历
		for (TbContent tbContent : contentlist) {
			//用map拼接数据
			//{"srcB":"http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg","height":240,"alt":"","width":670,"src":"http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg","widthB":550,"href":"http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE","heightB":240}
			Map map = new HashMap<>();
			map.put("srcB", tbContent.getPic());
			map.put("height",240 );
			map.put("alt", "");
			map.put("width",670 );
			map.put("src", tbContent.getPic2());
			map.put("widthB", 550);
			map.put("href", tbContent.getUrl());
			map.put("heightB", 240);
			//将map数据添加到list中
			list.add(map);
		}
		//返回json数据
		return JsonUtils.objectToJson(list);
	}
}
