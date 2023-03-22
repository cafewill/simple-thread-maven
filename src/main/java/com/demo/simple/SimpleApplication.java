package com.demo.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.demo.simple.service.LottoService;
import com.demo.simple.service.ResultService;
import com.demo.simple.service.WorkerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SimpleApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		
		ApplicationContext context = SpringApplication.run (SimpleApplication.class, args);
		
		workerDemo (context);
		// resultDemo (context); 
		// lottoDemo (context);
	}

	private static void workerDemo (ApplicationContext context) {

		WorkerService workerService = context.getBean (WorkerService.class);
		
        for (int i = 0; i < 10; i++) {
        	workerService.doJob (i);
        }
	}

	private static void resultDemo (ApplicationContext context) throws InterruptedException, ExecutionException {
		
		ResultService resultService = context.getBean (ResultService.class);

		List <CompletableFuture <Integer []>> futures = new ArrayList <> ();
        for (int i = 0; i < 10; i++) {
            futures.add (resultService.doJob (i));
        }		

		CompletableFuture <Void> allOfFutures = CompletableFuture.allOf (
				futures.toArray (new CompletableFuture [futures.size ()])
        );

        CompletableFuture <List <Integer []>> completableFutures = allOfFutures.thenApply (v -> {
            return futures.stream ()
                    .map (CompletableFuture::join)
                    .collect (Collectors.toList ());
        });

        List <Integer []> results = new ArrayList <> ();
        for (Integer [] result : completableFutures.get ()) {
            results.add (result);
        }

        log.info ("All workers finished. Results: {}", results.stream ().map (Arrays::toString).collect (Collectors.joining (", ")));
	}
	
	private static void lottoDemo (ApplicationContext context) throws IOException, InterruptedException, ExecutionException {

		final int maxList = 12;
		
		LottoService lottoService = context.getBean (LottoService.class);

		List <String> latest = lottoService.getList (maxList);
        log.info ("{}", latest);
        
		List <CompletableFuture <List <Integer>>> futures = new ArrayList <> ();
        for (String round : latest) {
        	futures.add ((CompletableFuture <List <Integer>>) lottoService.getInfo (round));
        }

        CompletableFuture <Void> allOfFutures = CompletableFuture.allOf (
				futures.toArray (new CompletableFuture [futures.size ()])
        );

        CompletableFuture <List <List <Integer>>> completableFutures = allOfFutures.thenApply (v -> {
            return futures.stream ()
                    .map (CompletableFuture::join)
                    .collect (Collectors.toList ());
        });

        List <List <Integer>> results = new ArrayList <> ();
        for (List <Integer> result : completableFutures.get ()) {
            results.add (result);
        }
		
        Map <String, Map <Integer, Integer>> stats = new HashMap <> ();
        stats.put ("win", new HashMap <> ());
        stats.put ("all", new HashMap <> ());

        for (int n = 1; n <= 45; n++) {
            stats.get ("win").put (n, 0);
            stats.get ("all").put (n, 0);
        }
        
        for (List<Integer> result : results) {
            int n = 0;
            for (int w : result) {
                n++;
                stats.get ("all").put (w, stats.get ("all").get (w) + 1);
                if (n <= 6) {
                    stats.get ("win").put (w, stats.get ("win").get (w) + 1);
                }
            }
        }        

        log.info ("Stats (win, 당첨 번호 통계) : {}", stats.get ("win"));
        log.info ("Stats (all, 당첨 및 보너스 번호 포함 통계) : {}", stats.get ("all"));
	}	
}
