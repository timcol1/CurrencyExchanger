package avlyakulov.timur.servlets.exchange;

import avlyakulov.timur.custom_exception.ExchangeRateCurrencyCodePairException;
import avlyakulov.timur.custom_exception.RequiredFormFieldIsMissingException;
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

@Slf4j
@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    private ExchangeRateService exchangeRateService;
    private ObjectMapper objectMapper;

    private final int CURRENCY_PAIR_CODE_LENGTH_URL = 6;

    @Override
    public void init() throws ServletException {
        exchangeRateService = new ExchangeRateServiceImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURL().toString();
        int lastIndexOfUrl = url.lastIndexOf("/");

        String currencyPairCode = url.substring(lastIndexOfUrl + 1);
        if (currencyPairCode.isBlank() || currencyPairCode.length() != CURRENCY_PAIR_CODE_LENGTH_URL) {
            throw new ExchangeRateCurrencyCodePairException("The currency codes of the pair are missing from the address or it is specified incorrectly");
        } else {
            log.info("We got a request to find a currency pair with such code {}", currencyPairCode);
            ExchangeRate exchangeRate = exchangeRateService.findByCodes(currencyPairCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(exchangeRate));
        }
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

        String currencyPairCode = url.substring(lastIndexOfUrl + 1);
        if (currencyPairCode.isBlank() || currencyPairCode.length() != CURRENCY_PAIR_CODE_LENGTH_URL) {
            throw new ExchangeRateCurrencyCodePairException("The currency codes of the pair are missing from the address or it is specified incorrectly");
        } else {
            log.info("We get a PATCH request to update rate in currency pair {} to this {}", currencyPairCode, rateFromParameter);
            if (CheckValidityOfParameter.checkValidityOfParameters(rateFromParameter) && rateFromParameter.contains("rate=")) {
                int indexOfRate = rateFromParameter.indexOf("=");
                String rateStr = rateFromParameter.substring(indexOfRate + 1);
                BigDecimal rate = new BigDecimal(rateStr);
                ExchangeRate updatedExchangeRate = exchangeRateService.updateExchangeRate(currencyPairCode, rate);
                log.info("The exchange rate was updated");
                resp.setStatus(HttpServletResponse.SC_OK);
                out.print(objectMapper.writeValueAsString(updatedExchangeRate));
            } else {
                throw new RequiredFormFieldIsMissingException("A required form field rate is missing ");
            }
        }
    }

}