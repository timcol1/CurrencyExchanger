package avlyakulov.timur.servlets.exchange;

import avlyakulov.timur.dao.ExchangeDaoImpl;
import avlyakulov.timur.dto.exchange.ExchangeResponse;
import avlyakulov.timur.mapper.ExchangeMapper;
import avlyakulov.timur.service.ExchangeService;
import avlyakulov.timur.service.impl.ExchangeServiceImpl;
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

    ExchangeService exchangeService;
    ObjectMapper objectMapper;
    ExchangeMapper exchangeMapper;

    @Override
    public void init() throws ServletException {
        exchangeService = new ExchangeServiceImpl(new ExchangeDaoImpl());
        objectMapper = new ObjectMapper();
        exchangeMapper = new ExchangeMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        log.info("We got a request to exchange with such parameters from {} to {} amount {}", baseCurrencyCode, targetCurrencyCode, amount);

        ExchangeResponse exchangeResponse = exchangeMapper.mapToResponse(exchangeService.exchange(baseCurrencyCode, targetCurrencyCode, amount));
        resp.setStatus(HttpServletResponse.SC_OK);//status 200
        out.print(objectMapper.writeValueAsString(exchangeResponse));
        out.flush();
    }
}