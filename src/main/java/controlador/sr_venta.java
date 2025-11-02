/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import modelo.Venta;

public class sr_venta extends HttpServlet {

    private int toInt(String s){ try { return Integer.parseInt(s); } catch(Exception e){ return 0; } }
    private double toDouble(String s){ try { return Double.parseDouble(s); } catch(Exception e){ return 0.0; } }
    private java.sql.Date toSqlDate(String s){ try { return java.sql.Date.valueOf(s); } catch(Exception e){ return null; } }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Maestro
        Venta v = new Venta();
        v.setNofactura( toInt(req.getParameter("txt_nofactura")) );
        String serie = req.getParameter("txt_serie");
        v.setSerie( (serie==null || serie.isEmpty()) ? "A" : serie.substring(0,1) );
        v.setFechafactura( toSqlDate(req.getParameter("txt_fecha")) );
        v.setIdcliente( toInt(req.getParameter("drop_cliente")) );
        v.setIdempleado( toInt(req.getParameter("drop_empleado")) );

        // Detalle (arreglos)
        String[] idsP = req.getParameterValues("detail_idProducto");
        String[] cants = req.getParameterValues("detail_cantidad");
        String[] preus = req.getParameterValues("detail_precio");

        if (idsP == null || idsP.length == 0){
            // sin detalle: volver sin cambios
            resp.sendRedirect("ventas.jsp");
            return;
        }

        int n = idsP.length;
        int[] idProd = new int[n];
        int[] cantidades = new int[n];
        double[] precios  = new double[n];

        for (int i=0; i<n; i++){
            idProd[i] = toInt(idsP[i]);
            cantidades[i] = toInt(cants[i]);
            precios[i] = toDouble(preus[i]);
        }

        v.crearConDetalles(idProd, cantidades, precios);
        resp.sendRedirect("ventas.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("ventas.jsp");
    }
}
