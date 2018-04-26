package com.betvictor.service;

import com.betvictor.dao.DataRepository;
import com.betvictor.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jcarretero on 25/04/2018.
 */
@Service("dataService")
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    @Transactional
    public List<Data> getAllData() {
        return dataRepository.findTop10Elements();
    }

    @Transactional
    public Data saveData(Data data) {
        return dataRepository.save(data);
    }

}
