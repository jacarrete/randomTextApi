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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (id != null ? !id.equals(data.id) : data.id != null) return false;
        if (freqWord != null ? !freqWord.equals(data.freqWord) : data.freqWord != null) return false;
        if (avgParagraphSize != null ? !avgParagraphSize.equals(data.avgParagraphSize) : data.avgParagraphSize != null)
            return false;
        if (avgParagraphProcessingTime != null ? !avgParagraphProcessingTime.equals(data.avgParagraphProcessingTime) : data.avgParagraphProcessingTime != null)
            return false;
        return totalProcessingTime != null ? totalProcessingTime.equals(data.totalProcessingTime) : data.totalProcessingTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (freqWord != null ? freqWord.hashCode() : 0);
        result = 31 * result + (avgParagraphSize != null ? avgParagraphSize.hashCode() : 0);
        result = 31 * result + (avgParagraphProcessingTime != null ? avgParagraphProcessingTime.hashCode() : 0);
        result = 31 * result + (totalProcessingTime != null ? totalProcessingTime.hashCode() : 0);
        return result;
    }
}
