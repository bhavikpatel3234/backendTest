package io.golo.backendtest;

import io.golo.backendtest.model.RequestDTO;
import io.golo.backendtest.model.ResponseDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Default test class
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendTestApplicationTests {
	@LocalServerPort
	int randomServerPort;

	URI applicationURI;
	RestTemplate restTemplate;
	int timeIntervalInSeconds;
	String url;
	HttpHeaders headers;
	CountDownLatch lock;

	@Before
	public void defaultSetup() throws URISyntaxException {
		restTemplate = new RestTemplate();
		lock = new CountDownLatch(10);
		headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
	}

	@Test
	public void testSchedularFor10Seconds() throws Exception
	{
		String startMonitorURI = "http://localhost:" + randomServerPort + "/monitor/start";
		applicationURI = new URI(startMonitorURI);

		timeIntervalInSeconds = 5;
		url= "https://api.test.paysafe.com/accountmanagement/monitor";
		RequestDTO requestDTO = new RequestDTO(timeIntervalInSeconds, url);
		HttpEntity<RequestDTO> request = new HttpEntity<>(requestDTO, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(applicationURI, request, String.class);
		Assert.assertEquals(Constants.MONITOR_START, result.getBody().toString());

		lock.countDown();
		lock.await(10, TimeUnit.SECONDS);

		String stopMonitorURI = "http://localhost:" + randomServerPort + "/monitor/stop";
		applicationURI = new URI(stopMonitorURI);
		String response = restTemplate.getForObject(applicationURI, String.class);
		Assert.assertEquals(Constants.MONITOR_STOP, response);

		applicationURI = new URI("http://localhost:" + randomServerPort + "/overview");
		ResponseDTO responseDTO = restTemplate.getForObject(applicationURI, ResponseDTO.class);
		Assert.assertEquals(200, responseDTO.getStatusCode());
		Assert.assertTrue(responseDTO.getLogs().size()>=2);
	}

}
