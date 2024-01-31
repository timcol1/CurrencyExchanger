package avlyakulov.timur.servlets.exchange;

import avlyakulov.timur.connection.PoolConnectionBuilder;
import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.RequiredFormFieldIsMissingException;
import avlyakulov.timur.dto.exchange.ExchangeRateResponse;
import avlyakulov.timur.mapper.ExchangeRateMapper;
import avlyakulov.timur.service.ExchangeRateService;
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
@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String rateParameter = "rate=";
    private final int rateParameterLength = rateParameter.length();
    private final String generalUrl = "http://localhost:8080/exchangeRate/";
    private final int lengthUrl = generalUrl.length();
    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    @Override
    public void init() throws ServletException {
        exchangeRateService.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURL().toString();
        if (url.length() < generalUrl.length()) {
            throw new ExchangeRateCurrencyCodePairException("The currency code of the pair are missing from the address or it is specified incorrectly");
        }
        String currencyPairCode = url.substring(lengthUrl);
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
        if (url.length() < lengthUrl) {
            throw new ExchangeRateCurrencyCodePairException("The currency code of the pair are missing from the address or it is specified incorrectly");
        }
        String currencyPairCode = url.substring(lengthUrl);
        log.info("We get a PATCH request to update rate in currency pair {} to this {}", currencyPairCode, rateFromParameter);
        if (checkValidityOfParameters(rateFromParameter)) {
            String rateStr = rateFromParameter.substring(rateParameterLength);
            BigDecimal rate = new BigDecimal(rateStr);
            ExchangeRateResponse exchangeRateResponse = exchangeRateMapper.mapToResponse(exchangeRateService.updateExchangeRate(currencyPairCode, rate));
            log.info("The exchange rate was updated");
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(exchangeRateResponse));
        } else {
            throw new RequiredFormFieldIsMissingException("A required form field rate is missing ");
        }
    }

    private boolean checkValidityOfParameters(String... parameters) {
        for (String parameter : parameters) {
            if (parameter == null || parameter.isBlank()) {
                return false;
            }
        }
        return true;
    }
}