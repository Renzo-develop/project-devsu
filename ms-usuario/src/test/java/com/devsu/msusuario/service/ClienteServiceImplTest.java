package com.devsu.msusuario.service;

import com.devsu.msusuario.controller.ClienteController;
import com.devsu.msusuario.entity.ApiResponse;
import com.devsu.msusuario.entity.Cliente;
import com.devsu.msusuario.service.impl.ClienteServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteService service;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    @DisplayName("When create Client then response Http 200")
    void createClientWhenRequestBodyIsValidThenReturnResponseEntityOk() throws Exception{
        Cliente imput = TestUtils.jacksonConvertJSONFileToObject(
                ResourceUtils.getFile("classpath:mocks/mock-imput.json"), Cliente.class);

        Cliente output = TestUtils.jacksonConvertJSONFileToObject(
                ResourceUtils.getFile("classpath:mocks/mock-output.json"), Cliente.class);


        Mockito.when(service.createCliente(ArgumentMatchers.any(Cliente.class)))
                .thenReturn(output);

        ResponseEntity<ApiResponse> responseTest = clienteController.createCliente(imput);

        Assertions.assertEquals(responseTest.getBody().getResponse(), output);
    }

}
