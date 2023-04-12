package com.aeris.api;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.util.Map;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class ApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		ApiRestController ApiRestControllerMock = Mockito.mock(ApiRestController.class);

	}
	// Test for cache APICALLS counter in ApiRestController
	@Test
	public void apiCallsCounterisWorking() throws Exception {
		ApiRestController ApiQualityService = new ApiRestController();
		ApiQualityService.get_air("London");
		ApiQualityService.get_air("London");
		ApiQualityService.get_air("London");
		assertEquals(3, ApiQualityService.getAirQualityStats().get("Total API calls"));
	}

	// Test for Latitude and Longitude in ApiRestController
	@Test
	public void getLatLong() throws Exception {
		// Given
		ApiRestController ApiQualityService = new ApiRestController();
		// When
		Object result = ApiQualityService.get_air2("London").get(0);
		Map<String, Object> resultMap = (Map<String, Object>) result;
		String lat = (String) resultMap.get("lat");
		String lon = (String) resultMap.get("lon");
		// Then
		assertEquals("51.5073219", lat);
		assertEquals("-0.1276474", lon);
	}

	// Test for AirQualityStats status of connection in ApiRestController.
	// 200 = OK
	@Test
	public void testAirQualityStatsEndpoint() throws Exception {
		// given
		String url = "/airquality/stats";

		// when
		mockMvc.perform(MockMvcRequestBuilders.get(url))

				// then
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Test for the Cache empty when calling the api CLEAN method
	@Test
	public void testCleanCache() throws Exception {
		ApiRestController ApiQualityService = new ApiRestController();
		ApiQualityService.get_air("London");
		ApiQualityService.get_air("Aveiro");
		ApiQualityService.resetCacheAndStats();
		assertEquals(0, ApiQualityService.getAirQualityStats().get("Cache hits"));
		assertEquals(0, ApiQualityService.getAirQualityStats().get("Cache misses"));
		assertNotEquals(0, ApiQualityService.getAirQualityStats().get("Total API calls"));
	}

	@Test
	public void cacheCalled() throws Exception {
		ApiRestController ApiQualityService = new ApiRestController();
		ApiQualityService.get_air("London");
		ApiQualityService.get_air("London");
		ApiQualityService.get_air("London");
		assertEquals(2, ApiQualityService.getAirQualityStats().get("Cache hits"));
		assertEquals(1, ApiQualityService.getAirQualityStats().get("Cache misses"));
		assertEquals(3, ApiQualityService.getAirQualityStats().get("Total API calls"));
	}

}
