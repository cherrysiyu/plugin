package com.cherry.utils.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.util.Slowlog;

public interface RedisTemplate {

	public String set(String key, String value);
	
	public String set( byte[] key, byte[] value);

	public String get(String key);
	
	public byte[] get(byte[] key);

	public Boolean exists(String key);
	
	public Boolean exists(byte[] key);

	public Long del(String... keys);

	public Long del(byte[]... keys);

	public String type(String key);
	
	public String type(byte[] key);

	public Set<String> keys(String pattern);
	
	public Set<byte[]> keys(byte[] pattern);

	public String randomKey();

	public String rename (byte[] oldkey,byte[] newkey);
	
	public String rename(String oldkey, String newkey);

	public Long renamenx(String oldkey, String newkey);
	
	public Long renamenx(byte[] oldkey, byte[] newkey);

	public Long expire(String key, int seconds);
	
	public Long expire( byte[] key, int seconds);

	public Long expireAt(String key, long unixTime);
	
	public Long expireAt(byte[] key, long unixTime);

	public Long ttl(String key);
	
	public Long ttl(byte[] key);

	public Long move(String key, int dbIndex);
	
	public Long move( byte[] key, int dbIndex);

	public String getSet(String key, String value);
	
	public byte[] getSet(byte[] key, byte[] value);

	public List<String> mget(String... keys);
	
	public List<byte[]> mget(byte[]... keys);

	public Long setnx(String key, String value);
	
	public Long setnx(byte[] key, byte[] value);

	public String setex(String key, int seconds, String value);
	
	public String setex(byte[] key, int seconds, byte[] value);

	public String mset(String... keysvalues);
	public String mset(byte[]... keysvalues);

	public Long msetnx(String... keysvalues);
	public Long msetnx(byte[]... keysvalues);

	public Long decrBy(String key, long integer);
	public Long decrBy(byte[] key, long integer);

	public Long decr(String key);
	public Long decr(byte[] key);

	public Long incrBy(String key, long integer);
	public Long incrBy(byte[] key, long integer);

	public Long incr(String key);
	public Long incr(byte[] key);

	public Long append(String key, String value);
	public Long append(byte[] key, byte[] value);

	public String substr(String key, int start, int end);
	public byte[] substr(byte[] key, int start, int end);

	public Long hset(String key, String field, String value);
	public Long hset(byte[] key, byte[] field, byte[] value);

	public String hget(String key, String field);
	public byte[] hget(byte[] key, byte[] field);

	public Long hsetnx(String key, String field, String value);
	public Long hsetnx( byte[] key,  byte[] field,  byte[] value);

	public String hmset(String key, Map<String, String> hash);
	public String hmset(byte[] key, Map<byte[], byte[]> hash);

	public List<String> hmget(String key, String... fields);
	public List< byte[]> hmget( byte[] key,  byte[]... fields);

	public Long hincrBy(String key, String field, long value);
	public Long hincrBy(byte[] key, byte[] field, long value);

	public Boolean hexists(String key, String field);
	public Boolean hexists(byte[] key, byte[] field);

	public Long hdel(String key, String... fields);
	public Long hdel(byte[] key, byte[]... fields);

	public Long hlen(String key);
	public Long hlen(byte[] key);

	public Set<String> hkeys(String key);
	public Set<byte[]> hkeys(byte[] key);

	public List<String> hvals(String key);
	public List<byte[]> hvals(byte[] key);

	public Map<String, String> hgetAll(String key);
	public Map<byte[], byte[]> hgetAll(byte[] key);

	public Long rpush(String key, String... strings);
	public Long rpush(byte[] key, byte[]... strings);

	public Long lpush(String key, String... strings);
	public Long lpush(byte[] key, byte[]... strings);

	public Long llen(String key);
	public Long llen(byte[] key);

	public List<String> lrange(String key, long start,long end);
	public List<byte[]> lrange(byte[] key, int start,int end);

	public String ltrim(String key, long start, long end);
	public String ltrim(byte[] key, int start, int end);

	public String lindex(String key, long index);
	public byte[] lindex(byte[] key, int index);

	public String lset(String key, long index, String value);
	public String lset(byte[] key, int index, byte[] value);

	public Long lrem(String key, long count, String value);
	public Long lrem(byte[] key, int count, byte[] value);
	
	public String lpop(String key);
	public byte[] lpop(byte[] key);

	public String rpop(String key);
	public byte[] rpop(byte[] key);

	public String rpoplpush(String srckey, String dstkey);
	public byte[] rpoplpush(byte[] srckey, byte[] dstkey);

	public Long sadd(String key, String... members);
	public Long sadd(byte[] key, byte[]... members);

	public Set<String> smembers(String key);
	public Set<byte[]> smembers(byte[] key);

	public Long srem(String key, String... members);
	public Long srem(byte[] key, byte[]... members);

