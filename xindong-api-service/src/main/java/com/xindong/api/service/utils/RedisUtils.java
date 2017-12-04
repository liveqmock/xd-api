package com.xindong.api.service.utils;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

public class RedisUtils {
	private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
	private static JedisPool pool;
	/*static {  
	    JedisPoolConfig config = new JedisPoolConfig();  
	    config.setMaxActive(1024);  
	    config.setMaxIdle(200);  
	    config.setMaxWait(5000);  
	    config.setTestOnBorrow(true);  
	    config.setTestOnReturn(true);  
	    pool = new JedisPool(config, "124.202.141.110", 10318,Protocol.DEFAULT_TIMEOUT,"xindong@123456");
	} */
	
	public  void setPool(JedisPool pool) {
		RedisUtils.pool = pool;
	}
	public  JedisPool getPool() {
		return pool;
	}
	/*
	 * 
	 */public static String get(String key){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
		    return jedis.get(key);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
			return "";
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
	
	}
	
	/*
	 * 
	 */
	public static void set(String key, int seconds, String value){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			jedis.setex(key, seconds, value);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
	}
	
	public static void set(String key, String value){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			jedis.set(key, value);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
	}
	
	public static boolean exists(String key){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.exists(key);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
			return false;
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
	}
	
	public static Set<String> zrevrange(String key, int start, int end){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrange(key,start,end);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
		return null;
	}
	
	public static void del(String key){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			jedis.del(key);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
	}
	
	public static void zincrby(String key,Integer count, String member){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			jedis.zincrby(key, count, member);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
	}
	
	public static Set<Tuple> zrevrangeWithScores(String key, int start, int end){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.zrevrangeWithScores(key, start, end);
		}catch (Exception e) {
			log.error("获取redis出错："+e.getMessage());
		}finally{
			if(jedis != null){
				pool.returnResource(jedis);
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		RedisUtils.set("ceshi", "221");
		System.out.println(RedisUtils.get("ceshi"));
	}
}
