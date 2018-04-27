package com.betvictor;

import com.betvictor.model.Data;
import com.betvictor.service.DataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jcarretero on 26/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetvictorApplication.class)
@WebAppConfiguration
public class DataServiceTest {

    @Autowired
    private DataService dataService;

    private List<Data> dataList = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        dataService.deleteAll();
        Data data1 = new Data("freqWord1", 2.1, 0.1, 120L);
        Data data2 = new Data("freqWord2", 2.0, 0.2, 123L);
        Data data3 = new Data("freqWord3", 3.1, 0.1, 140L);
        dataList.addAll(new ArrayList<>(Arrays.asList(data1, data2, data3)));
        dataService.saveData(data1);
        dataService.saveData(data2);
        dataService.saveData(data3);
    }

    @Test
    public void getAllData() {
        List<Data> result = dataService.getAllData();
        for (int i=0; i<result.size(); i++) {
            assert result.get(i).equals(dataList.get(i));
        }
    }
}
