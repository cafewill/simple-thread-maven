package com.cube.simple.service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResultService {
	
	@Async
	public CompletableFuture <Integer []> doJob (int num) {
		
		int duration = 5000 + ((new Random ()).nextInt (10) * 1000);
		Integer [] result = {num, duration};
    	try {
    		log.info ("Worker #{} duration : {} {} started", num, duration, result);
    		Thread.sleep (duration);
    		log.info ("Worker #{} duration : {} {} finished", num, duration, result);
    	} catch (Exception e) { e.printStackTrace (); }

    	return CompletableFuture.completedFuture (result);
	}
}