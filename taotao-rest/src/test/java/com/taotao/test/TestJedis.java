package com.taotao.test;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {

	@Test
	public void testJedisSingle() {
		// 创建一个Jedis对象
		Jedis jedis = new Jedis("192.168.226.226", 6379);
		// 调用jedis对象的方法，方法名称与redis命令一样
		jedis.set("key1", "value1");
		String key1 = jedis.get("key1");
		System.out.println(key1);
		// 关闭jedis
		jedis.close();
	}

	// 使用连接池
	@Test
	public void testJedisPool() {
		JedisPool jedisPool = new JedisPool("192.168.226.226", 6379);
		// 从连接池获取jedis对象
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		jedisPool.close();
	}

	// 测试集群
	@Test
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.226.226", 7001));
		nodes.add(new HostAndPort("192.168.226.226", 7002));
		nodes.add(new HostAndPort("192.168.226.226", 7003));
		nodes.add(new HostAndPort("192.168.226.226", 7004));
		nodes.add(new HostAndPort("192.168.226.226", 7005));
		nodes.add(new HostAndPort("192.168.226.226", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("k1", "v1");
		jedisCluster.get("k1");
		System.out.println("k1");
	}

	// 单机版测试spring
	@Test
	public void testSpringJedisSingle() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisPool jedisPool = (JedisPool) applicationContext.getBean("jedisClient");
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		jedisPool.close();
	}

	// 单机版测试spring
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisCluster cluster = (JedisCluster) applicationContext.getBean("jedisClient");
		String string = cluster.get("k1");
		System.out.println(string);
		cluster.close();
	}
}
