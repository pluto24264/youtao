package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taotao.portal.service.ContentService;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String indexPage(Model model) {
		String content = contentService.getContentByCategoryId(89);
		model.addAttribute("ad1", content);// 与index.jsp中的数据同步
		return "index";
	}

}
