package avlyakulov.timur.filter;

import avlyakulov.timur.custom_exception.CurrencyAlreadyExists;
import avlyakulov.timur.custom_exception.ErrorResponse;
import avlyakulov.timur.custom_exception.RequiredFormFieldIsMissing;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class CurrenciesErrorHandlingFilter implements Filter {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter out = servletResponse.getWriter();
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (RequiredFormFieldIsMissing e) {
            log.error("One field of required fields is missing");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//status 400
            out.print(objectMapper.writeValueAsString(new ErrorResponse(e.getMessage())));
        } catch (CurrencyAlreadyExists e) {
            log.error("The currency with such parameters is already exists");
            resp.setStatus(HttpServletResponse.SC_CONFLICT);//status 409
            out.print(objectMapper.writeValueAsString(new ErrorResponse(e.getMessage())));
        }
    }
}
