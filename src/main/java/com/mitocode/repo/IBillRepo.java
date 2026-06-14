package com.mitocode.repo;

import java.time.LocalDate;
import com.mitocode.model.Bill;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IBillRepo extends IGenericRepo<Bill, String>{

	@Query("{'client' : { _id : ?0 }}")
	Flux<Bill> getBillsByClient(String client);
	
	@Query("{'createIn' : { $gte: ?0, $lt: ?1} }")
	Flux<Bill> getBillsByDates(LocalDate startDate, LocalDate endDate);
}