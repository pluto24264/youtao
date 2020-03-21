package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public EUDataGridResult getItemParamList(Integer page, Integer rows) {
		TbItemParamExample example = new TbItemParamExample();
		// 设置分页
		PageHelper.startPage(page, rows);
		/**
		 * 第一次使用selectByExample()这个查询方法,xml中的resultMap中返回的列表名不匹配，
		 * 导致页面请求时数据不显示，打开浏览器控制台发现控制台保了如下的错误 jquery.min.js:2 Uncaught TypeError: Cannot
		 * read property 'length' of null
		 */
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		/**
		 * 用来测试查询的数据是否正常
		 */
//		for (TbItemParam tbItemParam : list) {
//			System.out.println(tbItemParam);
//		}
		PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(list);
		long total = pageInfo.getTotal();
		EUDataGridResult result = new EUDataGridResult(total, list);
		return result;
	}

	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();

		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		//补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

}
