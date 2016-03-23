package com.lansha.test;

import java.util.*;

import com.yaowang.util.DateUtils;
import redis.clients.jedis.Jedis;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-3
 * Time: 下午9:30
 * To change this template use File | Settings | File Templates.
 */
public class JedisDemo {


    public void test1() {
        Jedis redis = new Jedis("192.168.0.172", 6379);//连接redis
        //redis.auth("redis");//验证密码
                   /* -----------------------------------------------------------------------------------------------------------  */
        /**  KEY操作
         21.
         22.           //KEYS
         23.           Set keys = redis.keys("*");//列出所有的key，查找特定的key如：redis.keys("foo")
         24.           Iterator t1=keys.iterator() ;
         25.           while(t1.hasNext()){
         26.               Object obj1=t1.next();
         27.               System.out.println(obj1);
         28.           }
         29.
         30.           //DEL 移除给定的一个或多个key。如果key不存在，则忽略该命令。
         31.           redis.del("name1");
         32.
         33.           //TTL 返回给定key的剩余生存时间(time to live)(以秒为单位)
         34.           redis.ttl("foo");
         35.
         36.           //PERSIST key 移除给定key的生存时间。
         37.           redis.persist("foo");
         38.
         39.           //EXISTS 检查给定key是否存在。
         40.           redis.exists("foo");
         41.
         42.           //MOVE key db  将当前数据库(默认为0)的key移动到给定的数据库db当中。如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定key，或者key不存在于当前数据库，那么MOVE没有任何效果。
         43.           redis.move("foo", 1);//将foo这个key，移动到数据库1
         44.
         45.           //RENAME key newkey  将key改名为newkey。当key和newkey相同或者key不存在时，返回一个错误。当newkey已经存在时，RENAME命令将覆盖旧值。
         46.           redis.rename("foo", "foonew");
         47.
         48.           //TYPE key 返回key所储存的值的类型。
         49.           System.out.println(redis.type("foo"));//none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
         50.
         51.           //EXPIRE key seconds 为给定key设置生存时间。当key过期时，它会被自动删除。
         52.           redis.expire("foo", 5);//5秒过期
         53.           //EXPIREAT EXPIREAT的作用和EXPIRE一样，都用于为key设置生存时间。不同在于EXPIREAT命令接受的时间参数是UNIX时间戳(unix timestamp)。
         54.
         55.           //一般SORT用法 最简单的SORT使用方法是SORT key。
         56.           redis.lpush("sort", "1");
         57.           redis.lpush("sort", "4");
         58.           redis.lpush("sort", "6");
         59.           redis.lpush("sort", "3");
         60.           redis.lpush("sort", "0");
         61.
         62.           List list = redis.sort("sort");//默认是升序
         63.           for(int i=0;i<list.size();i++){
         64.               System.out.println(list.get(i));
         65.           }
         66.
         67.        */
                   /* -----------------------------------------------------------------------------------------------------------  */
        /**  STRING 操作
         70.
         71.       //SET key value将字符串值value关联到key。
         72.       redis.set("name", "wangjun1");
         73.       redis.set("id", "123456");
         74.       redis.set("address", "guangzhou");
         75.
         76.       //SETEX key seconds value将值value关联到key，并将key的生存时间设为seconds(以秒为单位)。
         77.       redis.setex("foo", 5, "haha");
         78.
         79.       //MSET key value [key value ...]同时设置一个或多个key-value对。
         80.       redis.mset("haha","111","xixi","222");
         81.
         82.       //redis.flushAll();清空所有的key
         83.       System.out.println(redis.dbSize());//dbSize是多少个key的个数
         84.
         85.       //APPEND key value如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
         86.       redis.append("foo", "00");//如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
         87.
         88.       //GET key 返回key所关联的字符串值
         89.       redis.get("foo");
         90.
         91.       //MGET key [key ...] 返回所有(一个或多个)给定key的值
         92.       List list = redis.mget("haha","xixi");
         93.       for(int i=0;i<list.size();i++){
         94.           System.out.println(list.get(i));
         95.       }
         96.
         97.       //DECR key将key中储存的数字值减一。
         98.       //DECRBY key decrement将key所储存的值减去减量decrement。
         99.       //INCR key 将key中储存的数字值增一。
         100.        //INCRBY key increment 将key所储存的值加上增量increment。
         101.
         102.        */
                    /* -----------------------------------------------------------------------------------------------------------  */
        /**  Hash 操作
         105.
         106.        //HSET key field value将哈希表key中的域field的值设为value。
         107.        redis.hset("website", "google", "www.google.cn");
         108.        redis.hset("website", "baidu", "www.baidu.com");
         109.        redis.hset("website", "sina", "www.sina.com");
         110.
         111.        //HMSET key field value [field value ...] 同时将多个field - value(域-值)对设置到哈希表key中。
         112.        Map map = new HashMap();
         113.        map.put("cardid", "123456");
         114.        map.put("username", "jzkangta");
         115.        redis.hmset("hash", map);
         116.
         117.        //HGET key field返回哈希表key中给定域field的值。
         118.        System.out.println(redis.hget("hash", "username"));
         119.
         120.        //HMGET key field [field ...]返回哈希表key中，一个或多个给定域的值。
         121.        List list = redis.hmget("website","google","baidu","sina");
         122.        for(int i=0;i<list.size();i++){
         123.            System.out.println(list.get(i));
         124.        }
         125.
         126.        //HGETALL key返回哈希表key中，所有的域和值。
         127.        Map<String,String> map = redis.hgetAll("hash");
         128.        for(Map.Entry entry: map.entrySet()) {
         129.             System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
         130.        }
         131.
         132.        //HDEL key field [field ...]删除哈希表key中的一个或多个指定域。
         133.        //HLEN key 返回哈希表key中域的数量。
         134.        //HEXISTS key field查看哈希表key中，给定域field是否存在。
         135.        //HINCRBY key field increment为哈希表key中的域field的值加上增量increment。
         136.        //HKEYS key返回哈希表key中的所有域。
         137.        //HVALS key返回哈希表key中的所有值。
         138.
         139.         */
                   /* -----------------------------------------------------------------------------------------------------------  */
        /**  LIST 操作
         142.        //LPUSH key value [value ...]将值value插入到列表key的表头。
         143.        redis.lpush("list", "abc");
         144.        redis.lpush("list", "xzc");
         145.        redis.lpush("list", "erf");
         146.        redis.lpush("list", "bnh");
         147.
         148.        //LRANGE key start stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。你也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
         149.        List list = redis.lrange("list", 0, -1);
         150.        for(int i=0;i<list.size();i++){
         151.            System.out.println(list.get(i));
         152.        }
         153.
         154.        //LLEN key返回列表key的长度。
         155.        //LREM key count value根据参数count的值，移除列表中与参数value相等的元素。
         156.         */
                    /* -----------------------------------------------------------------------------------------------------------  */
        /**  SET 操作
         159.        //SADD key member [member ...]将member元素加入到集合key当中。
         160.        redis.sadd("testSet", "s1");
         161.        redis.sadd("testSet", "s2");
         162.        redis.sadd("testSet", "s3");
         163.        redis.sadd("testSet", "s4");
         164.        redis.sadd("testSet", "s5");
         165.
         166.        //SREM key member移除集合中的member元素。
         167.        redis.srem("testSet", "s5");
         168.
         169.        //SMEMBERS key返回集合key中的所有成员。
         170.        Set set = redis.smembers("testSet");
         171.        Iterator t1=set.iterator() ;
         172.        while(t1.hasNext()){
         173.            Object obj1=t1.next();
         174.            System.out.println(obj1);
         175.        }
         176.
         177.        //SISMEMBER key member判断member元素是否是集合key的成员。是（true），否则（false）
         178.        System.out.println(redis.sismember("testSet", "s4"));
         179.
         180.        //SCARD key返回集合key的基数(集合中元素的数量)。
         181.        //SMOVE source destination member将member元素从source集合移动到destination集合。
         182.
         183.        //SINTER key [key ...]返回一个集合的全部成员，该集合是所有给定集合的交集。
         184.        //SINTERSTORE destination key [key ...]此命令等同于SINTER，但它将结果保存到destination集合，而不是简单地返回结果集
         185.        //SUNION key [key ...]返回一个集合的全部成员，该集合是所有给定集合的并集。
         186.        //SUNIONSTORE destination key [key ...]此命令等同于SUNION，但它将结果保存到destination集合，而不是简单地返回结果集。
         187.        //SDIFF key [key ...]返回一个集合的全部成员，该集合是所有给定集合的差集 。
         188.        //SDIFFSTORE destination key [key ...]此命令等同于SDIFF，但它将结果保存到destination集合，而不是简单地返回结果集。
         189.           http://my.oschina.net/songhongxu/blog/303762?p=1
         http://www.dataguru.cn/thread-157191-1-1.html
         190.         */

//        redis.hset("website", "google", "1");
//        redis.hset("website", "baidu", "1");
//        redis.hset("website", "sina", "1");
//        redis.hincrBy("website", "sina", 1);
//        System.out.println(redis.hexists("website","sin121a"));
//        Map<String, String> map = redis.hgetAll("website");
//        System.out.println("=============增=============");
//        System.out.println("zset中添加元素element001："+redis.zadd("zset", 7.0, "element001"));
//        System.out.println("zset中添加元素element002："+redis.zadd("zset", 8.0, "element002"));
//        System.out.println("zset中添加元素element003："+redis.zadd("zset", 2.0, "element003"));
//        System.out.println("zset中添加元素element004："+redis.zadd("zset", 3.0, "element004"));
//
//        System.out.println("zset集合中的所有元素："+redis.zrange("zset", 1, -1));//按照权重值排序  System.out.println();
        Map<String, String> map = new HashMap<String, String>();
//        map.put("1","1");
//        redis.hmset("ticket_key",map);
//        redis.hset("ticket_key", "google", "1");
//        redis.expire("ticket_key",DateUtils.diffSeconds());
        //System.out.println(redis.exists("ticket_key"));
//        Map<String,String> map = redis.hgetAll("ticket_key");
//        for(Map.Entry<String,String> entry: map.entrySet())
//
//        {
//
//            String key=entry.getKey().toString();
//
//            String value=entry.getValue().toString();
//            System.out.println(key+"|"+value);
//        }
        for(int i=10000;i<30000;i++)
//        {
//            lanshaTicketService.put1("test"+i,i+1+"");
//        }
        System.out.println(11);
    }

    public static void SortedSet() {
        Jedis redis = new Jedis("192.168.0.172", 6379);//连接redis
////        redis.zadd("zset", 1, "element001");
////        redis.zadd("zset", 2, "element002");
//        redis.zadd("zset", 3, "element0044");
////        redis.zincrby("zset",99,"element006");//增加
////        System.out.println(redis.zrange("zset", 0, -1));
//        System.out.println(redis.zrank("zset", "element006"));//从小到大排序
//        System.out.println(redis.zrevrank("zset", "element0062"));//从大到小排序
//        System.out.println(redis.zscore("zset", "element006")); //获取某一个
//        Double aa = redis.zincrby("zset", 99, "element0061");//增加
//        System.out.println(redis.zrange("zset", 0, -1));
//        System.out.println(aa);

        for(int i=10000;i<30000;i++)
        {
            System.out.println(redis.zadd("ticket_key_zadd",i,"element"+i));//从小到大排序
        }

    }

    public static void main(String[] args) {

        JedisDemo t1 = new JedisDemo();
        t1.SortedSet();
    }

}
