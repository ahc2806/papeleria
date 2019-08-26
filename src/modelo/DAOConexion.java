package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

import controlador.ControladorBitacora;

public class DAOConexion {
	private String servidor, usuario, contrasena, puerto, base_datos;
	private Connection conexion;
	private ControladorBitacora ce;

	public DAOConexion(){
		this.servidor="localhost";
		this.usuario="postgres";
		this.contrasena="1234";
		this.puerto="5432";
		this.base_datos="papeleria";
		this.ce = new ControladorBitacora();
	}
	//Para conectar a la base de datos
	public boolean conectar(){
		try{
			Class.forName("org.postgresql.Driver");
			conexion=DriverManager.getConnection("jdbc:postgresql://" + servidor + ":"+puerto+"/"+ base_datos, usuario, contrasena);
		//	System.out.println("Conexión Exitosa a: " + conexion.getCatalog());
			return true;

		}catch(Exception e){
			e.printStackTrace();
			ce.imprimirError(e.getMessage(), "DAOConexion");
		//	System.out.println("Conexión Fallida");
			return false;
		}
	}
	//Para cerrar laconexión a la base de datos
	public boolean desconectar(){
		try{
			conexion.close();
		//	System.out.println("Conexión Cerrada");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//Recuperar la conexión a la base de datos
	public Connection getConexion(){
		return conexion;
	}
}
