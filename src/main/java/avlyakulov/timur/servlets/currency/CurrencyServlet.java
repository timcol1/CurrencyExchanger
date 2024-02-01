package avlyakulov.timur.servlets.currency;

import avlyakulov.timur.custom_exception.BadCurrencyCodeException;
import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.dto.currency.CurrencyResponse;
import avlyakulov.timur.mapper.CurrencyMapper;
import avlyakulov.timur.service.CurrencyServiceImpl;
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

    private CurrencyServiceImpl currencyServiceImpl;
    private ObjectMapper objectMapper;
    private final String generalUrl = "http://localhost:8080/currency/";
    private final int lengthUrl = generalUrl.length();
    private CurrencyMapper currencyMapper;

    @Override
    public void init() throws ServletException {
        currencyServiceImpl = new CurrencyServiceImpl(new CurrencyDaoImpl());
        objectMapper = new ObjectMapper();
        currencyMapper = new CurrencyMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURL().toString();
        String currencyCode = url.substring(lengthUrl);
        log.info("We got a request to find a currency with such code {}", currencyCode);
        if (url.length() < generalUrl.length()) {
            throw new BadCurrencyCodeException("Currency code is missing at address or you put wrong code");
        }
        CurrencyResponse currencyResponse = currencyMapper.mapToResponse(currencyServiceImpl.findByCode(currencyCode));
        resp.setStatus(HttpServletResponse.SC_OK);
        out.print(objectMapper.writeValueAsString(currencyResponse));
    }
}
