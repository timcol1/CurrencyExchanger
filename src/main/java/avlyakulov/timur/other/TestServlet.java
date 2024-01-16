package avlyakulov.timur.other;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//аналог web.xml тоже самое но лучше
@WebServlet(name = "Test", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {

    //private static Logger logger = LoggerFactory.getLogger(TestServlet.class);
    private static java.util.logging.Logger loggerJul = java.util.logging.Logger.getLogger(TestServlet.class.getName());


    //здесь мы можем уже задавать какие то классы и прочее, то есть инициализация классов в сервлете
    //срабатывает 1 раз и все
    @Override
    public void init() throws ServletException {
        loggerJul.info("Servlet is created");
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
