package juego.mapa;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;

public class Negocio {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image imagen;

	public Negocio(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		// this.imagen = Herramientas.cargarImagen("imagenes/negocio.png");
		this.imagen = null; // Temporalmente null hasta que exista la imagen
	}

	public void dibujar(Entorno entorno) {
		if (this.imagen != null) {
			entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.33);
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}
}
