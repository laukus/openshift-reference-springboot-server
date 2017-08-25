package no.skatteetaten.aurora.openshift.reference.springboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import no.skatteetaten.aurora.openshift.reference.springboot.service.CounterDatabaseService;

/**
 * This is an example Controller that demonstrates a very simple controller that increments a counter
 * in a oracle sql database and returns the previous value.
 * <p>
 * There should automatically be registered metrics for both the withMetrics block and the http endpoint
 * execute and http_server_request histograms
 */

@RestController
public class CounterController {

    private CounterDatabaseService service;

    public CounterController(CounterDatabaseService service) {
        this.service = service;
    }

    @Timed
    @GetMapping("/api/counter")
    public String counter() {

        String value =
            service.getAndIncrementCounter().get("value").toString();

        return value;
    }

}
