//package com.duan.javastuff.redis;
//
//import com.duan.javastuff.P;
//import org.junit.Test;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * Created on 2018/1/8.
// *
// * @author DuanJiaNing
// */
//public class JedisDemo1 {
//
//    @Test
//    public void test() {
//        Jedis jedis = new Jedis("192.168.18.128", 6379);
//        jedis.set("name", "duan");
//        String name = jedis.get("name");
//        P.out(name);
//        jedis.close();
//    }
//
//    @Test
//    public void testPool() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(20);
//        config.setMaxIdle(100);
//        //获得连接池
//        JedisPool jedisPool = new JedisPool(config, "192.168.18.128", 6379);
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set("name", "whill rewrite");
//            P.out(jedis.get("name"));
//        }
//
//    }
//}
