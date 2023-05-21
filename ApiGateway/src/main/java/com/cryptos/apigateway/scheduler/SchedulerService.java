package com.cryptos.apigateway.scheduler;

import com.cryptos.apigateway.jwt.JwtBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final JwtBlacklistService jwtBlacklistService;

	public SchedulerService(JwtBlacklistService jwtBlacklistService)
	{
		this.jwtBlacklistService = jwtBlacklistService;
	}
	
	@Scheduled(cron = "0 4 * * * ?")
	public void deleteExpiredJwtFromRedisBlacklist()
	{
		logger.info("Deleting expired JWT from blacklist");
		jwtBlacklistService.deleteExpiredTokens();
	}
}
