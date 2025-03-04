package com.devsu.mstarjeta.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsu.mstarjeta.repository.CuentaRepository;
import com.devsu.mstarjeta.repository.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CuentaControllerTest {

    @Mock
    CuentaRepository cuentaRepository;

    @Mock
    MovimientoRepository movimientoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void integrationTestfindAllCuentas() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/cuentas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    public void integrationTestfindAllMovimientos() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/movimientos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }
}
