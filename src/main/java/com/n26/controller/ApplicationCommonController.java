package com.n26.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.constants.ApplicationEndpoint;
import com.n26.errors.ResponseCode;
import com.n26.exception.ApplicationException;
import com.n26.request.PostTransactionRequest;
import com.n26.response.EmptyJson;
import com.n26.response.TransactionStatistics;
import com.n26.service.TransactionService;

@RestController
public class ApplicationCommonController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = ApplicationEndpoint.TRANSACTIONS)
	public EmptyJson postTransactions(@RequestBody PostTransactionRequest transactionRequest,
			HttpServletResponse response) {
		try {
			EmptyJson postResponse = transactionService.postTransaction(transactionRequest);
			response.setStatus(ResponseCode.TRANSACTION_POSTED.getStatus().value());
			return postResponse;
		} catch (ApplicationException ex) {
			throw ex;
		}
	}

	@GetMapping(value = ApplicationEndpoint.STATISTICS)
	public TransactionStatistics getStatistics() {
		return transactionService.getStatistics();
	}

	@DeleteMapping(value = ApplicationEndpoint.TRANSACTIONS)
	public void deleteTransactions(@RequestBody EmptyJson emptyJson) {
		 transactionService.deleteTransactions();
	}

}
