package com.taotao.portal.service;


import org.springframework.stereotype.Service;

@Service
public interface ContentService {

	String getContentByCategoryId(long categoryId);
}
