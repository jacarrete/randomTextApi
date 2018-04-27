package com.betvictor.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jcarretero on 26/04/2018.
 */
public class StatusData {

    private Map<String, Integer> words;
    private List<Integer> paragraphSizes;
    private List<Long> paragraphProcessingTimes;

    public StatusData() {
        this.words = new HashMap<>();
        this.paragraphSizes = new ArrayList<>();
        this.paragraphProcessingTimes = new ArrayList<>();
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public List<Integer> getParagraphSizes() {
        return paragraphSizes;
    }

    public List<Long> getParagraphProcessingTimes() {
        return paragraphProcessingTimes;
    }
}
