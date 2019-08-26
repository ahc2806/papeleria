package modelo;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DAOJasperSoft {
	
	private DAOConexion con;
	public DAOJasperSoft() {
		this.con=new DAOConexion();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void generarReporte(Date desde, Date hasta, String total) {
		try {
			if(con.conectar()) {
				JasperReport reporte = (JasperReport) JRLoader.loadObject(new File(getClass().getResource("../vista/reportes/ventas.jasper").getPath()));
				Map parametro = new HashMap();
				parametro.put("desde", desde);
				parametro.put("hasta", hasta);
				parametro.put("total", total);
				JasperPrint impreso = JasperFillManager.fillReport(reporte, parametro, con.getConexion());
				JasperViewer view = new JasperViewer(impreso, false);
				view.setTitle("Reporte de Ventas");
				view.setVisible(true);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
