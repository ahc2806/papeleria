package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controlador.ControladorBitacora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOProveedores {
	private int id_proveedor;
	private String nombre_proveedor, apeP_proveedor, apeM_proveedor, telefono_proveedor, correo_proveedor, direccion_proveedor, 
									cp_proveedor, mpio_proveedor, edo_proveedor, col_proveedor, sexo_proveedor, compania_proveedor;
	private DAOConexion con;
	private Boolean estado_proveedor;
	private PreparedStatement comando;
	private ObservableList<DAOProveedores> lista_proveedor;
	private ControladorBitacora ce;

	public DAOProveedores(){
		this.id_proveedor = 0;
		this.nombre_proveedor = "";
		this.apeP_proveedor = "";
		this.apeM_proveedor = "";
		this.telefono_proveedor = "";
		this.correo_proveedor = "";
		this.direccion_proveedor = "";
		this.cp_proveedor = "";
		this.mpio_proveedor = "";
		this.edo_proveedor = "";
		this.col_proveedor = "";
		this.sexo_proveedor = "";
		this.compania_proveedor = "";
		this.estado_proveedor =  false;
		this.con=new DAOConexion();
		this.lista_proveedor=FXCollections.observableArrayList();
		this.ce = new ControladorBitacora();
	}

	public int getId_proveedor() {
		return id_proveedor;
	}
	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}
	public String getNombre_proveedor() {
		return nombre_proveedor;
	}
	public void setNombre_proveedor(String nombre_proveedor) {
		this.nombre_proveedor = nombre_proveedor;
	}
	public String getApeP_proveedor() {
		return apeP_proveedor;
	}
	public void setApeP_proveedor(String apeP_proveedor) {
		this.apeP_proveedor = apeP_proveedor;
	}
	public String getApeM_proveedor() {
		return apeM_proveedor;
	}
	public void setApeM_proveedor(String apeM_proveedor) {
		this.apeM_proveedor = apeM_proveedor;
	}
	public String getTelefono_proveedor() {
		return telefono_proveedor;
	}
	public void setTelefono_proveedor(String telefono_proveedor) {
		this.telefono_proveedor = telefono_proveedor;
	}
	public String getCorreo_proveedor() {
		return correo_proveedor;
	}
	public void setCorreo_proveedor(String correo_proveedor) {
		this.correo_proveedor = correo_proveedor;
	}
	public String getDireccion_proveedor() {
		return direccion_proveedor;
	}
	public void setDireccion_proveedor(String direccion_proveedor) {
		this.direccion_proveedor = direccion_proveedor;
	}
	public String getCp_proveedor() {
		return cp_proveedor;
	}
	public void setCp_proveedor(String cp_proveedor) {
		this.cp_proveedor = cp_proveedor;
	}
	public String getMpio_proveedor() {
		return mpio_proveedor;
	}
	public void setMpio_proveedor(String mpio_proveedor) {
		this.mpio_proveedor = mpio_proveedor;
	}
	public String getEdo_proveedor() {
		return edo_proveedor;
	}
	public void setEdo_proveedor(String edo_proveedor) {
		this.edo_proveedor = edo_proveedor;
	}
	public String getSexo_proveedor() {
		return sexo_proveedor;
	}
	public void setSexo_proveedor(String sexo_proveedor) {
		this.sexo_proveedor = sexo_proveedor;
	}
	public String getCompania_proveedor() {
		return compania_proveedor;
	}
	public void setCompania_proveedor(String compania_proveedor) {
		this.compania_proveedor = compania_proveedor;
	}
	public Boolean getEstado_proveedor() {
		return estado_proveedor;
	}
	public void setEstado_proveedor(Boolean estado_proveedor) {
		this.estado_proveedor = estado_proveedor;
	}
	public String getCol_proveedor() {
		return col_proveedor;
	}
	public void setCol_proveedor(String col_proveedor) {
		this.col_proveedor = col_proveedor;
	}

	public ObservableList<DAOProveedores>consultar(String consulta){
		ResultSet rs = null; //Contendrá el resultado
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement(consulta);
				rs= comando.executeQuery();
				while(rs.next()){
					DAOProveedores oProveedor = new DAOProveedores();
					oProveedor.setId_proveedor(rs.getInt("id_proveedor"));
					oProveedor.setNombre_proveedor(rs.getString("nombre_proveedor"));
					oProveedor.setApeP_proveedor(rs.getString("apeP_proveedor"));
					if(rs.getString("apeM_proveedor") == null){
						oProveedor.setApeM_proveedor("");
					}
					else{
						oProveedor.setApeM_proveedor(rs.getString("apeM_proveedor"));
					}
					if(rs.getString("telefono_proveedor") == null){
						oProveedor.setTelefono_proveedor("");
					}
					else{
						oProveedor.setTelefono_proveedor(rs.getString("telefono_proveedor"));
					}
					if(rs.getString("correo_proveedor") == null){
						oProveedor.setCorreo_proveedor("");
					}
					else{
						oProveedor.setCorreo_proveedor(rs.getString("correo_proveedor"));
					}
					oProveedor.setDireccion_proveedor(rs.getString("direccion_proveedor"));
					oProveedor.setCp_proveedor(rs.getString("cp_proveedor"));
					oProveedor.setMpio_proveedor(rs.getString("mpio_proveedor"));
					oProveedor.setEdo_proveedor(rs.getString("edo_proveedor"));
					oProveedor.setCol_proveedor(rs.getString("col_proveedor"));
					oProveedor.setSexo_proveedor(rs.getString("sexo_proveedor"));
					if(rs.getString("compania_proveedor") == null){
						oProveedor.setCompania_proveedor("");
					}
					else {
						oProveedor.setCompania_proveedor(rs.getString("compania_proveedor"));
					}

					lista_proveedor.add(oProveedor);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return lista_proveedor;
	}
	
	public boolean agregar(){
		try{
			if(con.conectar()){
				String sql="insert into proveedores values(default,?,?,?,?,?,?,?,?,?,?,?,?,TRUE)";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre_proveedor);
				comando.setString(2, this.apeP_proveedor);
				comando.setString(3, this.apeM_proveedor);
				comando.setString(4, this.telefono_proveedor);
				comando.setString(5, this.correo_proveedor);
				comando.setString(6, this.direccion_proveedor);
				comando.setString(7, this.cp_proveedor);
				comando.setString(8, this.edo_proveedor);
				comando.setString(9, this.mpio_proveedor);
				comando.setString(10, this.col_proveedor);
				comando.setString(11, this.sexo_proveedor);
				comando.setString(12, this.compania_proveedor);
				comando.execute();
				ce.imprimirAccion("Agregar", "Catálogo de Proveedores", "Proveedor: "+ nombre_proveedor);
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
	
	public boolean editar(){
		try{
			if(con.conectar()){
				String sql = "update proveedores set nombre_proveedor = ?, apeP_proveedor = ?, apeM_proveedor = ?, telefono_proveedor = ?, correo_proveedor = ?, "
										+ "direccion_proveedor = ?, cp_proveedor = ?, edo_proveedor = ?, mpio_proveedor = ?, col_proveedor = ?, sexo_proveedor = ?, compania_proveedor = ? "
										+ "where id_proveedor = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre_proveedor);
				comando.setString(2, this.apeP_proveedor);
				comando.setString(3, this.apeM_proveedor);
				comando.setString(4, this.telefono_proveedor);
				comando.setString(5, this.correo_proveedor);
				comando.setString(6, this.direccion_proveedor);
				comando.setString(7, this.cp_proveedor);
				comando.setString(8, this.edo_proveedor);
				comando.setString(9, this.mpio_proveedor);
				comando.setString(10, this.col_proveedor);
				comando.setString(11, this.sexo_proveedor);
				comando.setString(12, this.compania_proveedor);
				comando.setInt(13, id_proveedor);
				comando.execute();
				ce.imprimirAccion("Editar", "Catálogo de Proveedores", "Proveedor: "+ nombre_proveedor);
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
		finally{
			con.desconectar();
		}
	}
	
	public boolean eliminar(){
		try{
			if(con.conectar()){
				String sql = "update proveedores set estado_proveedor = false where  id_proveedor = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_proveedor);
				comando.execute();
				ce.imprimirAccion("Eliminar", "Catálogo de Proveedores", "id_proveedor: "+ id_proveedor);
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
				String sql = "update proveedores set estado_proveedor = true where id_proveedor = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_proveedor);
				comando.execute();
				ce.imprimirAccion("Reactivar", "Catálogo de Proveedores", "id_proveedor: "+ id_proveedor);
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
