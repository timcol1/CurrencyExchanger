package avlyakulov.timur.servlets.exchange;


import avlyakulov.timur.model.ExchangeRate;
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
import java.util.List;

@WebServlet(urlPatterns = "/exchangeRates")
@Slf4j
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("We are getting all exchange rates");
        List<ExchangeRate> exchangeRates = exchangeRateService.findAll();
        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);//status 200
        out.print(objectMapper.writeValueAsString(exchangeRates));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}