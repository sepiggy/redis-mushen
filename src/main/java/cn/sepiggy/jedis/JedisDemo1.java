package cn.sepiggy.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDemo1 {

    // 单实例测试
    @Test
    public void demo1() {
        // 1. 设置IP和端口
        Jedis jedis = new Jedis("localhost");

        // 2. 保存数据
        jedis.set("name", "imooc");

        // 3. 获取数据
        String value = jedis.get("name");
        System.out.println(value);

        // 4. 释放资源
        jedis.close();
    }

    // 连接池方式连接
    @Test
    public void demo2() {
        // 获得连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
        config.setMaxTotal(30);
        // 设置最大空闲连接数
        config.setMaxIdle(10);

        // 获得连接池
        JedisPool pool = new JedisPool(config, "localhost", 6379);

        // 获得核心对象
        Jedis jedis = null;

        try {
            // 通过连接池获得连接
            jedis = pool.getResource();

            // 设置数据
            jedis.set("name", "张三");

            // 获取数据
            String name = jedis.get("name");
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (jedis != null) {
                jedis.close();
            }
            if (pool != null) {
                pool.close();
            }
        }
    }

}

