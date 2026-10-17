package juego.mapa;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Casa {
	double x;
	double y;
	double ancho;
	double alto;
	Image imagen;
	Entorno entorno;
	private double angulo;
	private boolean esNegocio;

	public Casa(double x, double y, double ancho, double alto, Entorno e) {
		this(x, y, ancho, alto, 0, false, e);
	}

	public Casa(double x, double y, double ancho, double alto, double angulo, boolean esNegocio, Entorno e) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.angulo = angulo;
		this.esNegocio = esNegocio;
		int estiloCasa = new Random().nextInt(5) + 1;
		this.imagen = Herramientas.cargarImagen("imagenes/casas/"+estiloCasa+".png");
		this.entorno = e;
	}

	public void dibujar() {
		this.entorno.dibujarImagen(this.imagen, this.x, this.y, this.angulo, 0.31);
	}

	public boolean isEsNegocio() {
		return esNegocio;
	}
}
