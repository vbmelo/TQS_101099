package com.aeris.api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class ApiRestController {
    private final Map<String, String> cache = new HashMap<>();
    private final Map<String, List<Object>> cache2 = new HashMap<>();
    private int cacheHits = 0;
    private int cacheMisses = 0;
    private int apiCalls = 0;
    final Logger logger = LoggerFactory.getLogger(ApiRestController.class);

    public ApiRestController() {
        Timer timer = new Timer();
        TimerTask cacheClearTask = new TimerTask() {
            public void run() {
                cache.clear();
                cache2.clear();
                logger.info("Cache cleared");
                logger.info("Recreated cache");
            }
        };

        timer.schedule(cacheClearTask, 600000, 600000);
    }

    @GetMapping("/airquality/{city}")
    public ResponseEntity<Object> get_air(
            @PathVariable(name = "city") String city
    ) throws Exception {
        apiCalls++;
        logger.info("Received request for city: {}", city);

        // If the city is not provided, return 400 Bad Request
        if (city == null || city.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String cachedResponse = cache.get(city);
        if (cachedResponse != null) {
            cacheHits++;
            logger.info("Cache hit for city: {}", city);
            return ResponseEntity.ok(cachedResponse);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://api.ambeedata.com/latest/by-city?city=" + city))
                .header(
                        "x-api-key",
                        "107cc05056211724d8679f13a91ea880e193009dc94d3d1a1df6a2f1817d2819"
                )
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        String responseBody = response.body();

        // If the response has a data field empty, it means the city is not found. Return 404 Not Found
        if (
                responseBody.contains("\"data\":[]") && responseBody.contains("message")
        ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found");
        }

        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(responseBody, Object.class);
        cache.put(city, responseBody);
        cacheMisses++;
        logger.info("Cache miss for city: {}", city);

        return ResponseEntity.ok(json);
    }

    @GetMapping("/airquality/predictions/{city}")
    public List<Object> get_air2(@PathVariable(name = "city") String city)
            throws Exception {
        apiCalls++;
        logger.info("Received request for predictions for city: {}", city);
        List<Object> cachedResponse = cache2.get(city);
        if (cachedResponse != null) {
            cacheHits++;
            logger.info("Cache hit for predictions for city: {}", city);
            logger.info("Cache hit for city: {}", city);

            return cachedResponse;
        }
        logger.info("Cache miss for city: {}", city);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(
                        URI.create(
                                "http://api.openweathermap.org/geo/1.0/direct?q=" +
                                        city +
                                        "&limit=1&appid=8c51d283c751a4ddc2ae24d6b9308f66"
                        )
                )
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        String responseBody = response.body();
        ObjectMapper mapper = new ObjectMapper();
        String lat = mapper.readTree(responseBody).get(0).get("lat").asText();
        String lon = mapper.readTree(responseBody).get(0).get("lon").asText();
        Object latlon = Map.of("lat", lat, "lon", lon);
        String url =
                "http://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=" +
                        lat +
                        "&lon=" +
                        lon +
                        "&appid=8c51d283c751a4ddc2ae24d6b9308f66";
        HttpRequest request2 = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response2 = client.send(
                request2,
                HttpResponse.BodyHandlers.ofString()
        );
        String responseBody2 = response2.body();
        JsonNode root = mapper.readTree(responseBody2);
        List<Object> results = new ArrayList<>();
        results.add(latlon);

        for (JsonNode node : root.get("list")) {
            long dt = node.get("dt").asLong();
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
                    dt,
                    0,
                    ZoneOffset.UTC
            );
            LocalDateTime twoDaysLater = LocalDateTime.now().plusDays(1);

            if (dateTime.toLocalDate().isEqual(twoDaysLater.toLocalDate())) {
                results.add(node);
                if (results.size() == 2) {
                    break;
                }
            }
        }

        for (JsonNode node : root.get("list")) {
            long dt = node.get("dt").asLong();
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
                    dt,
                    0,
                    ZoneOffset.UTC
            );
            LocalDateTime threeDaysLater = LocalDateTime.now().plusDays(2);
            if (dateTime.toLocalDate().isEqual(threeDaysLater.toLocalDate())) {
                results.add(node);
                if (results.size() == 3) {
                    break;
                }
            }
        }

        for (JsonNode node : root.get("list")) {
            long dt = node.get("dt").asLong();
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
                    dt,
                    0,
                    ZoneOffset.UTC
            );
            LocalDateTime fourDaysLater = LocalDateTime.now().plusDays(3);
            if (dateTime.toLocalDate().isEqual(fourDaysLater.toLocalDate())) {
                results.add(node);
                if (results.size() == 4) {
                    break;
                }
            }
        }

        for (JsonNode node : root.get("list")) {
            long dt = node.get("dt").asLong();
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
                    dt,
                    0,
                    ZoneOffset.UTC
            );
            LocalDateTime fiveDaysLater = LocalDateTime.now().plusDays(4);

            if (dateTime.toLocalDate().isEqual(fiveDaysLater.toLocalDate())) {
                results.add(node);
                if (results.size() == 5) {
                    break;
                }
            }
        }

        for (JsonNode node : root.get("list")) {
            long dt = node.get("dt").asLong();
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
                    dt,
                    0,
                    ZoneOffset.UTC
            );
            LocalDateTime sixDaysLater = LocalDateTime.now().plusDays(5);
            if (dateTime.toLocalDate().isEqual(sixDaysLater.toLocalDate())) {
                results.add(node);
                if (results.size() == 6) {
                    break;
                }
            }
        }


        String jsonResults = mapper.writeValueAsString(results);
        List<Object> jsonObjList = mapper.readValue(jsonResults, List.class);
        cacheMisses++;
        cache2.put(city, jsonObjList);
        logger.info("Cache miss for city: {}", city);

        return jsonObjList;
    }

    @GetMapping("/airquality/stats")
    public Map<String, Object> getAirQualityStats() {
        int totalRequests = cacheHits + cacheMisses;
        float hitRate = ((float) cacheHits / totalRequests) * 100;
        float missRate = ((float) cacheMisses / totalRequests) * 100;

        Map<String, Object> stats = new HashMap<>();
        stats.put("Cache hits", cacheHits);
        stats.put("Cache misses", cacheMisses);
        stats.put("Hit rate", hitRate + "%");
        stats.put("Miss rate", missRate + "%");
        stats.put("Total API calls", apiCalls);

        return stats;
    }

    @GetMapping("/airquality/reset")
    public void resetCacheAndStats() {
        cache.clear();
        cacheHits = 0;
        cacheMisses = 0;

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseEntity<?> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex
    ) {
        String message =
                "Method not allowed. Allowed methods: " + ex.getSupportedHttpMethods();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(message);
    }
}
