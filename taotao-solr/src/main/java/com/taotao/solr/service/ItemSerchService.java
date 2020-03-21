package com.taotao.solr.service;

import com.taotao.solr.pojo.SearchResult;

public interface ItemSerchService {
	SearchResult search(String queryString, int page, int rows) throws Exception;
}
