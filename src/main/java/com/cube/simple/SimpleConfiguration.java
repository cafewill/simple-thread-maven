package com.cube.simple;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class SimpleConfiguration {
	
	private static final int maxThreads = 5;
	
	// ThreadPoolTaskExecutor 를 활용하여 (비동기) 멀티 쓰레드 및 동시 실행 제한 설정
	/*
	@Bean
	public ThreadPoolTaskExecutor taskExecutor () {
		
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor ();
		taskExecutor.setCorePoolSize (maxThreads);
		taskExecutor.setMaxPoolSize (maxThreads);
		// taskExecutor.setQueueCapacity (maxThreads);
		taskExecutor.initialize ();
	    
	    return taskExecutor;
	}
	*/
}