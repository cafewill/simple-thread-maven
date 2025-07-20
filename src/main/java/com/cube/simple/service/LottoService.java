package com.cube.simple.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LottoService {
	
	public List <String> getList (int max) throws IOException {

		String url = "https://www.dhlottery.co.kr/gameResult.do?method=byWin";
		log.info ("getList : {}", url);

		Document doc = Jsoup.connect (url).get ();
        Element select = doc.selectFirst ("#dwrNoList");
        Elements options = select.select ("option");

        int count = 0;
        List <String> result = new ArrayList <> ();
        for (Element option : options) {
        	count++;
            result.add (option.text ());
            if (max == count) break;
        }
        
		return result;
    }	
	
	@Async ("taskExecutor")
	public CompletableFuture <List <Integer>> getInfo (String round) throws IOException {
		
		log.info ("Worker getInfo ({}) started", round);
		
        String url = "https://www.dhlottery.co.kr/gameResult.do?method=byWin&drwNo=" + round;
		log.info ("getInfo : {}", url);
        
        Document doc = Jsoup.connect (url).get ();
        Element nums = doc.selectFirst ("div.nums");
        Elements spans = nums.select ("span");

        List <Integer> result = new ArrayList <> ();
        for (Element num : spans) {
        	result.add (Integer.valueOf (num.text ()));
        }

		log.info ("Worker getInfo ({}) finished", round);
    	return CompletableFuture.completedFuture (result);
	}
}