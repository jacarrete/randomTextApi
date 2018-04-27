package com.betvictor;

import com.betvictor.dto.StatusData;
import com.betvictor.model.Data;
import com.betvictor.service.TextService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by jcarretero on 26/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetvictorApplication.class)
@WebAppConfiguration
public class TextServiceTest {

    @Test
    public void getFinalData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Data finalData = new Data("Word2", 2.0, 2.0E-6, 0L);
        Method method = TextService.class.getDeclaredMethod("getFinalData", Collection.class);
        method.setAccessible(true);
        List<StatusData> statusDataList = new ArrayList<>();
        statusDataList.add(populateStatusData());
        Data result = (Data) method.invoke(new TextService(), statusDataList);
        assert finalData.equals(result);
    }

    private StatusData populateStatusData() {
        StatusData statusData = new StatusData();
        statusData.getWords().put("Word1", 1);
        statusData.getWords().put("Word2", 3);
        statusData.getWords().put("Word3", 1);
        statusData.getParagraphProcessingTimes().addAll(new ArrayList<>(Arrays.asList(1L, 2L, 3L)));
        statusData.getParagraphSizes().addAll(new ArrayList<>(Arrays.asList(1,2,3)));
        return statusData;
    }

}
