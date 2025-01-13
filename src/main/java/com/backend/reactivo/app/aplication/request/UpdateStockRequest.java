package com.backend.reactivo.app.aplication.request;

public class UpdateStockRequest {

	private final Long stock;

	public UpdateStockRequest(Long stock) {
		this.stock = stock;
	}

	public Long getStock() {
		return stock;
	}
}
