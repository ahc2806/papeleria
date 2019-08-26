 package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOSepomex {

	private String codigo_postal;
	private DAOConexion con;
	private PreparedStatement comando;
	private ObservableList<String> listaEstados;
	private ObservableList<String> listaMunicipios;
	private ObservableList<String> listaColonias;

	public DAOSepomex() {
		this.codigo_postal = "";
		this.con = new DAOConexion();
		this.listaEstados = FXCollections.observableArrayList();
		this.listaMunicipios = FXCollections.observableArrayList();
		this.listaColonias = FXCollections.observableArrayList();
	}
	
	public String getCodigo_postal() {
		return codigo_postal;
	}
	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}
	
	public DAOSepomex validarCP(){
		DAOSepomex oSepomex=null;
		ResultSet rs=null;
		try{
			if(con.conectar()){
				String sql="select d_codigo from sepomex where d_codigo = '"+this.codigo_postal+"';";
				comando = con.getConexion().prepareStatement(sql);
				rs= comando.executeQuery();
				//Para recuperar información
				while(rs.next()){
					oSepomex=new DAOSepomex();
					oSepomex.codigo_postal=rs.getString("d_codigo");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return oSepomex;
	}

	public ObservableList<String> consultarEstado(){
		ResultSet rs = null;
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement("select distinct d_estado "
															+ "from sepomex "
															+ "where d_codigo = '"+this.codigo_postal
															+ "' order by d_estado;");
				rs = comando.executeQuery();
				while(rs.next()){
					listaEstados.add(rs.getString("d_estado"));
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return listaEstados;
	}

	public ObservableList<String> consultarMunicipio(){
		ResultSet rs = null;
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement("select distinct d_mnpio "
															+ "from sepomex "
															+ "where d_codigo = '"+this.codigo_postal
															+ "' order by D_mnpio;");
				rs = comando.executeQuery();
				while(rs.next()){
					listaMunicipios.add(rs.getString("d_mnpio"));
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return listaMunicipios;
	}

	public ObservableList<String> consultarColonias(){
		ResultSet rs = null;
		try{
			if(con.conectar()){
				comando = con.getConexion().prepareStatement("select distinct d_asenta "
															+ "from sepomex "
															+ "where d_codigo = '"+this.codigo_postal
															+ "' order by d_asenta;");
				rs = comando.executeQuery();
				while(rs.next()){
					listaColonias.add(rs.getString("d_asenta"));
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			con.desconectar();
		}
		return listaColonias;
	}
}
