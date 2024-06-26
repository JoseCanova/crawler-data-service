package org.nanotek.crawler.data.util.graph;

import java.util.Optional;

import org.nanotek.crawler.data.util.graph.actuator.mappings.mb.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/*
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.EurekaClient;*/

@Service
public class RestClient {
	/*
	 * @Autowired
	 * 
	 * @Lazy(true) EurekaClient eurekaClient;
	 */
	
	@Autowired
	@Lazy(true)
	RestTemplate restTemplate;
	
	public static final String mdDataService = "mb-data-service";
	
	public Optional<String> getHomeUrl(String serviceName){
		return Optional.of( "http://127.0.0.1:8086");
		/*
		 * return eurekaClient.getApplication(serviceName) .getInstances() .stream()
		 * .filter (i->i.getStatus() == InstanceStatus.UP).findFirst() .map(i ->
		 * i.getHomePageUrl());
		 */
	}
	
	public Optional<Root> getMbRootMapping(){
			
		return 	getHomeUrl(mdDataService)
			.map(home -> {
				return restTemplate.getForObject(home + "/actuator/mappings", Root.class);
			});
			
	}
}
