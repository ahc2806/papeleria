package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;

import controlador.ControladorBitacora;
import javafx.beans.property.SimpleStringProperty;

public class DAOVentas {
	private int id_venta;
	public SimpleStringProperty descripcion, cantidad, importe, subtotal;
	private String descripcion_venta, cantidad_venta, importe_venta, subtotal_venta, vendedor_venta;
	private Date fecha_venta;
	private Boolean estado_venta;
	private DAOConexion con;
	private PreparedStatement comando;
	private ControladorBitacora ce;

	public DAOVentas(String descripcion, String cantidad, String importe, String subtotal) {
		this.descripcion = new SimpleStringProperty (descripcion);
		this.cantidad =  new SimpleStringProperty(cantidad);
		this.importe = new SimpleStringProperty(importe);
		this.subtotal = new SimpleStringProperty(subtotal);
		this.descripcion_venta = "";
		this.cantidad_venta =  "";
		this.importe_venta = "";
		this.subtotal_venta = "";
		this.vendedor_venta = "";
		this.fecha_venta =  null;
		this.id_venta = 0;
		this.con=new DAOConexion();
		this.ce = new ControladorBitacora();
	}
	
	public String getDescripcion() {
		return descripcion.get();
	}
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);;
	}
	public String getCantidad() {
		return cantidad.get();
	}
	public void setCantidad(String cantidad) {
		this.cantidad.set(cantidad);;
	}
	public String getImporte() {
		return importe.get();
	}
	public void setImporte(String importe) {
		this.importe.set(importe);;
	}
	public String getSubtotal() {
		return subtotal.get();
	}
	public void setSubtotal(String subtotal) {
		this.subtotal.set(subtotal);;
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

	public boolean agregar(){
		try{
			if(con.conectar()){
				String sql="insert into ventas values(default,?,?,?,?,?,?,TRUE)";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.descripcion_venta);
				comando.setString(2, this.cantidad_venta);
				comando.setString(3, this.importe_venta);
				comando.setString(4, this.subtotal_venta);
				comando.setDate(5, this.fecha_venta);
				comando.setString(6, this.vendedor_venta);
				comando.execute();
				ce.imprimirAccion("Agregar", "Catálogo de Ventas", "Descripción: "+ descripcion_venta);
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally {
			con.desconectar();
		}
	}
}