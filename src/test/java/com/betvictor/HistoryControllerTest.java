package com.betvictor;

import com.betvictor.model.Data;
import com.betvictor.service.DataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Created by jcarretero on 26/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetvictorApplication.class)
@WebAppConfiguration
public class HistoryControllerTest {

    @Autowired
    private DataService dataService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<Data> dataList = new ArrayList<>();

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        dataService.deleteAll();
        for (int i=0; i<15; i++) {
            dataList.add(dataService.saveData(new Data("freqWord" +i, 2.1 + i, 0.1 + i, 120L + i)));
        }
    }

    @Test
    public void getHistory() throws Exception {
        mockMvc.perform(get("/betvictor/history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].freqWord", is("freqWord0")))
                .andExpect(jsonPath("$[0].avgParagraphSize", is(2.1)))
                .andExpect(jsonPath("$[0].avgParagraphProcessingTime", is(0.1)))
                .andExpect(jsonPath("$[0].totalProcessingTime", is(120)))
                .andExpect(jsonPath("$[9].freqWord", is("freqWord9")))
                .andExpect(jsonPath("$[9].avgParagraphSize", is(11.1)))
                .andExpect(jsonPath("$[9].avgParagraphProcessingTime", is(9.1)))
                .andExpect(jsonPath("$[9].totalProcessingTime", is(129)));
    }

}

