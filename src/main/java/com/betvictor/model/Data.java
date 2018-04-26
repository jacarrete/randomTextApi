package com.betvictor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * Created by jcarretero on 25/04/2018.
 */
@Entity
public class Data {

    @GeneratedValue
    @Id
    @JsonIgnore
    private Long id;
    private String freqWord;
    private Double avgParagraphSize;
    private Double avgParagraphProcessingTime;
    private Long totalProcessingTime;

    public Data() {
    }

    public Data(String freqWord, Double avgParagraphSize, Double avgParagraphProcessingTime, Long totalProcessingTime) {
        this.freqWord = freqWord;
        this.avgParagraphSize = avgParagraphSize;
        this.avgParagraphProcessingTime = avgParagraphProcessingTime;
        this.totalProcessingTime = totalProcessingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFreqWord() {
        return freqWord;
    }

    public void setFreqWord(String freqWord) {
        this.freqWord = freqWord;
    }

    public Double getAvgParagraphSize() {
        return avgParagraphSize;
    }

    public void setAvgParagraphSize(Double avgParagraphSize) {
        this.avgParagraphSize = avgParagraphSize;
    }

    public Double getAvgParagraphProcessingTime() {
        return avgParagraphProcessingTime;
    }

    public void setAvgParagraphProcessingTime(Double avgParagraphProcessingTime) {
        this.avgParagraphProcessingTime = avgParagraphProcessingTime;
    }

    public Long getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(Long totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", freqWord='" + freqWord + '\'' +
                ", avgParagraphSize=" + avgParagraphSize +
                ", avgParagraphProcessingTime=" + avgParagraphProcessingTime +
                ", totalProcessingTime=" + totalProcessingTime +
                '}';
    }
}
