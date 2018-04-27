package com.betvictor;

import com.betvictor.controller.TextController;
import com.betvictor.model.Data;
import com.betvictor.service.DataService;
import com.betvictor.service.TextService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by jcarretero on 26/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetvictorApplication.class)
@WebAppConfiguration
public class TextControllerTest {

    @InjectMocks
    private TextController textController;

    @Mock
    private DataService mockDataService;

    @Mock
    private TextService mockTextService;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Data data = new Data();

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
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(textController).build();
        data = new Data("freqWord", 2.1, 0.1, 120L);
    }

    @Test
    public void getData() throws Exception {
        when(mockDataService.saveData(data)).thenReturn(data);
        when(mockTextService.generateRandomText(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(data);
        mockMvc.perform(get("/betvictor/text?p_start=1&p_end=30&w_count_min=24&w_count_max=25"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
        verify(mockTextService, times(1)).generateRandomText(anyInt(), anyInt(), anyInt(), anyInt());
    }

}

