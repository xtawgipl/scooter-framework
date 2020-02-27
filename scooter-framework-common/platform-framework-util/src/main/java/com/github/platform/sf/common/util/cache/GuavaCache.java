package com.github.platform.sf.common.util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;


public class GuavaCache<K, V> {

	private final Cache<K, V> CACHE;

	public GuavaCache() {
		CACHE = CacheBuilder
				.newBuilder()
				.expireAfterAccess(1, TimeUnit.SECONDS)
				.maximumSize(10000)
				.build();
	}

	public GuavaCache(Long expireAfterAccess, Long maximumSize) {
		CACHE = CacheBuilder
				.newBuilder()
				.expireAfterAccess(expireAfterAccess, TimeUnit.SECONDS)
				.maximumSize(maximumSize)
				.build();
	}


	public V get(K key) {
		if(key == null){
			return null;
		}
		return CACHE.getIfPresent(key);
	}

	public void put(K key, V value) {
		CACHE.put(key, value);
	}

	/*public static void main(String[] args) throws InterruptedException {
		GuavaCache<String, String> guavaCache = new GuavaCache<>();
		guavaCache.put("ff", "ffff");
		System.out.println(guavaCache.get("ff"));
		Thread.sleep(2000L);
		System.out.println(guavaCache.get("ff"));
		System.out.println(guavaCache.get("dd"));
	}*/
}
