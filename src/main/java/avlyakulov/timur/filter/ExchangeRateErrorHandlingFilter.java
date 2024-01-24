package avlyakulov.timur.filter;

import avlyakulov.timur.custom_exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class ExchangeRateErrorHandlingFilter implements Filter {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter out = servletResponse.getWriter();
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExchangeRateCurrencyCodePairException e) {
            log.error("This code pair that was written by user is wrong");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status 400
            out.print(objectMapper.writeValueAsString(new ErrorResponse(e.getMessage())));
        } catch (ExchangeRateCurrencyPairNotFoundException e) {
            log.error("The currency pair with such code wasn't found");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);//status 404
            out.print(objectMapper.writeValueAsString(new ErrorResponse(e.getMessage())));
        } catch (ExchangeRateAlreadyExistsException e) {
            log.error("The exchange rate with such code pair is already exists");
            resp.setStatus(HttpServletResponse.SC_CONFLICT);//status 409
            out.print(objectMapper.writeValueAsString(new ErrorResponse(e.getMessage())));
        }
    }
}