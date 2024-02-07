package avlyakulov.timur.servlets.currency;

import avlyakulov.timur.connection.ConnectionDB;
import avlyakulov.timur.custom_exception.RequiredFormFieldIsMissingException;
import avlyakulov.timur.dao.CurrencyDaoImpl;
import avlyakulov.timur.dao.DeploymentEnvironment;
import avlyakulov.timur.dto.currency.CurrencyRequest;
import avlyakulov.timur.dto.currency.CurrencyResponse;
import avlyakulov.timur.mapper.CurrencyMapper;
import avlyakulov.timur.model.Currency;
import avlyakulov.timur.service.CurrencyService;
import avlyakulov.timur.service.impl.CurrencyServiceImpl;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/currencies")
@Slf4j
public class CurrenciesServlet extends HttpServlet {

    private CurrencyService currencyService;
    private ObjectMapper objectMapper;
    private CurrencyMapper currencyMapper;

    @Override
    public void init() throws ServletException {
        currencyService = new CurrencyServiceImpl(new CurrencyDaoImpl(DeploymentEnvironment.PROD));
        objectMapper = new ObjectMapper();
        currencyMapper = new CurrencyMapper();
        log.info("Currencies servlet was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("We are getting all currencies");
        List<CurrencyResponse> currencies = currencyService.findAll()
                .stream()
                .map(currencyMapper::mapToResponse)
                .toList();
        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);//status 200
        out.print(objectMapper.writeValueAsString(currencies));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");

        log.info("We got a request to create currency with such parameters code {}, name {}, sign {}", code, name, sign);

        if (CheckValidityOfParameter.checkValidityOfParameters(code, name, sign)) {

            Currency currency = currencyMapper.mapToEntity(new CurrencyRequest(code, name, sign));

            PrintWriter out = resp.getWriter();

            currency = currencyService.createCurrency(currency);

            log.info("Currency with such parameters was created");

            resp.setStatus(HttpServletResponse.SC_OK);//status 200
            out.print(objectMapper.writeValueAsString(currency));
            out.flush();
        } else {
            throw new RequiredFormFieldIsMissingException("A required field in currency is missing");
        }
    }


    @Override
    public void destroy() {
        log.info("Currencies servlet was destroyed");
    }
}