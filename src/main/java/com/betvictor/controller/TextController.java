package com.betvictor.controller;

import com.betvictor.helper.CalculateData;
import com.betvictor.model.Data;
import com.betvictor.service.DataService;
import com.betvictor.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jcarretero on 25/04/2018.
 */
@RestController
@RequestMapping("betvictor")
public class TextController {

    @Autowired
    private DataService dataService;

    @Autowired
    private TextService textService;

    @GetMapping("/text")
    @ResponseStatus(HttpStatus.OK)
    public Data getData(@RequestParam(value = "p_start") int pStart,
                        @RequestParam(value = "p_end") int pEnd,
                        @RequestParam(value = "w_count_min") int wCountMin,
                        @RequestParam(value = "w_count_max") int wCountMax) {

        //GENERATE DATA
        long startProcessingTime = System.currentTimeMillis();
        textService.setCalculateData(new CalculateData());
        Data data = textService.generateRandomText(pStart, pEnd, wCountMin, wCountMax);
        data.setTotalProcessingTime(System.currentTimeMillis() - startProcessingTime);

        //SAVE TO THE DATABASE
        dataService.saveData(data);

        return data;
    }

}
