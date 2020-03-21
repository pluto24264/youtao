package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
	TaotaoResult checkData(String content, Integer type); // 注册时数据检查

	TaotaoResult createUser(TbUser user); // 用户注册

	TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response); // 登录

	TaotaoResult getUserByToken(String token); // 根据令牌取用户信息

}
