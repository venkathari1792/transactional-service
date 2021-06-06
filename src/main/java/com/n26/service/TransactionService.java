package com.n26.service;

import com.n26.request.PostTransactionRequest;
import com.n26.response.EmptyJson;
import com.n26.response.TransactionStatistics;

public interface TransactionService {

	public EmptyJson postTransaction(PostTransactionRequest transactionRequest);

	public TransactionStatistics getStatistics();

	public void deleteTransactions();
	
	public void clearOldTransactions();
	
	
}
