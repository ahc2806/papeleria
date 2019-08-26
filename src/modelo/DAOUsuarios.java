package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controlador.ControladorBitacora;
import modelo.DAOConexion;
import modelo.DAOUsuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOUsuarios {
	private int id_usuario;
	private String usuario_usuario, contrasena_usuario, tipo_usuario, nombre_usuario,
					apeP_usuario, apeM_usuario, sexo_usuario, correo_usuario, telefono_usuario, foto_usuario;
	private DAOConexion con;
	private Date fechaNac_usuario;
	private Boolean estado_usuario;
	private PreparedStatement comando;
	private ObservableList<DAOUsuarios> lista_usuario;
	private ControladorBitacora ce;

	public DAOUsuarios(){
		this.id_usuario=0;
		this.usuario_usuario="";
		this.contrasena_usuario="";
		this.estado_usuario= false;
		this.tipo_usuario="";
		this.nombre_usuario="";
		this.apeP_usuario="";
		this.apeM_usuario="";
		this.sexo_usuario="";
		this.fechaNac_usuario= null;
		this.correo_usuario="";
		this.telefono_usuario="";
		this.foto_usuario="";
		this.con=new DAOConexion();
		this.lista_usuario=FXCollections.observableArrayList();
		this.ce = new ControladorBitacora();
	}

	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getUsuario_usuario() {
		return usuario_usuario;
	}
	public void setUsuario_usuario(String usuario_usuario) {
		this.usuario_usuario = usuario_usuario;
	}
	public String getContrasena_usuario() {
		return contrasena_usuario;
	}
	public void setContrasena_usuario(String contrasena_usuario) {
		this.contrasena_usuario = contrasena_usuario;
	}
	public String getTipo_usuario() {
		return tipo_usuario;
	}
	public void setTipo_usuario(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}
	public String getApeP_usuario() {
		return apeP_usuario;
	}
	public void setApeP_usuario(String apeP_usuario) {
		this.apeP_usuario = apeP_usuario;
	}
	public String getApeM_usuario() {
		return apeM_usuario;
	}
	public void setApeM_usuario(String apeM_usuario) {
		this.apeM_usuario = apeM_usuario;
	}
	public String getSexo_usuario() {
		return sexo_usuario;
	}
	public void setSexo_usuario(String sexo_usuario) {
		this.sexo_usuario = sexo_usuario;
	}
	public String getCorreo_usuario() {
		return correo_usuario;
	}
	public void setCorreo_usuario(String correo_usuario) {
		this.correo_usuario = correo_usuario;
	}
	public String getTelefono_usuario() {
		return telefono_usuario;
	}
	public void setTelefono_usuario(String telefono_usuario) {
		this.telefono_usuario = telefono_usuario;
	}
	public Date getFechaNac_usuario() {
		return fechaNac_usuario;
	}
	public void setFechaNac_usuario(Date fechaNac_usuario) {
		this.fechaNac_usuario = fechaNac_usuario;
	}
	public Boolean getEstado_usuario() {
		return estado_usuario;
	}
	public void setEstado_usuario(Boolean estado_usuario) {
		this.estado_usuario = estado_usuario;
	}
	public String getFoto_usuario() {
		return foto_usuario;
	}
	public void setFoto_usuario(String foto_usuario) {
		this.foto_usuario = foto_usuario;
	}

	public DAOUsuarios validarDatos(){
		DAOUsuarios oUsuario=null;
		ResultSet rs=null;
		try{
			if(con.conectar()){
				String sql="select * from usuarios where usuario_usuario = '"+this.usuario_usuario+"' and contrasena_usuario = '" +this.contrasena_usuario+
										"' and estado_usuario = 'TRUE'";
				comando = con.getConexion().prepareStatement(sql);
				rs= comando.executeQuery();
				//Para recuperar información
				while(rs.next()){
					oUsuario=new DAOUsuarios();
					oUsuario.id_usuario=rs.getInt("id_usuario");
					oUsuario.usuario_usuario = rs.getString("usuario_usuario");
					oUsuario.contrasena_usuario = rs.getString("contrasena_usuario");
					oUsuario.nombre_usuario=rs.getString("nombre_usuario");
					oUsuario.apeP_usuario=rs.getString("apeP_usuario");
					oUsuario.apeM_usuario=rs.getString("apeM_usuario");
					oUsuario.tipo_usuario=rs.getString("tipo_usuario");
					oUsuario.telefono_usuario=rs.getString("telefono_usuario");
					oUsuario.correo_usuario=rs.getString("correo_usuario");
					oUsuario.fechaNac_usuario=rs.getDate("fechaNac_usuario");
					oUsuario.foto_usuario=rs.getString("foto_usuario");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return oUsuario;
	}

	public ObservableList<DAOUsuarios>consultar(String consulta){
		ResultSet rs = null; //Contendrá el resultado
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement(consulta);
				rs= comando.executeQuery();
				while(rs.next()){
					DAOUsuarios oUsuario=new DAOUsuarios();
					oUsuario.setId_usuario(rs.getInt("id_usuario"));
					oUsuario.setUsuario_usuario(rs.getString("usuario_usuario"));
					oUsuario.setContrasena_usuario(rs.getString("contrasena_usuario"));
					oUsuario.setNombre_usuario(rs.getString("nombre_usuario"));
					oUsuario.setApeP_usuario(rs.getString("apeP_usuario"));
					if(rs.getString("apeM_usuario") == null){
						oUsuario.setApeM_usuario("");
					}
					else{
						oUsuario.setApeM_usuario(rs.getString("apeM_usuario"));
					}
					oUsuario.setFechaNac_usuario(rs.getDate("fechaNac_usuario"));
					if(rs.getString("telefono_usuario") == null){
						oUsuario.setTelefono_usuario("");
					}
					else{
						oUsuario.setTelefono_usuario(rs.getString("telefono_usuario"));
					}
					if(rs.getString("correo_usuario") == null){
						oUsuario.setCorreo_usuario("");
					}
					else{
						oUsuario.setCorreo_usuario(rs.getString("correo_usuario"));
					}
					oUsuario.setSexo_usuario(rs.getString("sexo_usuario"));
					oUsuario.setTipo_usuario(rs.getString("tipo_usuario"));
					oUsuario.setFoto_usuario(rs.getString("foto_usuario"));

					lista_usuario.add(oUsuario);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return lista_usuario;
	}

	public boolean agregar(){
		try{
			if(con.conectar()){
				String sql="insert into usuarios values(default,?,?,?,?,?,?,?,?,?,?,"
								+ "'file:src/vista/img/usuario2.png',TRUE)";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.usuario_usuario);
				comando.setString(2, this.contrasena_usuario);
				comando.setString(3, this.nombre_usuario);
				comando.setString(4, this.apeP_usuario);
				comando.setString(5, this.apeM_usuario);
				comando.setString(6, this.sexo_usuario);
				comando.setDate(7, this.fechaNac_usuario);
				comando.setString(8, this.tipo_usuario);
				comando.setString(9, this.telefono_usuario);
				comando.setString(10, this.correo_usuario);
				comando.execute();
				ce.imprimirAccion("Agregar", "Catálogo de Usuarios", "Usuario: "+ usuario_usuario);
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
	
	public boolean agregar2(){
		try{
			if(con.conectar()){
				String sql="insert into usuarios values(default,?,?,?,?,?,?,?,'Invitado',?,?,"
								+ "'file:src/vista/img/usuario2.png',TRUE)";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.usuario_usuario);
				comando.setString(2, this.contrasena_usuario);
				comando.setString(3, this.nombre_usuario);
				comando.setString(4, this.apeP_usuario);
				comando.setString(5, this.apeM_usuario);
				comando.setString(6, this.sexo_usuario);
				comando.setDate(7, this.fechaNac_usuario);
				comando.setString(8, this.telefono_usuario);
				comando.setString(9, this.correo_usuario);
				comando.execute();
				ce.imprimirAccion("Agregar2", "Catálogo de Usuarios", "Usuario: "+ usuario_usuario);
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
				String sql = "update usuarios set usuario_usuario = ?, contrasena_usuario = ?, nombre_usuario = ?, apeP_usuario = ?, "
									+ "apeM_usuario = ?, sexo_usuario = ?, fechaNac_usuario = ?, tipo_usuario = ?, telefono_usuario = ?, correo_usuario = ? "
									+ "where id_usuario = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.usuario_usuario);
				comando.setString(2, this.contrasena_usuario);
				comando.setString(3, this.nombre_usuario);
				comando.setString(4, this.apeP_usuario);
				comando.setString(5, this.apeM_usuario);
				comando.setString(6, this.sexo_usuario);
				comando.setDate(7, this.fechaNac_usuario);
				comando.setString(8, this.tipo_usuario);
				comando.setString(9, this.telefono_usuario);
				comando.setString(10, this.correo_usuario);
				comando.setInt(11, id_usuario);
				comando.execute();
				ce.imprimirAccion("Editar", "Catálogo de Usuarios", "Usuario: "+ usuario_usuario);
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
	
	public boolean editar2(){
		try{
			if(con.conectar()){
				String sql = "update usuarios set usuario_usuario = ?, contrasena_usuario = ?, nombre_usuario = ?, apeP_usuario = ?, "
									+ "apeM_usuario = ?, fechaNac_usuario = ?, telefono_usuario = ?, correo_usuario = ? "
									+ "where id_usuario = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.usuario_usuario);
				comando.setString(2, this.contrasena_usuario);
				comando.setString(3, this.nombre_usuario);
				comando.setString(4, this.apeP_usuario);
				comando.setString(5, this.apeM_usuario);
				comando.setDate(6, this.fechaNac_usuario);
				comando.setString(7, this.telefono_usuario);
				comando.setString(8, this.correo_usuario);
				comando.setInt(9, id_usuario);
				comando.execute();
				ce.imprimirAccion("Editar2", "Catálogo de Usuarios", "Usuario: "+ usuario_usuario);
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
				String sql = "update usuarios set estado_usuario = false where  id_usuario = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_usuario);
				comando.execute();
				ce.imprimirAccion("Eliminar", "Catálogo de Usuarios", "id_usuario: "+ id_usuario);
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
				String sql = "update usuarios set estado_usuario = true where id_usuario = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.id_usuario);
				comando.execute();
				ce.imprimirAccion("Reactivar", "Catálogo de Usuarios", "id_usuario: "+ id_usuario);
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
	
	public boolean foto_de_perfil(){
		try{
			if(con.conectar()){
				String sql = "update usuarios set foto_usuario = ? where id_usuario = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.foto_usuario);
				comando.setInt(2, this.id_usuario);
				comando.execute();
				ce.imprimirAccion("Foto de Perfil", "Catálogo de Usuarios", "id_usuario: "+ id_usuario);
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