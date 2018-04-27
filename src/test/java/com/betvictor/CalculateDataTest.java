package com.betvictor;

import com.betvictor.dto.StatusData;
import com.betvictor.helper.CalculateData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by jcarretero on 26/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetvictorApplication.class)
@WebAppConfiguration
public class CalculateDataTest {

    private StatusData statusData;

    @Before
    public void setup() throws Exception {
        statusData = new StatusData();
    }

    @Test
    public void calculateData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        statusData.getWords().putAll(generateWords());
        statusData.getParagraphSizes().addAll(Arrays.asList(1,2));
        String textOut = "<p>Dear until bought a the.</p>\r<p>Within spryly a alongside miraculously.</p>\r";
        Method method = CalculateData.class.getDeclaredMethod("calculateData", String.class);
        method.setAccessible(true);
        StatusData result = (StatusData) method.invoke(new CalculateData(1,2,3), textOut);
        assertEquals(statusData.getWords().get("a"), result.getWords().get("a"));
        assertEquals(statusData.getWords().get("alongside"), result.getWords().get("alongside"));
        assertEquals(statusData.getWords().get("Dear"), result.getWords().get("Dear"));
        assertEquals(statusData.getWords().size(), result.getWords().size());
        assertEquals(statusData.getParagraphSizes().size(), result.getParagraphSizes().size());
    }

    private Map<String, Integer> generateWords() {
        Map<String, Integer> map = new HashMap<>();
        map.put("the", 1);
        map.put("a", 2);
        map.put("alongside", 1);
        map.put("miraculously", 1);
        map.put("bought", 1);
        map.put("Within", 1);
        map.put("until", 1);
        map.put("Dear", 1);
        map.put("spryly", 1);
        return map;
    }

    @Test
    public void getEntity() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = CalculateData.class.getDeclaredMethod("getEntity");
        method.setAccessible(true);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        assertEquals(entity, method.invoke(new CalculateData(1,2,3)));
    }

}
