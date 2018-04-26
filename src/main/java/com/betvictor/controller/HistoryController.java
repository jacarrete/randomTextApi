package com.betvictor.controller;

import com.betvictor.model.Data;
import com.betvictor.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jcarretero on 25/04/2018.
 */
@RestController
@RequestMapping("betvictor")
public class HistoryController {

    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    private DataService dataService;

    @GetMapping("/history")
    public List<Data> getHistory() {
        //Return only 10 first
        for (Data data : dataService.getAllData()) {
            log.info(data.toString());
        }
        return dataService.getAllData();
    }


}
