package juego.mapa;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Casa {
	double x;
	double y;
	double ancho;
	double alto;
	Image imagen;
	private double angulo;
	private boolean esNegocio;

	public Casa(double x, double y, double ancho, double alto) {
		this(x, y, ancho, alto, 0, false);
	}

	public Casa(double x, double y, double ancho, double alto, double angulo, boolean esNegocio) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.angulo = angulo;
		this.esNegocio = esNegocio;
		this.imagen = Herramientas.cargarImagen("imagenes/casa1.png");
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, this.angulo, 0.33);
	}

	public boolean isEsNegocio() {
		return esNegocio;
	}
}
