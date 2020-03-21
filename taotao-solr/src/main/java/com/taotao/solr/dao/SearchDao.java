package com.taotao.solr.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.solr.pojo.SearchResult;

public interface SearchDao {
	SearchResult search(SolrQuery query) throws Exception;

}
