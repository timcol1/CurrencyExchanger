package avlyakulov.timur.servlets.currency;

import avlyakulov.timur.dto.currency.CurrencyRequest;
import avlyakulov.timur.mapper.CurrencyMapper;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrenciesServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrenciesServlet currenciesServlet;


    @Test
    void test() throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Задаем параметры запроса
        when(request.getParameter("code")).thenReturn("USD");
        when(request.getParameter("fullName")).thenReturn("US Dollar");
        when(request.getParameter("sign")).thenReturn("$");

        // Имитируем создание валюты
        Currency currency = new Currency("code");
        when(currencyService.createCurrency(any())).thenReturn(currency);

        // Создаем StringWriter для записи ответа
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Вызываем метод doPost
        currenciesServlet.doPost(request, response);

        // Проверяем, что метод createCurrency был вызван с правильными параметрами
        verify(currencyService).createCurrency(any());

        // Проверяем, что был вызван метод записи в PrintWriter
        verify(response).getWriter();

        // Проверяем, что ответ содержит JSON с данными валюты
        String expectedJson = objectMapper.writeValueAsString(currency);
        Assertions.assertEquals(stringWriter.toString().trim(), expectedJson);

        // Проверяем, что статус установлен на 200
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }


//    MethodName_ExpectedBehavior_StateUnderTest
//
//    cons: should be renamed if method change name
//
//    example: isAdult_False_AgeLessThan18


}