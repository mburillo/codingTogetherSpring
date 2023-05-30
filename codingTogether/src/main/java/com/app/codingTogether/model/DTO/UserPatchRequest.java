package com.app.codingTogether.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class UserPatchRequest {
    private Long id;
    private String usuario;
    private String lenguaje;
    private String nivel;
    private MultipartFile imagen;
    public UserPatchRequest() {
    	
    }
    
	public UserPatchRequest(Long id, String usuario, String lenguaje, String nivel, MultipartFile imagen) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.lenguaje = lenguaje;
		this.nivel = nivel;
		this.imagen = imagen;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getLenguaje() {
		return lenguaje;
	}
	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public MultipartFile getImagen() {
		return imagen;
	}
	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "UserPatchRequest [id=" + id + ", usuario=" + usuario + ", lenguaje=" + lenguaje + ", nivel=" + nivel
				+ ", imagen=" + imagen + "]";
	}

}