	public String spop(String key);
	public byte[] spop(byte[] key);

	public Long smove(String srckey, String dstkey,String member);
	public Long smove( byte[] srckey, byte[] dstkey,byte[] member);

	public Long scard(String key);
	public Long scard(byte[] key);

	public Boolean sismember(String key, String member);
	public Boolean sismember(byte[] key, byte[] member);

	public Set<String> sinter(String... keys);
	public Set<byte[]> sinter(byte[]... keys);

	public Long sinterstore(String dstkey, String... keys);
	public Long sinterstore(byte[] dstkey, byte[]... keys);

	public Set<String> sunion(String... keys);
	public Set<byte[]> sunion(byte[]... keys);

	public Long sunionstore(String dstkey, String... keys);
	public Long sunionstore(byte[] dstkey, byte[]... keys);

	public Set<String> sdiff(String... keys);
	public Set<byte[]> sdiff(byte[]... keys);

	public Long sdiffstore(String dstkey, String... keys);
	public Long sdiffstore(byte[] dstkey, byte[]... keys);

	public String srandmember(String key);
	public byte[] srandmember(byte[] key);

	public Long zadd(String key, double score, String member);
	public Long zadd(byte[] key, double score, byte[] member);

	public Long zadd(String key, Map<String,Double> scoreMembers);
	public Long zadd(byte[] key, Map<byte[],Double> scoreMembers);

	public Set<String> zrange(String key, long start, long end);
	public Set<byte[]> zrange(byte[] key, int start, int end);

	public Long zrem(String key, String... members);
	public Long zrem(byte[] key, byte[]... members);

	public Double zincrby(String key, double score,String member);
	public Double zincrby(byte[] key, double score,byte[] member);

	public Long zrank(String key, String member);
	public Long zrank(byte[] key, byte[] member);

	public Long zrevrank(String key, String member);
	public Long zrevrank(byte[] key, byte[] member);

	public Set<String> zrevrange(String key, long start,long end);
	public Set<byte[]> zrevrange(byte[] key, int start,int end);

	public Set<Tuple> zrangeWithScores(String key, long start,long end);
	public Set<Tuple> zrangeWithScores(byte[] key, int start,int end);

	public Set<Tuple> zrevrangeWithScores(String key, long start,long end);
	public Set<Tuple> zrevrangeWithScores(byte[] key, int start,int end);

	public Long zcard(String key);
	public Long zcard(byte[] key);

	public Double zscore(String key, String member);
	public Double zscore(byte[] key, byte[] member);

	public String watch(String... keys);
	public String watch(byte[]... keys);

	public List<String> sort(String key);
	public List<byte[]> sort(byte[] key);

	public List<String> sort(String key,SortingParams sortingParameters);
	public List<byte[]> sort(byte[] key,SortingParams sortingParameters);

	public List<String> blpop(int timeout, String... keys);
	public List<byte[]> blpop(int timeout, byte[]... keys);

	public Long sort(String key, SortingParams sortingParameters,String dstkey);
	public Long sort(byte[] key, SortingParams sortingParameters,byte[] dstkey);

	public Long sort(String key, String dstkey);
	public Long sort(byte[] key, byte[] dstkey);

	public List<String> brpop(int timeout, String... keys);
	public List<byte[]> brpop(int timeout, byte[]... keys);

	public Long zcount(String key, double min, double max);
	public Long zcount(byte[] key, double min, double max);

	public Long zcount(String key, String min, String max);
	public Long zcount(byte[] key, byte[] min, byte[] max);

	public Set<String> zrangeByScore(String key, double min,double max);
	public Set<byte[]> zrangeByScore(byte[] key, double min,double max);

