package name.ruiz.juanfco.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MuestraConfiguracionServlet
 */
@WebServlet("/info")
public class MuestraConfiguracionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MuestraConfiguracionServlet() {
        super();
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String SL = System.getProperty("line.separator");
        String codificacion = request.getCharacterEncoding();
        if (codificacion == null) {
            request.setCharacterEncoding("UTF-8");
            codificacion = request.getCharacterEncoding();
        }
        String tipoContenido = request.getContentType();

        response.setCharacterEncoding(codificacion);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter()
                .append("<!DOCTYPE html>").append(SL)
                .append("<html lang='es'>").append(SL)
                .append("<head>").append(SL)
                .append("<title>Informacion</title>").append(SL)
                .append("<meta http-equiv='content-type' content='text/html;charset='").append(codificacion).append("'>").append(SL)
                .append("</head>").append(SL)
                .append("<body style='font-family: monospace;'>").append(SL)
                .append("<h1 style='text-align:center'>Información de la sesión</h1>").append(SL)
                .append("<br/>").append(SL)
                .append("<ul>").append(SL)
                .append("<li>Sesión :")
                .append(request.getSession().getId())
                .append("</li>").append(SL)
                .append("<li>Servido en: ")
                .append(request.getContextPath())
                .append("</li>").append(SL)
                .append("<li>Codificación de caracteres: ")
                .append(codificacion)
                .append("</li>")
                .append("<li>Tipo de contenido: ")
                .append(tipoContenido)
                .append("</li>").append(SL)
                .append("</ul><br/>").append(SL)
                .append("<div style='text-align:center'>").append(SL)
                .append("<a href='")
                .append(request.getContextPath())
                .append("'>")
                .append("Volver")
                .append("</a>").append(SL)
                .append("</div>").append(SL)
                .append("</body>").append(SL)
                .append("</html>");
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
