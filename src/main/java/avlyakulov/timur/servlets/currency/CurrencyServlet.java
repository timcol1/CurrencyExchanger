package avlyakulov.timur.servlets.currency;

import avlyakulov.timur.custom_exception.BadCurrencyCodeException;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.CurrencyService;
import avlyakulov.timur.service.impl.CurrencyServiceImpl;
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
@WebServlet(urlPatterns = "/currency/*")
public class CurrencyServlet extends HttpServlet {

    private CurrencyService currencyService;
    private ObjectMapper objectMapper;

    private static final int CODE_LENGTH_URL = 3;

    @Override
    public void init() throws ServletException {
        currencyService = new CurrencyServiceImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURL().toString();
        int lastIndexOfUrl = url.lastIndexOf("/");
        if (lastIndexOfUrl == -1) {
            throw new BadCurrencyCodeException("Currency code is missing at address or you put wrong code");
        }
        String currencyCode = url.substring(lastIndexOfUrl + 1);
        if (currencyCode.isBlank() || currencyCode.length() != CODE_LENGTH_URL) {
            throw new BadCurrencyCodeException("Currency code is missing at address or you put wrong code");
        } else {
            log.info("We got a request to find a currency with such code {}", currencyCode);
            Currency currency = currencyService.findByCode(currencyCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(currency));
        }
    }
}
