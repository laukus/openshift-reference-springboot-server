package no.skatteetaten.aurora.openshift.reference.springboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import io.micrometer.core.annotation.Timed;
import no.skatteetaten.aurora.openshift.reference.springboot.util.Operation;

/*
 * An example controller that shows how to do a REST call and how to do an operation with a operations metrics
 * There should be a metric called http_client_requests http_server_requests and operations
 */
@RestController
public class ExampleController {

    private static final String SOMETIMES = "sometimes";
    private static final int SECOND = 1000;
    private RestTemplate restTemplate;
    private Operation opt;

    public ExampleController(RestTemplate restTemplate, Operation opt) {

        this.restTemplate = restTemplate;
        this.opt = opt;
    }

    @Timed()
    @GetMapping("/api/example/ip")
    public String ip() {
        JsonNode forEntity = restTemplate.getForObject("http://httpbin.org/ip", JsonNode.class);
        return forEntity.get("origin").textValue();
    }


    @Timed(quantiles = {0.5, 0.95})
    @GetMapping("/api/example/sometimes")
    public String example() {
        return opt.withMetrics(SOMETIMES, () -> {
            long sleepTime = (long) (Math.random() * SECOND);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Sleep interupted", e);
            }
            return "sometimes i succeed";

/*
            if (sleepTime % 2 == 0) {
                status(SOMETIMES, OK);
                return "sometimes i succeed";
            } else {
                status(SOMETIMES, CRITICAL);
                throw new RuntimeException("Sometimes i fail");
            }*/
        });
    }
}

