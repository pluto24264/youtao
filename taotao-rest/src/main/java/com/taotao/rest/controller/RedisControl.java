package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;

@Controller
@RequestMapping("/cache/syn")
public class RedisControl {

	@Autowired
	private RedisService redisService;

	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	public TaotaoResult contentCacheSyn(@PathVariable Long contentCid) {
		TaotaoResult result = redisService.synContent(contentCid);
		return result;
	}
}