	public Set<String> zrangeByScore(String key, String min,String max);
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min,byte[] max);

	public Set<String> zrangeByScore(String key, double min,double max, int offset, int count);
	public Set<byte[]> zrangeByScore(byte[] key, double min,double max, int offset, int count);

	public Set<String> zrangeByScore(String key, String min,String max, int offset, int count);
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min,byte[] max, int offset, int count);

	public Set<Tuple> zrangeByScoreWithScores(String key,double min, double max);
	public Set<Tuple> zrangeByScoreWithScores(byte[] key,double min, double max);

	public Set<Tuple> zrangeByScoreWithScores(String key,String min, String max);
	public Set<Tuple> zrangeByScoreWithScores(byte[] key,byte[] min, byte[] max);

	public Set<Tuple> zrangeByScoreWithScores(String key,double min, double max, int offset,int count);
	public Set<Tuple> zrangeByScoreWithScores(byte[] key,double min, double max, int offset,int count);

	public Set<Tuple> zrangeByScoreWithScores(String key,String min, String max, int offset,int count);
	public Set<Tuple> zrangeByScoreWithScores(byte[] key,byte[] min, byte[] max, int offset,int count);

	public Set<String> zrevrangeByScore(String key, double max,double min);
	public Set<byte[]> zrevrangeByScore(byte[] key, double max,double min);

	public Set<String> zrevrangeByScore(String key, String max,String min);
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max,byte[] min);

	public Set<String> zrevrangeByScore(String key, double max,double min, int offset, int count);
	public Set<byte[]> zrevrangeByScore(byte[] key, double max,double min, int offset, int count);

	public Set<Tuple> zrevrangeByScoreWithScores(String key,double max, double min);
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key,double max, double min);

	public Set<Tuple> zrevrangeByScoreWithScores(String key,double max, double min, int offset,int count);
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key,double max, double min, int offset,int count);

	public Set<Tuple> zrevrangeByScoreWithScores(String key,String max, String min, int offset,int count);
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key,byte[] max, byte[] min, int offset,int count);

	public Set<String> zrevrangeByScore(String key, String max,String min, int offset, int count);
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max,byte[] min, int offset, int count);

	public Set<Tuple> zrevrangeByScoreWithScores(String key,String max, String min);
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key,byte[] max, byte[] min);

	public Long zremrangeByRank(String key, long start,long end);
	public Long zremrangeByRank(byte[] key, int start,int end);
	
	public Long zremrangeByScore(String key, double start,double end);
	public Long zremrangeByScore(byte[] key, double start,double end);

	public Long zremrangeByScore(String key, String start,String end);
	public Long zremrangeByScore(byte[] key, byte[] start,byte[] end);

	public Long zunionstore(String dstkey, String... sets);
	public Long zunionstore(byte[] dstkey, byte[]... sets);

	public Long zunionstore(String dstkey, ZParams params,String... sets);
	public Long zunionstore(byte[] dstkey, ZParams params,byte[]... sets);

	public Long zinterstore(String dstkey, String... sets);
	public Long zinterstore(byte[] dstkey, byte[]... sets);

	public Long zinterstore(String dstkey, ZParams params,String... sets);
	public Long zinterstore(byte[] dstkey, ZParams params,byte[]... sets);

	public Long strlen(String key);
	public Long strlen(byte[] key);

	public Long lpushx(String key, String string);
	public Long lpushx(byte[] key, byte[] string);

	public Long persist(String key);
	public Long persist(byte[] key);

	public Long rpushx(String key, String string);
	public Long rpushx(byte[] key, byte[] string);

	public String echo(String string);
	public byte[] echo(byte[] string);

	public Long linsert(String key, LIST_POSITION where,String pivot, String value);
	public Long linsert(byte[] key, LIST_POSITION where,byte[] pivot, byte[] value);

	public String brpoplpush(String source, String destination, int timeout);
	public byte[] brpoplpush(byte[] source, byte[] destination, int timeout);

	public Boolean setbit(String key, long offset, boolean value);
	public Boolean setbit(byte[] key, long offset, byte[] value);

	public Boolean getbit(String key, long offset);
	public Boolean getbit(byte[] key, long offset);

	public Long setrange(String key, long offset, String value);
	public Long setrange(byte[] key, long offset, byte[] value);

	public String getrange(String key, long startOffset, long endOffset);
	public byte[] getrange(byte[] key, long startOffset, long endOffset);

	public List<String> configGet(String pattern);
	public List<byte[]> configGet(byte[] pattern);


	public String configSet(String parameter, String value);
	public byte[] configSet(byte[] parameter, byte[] value);

	public Object eval(String script, int keyCount, String... params);
	public Object eval(byte[] script, byte[] keyCount, byte[]... params);

	public void subscribe(JedisPubSub jedisPubSub,String... channels);
	public void subscribe(BinaryJedisPubSub jedisPubSub,byte[]... channels);

	public Long publish(String channel, String message);
	public Long publish(byte[] channel, byte[] message);

	public void psubscribe(JedisPubSub jedisPubSub,String... patterns);
	public void psubscribe(BinaryJedisPubSub jedisPubSub,byte[]... patterns);

	public Object eval(String script, List<String> keys, List<String> args);
	public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args);

	public Object eval(String script);

	public Object evalsha(String script);

	public Object evalsha(String sha1, List<String> keys, List<String> args);

	public Object evalsha(String sha1, int keyCount, String... params);

	public Boolean scriptExists(String sha1);

	public List<Boolean> scriptExists(String... sha1);
	public List<Long> scriptExists(byte[]... sha1);

	public String scriptLoad(String script);

	public List<Slowlog> slowlogGet();

	public List<Slowlog> slowlogGet(long entries);

	public Long objectRefcount(String key);
	public Long objectRefcount(byte[] key);

	public String objectEncoding(String string);
	public byte[] objectEncoding(byte[] string);
	

	public Long objectIdletime(String string);
	public Long objectIdletime(byte[] string);

	public String auth(String password); 
	

}
