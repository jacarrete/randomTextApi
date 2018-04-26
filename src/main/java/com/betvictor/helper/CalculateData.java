package com.betvictor.helper;

import com.betvictor.dto.StatusData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.function.Supplier;

/**
 * Created by jcarretero on 26/04/2018.
 */
public class CalculateData implements Supplier<StatusData> {

    private int number;
    private int wCountMin;
    private int wCountMax;

    public CalculateData(int number, int wCountMin, int wCountMax) {
        this.number = number;
        this.wCountMin = wCountMin;
        this.wCountMax = wCountMax;
    }

    @Override
    public StatusData get() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://www.randomtext.me/api/giberish/p-"+number+"/"+wCountMin+"-"+wCountMax;
        Object response = restTemplate.exchange(uri, HttpMethod.GET, getEntity(), Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) ((ResponseEntity) response).getBody();
        return calculateData((String) map.get("text_out"));
    }

    private HttpEntity<String> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity<>("parameters", headers);
    }

    private StatusData calculateData(String textOut) {
        StatusData statusData = new StatusData();
        String[] paragraphs = textOut.replaceAll("<.*?>", "").replace("\r", "").split("\\.");
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

}
