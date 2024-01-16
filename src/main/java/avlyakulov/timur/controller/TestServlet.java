package avlyakulov.timur.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//аналог web.xml тоже самое но лучше
@WebServlet(name = "Test", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(TestServlet.class);


    //здесь мы можем уже задавать какие то классы и прочее, то есть инициализация классов в сервлете
    //срабатывает 1 раз и все
    @Override
    public void init() throws ServletException {
        logger.info("Servlet is created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String surname = req.getParameter("surname");
        resp.getWriter().println(surname);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
