package com.n26.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n26.Application;
import com.n26.errors.ResponseCode;
import com.n26.exception.ApplicationException;
import com.n26.request.PostTransactionRequest;
import com.n26.response.EmptyJson;
import com.n26.response.TransactionStatistics;
import com.n26.util.TransactionUtils;


@Service
public class TransactionServiceImpl implements TransactionService {
	
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);

	private List<PostTransactionRequest> transactionsList = Collections.synchronizedList(new ArrayList<>());

	public List<PostTransactionRequest> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(List<PostTransactionRequest> transactionsList) {
		this.transactionsList = transactionsList;
	}

	@Override
	public EmptyJson postTransaction(PostTransactionRequest transactionRequest) {
		TransactionUtils.validateTransaction(transactionRequest);
		putTransaction(transactionRequest);
		return new EmptyJson();
	}

	private synchronized void putTransaction(PostTransactionRequest transactionRequest) {
		transactionsList.add(transactionRequest);
		LOG.debug("Data Posted ----> " + transactionsList.size());

	}

	@Override
	public TransactionStatistics getStatistics() {
		TransactionStatistics transactionStatistics = new TransactionStatistics();
		transactionStatistics.setSum(TransactionUtils.getSum(transactionsList));
		transactionStatistics.setAvg(TransactionUtils.getAvg(transactionsList));
		transactionStatistics.setMin(TransactionUtils.getMin(transactionsList));
		transactionStatistics.setMax(TransactionUtils.getMax(transactionsList));
		transactionStatistics.setCount(TransactionUtils.getCount(transactionsList));
		return transactionStatistics;
	}

	@Override
	public synchronized void deleteTransactions() {
		transactionsList.clear();
		LOG.debug("Data Cleared ----> " + transactionsList.size());
		throw new ApplicationException(ResponseCode.TRANSACTIONS_DELETED);
	}

	@Override
	public void clearOldTransactions() {
		setTransactionsList(Collections.synchronizedList(transactionsList.stream()
				.filter(e -> TransactionUtils.isNewTransaction(e)).collect(Collectors.toList())));
	}

}
