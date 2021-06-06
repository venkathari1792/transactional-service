package com.n26.response;

import java.io.Serializable;

public class TransactionStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4_687_150_187_077_783_169L;

	public Double sum;

	public Double avg;

	public Double max;

	public Double min;

	public Long count;

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
