package avlyakulov.timur.servlets.exchange;

import avlyakulov.timur.custom_exception.RequiredFormFieldIsMissingException;
import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.dao.ExchangeRateDaoImpl;
import avlyakulov.timur.dto.exchange.ExchangeRateRequest;
import avlyakulov.timur.dto.exchange.ExchangeRateResponse;
import avlyakulov.timur.mapper.ExchangeRateMapper;
import avlyakulov.timur.model.ExchangeRate;
import avlyakulov.timur.service.ExchangeRateService;
import avlyakulov.timur.service.impl.ExchangeRateServiceImpl;
import avlyakulov.timur.utils.CheckValidityOfParameter;
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
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private ExchangeRateService exchangeRateService;
    private ObjectMapper objectMapper;
    private ExchangeRateMapper exchangeRateMapper;

    @Override
    public void init() throws ServletException {
        exchangeRateService = new ExchangeRateServiceImpl(new ExchangeRateDaoImpl(), new CurrencyDaoImpl());
        objectMapper = new ObjectMapper();
        exchangeRateMapper = new ExchangeRateMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("We are getting all exchange rates");
        List<ExchangeRateResponse> exchangeRateResponses = exchangeRateService.findAll().stream().map(exchangeRateMapper::mapToResponse).toList();
        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);//status 200
        out.print(objectMapper.writeValueAsString(exchangeRateResponses));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        log.info("We got a request to create exchange rate with such parameters baseCurrencyCode {}, targetCurrencyCode {}, rate {}", baseCurrencyCode, targetCurrencyCode, rate);

        if (CheckValidityOfParameter.checkValidityOfParameters(baseCurrencyCode, targetCurrencyCode, rate)) {
            PrintWriter out = resp.getWriter();
            ExchangeRate exchangeRate = exchangeRateMapper.mapToEntity(new ExchangeRateRequest(baseCurrencyCode, targetCurrencyCode, new BigDecimal(rate)));
            exchangeRate = exchangeRateService.createExchangeRate(exchangeRate);
            log.info("Exchange pair with such parameters was created");
            resp.setStatus(HttpServletResponse.SC_OK);//status 200
            out.print(objectMapper.writeValueAsString(exchangeRate));
            out.flush();
        } else {
            throw new RequiredFormFieldIsMissingException("A required field in exchange rate is missing");
        }
    }

}