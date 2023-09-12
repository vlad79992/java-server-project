package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //@RestController annotation, which marks the class as a controller where every method returns a domain object
public class GreetingController {
    
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting") /*The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method. */
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) { /*@RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method */
		return new Greeting(counter.incrementAndGet(), String.format(template, name)); //creates and returns a new Greeting object with id and content attributes
    }
}
