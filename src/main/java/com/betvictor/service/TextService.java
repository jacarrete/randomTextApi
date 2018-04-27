package com.betvictor.service;

import com.betvictor.dto.StatusData;
import com.betvictor.helper.CalculateData;
import com.betvictor.model.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger log = LoggerFactory.getLogger(TextService.class);

    @Autowired
    private CalculateData calculateData;

    public Data generateRandomText(int pStart, int pEnd, int wCountMin, int wCountMax) {
        Collection<StatusData> results = new ConcurrentLinkedQueue<>();
        CompletableFuture<?>[] allFutures = new CompletableFuture[pEnd-pStart];
        int index = 0;
        for (int i=pStart; i<pEnd; i++) {
            calculateData.setCalculateData(i, wCountMin, wCountMax);
            CompletableFuture<StatusData> future = CompletableFuture.supplyAsync(calculateData);
            allFutures[index] = future.thenAccept(results::add);
            index++;
        }
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(allFutures);
        try {
            combinedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception");
        }
        return getFinalData(results);
    }

    private Data getFinalData(Collection<StatusData> statusDataList) {
        Data data = new Data();
        Map<String, Integer> words = new HashMap<>();
        List<Integer> paragraphSizes = new ArrayList<>();
        List<Long> paragraphProcessingTimes = new ArrayList<>();

        for (StatusData sd : statusDataList) {
            for (Map.Entry<String, Integer> entry : sd.getWords().entrySet()) {
                String word = entry.getKey();
                words.put(word, words.containsKey(word) ? words.get(word) + 1 : entry.getValue());
            }
            paragraphSizes.addAll(sd.getParagraphSizes());
            paragraphProcessingTimes.addAll(sd.getParagraphProcessingTimes());
        }
        data.setFreqWord(words.size()!=0 ? Collections.max(words.entrySet(), Map.Entry.comparingByValue()).getKey() : "");
        data.setAvgParagraphSize(calculateAverage(paragraphSizes));
        data.setAvgParagraphProcessingTime(calculateAverage(paragraphProcessingTimes)/1000000);
        data.setTotalProcessingTime(0L);
        return data;
    }

    private double calculateAverage(List<?> values) {
        double sum = 0;
        if (values.isEmpty()) {
            return 0;
        }
        for (Object object : values) {
            double value = object instanceof Integer ? ((Integer)object).doubleValue() : ((Long)object).doubleValue();
            sum = sum + value;
        }
        return sum / values.size();
    }
}
