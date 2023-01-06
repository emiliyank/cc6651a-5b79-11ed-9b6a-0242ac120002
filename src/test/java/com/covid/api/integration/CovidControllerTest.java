package com.covid.api.integration;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.covid.api.CovidApplication;
import com.covid.api.service.CovidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest(classes = CovidApplication.class)
@DirtiesContext
public class CovidControllerTest {
    @Value("${server.port}")
    private static int serverPort;
    private static final String COVID_API_RESOURCE_PATH = "http://localhost:" + serverPort + "/api/v1/";

    @MockBean
    private CovidService covidService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetExternalDataThenReturn200Ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(COVID_API_RESOURCE_PATH + "allCovidData")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        verify(covidService, times(1)).getApiSummary();
    }

    @Test
    public void whenGetCovidDataThenReturn200Ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(COVID_API_RESOURCE_PATH + "country/" + "BG")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        verify(covidService, times(1)).getCountry(eq("BG"));
    }
}
