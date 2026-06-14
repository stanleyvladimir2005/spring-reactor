package com.mitocode.serviceImpl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.mitocode.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mitocode.dto.FilterDTO;
import com.mitocode.repo.IClientRepo;
import com.mitocode.repo.IBillRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IDishRepo;
import com.mitocode.service.IBillService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BillServiceImpl extends CRUDImpl<Bill, String> implements IBillService {
	
	@Autowired
	private IBillRepo repo;
	
	@Autowired
	private IClientRepo clientRepo;
	
	@Autowired
	private IDishRepo dishRepo;

	@Override
	protected IGenericRepo<Bill, String> getRepo() {
		return repo;
	}

	@Override
	public Flux<Bill> getBillsByFilter(FilterDTO filter) {
		String criterion = filter.getIdClient() != null ? "C" : "O";
		
		if (criterion.equalsIgnoreCase("C"))
			return repo.getBillsByClient(filter.getIdClient());
		 else
			return repo.getBillsByDates(filter.getStarDate(), filter.getEndDate());
	}
	
	@Override
	public Mono<byte[]> generateReport(String idBill) {
		return repo.findById(idBill) //Mono<Bill>
				.flatMap(f -> { //Obteniendo Client
						return Mono.just(f)
								.zipWith(clientRepo.findById(f.getClient().getId()), (fa, cl) -> {
									fa.setClient(cl);
									return fa;
								});
					})
					.flatMap(f -> {		//Obteniendo cada Dish
						return Flux.fromIterable(f.getItems()).flatMap(it -> dishRepo.findById(it.getDish().getId())
								.map(p -> {
									it.setDish(p);
									return it;
								})).collectList().flatMap(list -> {
							f.setItems(list);//Seteando la nueva lista a factura
							return Mono.just(f); //devolviendo factura para el siguiente operador (doOnNext)
							});						
					})
					.map(f -> {
						InputStream stream;
						try {									
							Map<String, Object> parametros = new HashMap<>();		//obteniendo los parametros de entrada del reporte
							parametros.put("txt_cliente", f.getClient().getFirstName() + " " + f.getClient().getLastName()); //llenamos el parametro de cliente del reporte
							stream = getClass().getResourceAsStream("/facturas.jrxml"); //obteniendo reporte y compilando en memoria
							JasperReport report = JasperCompileManager.compileReport(stream); //compilamos reporte
							JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(f.getItems())); //Pasando parametros al reporte
							return JasperExportManager.exportReportToPdf(print); //exportando reporte a pdf
						} catch (Exception e) {
							e.printStackTrace();
						}
						return new byte[0];					
					});
	}
}