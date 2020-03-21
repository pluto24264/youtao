package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.utils.JsonUtils;

/**
 * 商品分类列表
 * 
 * @author xcy
 *
 */
@Controller
public class ItemCatControl {

	@Autowired
	private ItemCatService itemCatService;

	/*方法一
	//如果不加pruduces显示出来的结果是乱码
	@RequestMapping(value = "/itemcat/list",
			produces = MediaType.APPLICATION_JSON_VALUE +";charset=utf-8")
	@ResponseBody
	public String getItemList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		// 把pojo转换成字符串
		String json = JsonUtils.objectToJson(catResult);
		// 字符串拼装
		String result = callback + "(" + json + ");";
		return result;
	}
	*/
	//方法二
	@RequestMapping("/itemcat/list")
	@ResponseBody
	public Object getItemCat(String callback) {
		
		CatResult catResule = itemCatService.getItemCatList();
		MappingJacksonValue maJacksonValue = new MappingJacksonValue(catResule);
		maJacksonValue.setJsonpFunction(callback);
		return maJacksonValue;
	}
	
	
}
