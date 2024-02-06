package avlyakulov.timur.servlets.exchange;

import avlyakulov.timur.connection.ConnectionDB;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.RequiredFormFieldIsMissingException;
import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.dao.ExchangeRateDaoImpl;
import avlyakulov.timur.dto.exchange.ExchangeRateResponse;
import avlyakulov.timur.mapper.ExchangeRateMapper;
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
import java.sql.SQLException;

@Slf4j
@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private ExchangeRateService exchangeRateService;
    private ObjectMapper objectMapper;
    private ExchangeRateMapper exchangeRateMapper;

    @Override
    public void init() throws ServletException {
        exchangeRateService = new ExchangeRateServiceImpl(new ExchangeRateDaoImpl(ConnectionDB.getConnection()), new CurrencyDaoImpl(ConnectionDB.getConnection()));
        objectMapper = new ObjectMapper();
        exchangeRateMapper = new ExchangeRateMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURL().toString();
        int lastIndexOfUrl = url.lastIndexOf("/");
        if (lastIndexOfUrl == -1) {
            throw new ExchangeRateCurrencyCodePairException("The currency code of the pair are missing from the address or it is specified incorrectly");
        }
        String currencyPairCode = url.substring(lastIndexOfUrl + 1);
        log.info("We got a request to find a currency pair with such code {}", currencyPairCode);
        ExchangeRateResponse exchangeRateResponse = exchangeRateMapper.mapToResponse(exchangeRateService.findByCodes(currencyPairCode));
        resp.setStatus(HttpServletResponse.SC_OK);
        out.print(objectMapper.writeValueAsString(exchangeRateResponse));
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String rateFromParameter = req.getReader().readLine();
        String url = req.getRequestURL().toString();
        int lastIndexOfUrl = url.lastIndexOf("/");
        if (lastIndexOfUrl == -1) {
            throw new ExchangeRateCurrencyCodePairException("The currency code of the pair are missing from the address or it is specified incorrectly");
        }
        String currencyPairCode = url.substring(lastIndexOfUrl + 1);
        log.info("We get a PATCH request to update rate in currency pair {} to this {}", currencyPairCode, rateFromParameter);
        if (CheckValidityOfParameter.checkValidityOfParameters(rateFromParameter) && rateFromParameter.contains("rate=")) {
            int indexOfRate = rateFromParameter.indexOf("=");
            String rateStr = rateFromParameter.substring(indexOfRate + 1);
            BigDecimal rate = new BigDecimal(rateStr);
            ExchangeRateResponse exchangeRateResponse = exchangeRateMapper.mapToResponse(exchangeRateService.updateExchangeRate(currencyPairCode, rate));
            log.info("The exchange rate was updated");
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(exchangeRateResponse));
        } else {
            throw new RequiredFormFieldIsMissingException("A required form field rate is missing ");
        }
    }

}