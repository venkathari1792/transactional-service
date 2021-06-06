package com.n26.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.n26.service.TransactionService;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	@Autowired
	private TransactionService transactionService;

	@Scheduled(fixedRate = 60000)
	public void clearList() {
		transactionService.clearOldTransactions();
	}

}
