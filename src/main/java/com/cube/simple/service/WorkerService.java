package com.cube.simple.service;

import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WorkerService {
	
    @Async
    public void doJob (int num) {

    	int duration = 5000 + ((new Random ()).nextInt (10) * 1000);
    	try {
    		log.info ("Worker #{} duration : {} started", num, duration);
    		Thread.sleep (duration);
    		log.info ("Worker #{} duration : {} finished", num, duration);
    	} catch (Exception e) { e.printStackTrace (); }
    }
}