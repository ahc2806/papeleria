package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controlador.ControladorBitacora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOInventario {
		private int id_inventario, cantidad_inventario;
		private String seccion_inventario, tipo_inventario;
		private String nombre_inventario;
		private String modelo_inventario;
		private String codigo_inventario;
		private String precio_inventario;
		private String color_inventario;
		private String tamano_inventario;
		private String medidas_inventario;
		private String especificaciones_inventario;
		private String marca_inventario;
		private String capacidad_inventario;
		private String num_de_pag_inventario;
		private String genero_inventario;
		private DAOConexion con;
		private Boolean estado_inventario;
		private PreparedStatement comando;
		private ObservableList<DAOInventario> lista_inventario;
		private ControladorBitacora ce;
		
		public DAOInventario() {
			this.id_inventario=0;
			this.seccion_inventario="";
			this.tipo_inventario="";
			this.nombre_inventario="";
			this.modelo_inventario="";
			this.codigo_inventario="";
			this.precio_inventario="";
			this.color_inventario="";
			this.tamano_inventario="";
			this.medidas_inventario="";
			this.cantidad_inventario=0;
			this.especificaciones_inventario="";
			this.marca_inventario="";
			this.capacidad_inventario="";
			this.num_de_pag_inventario="";
			this.genero_inventario="";
			this.estado_inventario=false;
			this.con=new DAOConexion();
			this.lista_inventario=FXCollections.observableArrayList();
			this.ce = new ControladorBitacora();
		}

		public int getId_inventario() {
			return id_inventario;
		}
		public void setId_inventario(int id_inventario) {
			this.id_inventario = id_inventario;
		}
		public int getCantidad_inventario() {
			return cantidad_inventario;
		}
		public void setCantidad_inventario(int cantidad_inventario) {
			this.cantidad_inventario = cantidad_inventario;
		}
		public String getSeccion_inventario() {
			return seccion_inventario;
		}
		public void setSeccion_inventario(String seccion_inventario) {
			this.seccion_inventario = seccion_inventario;
		}
		public String getTipo_inventario() {
			return tipo_inventario;
		}
		public void setTipo_inventario(String tipo_inventario) {
			this.tipo_inventario = tipo_inventario;
		}
		public String getNombre_inventario() {
			return nombre_inventario;
		}
		public void setNombre_inventario(String nombre_inventario) {
			this.nombre_inventario = nombre_inventario;
		}
		public String getModelo_inventario() {
			return modelo_inventario;
		}
		public void setModelo_inventario(String modelo_inventario) {
			this.modelo_inventario = modelo_inventario;
		}
		public String getCodigo_inventario() {
			return codigo_inventario;
		}
		public void setCodigo_inventario(String codigo_inventario) {
			this.codigo_inventario = codigo_inventario;
		}
		public String getPrecio_inventario() {
			return precio_inventario;
		}
		public void setPrecio_inventario(String precio_inventario) {
			this.precio_inventario = precio_inventario;
		}
		public String getColor_inventario() {
			return color_inventario;
		}
		public void setColor_inventario(String color_inventario) {
			this.color_inventario = color_inventario;
		}
		public String getTamano_inventario() {
			return tamano_inventario;
		}
		public void setTamano_inventario(String tamano_inventario) {
			this.tamano_inventario = tamano_inventario;
		}
		public String getMedidas_inventario() {
			return medidas_inventario;
		}
		public void setMedidas_inventario(String medidas_inventario) {
			this.medidas_inventario = medidas_inventario;
		}
		public String getEspecificaciones_inventario() {
			return especificaciones_inventario;
		}
		public void setEspecificaciones_inventario(String especificaciones_inventario) {
			this.especificaciones_inventario = especificaciones_inventario;
		}
		public String getMarca_inventario() {
			return marca_inventario;
		}
		public void setMarca_inventario(String marca_inventario) {
			this.marca_inventario = marca_inventario;
		}
		public String getCapacidad_inventario() {
			return capacidad_inventario;
		}
		public void setCapacidad_inventario(String capacidad_inventario) {
			this.capacidad_inventario = capacidad_inventario;
		}
		public String getNum_de_pag_inventario() {
			return num_de_pag_inventario;
		}
		public void setNum_de_pag_inventario(String num_de_pag_inventario) {
			this.num_de_pag_inventario = num_de_pag_inventario;
		}
		public String getGenero_inventario() {
			return genero_inventario;
		}
		public void setGenero_inventario(String genero_inventario) {
			this.genero_inventario = genero_inventario;
		}
		public Boolean getEstado_inventario() {
			return estado_inventario;
		}
		public void setEstado_inventario(Boolean estado_inventario) {
			this.estado_inventario = estado_inventario;
		}

		public DAOInventario validarProducto() {
			DAOInventario oInventario = null;
			ResultSet rs = null;
			try {
				if(con.conectar()) {
					String sql="select * from inventario where codigo_inventario = '"+this.codigo_inventario+"' and estado_inventario = 'TRUE'";
					comando = con.getConexion().prepareStatement(sql);
					rs= comando.executeQuery();
					//Para recuperar información
					while(rs.next()){
						oInventario = new DAOInventario();
						oInventario.codigo_inventario = rs.getString("codigo_inventario");
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				con.desconectar();
			}
			return oInventario;
		}
		
		public ObservableList<DAOInventario>consultar(String consulta){
			ResultSet rs = null; //Contendrá el resultado
			try{
				if(con.conectar()){
					comando = con.getConexion().prepareStatement(consulta);
					rs= comando.executeQuery();
					while(rs.next()){
						DAOInventario oInventario=new DAOInventario();
						oInventario.setId_inventario(rs.getInt("id_inventario"));
						oInventario.setSeccion_inventario(rs.getString("seccion_inventario"));
						oInventario.setTipo_inventario(rs.getString("tipo_inventario"));
						oInventario.setNombre_inventario(rs.getString("nombre_inventario"));
						if(rs.getString("modelo_inventario") == null) {
							oInventario.setModelo_inventario("");
						}else {
							oInventario.setModelo_inventario(rs.getString("modelo_inventario"));
						}
						oInventario.setCodigo_inventario(rs.getString("codigo_inventario"));
						oInventario.setPrecio_inventario(rs.getString("precio_inventario"));
						if(rs.getString("color_inventario") == null) {
							oInventario.setColor_inventario("");
						}else {
							oInventario.setColor_inventario(rs.getString("color_inventario"));
						}
						if(rs.getString("tamano_inventario") == null) {
							oInventario.setTamano_inventario("");
						}else {
							oInventario.setTamano_inventario(rs.getString("tamano_inventario"));
						}
						if(rs.getString("medidas_inventario") == null) {
							oInventario.setMedidas_inventario("");
						}else {
							oInventario.setMedidas_inventario(rs.getString("medidas_inventario"));	
						}
						oInventario.setCantidad_inventario(rs.getInt("cantidad_inventario"));
						if(rs.getString("especificaciones_inventario") == null) {
							oInventario.setEspecificaciones_inventario("");
						}else {
							oInventario.setEspecificaciones_inventario(rs.getString("especificaciones_inventario"));	
						}
						if(rs.getString("marca_inventario") == null) {
							oInventario.setMarca_inventario("");
						}else {
							oInventario.setMarca_inventario(rs.getString("marca_inventario"));
						}
						if(rs.getString("capacidad_inventario") == null) {
							oInventario.setCapacidad_inventario("");
						}else {
							oInventario.setCapacidad_inventario(rs.getString("capacidad_inventario"));
						}
						if(rs.getString("num_de_pag_inventario") == null) {
							oInventario.setNum_de_pag_inventario("");
						}else {
							oInventario.setNum_de_pag_inventario(rs.getString("num_de_pag_inventario"));	
						}
						if(rs.getString("genero_inventario") == null) {
							oInventario.setGenero_inventario("");
						}else {
							oInventario.setGenero_inventario(rs.getString("genero_inventario"));
						}
						oInventario.setEstado_inventario(rs.getBoolean("estado_inventario"));
						
						lista_inventario.add(oInventario);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				con.desconectar();
			}
			return lista_inventario;
		}
		
		public boolean agregar(){
			try{
				if(con.conectar()){
					String sql="insert into inventario values(default,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TRUE)";
					comando = con.getConexion().prepareStatement(sql);
					comando.setString(1, this.seccion_inventario);
					comando.setString(2, this.tipo_inventario);
					comando.setString(3, this.nombre_inventario);
					comando.setString(4, this.modelo_inventario);
					comando.setString(5, this.codigo_inventario);
					comando.setString(6, this.precio_inventario);
					comando.setString(7, this.color_inventario);
					comando.setString(8, this.tamano_inventario);
					comando.setString(9, this.medidas_inventario);
					comando.setInt(10, this.cantidad_inventario);
					comando.setString(11, this.especificaciones_inventario);
					comando.setString(12, this.marca_inventario);
					comando.setString(13, this.capacidad_inventario);
					comando.setString(14, this.num_de_pag_inventario);
					comando.setString(15, this.genero_inventario);
					comando.execute();
					ce.imprimirAccion("Agregar", "Catálogo de Productos", "Producto: "+ nombre_inventario);
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
					String sql = "update inventario set seccion_inventario = ?, tipo_inventario = ?, nombre_inventario = ?, modelo_inventario = ?, codigo_inventario = ?, precio_inventario = ?, "
										+ "color_inventario = ?, tamano_inventario = ?, medidas_inventario = ?, cantidad_inventario = ?,  especificaciones_inventario = ?, marca_inventario = ?, "
										+ "capacidad_inventario = ?, num_de_pag_inventario = ?, genero_inventario = ? "
										+ "where id_inventario = ?";
					comando = con.getConexion().prepareStatement(sql);
					comando.setString(1, this.seccion_inventario);
					comando.setString(2, this.tipo_inventario);
					comando.setString(3, this.nombre_inventario);
					comando.setString(4, this.modelo_inventario);
					comando.setString(5, this.codigo_inventario);
					comando.setString(6, this.precio_inventario);
					comando.setString(7, this.color_inventario);
					comando.setString(8, this.tamano_inventario);
					comando.setString(9, this.medidas_inventario);
					comando.setInt(10, this.cantidad_inventario);
					comando.setString(11, this.especificaciones_inventario);
					comando.setString(12, this.marca_inventario);
					comando.setString(13, this.capacidad_inventario);
					comando.setString(14, this.num_de_pag_inventario);
					comando.setString(15, this.genero_inventario);
					comando.setInt(16, id_inventario);
					comando.execute();
					ce.imprimirAccion("Editar", "Catálogo de Productos", "Producto: "+ nombre_inventario);
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
					String sql = "update inventario set estado_inventario = false where  id_inventario = ?";
					comando = con.getConexion().prepareStatement(sql);
					comando.setInt(1, this.id_inventario);
					comando.execute();
					ce.imprimirAccion("Eliminar", "Catálogo de Productos", "id_inventario: "+ id_inventario);
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
					String sql = "update inventario set estado_inventario = true where id_inventario = ?";
					comando = con.getConexion().prepareStatement(sql);
					comando.setInt(1, this.id_inventario);
					comando.execute();
					ce.imprimirAccion("Reactivar", "Catálogo de Productos", "id_inventario: "+ id_inventario);
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
		
		public boolean dismAlmac(){
			try{
				if(con.conectar()){
					String sql = "update inventario set cantidad_inventario = ? where id_inventario = ?";
					comando = con.getConexion().prepareStatement(sql);
					comando.setInt(1, this.cantidad_inventario);
					comando.setInt(2, this.id_inventario);
					comando.execute();
					ce.imprimirAccion("Disminuir/Restaurar Almacén", "Catálogo de Productos", "id_inventario: "+ id_inventario);
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
		
		public String consultarNombre(String consulta){
			ResultSet rs = null; //Contendrá el resultado
			try{
				if(con.conectar()){
					comando = con.getConexion().prepareStatement(consulta);
					rs= comando.executeQuery();
					while(rs.next()){
						nombre_inventario = rs.getString("nombre_inventario");
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				con.desconectar();
			}
			return nombre_inventario;
		}
		
		public String consultarPrecio(String consulta){
			ResultSet rs = null; //Contendrá el resultado
			try{
				if(con.conectar()){
					comando = con.getConexion().prepareStatement(consulta);
					rs= comando.executeQuery();
					while(rs.next()){
						precio_inventario = rs.getString("precio_inventario");
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				con.desconectar();
			}
			return precio_inventario;
		}
		
		public int consultarCantidad(String consulta){
			ResultSet rs = null; //Contendrá el resultado
			try{
				if(con.conectar()){
					comando = con.getConexion().prepareStatement(consulta);
					rs= comando.executeQuery();
					while(rs.next()){
						cantidad_inventario = rs.getInt("cantidad_inventario");
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				con.desconectar();
			}
			return cantidad_inventario;
		}
		
		public int consultarId(String consulta){
			ResultSet rs = null; //Contendrá el resultado
			try{
				if(con.conectar()){
					comando = con.getConexion().prepareStatement(consulta);
					rs= comando.executeQuery();
					while(rs.next()){
						id_inventario = rs.getInt("id_inventario");
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				con.desconectar();
			}
			return id_inventario;
		}
}
