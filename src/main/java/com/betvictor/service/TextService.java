package com.betvictor.service;

import com.betvictor.helper.CalculateData;
import com.betvictor.dto.StatusData;
import com.betvictor.model.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

/**
 * Created by jcarretero on 26/04/2018.
 */
@Service("textService")
public class TextService {

    public Data generateRandomText(int pStart, int pEnd, int wCountMin, int wCountMax) {
        Collection<StatusData> results = new ConcurrentLinkedQueue<>();
        CompletableFuture<?>[] allFutures = new CompletableFuture[pEnd+1-pStart];
        int index = 0;
        for (int i=pStart; i<=pEnd; i++) {
            int temp = i;
            CompletableFuture<StatusData> future = CompletableFuture.supplyAsync(() -> new CalculateData(temp, wCountMin, wCountMax).get());
            allFutures[index] = future.thenAccept(results::add);
            index++;
        }
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(allFutures);
        try {
            combinedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return getFinalData(results);
    }

    private Data getFinalData(Collection<StatusData> statusDataList) {
        Data data = new Data();
        Map<String, Integer> words = new HashMap<>();
        List<Integer> paragraphSizes = new ArrayList<>();
        List<Long> paragraphProcessingTimes = new ArrayList<>();

        for (StatusData sd : statusDataList) {
            words = populateMap(sd.getWords());
            paragraphSizes.addAll(sd.getParagraphSizes());
            paragraphProcessingTimes.addAll(sd.getParagraphProcessingTimes());
        }
        data.setFreqWord(Collections.max(words.entrySet(), Map.Entry.comparingByValue()).getKey());
        data.setAvgParagraphSize(calculateAverage(paragraphSizes));
        data.setAvgParagraphProcessingTime(calculateAverage(paragraphProcessingTimes)/1000000);
        return data;
    }

    private Map<String, Integer> populateMap(Map<String, Integer> words) {
        Map<String, Integer> aux = new HashMap<>();
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            String word = entry.getKey();
            aux.put(word, aux.containsKey(word) ? aux.get(word) + 1 : entry.getValue());
        }
        return aux;
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
