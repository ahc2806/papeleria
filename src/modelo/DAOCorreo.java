package modelo;

public class DAOCorreo {
	
	public String username = "";
	public String contrasena = "";
	public String mensaje = "";
	public String para = "";
	public String asunto = "";
	public String ruta = "";
	public String archivo = "";
	
	public DAOCorreo() {
		this.username = "";
		this.contrasena = "";
		this.mensaje = "";
		this.para = "";
		this.asunto = "";
		this.ruta = "";
		this.archivo = "";
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
}
