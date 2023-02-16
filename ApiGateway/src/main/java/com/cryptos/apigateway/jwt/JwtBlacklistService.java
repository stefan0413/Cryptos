package com.cryptos.apigateway.jwt;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JwtBlacklistService
{
	private final RedisTemplate<String, String> redisTemplate;
	private final JwtUtils jwtUtils;

	public JwtBlacklistService(RedisTemplate<String, String> redisTemplate, JwtUtils jwtUtils)
	{
		this.redisTemplate = redisTemplate;
		this.jwtUtils = jwtUtils;
	}

	public void addTokenToBlacklist(String token) {
		redisTemplate.opsForSet().add("blacklist", token);
	}

	public boolean isTokenBlacklisted(String token) {
		return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("blacklist", token));
	}

	public void deleteExpiredTokens() {
		Set<String> tokens = redisTemplate.opsForSet().members("blacklist");
		if (tokens != null) {
			for (String token : tokens) {
				if (jwtUtils.isTokenExpired(token)) {
					redisTemplate.opsForSet().remove("blacklist", token);
				}
			}
		}
	}
}
