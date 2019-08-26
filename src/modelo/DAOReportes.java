package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controlador.ControladorBitacora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOReportes {
	private int id_venta;
	private String descripcion_venta, cantidad_venta, importe_venta, subtotal_venta, vendedor_venta;
	private Date fecha_venta;
	private Boolean estado_venta;
	private DAOConexion con;
	private PreparedStatement comando;
	private ObservableList<DAOReportes> lista_reporte;
	private ControladorBitacora ce;
	
	public DAOReportes() {
		this.descripcion_venta = "";
		this.cantidad_venta =  "";
		this.importe_venta = "";
		this.subtotal_venta = "";
		this.vendedor_venta = "";
		this.fecha_venta =  null;
		this.id_venta = 0;
		this.estado_venta= false;
		this.con=new DAOConexion();
		this.lista_reporte=FXCollections.observableArrayList();
		this.ce = new ControladorBitacora();
	}
	
	public String getDescripcion_venta() {
		return descripcion_venta;
	}
	public void setDescripcion_venta(String descripcion_venta) {
		this.descripcion_venta = descripcion_venta;
	}
	public String getCantidad_venta() {
		return cantidad_venta;
	}
	public void setCantidad_venta(String cantidad_venta) {
		this.cantidad_venta = cantidad_venta;
	}
	public String getImporte_venta() {
		return importe_venta;
	}
	public void setImporte_venta(String importe_venta) {
		this.importe_venta = importe_venta;
	}
	public String getSubtotal_venta() {
		return subtotal_venta;
	}
	public void setSubtotal_venta(String subtotal_venta) {
		this.subtotal_venta = subtotal_venta;
	}
	public int getId_venta() {
		return id_venta;
	}
	public void setId_venta(int id_venta) {
		this.id_venta = id_venta;
	}
	public String getVendedor_venta() {
		return vendedor_venta;
	}
	public void setVendedor_venta(String vendedor_venta) {
		this.vendedor_venta = vendedor_venta;
	}
	public Date getFecha_venta() {
		return fecha_venta;
	}
	public void setFecha_venta(Date fecha_venta) {
		this.fecha_venta = fecha_venta;
	}
	public Boolean getEstado_venta() {
		return estado_venta;
	}
	public void setEstado_venta(Boolean estado_venta) {
		this.estado_venta = estado_venta;
	}
	
	public ObservableList<DAOReportes>consultar(String consulta){
		ResultSet rs = null; //Contendrá el resultado
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement(consulta);
				rs= comando.executeQuery();
				while(rs.next()){
					DAOReportes oReporte=new DAOReportes();
					oReporte.setId_venta(rs.getInt("id_venta"));
					oReporte.setDescripcion_venta(rs.getString("descripcion_venta"));
					oReporte.setCantidad_venta(rs.getString("cantidad_venta"));
					oReporte.setImporte_venta(rs.getString("importe_venta"));
					oReporte.setSubtotal_venta(rs.getString("subtotal_venta"));
					oReporte.setFecha_venta(rs.getDate("fecha_venta"));
					oReporte.setVendedor_venta(rs.getString("vendedor_venta"));

					lista_reporte.add(oReporte);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return lista_reporte;
	}
	
	public boolean eliminar(){
		try{
			if(con.conectar()){
				String sql = "update ventas set estado_venta = false where  id_venta = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_venta);
				comando.execute();
				ce.imprimirAccion("Eliminar", "Catálogo de Reportes", "id_venta: "+ id_venta);
			}
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
	
	public boolean reactivar(){
		try{
			if(con.conectar()){
				String sql = "update ventas set estado_venta = true where id_venta = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_venta);
				comando.execute();
				ce.imprimirAccion("Reactivar", "Catálogo de Reportes", "id_venta: "+ id_venta);
			}
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
}
