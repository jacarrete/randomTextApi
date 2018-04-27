package com.betvictor.controller;

import com.betvictor.model.Data;
import com.betvictor.service.DataService;
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

    @Autowired
    private DataService dataService;

    @GetMapping("/history")
    public List<Data> getHistory() {
        return dataService.getAllData();
    }


}
