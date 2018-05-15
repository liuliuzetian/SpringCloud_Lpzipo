package com.lpzipo.common.redis;

import com.lpzipo.common.util.LoggerUtil;
import com.lpzipo.common.util.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RedisClient {

    private static JedisPool jedisPool;

    /**
     * 可通过spring自动注入进来，也可手动进行设置
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool){
        RedisClient.jedisPool = jedisPool;
    }

    private static Jedis getJedis(){
       return jedisPool.getResource();
    }

    /**-------------------------------操作Redis服务器的方法-----------------------------------------*/

    /**
     *使用客户端向 当前Redis服务器发送一个 PING ，如果服务器运作正常的话，会返回一个 PONG。
     * 通常用于测试与服务器的连接是否仍然生效，或者用于测量延迟值。
     *
     * @return 如果连接正常就返回一个 PONG ，否则返回一个连接错误。
     */
    public static String ping(){
        String result = null;
        Jedis jedis = getJedis();
        if(jedis == null){
            return  result;
        }
        result = jedis.ping();
        jedis.close();
        return  result;
    }

    /**
     * 切换到当前Redis指定的数据库，数据库索引号index用数字值指定，以0作为起始索引值。默认使用0号数据库。
     * @param index
     * @return
     */
    public static String select(int index){
        String result = null;
        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }
        result = jedis.select(index);
        jedis.close();
        return result;
    }

    /**
     * 返回关于redis服务器各种信息和统计数值
     * 返回参数详情见：http://redisdoc.com/server/info.html
     * @return
     */
    public static String info(){
        String result = null;
        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.info();
        } catch (Exception e) {
        } finally {
            jedis.close();
        }
        return result;
    }

    /**================Redis key操作================================*/

    /**
     * 查找当前数据库中所有符合给定模式 pattern 的 key 。 <br>
     * KEYS * 匹配数据库中所有 key 。 <br>
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。 <br>
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。 <br>
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。 <br>
     * 特殊符号用 \ 隔开<br>
     *
     * 注意：KEYS 的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，如果你需要从一个数据集中查找特定的 key， 你最好还是用
     * Redis 的集合结构(set)来代替。
     *
     *  网易NCR不支持此命令
     *
     * @param pattern
     *            符合给定模式的 key 列表。
     */
    public static Set<String> keys(String pattern){
        Jedis jedis = getJedis();
        Set<String> result = null;
        if(jedis == null){
            return  result;
        }
        try {
            result = jedis.keys(pattern);
        }catch (Exception e){
            LoggerUtil.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 返回 key 所储存的值的类型。
     *
     * @param key
     * @return none(key不存在)、string(字符串)、list(列表)、set(集合)、zset(有序集)、hash(哈希表)
     */
    public static String type(String key) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.type(key);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     *
     * @param key
     * @param seconds
     *            以秒为单位的时间
     * @return result 设置成功返回 1 。 当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的
     *         Redis 中你尝试更新 key 的生存时间)，返回 0 。
     */
    public static Long expire(String key, int seconds) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX
     * 时间戳(unix timestamp)。 它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
     *
     * @param key
     * @param unixTime
     *            UNIX时间戳
     * @return result 如果生存时间设置成功，返回 1 。 当 key 不存在或没办法设置生存时间，返回 0 。
     */
    public static Long expireAt(String key, long unixTime) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.expireAt(key, unixTime);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 移除当前数据库中给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的
     * key )。
     *
     * @param key
     * @return result 当生存时间移除成功时，返回 1 . 如果 key 不存在或 key 没有设置生存时间，返回 0 。
     */
    public static Long persist(String key) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.persist(key);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 删除当前数据库中给定的一个或多个 key 。不存在的 key 会被忽略。
     *
     * @param keys
     * @return result 被删除 key 的数量。
     */
    public static Long del(String... keys) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.del(keys);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 检查给定 key 是否存在。
     *
     * @param key
     * @return result 若 key 存在，返回 1 ，否则返回 0 。
     */
    public static Boolean exists(String key) {
        Boolean result = false;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /** ========================对存储结构为String（字符串）类型的操作======================== */
    /**
     * 将字符串值 value 关联到 key 。如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     *
     * @param key
     * @param value
     * @return result SET在设置操作成功完成时，才返回 OK ，如果设置操作未执行，那么命令返回空批量回复（NULL Bulk
     *         Reply）。
     */
    public static String set(String key, String value) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.set(key, value);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。 <br>
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     *
     * @param key
     * @param value
     *            值
     * @return 设置成功，返回 1 ； 设置失败，返回 0 。
     */
    public static Long setnx(String key, String value) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 尝试获取分布式锁
     * @param key 锁
     * @param vallue 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean setnxLock(String key, String vallue, long expireTime) {
        Boolean res = false;
        final String LOCK_SUCCESS = "OK";
        final String SET_IF_NOT_EXIST = "NX";
        final String SET_WITH_EXPIRE_TIME = "EX";
        Jedis jedis = getJedis();
        if (jedis == null) {
            return res;
        }

        try {
            String result = jedis.set(key, vallue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                res = true;
            }
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param value 请求标识
     * @return 是否释放成功
     */
    public static boolean setnxUnLock(String lockKey, String value) {
        Boolean res = false;
        Jedis jedis = getJedis();
        if (jedis == null) {
            return res;
        }
        final Long RELEASE_SUCCESS = 1L;
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(value));
            if (RELEASE_SUCCESS.equals(result)) {
                res = true;
            }
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return res;
    }

    /** ========================存储对象======================== */

    /**
     * 将对象序列化 存储到redis 对象需实现序列化接口
     * @param key
     * @param value
     */
    public static String setSerialized(Object key ,Object value){
        Jedis jedis = getJedis();
        String result = null;
        if (jedis == null) {
            return null;
        }
        try {
            jedis.select(0);
            byte[] skey = SerializeUtil.serialize(key);
            byte[] svalue = SerializeUtil.serialize(value);
            result = jedis.set(skey, svalue);
        } catch (Exception e) {
            LoggerUtil.error("setSerialized objec error :",e);
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 将去除的byte数组反序列化成对象
     * @param <T>
     * @param key
     * @param requiredType
     * @return
     */
    public static <T> T getSerialized(String key , Class<T>...requiredType){
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            jedis.select(0);
            byte[] skey = SerializeUtil.serialize(key);
            return SerializeUtil.deserialize(jedis.get(skey),requiredType);
        } catch (Exception e) {
            LoggerUtil.error("deSerialized objec error :",e);
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     *
     * @param key
     * @param seconds
     *            以秒为单位的时间
     * @return result 设置成功返回 1 。 当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的
     *         Redis 中你尝试更新 key 的生存时间)，返回 0 。
     */
    public static Long expireSerialized(String key, int seconds) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            byte[] skey = SerializeUtil.serialize(key);
            result = jedis.expire(skey, seconds);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 序列化后的key 删除
     * @param key
     * @param key
     */
    public static Long delOfSerialize(Object key){
        Jedis jedis = getJedis();
        Long result = null;
        if (jedis == null) {
            return null;
        }
        try {
            jedis.select(0);
            byte[] skey = SerializeUtil.serialize(key);
            result = jedis.del(skey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }
    /**
     * 返回 key 所关联的字符串值。GET 只能用于处理字符串值。
     *
     * @param key
     * @return result 当 key 不存在时，返回 nil ，否则，返回 key 的值。 如果 key 不是字符串类型，那么返回一个错误。
     */
    public static String get(String key) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }


    /** ========================对存储结构为List（列表）类型的操作======================== */

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。 如果有多个 value 值，那么各个 value
     * 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c
     * ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c 。 <br>
     * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 <br>
     * 当 key 存在但不是列表类型时，返回一个错误。
     *
     * @param key
     * @param string
     *            一个或多个string值
     * @return result 执行 RPUSH 操作后，表的长度。
     */
    public static Long rpush(String key, String... string) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.rpush(key, string);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。 <br>
     * 和 RPUSH 命令相反，当 key 不存在时， RPUSHX 命令什么也不做。
     *
     * @param key
     * @param string
     *            一个或多个string值
     * @return result RPUSHX 命令执行之后，表的长度。
     */
    public static Long rpushx(String key, String... string) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.rpushx(key, string);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }
    /**
     * 将一个或多个值 value 插入到列表 key 的表头 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
     * 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH
     * mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。<br>
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 <br>
     * 当 key 存在但不是列表类型时，返回一个错误。
     *
     * @param key
     * @param string
     *            一个或多个string值
     * @return result 执行 LPUSH 命令后，列表的长度。
     */
    public static Long lpush(String key, String... string) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.lpush(key, string);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。<br>
     * 和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。
     *
     * @param key
     * @param string
     *            一个或多个string值
     * @return result 执行 LPUSHX 命令后，列表的长度。
     */
    public static Long lpushx(String key, String... string) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.lpushx(key, string);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 返回列表 key 的长度。<br>
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 .<br>
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key
     * @return result 列表 key 的长度。
     */
    public static Long llen(String key) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.llen(key);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。<br>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br>
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。<br>
     * 超出范围的下标值不会引起错误。
     *
     * @param key
     * @param start
     *            开始位置
     * @param end
     *            结束如果为负数，则尾部开始计算
     * @return result 一个列表，包含指定区间内的元素。
     */
    public static List<String> lrange(String key, long start, long end) {
        List<String> result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。<br>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br>
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。<br>
     * 当 key 不是列表类型时，返回一个错误。<br>
     * 超出范围的下标值不会引起错误。
     *
     * @param key
     * @param start
     *            开始位置
     * @param end
     *            结束位置，则尾部开始计算
     * @return result 命令执行成功时，返回 ok 。
     */
    public static String ltrim(String key, long start, long end) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 返回列表 key 中，下标为 index 的元素。<br>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br>
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。<br>
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key
     * @param index
     *            下标
     * @return result 列表中下标为 index 的元素。 如果 index 参数的值不在列表的区间范围内，返回 nil 。
     */
    public static String lindex(String key, long index) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.lindex(key, index);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value 。<br>
     * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。<br>
     * 关于列表下标的更多信息，请参考 LINDEX 命令。
     *
     * @param key
     * @param index
     *            下标
     * @param value
     *            值
     * @return result 操作成功返回 ok ，否则返回错误信息。
     */
    public static String lset(String key, long index, String value) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.lset(key, index, value);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。<br>
     * count 的值可以是以下几种：<br>
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。<br>
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。<br>
     * count = 0 : 移除表中所有与 value 相等的值。
     *
     * @param key
     * @param count
     *            要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param value
     *            要匹配删除的值
     * @return result 被移除元素的数量。 因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM
     *         命令总是返回 0 。
     */
    public static Long lrem(String key, long count, String value) {
        Long result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.lrem(key, count, value);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }

    /**
     * 移除并返回列表 key 的头元素。
     *
     * @param key
     * @return result 列表的头元素。 当 key 不存在时，返回 nil 。
     */
    public static String lpop(String key) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.lpop(key);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }
    /**
     * 移除并返回列表 key 的尾元素。
     *
     * @param key
     * @return result 列表的尾元素。 当 key 不存在时，返回 nil 。
     */
    public static String rpop(String key) {
        String result = null;

        Jedis jedis = getJedis();
        if (jedis == null) {
            return result;
        }

        try {
            result = jedis.rpop(key);
        } catch (Exception e) {
            LoggerUtil.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }

        return result;
    }


}
