package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
import com.taotao.utils.HttpClientUtil;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BATH_URL}")
	private String REST_BATH_URL;
	@Value("${REST_CONTENT_CATEGORY_URL}")
	private String REST_CONTENT_CATEGORY_URL;

	/**
	 * 内容列表展示
	 */
	@Override
	public EUDataGridResult getContentList(Integer page, Integer rows, Long categoryId) {
		// 设置查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 设置分页
		PageHelper.startPage(page, rows);
		// 查询
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		long total = pageInfo.getTotal();
		EUDataGridResult result = new EUDataGridResult(total, list);
		return result;
	}

	/**
	 * 内容分类添加
	 */
	@Override
	public TaotaoResult insertContent(TbContent content) {
		// 补全信息
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		try {
			// 添加缓存同步逻辑
			HttpClientUtil.doGet(REST_BATH_URL + REST_CONTENT_CATEGORY_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

}
