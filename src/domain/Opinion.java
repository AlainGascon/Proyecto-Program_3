package domain;

import java.util.Date;

public class Opinion {


	private int id;
    private Usuario usuario;
    private Producto producto;
    private int puntuacion; 
    private String titulo;
    private String comentario;
    private Date fechaOpinion;
    private int meGusta;
    private boolean visible;
    
    
	public Opinion() {
		super();
	}


	public Opinion(int id, Usuario usuario, Producto producto, int puntuacion, String titulo, String comentario,
			Date fechaOpinion, int meGusta, boolean visible) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.producto = producto;
		this.puntuacion = puntuacion;
		this.titulo = titulo;
		this.comentario = comentario;
		this.fechaOpinion = fechaOpinion;
		this.meGusta = meGusta;
		this.visible = visible;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		if (puntuacion > 5 || puntuacion < 1) {
			throw new IllegalArgumentException("La puntuacion debe estar entre 1 y 5. ");
		} 
		this.puntuacion = puntuacion;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaOpinion() {
		return fechaOpinion;
	}

	public void setFechaOpinion(Date fechaOpinion) {
		this.fechaOpinion = fechaOpinion;
	}

	public int getMeGusta() {
		return meGusta;
	}

	public void setMeGusta(int meGusta) {
		this.meGusta = meGusta;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
    
	@Override
	public String toString() {
		return "Opinion [id=" + id + ", usuario=" + usuario + ", producto=" + producto + ", puntuacion=" + puntuacion
				+ ", titulo=" + titulo + ", comentario=" + comentario + ", fechaOpinion=" + fechaOpinion + ", meGusta="
				+ meGusta + ", visible=" + visible + "]";
	}


	public boolean valoracionEsPositiva() {
		if (this.puntuacion >= 4) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean valoracionEsNegativa() {
		if (this.puntuacion <= 2) {
			return true;
		} else {
			return false;
		}
	}
	
}
