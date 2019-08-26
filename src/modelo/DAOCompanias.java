package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controlador.ControladorBitacora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOCompanias {
	private int id_compania;
	private String nombre_compania, telefono_compania, correo_compania, direccion_compania, 
									cp_compania, mpio_compania, edo_compania, col_compania;
	private DAOConexion con;
	private Boolean estado_compania;
	private PreparedStatement comando;
	private ObservableList<DAOCompanias> lista_compania;
	private ObservableList<String> listaCompanias;
	private ControladorBitacora ce;

	public DAOCompanias(){
		this.id_compania = 0;
		this.nombre_compania = "";
		this.telefono_compania = "";
		this.correo_compania = "";
		this.direccion_compania = "";
		this.cp_compania = "";
		this.mpio_compania = "";
		this.edo_compania = "";
		this.col_compania= "";
		this.estado_compania =  false;
		this.listaCompanias = FXCollections.observableArrayList();
		this.con=new DAOConexion();
		this.lista_compania = FXCollections.observableArrayList();
		this.ce = new ControladorBitacora();
	}

	public int getId_compania() {
		return id_compania;
	}
	public void setId_compania(int id_compania) {
		this.id_compania = id_compania;
	}
	public String getNombre_compania() {
		return nombre_compania;
	}
	public void setNombre_compania(String nombre_compania) {
		this.nombre_compania = nombre_compania;
	}
	public String getTelefono_compania() {
		return telefono_compania;
	}
	public void setTelefono_compania(String telefono_compania) {
		this.telefono_compania = telefono_compania;
	}
	public String getCorreo_compania() {
		return correo_compania;
	}
	public void setCorreo_compania(String correo_compania) {
		this.correo_compania = correo_compania;
	}
	public String getDireccion_compania() {
		return direccion_compania;
	}
	public void setDireccion_compania(String direccion_compania) {
		this.direccion_compania = direccion_compania;
	}
	public String getCp_compania() {
		return cp_compania;
	}
	public void setCp_compania(String cp_compania) {
		this.cp_compania = cp_compania;
	}
	public String getMpio_compania() {
		return mpio_compania;
	}
	public void setMpio_compania(String mpio_compania) {
		this.mpio_compania = mpio_compania;
	}
	public String getEdo_compania() {
		return edo_compania;
	}
	public void setEdo_compania(String edo_compania) {
		this.edo_compania = edo_compania;
	}
	public String getCol_compania() {
		return col_compania;
	}
	public void setCol_compania(String col_compania) {
		this.col_compania = col_compania;
	}
	public Boolean getEstado_compania() {
		return estado_compania;
	}
	public void setEstado_compania(Boolean estado_compania) {
		this.estado_compania = estado_compania;
	}
	
	public ObservableList<String>consultarCompanias(){
		ResultSet rs = null; //Contendrá el resultado
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement("select nombre_compania from companias where estado_compania = TRUE order by nombre_compania");
				rs= comando.executeQuery();
				while(rs.next()){
					listaCompanias.add(rs.getString("nombre_compania"));
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return listaCompanias;
	}
	
	public ObservableList<DAOCompanias>consultar(String consulta){
		ResultSet rs = null; //Contendrá el resultado
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement(consulta);
				rs= comando.executeQuery();
				while(rs.next()){
					DAOCompanias oCompania = new DAOCompanias();
					oCompania.setId_compania(rs.getInt("id_compania"));
					oCompania.setNombre_compania(rs.getString("nombre_compania"));
					if(rs.getString("telefono_compania") == null){
						oCompania.setTelefono_compania("");
					}
					else{
						oCompania.setTelefono_compania(rs.getString("telefono_compania"));
					}
					if(rs.getString("correo_compania") == null){
						oCompania.setCorreo_compania("");
					}
					else{
						oCompania.setCorreo_compania(rs.getString("correo_compania"));
					}
					oCompania.setDireccion_compania(rs.getString("direccion_compania"));
					oCompania.setCp_compania(rs.getString("cp_compania"));
					oCompania.setMpio_compania(rs.getString("mpio_compania"));
					oCompania.setEdo_compania(rs.getString("edo_compania"));
					oCompania.setCol_compania(rs.getString("col_compania"));

					lista_compania.add(oCompania);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return lista_compania;
	}
	
	public boolean agregar(){
		try{
			if(con.conectar()){
				String sql="insert into companias values(default,?,?,?,?,?,?,?,?,TRUE)";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre_compania);
				comando.setString(2, this.telefono_compania);
				comando.setString(3, this.correo_compania);
				comando.setString(4, this.direccion_compania);
				comando.setString(5, this.cp_compania);
				comando.setString(6, this.edo_compania);
				comando.setString(7, this.mpio_compania);
				comando.setString(8, this.col_compania);
				comando.execute();
				ce.imprimirAccion("Agregar", "Catálogo de Compañías", "Compañía: "+ nombre_compania);
				
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
				String sql = "update companias set nombre_compania = ?, telefono_compania = ?, correo_compania = ?, "
										+ "direccion_compania = ?, cp_compania = ?, edo_compania = ?, mpio_compania = ?, col_compania = ? "
										+ "where id_compania = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre_compania);
				comando.setString(2, this.telefono_compania);
				comando.setString(3, this.correo_compania);
				comando.setString(4, this.direccion_compania);
				comando.setString(5, this.cp_compania);
				comando.setString(6, this.edo_compania);
				comando.setString(7, this.mpio_compania);
				comando.setString(8, this.col_compania);
				comando.setInt(9, id_compania);
				comando.execute();
				ce.imprimirAccion("Editar", "Catálogo de Compañías", "Compañía: "+ nombre_compania);
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
				String sql = "update companias set estado_compania = false where  id_compania = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_compania);
				comando.execute();
				ce.imprimirAccion("Eliminar", "Catálogo de Compañías", "id_compania: "+ id_compania);
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
				String sql = "update companias set estado_compania = true where id_compania = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_compania);
				comando.execute();
				ce.imprimirAccion("Reactivar", "Catálogo de Compañías", "id_compania: "+ id_compania);
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
