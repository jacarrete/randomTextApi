package com.betvictor.service;

import com.betvictor.model.Data;
import com.betvictor.dto.StatusData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jcarretero on 26/04/2018.
 */
@Service("textService")
public class TextService {

    public Data generateRandomText(int pStart, int pEnd, int wCountMin, int wCountMax) {
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap<String, Object> map;
        StatusData statusData = new StatusData();
        Data data = new Data();
        for (int i=pStart; i<=pEnd; i++) {
            String uri = "http://www.randomtext.me/api/giberish/p-"+i+"/"+wCountMin+"-"+wCountMax;
            Object response = restTemplate.exchange(uri, HttpMethod.GET, getEntity(), Object.class);
            map = (LinkedHashMap<String, Object>) ((ResponseEntity) response).getBody();
            statusData = calculateData((String) map.get("text_out"), statusData);
        }
        data.setFreqWord(Collections.max(statusData.getWords().entrySet(), Map.Entry.comparingByValue()).getKey());
        data.setAvgParagraphSize(calculateAverage(statusData.getParagraphSizes()));
        data.setAvgParagraphProcessingTime(calculateAverage(statusData.getParagraphProcessingTimes())/1000000);
        return data;
    }

    private HttpEntity<String> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity<>("parameters", headers);
    }

    private StatusData calculateData(String text_out, StatusData statusData) {
        String[] paragraphs = text_out.replaceAll("<.*?>", "").replace("\r", "").split("\\.");
        for (String paragraph : paragraphs) {
            long startProcessingParagraph = System.nanoTime();
            statusData.getParagraphSizes().add(paragraph.length());
            for (String word : paragraph.split(" ")) {
                statusData.getWords().put(word, statusData.getWords().get(word) != null ? statusData.getWords().get(word) + 1 : 1);
            }
            statusData.getParagraphProcessingTimes().add((System.nanoTime() - startProcessingParagraph));
        }
        return statusData;
    }

    private double calculateAverage(List<?> values) {
        double sum = 0;
        for (Object object : values) {
            double value = object instanceof Integer ? ((Integer)object).doubleValue() : ((Long)object).doubleValue();
            sum = sum + value;
        }
        return sum / values.size();
    }
}
