package com.mitocode.service;

import com.mitocode.dto.FilterDTO;
import com.mitocode.model.Bill;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBillService extends ICRUD<Bill, String> {
	
	Flux<Bill> getBillsByFilter(FilterDTO filter);

	Mono<byte[]> generateReport(String idBill);
}