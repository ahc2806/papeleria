package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorBitacora {
	private DateFormat formato;
	private Date fecha;

	public ControladorBitacora(){
		formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		fecha = new Date();
	}

	public void imprimirError(String mensaje, String clase){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			File archivo = new File("src/logs/log.txt");
			fw = new FileWriter(archivo, true);
			bw = new BufferedWriter(fw);
			bw.write("BIT�CORA DE CONEXI�N");
			bw.newLine();
			bw.write("Error en: " + clase);
			bw.newLine();
			bw.write("Fecha: " + formato.format(fecha));
			bw.newLine();
			bw.write("Descripci�n: " + mensaje);
			bw.newLine();
			bw.write("******************************************************************");
			bw.newLine();
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void imprimirAccion(String mensaje, String clase, String cambio){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			File archivo = new File("src/logs/bitacora.txt");
			fw = new FileWriter(archivo, true);
			bw = new BufferedWriter(fw);
			bw.write("BIT�CORA DE OPERACIONES");
			bw.newLine();
			bw.write("Operaci�n en: " + clase);
			bw.newLine();
			bw.write(cambio);
			bw.newLine();
			bw.write("Fecha: " + formato.format(fecha));
			bw.newLine();
			bw.write("Descripci�n: " + mensaje);
			bw.newLine();
			bw.write("******************************************************************");
			bw.newLine();
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}