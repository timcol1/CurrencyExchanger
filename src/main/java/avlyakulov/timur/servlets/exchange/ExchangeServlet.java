package avlyakulov.timur.servlets.exchange;

import avlyakulov.timur.dto.exchange.ExchangeResponse;
import avlyakulov.timur.service.ExchangeRateService;
import avlyakulov.timur.service.impl.ExchangeRateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@Slf4j
@WebServlet(urlPatterns = "/exchange")
public class ExchangeServlet extends HttpServlet {

    ExchangeRateService exchangeRateService;
    ObjectMapper objectMapper;


    @Override
    public void init() throws ServletException {
        exchangeRateService = new ExchangeRateServiceImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        log.info("We got a request to exchange with such parameters from {} to {} amount {}", baseCurrencyCode, targetCurrencyCode, amount);

        ExchangeResponse exchangeResponse = exchangeRateService.exchange(baseCurrencyCode, targetCurrencyCode, amount);
        resp.setStatus(HttpServletResponse.SC_OK);//status 200
        out.print(objectMapper.writeValueAsString(exchangeResponse));
        out.flush();
    }
}