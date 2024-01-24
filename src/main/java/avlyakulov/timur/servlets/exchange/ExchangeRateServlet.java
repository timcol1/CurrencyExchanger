package avlyakulov.timur.servlets.exchange;

import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
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

@Slf4j
@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String generalUrl = "http://localhost:8080/exchangeRate/";
    private final int lengthUrl = generalUrl.length();
    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURL().toString();
        if (url.length() < generalUrl.length()) {
            throw new ExchangeRateCurrencyCodePairException("The currency codes of the pair are missing from the address or it is specified incorrectly");
        }
        String currencyPairCode = url.substring(lengthUrl);
        log.info("We got a request to find a currency pair with such code {}", currencyPairCode);
        ExchangeRateResponse exchangeRateResponse = exchangeRateMapper.mapToResponse(exchangeRateService.findByCodes(currencyPairCode));
        resp.setStatus(HttpServletResponse.SC_OK);
        out.print(objectMapper.writeValueAsString(exchangeRateResponse));
    }
}