package com.cherry.utils.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.util.Slowlog;

public class RedisTemplateImpl implements RedisTemplate{
	private int dbIndex;
	
	public RedisTemplateImpl(int dbIndex) {
		super();
		this.dbIndex = dbIndex;
	}

	public Jedis getRedisConnection(){
		return RedisUtils.getRedisConnection(dbIndex);
	}
	
	public void colseRedisConnection(Jedis connection){
		if(connection != null)
			RedisUtils.colseRedisConnection(connection);
	}
	

	@Override
	public List<String> blpop(int timeout, String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.blpop(timeout, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}



	@Override
	public List<String> brpop(int timeout, String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.brpop(timeout, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String brpoplpush(String source, String destination, int timeout) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.brpoplpush(source, destination, timeout);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<String> configGet(String pattern) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.configGet(pattern);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String configSet(String parameter, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.configSet(parameter, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long decr(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.decr(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long decrBy(String key, long integer) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.decrBy(key, integer);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long del(String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.del(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long del(byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.del(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String echo(String string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.echo(string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Object eval(String script, int keyCount, String... params) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.eval(script, keyCount, params);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Object eval(String script, List<String> keys, List<String> args) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.eval(script, keys, args);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Object eval(String script) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.eval(script);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Object evalsha(String script) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.evalsha(script);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Object evalsha(String sha1, List<String> keys, List<String> args) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.evalsha(sha1, keys, args);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Object evalsha(String sha1, int keyCount, String... params) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.evalsha(sha1, keyCount, params);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Boolean exists(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.exists(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.expire(key,seconds);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.expireAt(key,unixTime);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String get(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.get(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String getSet(String key, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.getSet(key,value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean getbit(String key, long offset) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.getbit(key,offset);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.getrange(key,startOffset,endOffset);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hdel(String key, String... fields) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hdel(key, fields);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean hexists(String key, String field) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hexists(key,field);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String hget(String key, String field) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hget(key,field);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hgetAll(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hincrBy(key, field, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> hkeys(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hkeys(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hlen(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hlen(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hmget(key, fields);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hmset(key,hash);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hset(key, field, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hset(key, field, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<String> hvals(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hvals(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long incr(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.incr(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long incrBy(String key, long integer) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.incrBy(key,integer);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> keys(String pattern) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.keys(pattern);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String lindex(String key, long index) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lindex(key,index);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.linsert(key, where, pivot, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long llen(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.llen(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String lpop(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lpop(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long lpush(String key, String... strings) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lpush(key,strings);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long lpushx(String key, String string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lpushx(key, string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lrange(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long lrem(String key, long count, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lrem(key, count, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String lset(String key, long index, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lset(key, index, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String ltrim(String key, long start, long end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.ltrim(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<String> mget(String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.mget(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long move(String key, int dbIndex) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.move(key, dbIndex);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String mset(String... keysvalues) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.mset(keysvalues);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long msetnx(String... keysvalues) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.msetnx(keysvalues);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String objectEncoding(String string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.objectEncoding(string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long objectIdletime(String string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.objectIdletime(string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long objectRefcount(String string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.objectRefcount(string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long persist(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.persist(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			 jedisconnection.psubscribe(jedisPubSub, patterns);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Long publish(String channel, String message) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.publish(channel, message);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String randomKey() {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.randomKey();
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String rename(String oldkey, String newkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rename(oldkey, newkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long renamenx(String oldkey, String newkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.renamenx(oldkey, newkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String rpop(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpop(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String rpoplpush(String srckey, String dstkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpoplpush(srckey, dstkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long rpush(String key, String... strings) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpush(key, strings);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long rpushx(String key, String string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpushx(key, string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sadd(String key, String... members) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sadd(key, members);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long scard(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.scard(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean scriptExists(String sha1) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.scriptExists(sha1);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<Boolean> scriptExists(String... sha1) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.scriptExists(sha1);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String scriptLoad(String script) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.scriptLoad(script);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> sdiff(String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sdiff(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sdiffstore(String dstkey, String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sdiffstore(dstkey, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}



	@Override
	public String set(String key, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.set(key, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	public Boolean setbit(String key, long offset, boolean value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setbit(key, offset,value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}


	@Override
	public String setex(String key, int seconds, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setex(key, seconds,value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long setnx(String key, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setnx(key, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setrange(key, offset,value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> sinter(String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sinter(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sinterstore(String dstkey, String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sinterstore(dstkey, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean sismember(String key, String member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sismember(key, member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<Slowlog> slowlogGet() {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.slowlogGet();
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<Slowlog> slowlogGet(long entries) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.slowlogGet(entries);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> smembers(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.smembers(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long smove(String srckey, String dstkey, String member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.smove(srckey, dstkey, member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<String> sort(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key,sortingParameters);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sort(String key, SortingParams sortingParameters, String dstkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key, sortingParameters,dstkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sort(String key, String dstkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key, dstkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String spop(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.spop(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String srandmember(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.srandmember(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long srem(String key, String... members) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.srem(key, members);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long strlen(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.strlen(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			 jedisconnection.subscribe(jedisPubSub, channels);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public String substr(String key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.substr(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> sunion(String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sunion(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sunionstore(String dstkey, String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sunionstore(dstkey, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long ttl(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.ttl(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String type(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.type(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String watch(String... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.watch(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zadd(String key, double score, String member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zadd(key, score,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zadd(String key, Map<String,Double> scoreMembers) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zadd(key, scoreMembers);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zcard(String key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zcard(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zcount(String key, double min, double max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zcount(key, min,max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zcount(String key, String min, String max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zcount(key, min,max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zincrby(key, score,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zinterstore(dstkey,sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zinterstore(dstkey, params,sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrange(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min,max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min,max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min,max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min,max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min,max,offset,count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min,
			String max, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min,max,offset,count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeWithScores(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zrank(String key, String member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrank(key,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zrem(String key, String... members) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrem(key,members);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zremrangeByRank(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zremrangeByScore(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zremrangeByScore(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrange(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScore(key, max,min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScore(key, max,min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScoreWithScores(key, max,min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScoreWithScores(key, max, min, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScoreWithScores(key, max,min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeWithScores(key, start,end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zrevrank(String key, String member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrank(key,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Double zscore(String key, String member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zscore(key,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zunionstore(dstkey,sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zunionstore(dstkey, params,sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long append(String key, String value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.append(key,value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String auth(String password) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.auth(password);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] get(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.get(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String set(byte[] key, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.set(key, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean exists(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.exists(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long append(byte[] key, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.append(key, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long decr(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.decr(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long decrBy(byte[] key, long integer) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.decrBy(key,integer);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long expire(byte[] key, int seconds) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.expire(key,seconds);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long expireAt(byte[] key, long unixTime) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.expireAt(key, unixTime);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] getSet(byte[] key, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.getSet(key,value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hdel(byte[] key, byte[]... fields) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hdel(key,fields);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hexists(key,field);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hget(key,field);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hgetAll(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hincrBy(byte[] key, byte[] field, long value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hincrBy(key, field, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> hkeys(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hkeys(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hlen(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hlen(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hmget(key,fields);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hmset(key, hash);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hset(key, field, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hsetnx(key, field, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> hvals(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.hvals(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long incr(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.incr(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long incrBy(byte[] key, long integer) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.incrBy(key,integer);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.keys(pattern);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] lindex(byte[] key, int index) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lindex(key, index);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long llen(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.llen(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] lpop(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lpop(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long lpush(byte[] key, byte[]... strings) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lpush(key,strings);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> lrange(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lrange(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long lrem(byte[] key, int count, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lrem(key, count, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String lset(byte[] key, int index, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lset(key, index, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String ltrim(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.ltrim(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> mget(byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.mget(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long move(byte[] key, int dbIndex) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.move(key,dbIndex);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String mset(byte[]... keysvalues) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.mset(keysvalues);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long msetnx(byte[]... keysvalues) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.msetnx(keysvalues);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String rename(byte[] oldkey, byte[] newkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rename(oldkey, newkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long renamenx(byte[] oldkey, byte[] newkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.renamenx(oldkey, newkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] rpop(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpop(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] rpoplpush(byte[] srckey, byte[] dstkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpoplpush(srckey, dstkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long rpush(byte[] key, byte[]... strings) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpush(key, strings);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sadd(byte[] key, byte[]... members) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sadd(key, members);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setex(key, seconds, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long setnx(byte[] key, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setnx(key, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> smembers(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.smembers(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] substr(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.substr(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long ttl(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.ttl(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String type(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.type(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> blpop(int timeout, byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.blpop(timeout, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}


	@Override
	public Long scard(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.scard(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> sdiff(byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sdiff(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sdiffstore(byte[] dstkey, byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sdiffstore(dstkey, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> sinter(byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sinter(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sinterstore(byte[] dstkey, byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sinterstore(dstkey, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean sismember(byte[] key, byte[] member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sismember(key, member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long smove(byte[] srckey, byte[] dstkey, byte[] member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.smove(srckey, dstkey, member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> sort(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key, sortingParameters);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sort(byte[] key, SortingParams sortingParameters, byte[] dstkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key, sortingParameters, dstkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sort(byte[] key, byte[] dstkey) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sort(key, dstkey);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] spop(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.spop(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] srandmember(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.srandmember(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long srem(byte[] key, byte[]... members) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.srem(key,members);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> sunion(byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sunion(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long sunionstore(byte[] dstkey, byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.sunionstore(dstkey,keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public String watch(byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.watch(keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zadd(key, score, member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zadd(byte[] key, Map< byte[],Double> scoreMembers) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zadd(key, scoreMembers);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zcard(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zcard(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zcount(byte[] key, double min, double max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zcount(key, min, max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zcount(byte[] key, byte[] min, byte[] max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zcount(key, min, max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zincrby(key, score, member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrange(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrange(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeWithScores(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zrank(byte[] key, byte[] member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrank(key,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zrem(byte[] key, byte[]... members) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrem(key,members);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrevrange(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrange(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeWithScores(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zrevrank(byte[] key, byte[] member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrank(key,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Double zscore(byte[] key, byte[] member) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zscore(key,member);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> brpop(int timeout, byte[]... keys) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.blpop(timeout, keys);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] brpoplpush(byte[] source, byte[] destination, int timeout) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.brpoplpush(source, destination, timeout);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public List<byte[]> configGet(byte[] pattern) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.configGet(pattern);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] configSet(byte[] parameter, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.configSet(parameter, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public byte[] echo(byte[] string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.echo(string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean getbit(byte[] key, long offset) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.getbit(key, offset);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot,byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.linsert(key, where, pivot, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long lpushx(byte[] key, byte[] string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.lpush(key,string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long persist(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.persist(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long rpushx(byte[] key, byte[] string) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.rpush(key, string);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Boolean setbit(byte[] key, long offset, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setbit(key, offset, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long setrange(byte[] key, long offset, byte[] value) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.setrange(key, offset, value);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}
	@Override
	public byte[] getrange(byte[] key, long startOffset, long endOffset) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.getrange(key, startOffset, endOffset);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long strlen(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.strlen(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zinterstore(byte[] dstkey, byte[]... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zinterstore(dstkey, sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zinterstore(byte[] dstkey, ZParams params, byte[]... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zinterstore(dstkey, params, sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScore(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min, max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min, max);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min,double max, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min,byte[] max, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrangeByScoreWithScores(key, min, max, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zremrangeByRank(byte[] key, int start, int end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zremrangeByRank(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zremrangeByScore(byte[] key, double start, double end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zremrangeByScore(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zremrangeByScore(key, start, end);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScore(key, max, min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScore(key, max, min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScore(key, max, min,offset,count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min,
			int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScore(key, max, min, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
			double min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScoreWithScores(key,max,min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
			double min, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScoreWithScores(key, max, min, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max,byte[] min, int offset, int count) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScoreWithScores(key, max, min, offset, count);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max,byte[] min) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zrevrangeByScoreWithScores(key, max, min);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zunionstore(byte[] dstkey, byte[]... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zunionstore(dstkey, sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long zunionstore(byte[] dstkey, ZParams params, byte[]... sets) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.zunionstore(dstkey, params, sets);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Object eval(byte[] script, byte[] keyCount, byte[]... params) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.eval(script, keyCount, params);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.eval(script, keys, args);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}




	@Override
	public byte[] objectEncoding(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.objectEncoding(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long objectIdletime(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.objectIdletime(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public Long objectRefcount(byte[] key) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.objectRefcount(key);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

	@Override
	public void psubscribe(BinaryJedisPubSub jedisPubSub, byte[]... patterns) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			 jedisconnection.psubscribe(jedisPubSub, patterns);
		}finally{
			colseRedisConnection(jedisconnection);
		}
		
	}

	@Override
	public Long publish(byte[] channel, byte[] message) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.publish(channel, message);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}


	@Override
	public List<Long> scriptExists(byte[]... sha1) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			return jedisconnection.scriptExists(sha1);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}


	@Override
	public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {
		Jedis  jedisconnection = getRedisConnection();
		try{
			 jedisconnection.subscribe(jedisPubSub, channels);
		}finally{
			colseRedisConnection(jedisconnection);
		}
	}

}
