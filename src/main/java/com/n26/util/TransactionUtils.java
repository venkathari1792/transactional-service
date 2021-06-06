package com.n26.util;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.n26.Application;
import com.n26.errors.ResponseCode;
import com.n26.exception.ApplicationException;
import com.n26.request.PostTransactionRequest;

public class TransactionUtils {
	
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);

	public synchronized static void validateTransaction(PostTransactionRequest transactionRequest) {
		if (null == transactionRequest.getAmount() || null == transactionRequest.getTimestamp()) {
			throw new ApplicationException(ResponseCode.JSON_MAPPING_ERROR);
		}
		if (secondsPassed(transactionRequest) > 60) {
			throw new ApplicationException(ResponseCode.OLD_TRANSACTION);
		}
		if (secondsPassed(transactionRequest) < 0) {
			throw new ApplicationException(ResponseCode.PARSE_EXCEPTION);
		}

	}

	public synchronized static Double getSum(List<PostTransactionRequest> transactionsList) {
		return transactionsList.stream().filter(e -> isNewTransaction(e))
				.collect(Collectors.summingDouble(PostTransactionRequest::getAmount));
	}

	public synchronized static Double getAvg(List<PostTransactionRequest> transactionsList) {
		return transactionsList.stream().filter(e -> isNewTransaction(e))
				.collect(Collectors.averagingDouble(PostTransactionRequest::getAmount));
	}

	public synchronized static Double getMin(List<PostTransactionRequest> transactionsList) {
		Optional<Double> minValue = transactionsList.stream().filter(e -> isNewTransaction(e))
				.map(PostTransactionRequest::getAmount).collect(Collectors.minBy(Comparator.naturalOrder()));
		if (minValue.isPresent()) {
			return minValue.get();
		}
		return null;
	}

	public synchronized static Double getMax(List<PostTransactionRequest> transactionsList) {

		Optional<Double> maxValue = transactionsList.stream().filter(e -> isNewTransaction(e))
				.map(PostTransactionRequest::getAmount).collect(Collectors.maxBy(Comparator.naturalOrder()));
		if (maxValue.isPresent()) {
			return maxValue.get();
		}
		return null;

	}

	public synchronized static Long getCount(List<PostTransactionRequest> transactionsList) {
		return (long) transactionsList.stream().filter(e -> isNewTransaction(e)).count();
	}

	public synchronized static boolean isNewTransaction(PostTransactionRequest request) {
		return secondsPassed(request) < 60;
	}

	public synchronized static long secondsPassed(PostTransactionRequest request) {
		Instant currentTime = Instant.now();
		Instant transactionTime = Instant.ofEpochMilli(request.getTimestamp().getTime());
		LOG.debug("Current Time ----> " + currentTime);
		LOG.debug("Transaction Time ----> " + transactionTime);
		long seconds = (currentTime.toEpochMilli() - transactionTime.toEpochMilli()) / 1000;
		LOG.debug("Seconds Passed ----> " + seconds);
		return seconds;
	}
}
