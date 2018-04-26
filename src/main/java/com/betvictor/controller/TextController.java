package com.betvictor.controller;

import com.betvictor.model.Data;
import com.betvictor.service.DataService;
import com.betvictor.service.TextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(TextController.class);

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

        log.info("p_start: " + pStart + " p_end: " + pEnd + " w_count_min: " + wCountMin + " w_count_max: " + wCountMax);

        //GENERATE DATA
        long startProcessingTime = System.currentTimeMillis();
        Data data = textService.generateRandomText(pStart, pEnd, wCountMin, wCountMax);
        data.setTotalProcessingTime(System.currentTimeMillis() - startProcessingTime);

        //ADD TO THE DATABASE
        dataService.saveData(data);

        return data;
    }

    //{"type":"gibberish","amount":2,"number":"25","number_max":"25","format":"p","time":"07:35:19","text_out":"<p>Until falcon one hello apart irrespective regarding yikes less krill some where callously precariously a less far crud much far unreceptive more airily a wasp.<\/p>\r<p>Groaned tenable activated tardily goodness the far darn as wetted yet circa bowed hawk strategic some and more across snootily jeez hotly rhythmic and militant.<\/p>\r"}

    //http://localhost:8080/betvictor/text?p_start=1&p_end=50&w_count_min=1&w_count_max=25
}
