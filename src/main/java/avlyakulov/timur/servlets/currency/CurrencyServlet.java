package avlyakulov.timur.servlets.currency;

import avlyakulov.timur.custom_exception.BadCurrencyCodeException;
import avlyakulov.timur.custom_exception.CurrencyNotFoundException;
import avlyakulov.timur.custom_exception.ErrorResponse;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/currency/*")
@Slf4j
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String generalUrl = "http://localhost:8080/currency";
    private final int lengthUrl = generalUrl.length();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURL().toString();
        String currencyCode = url.substring(lengthUrl);
        log.info("We got a request to find a currency with such code {}", currencyCode);
        try {
            Currency currency = currencyService.findByCode(currencyCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print(objectMapper.writeValueAsString(currency));
        } catch (BadCurrencyCodeException e) {
            log.error("This code was written by user is wrong");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status 400
            out.print(objectMapper.writeValueAsString(new ErrorResponse(e.getMessage())));
        } catch (CurrencyNotFoundException e) {
            log.error("The currency with such code {} wasn't found", currencyCode);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);//status 404
            out.print(objectMapper.writeValueAsString(new ErrorResponse(e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
