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

	public Casa(double x, double y, double ancho, double alto, Entorno e, String direccion) {
		this(x, y, ancho, alto, 0, false, e, direccion);
	}

	public Casa(double x, double y, double ancho, double alto, double angulo, boolean esNegocio, Entorno e,
			String direccion) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.angulo = angulo;
		this.esNegocio = esNegocio;
		this.entorno = e;
		this.setImage(direccion);
	}

	public void setImage(String d) {
		int estiloCasa = this.esNegocio ? new Random().nextInt(4) + 1 : new Random().nextInt(5) + 1; // Genera un número
		String tipo = this.esNegocio ? "negocios" : "casas";
		switch (d) {
			case "izquierda":
				this.imagen = Herramientas.cargarImagen("imagenes/" + tipo + "/" + estiloCasa + "-izquierda.png");
				break;
			case "derecha":
				this.imagen = Herramientas.cargarImagen("imagenes/" + tipo + "/" + estiloCasa + "-derecha.png");
				break;
			default:
				this.imagen = Herramientas.cargarImagen("imagenes/" + tipo + "/" + estiloCasa + ".png");
				break;
		}
	}

	public void dibujar() {
		this.entorno.dibujarImagen(this.imagen, this.x, this.y, this.angulo, 0.31);
	}

	public boolean isEsNegocio() {
		return esNegocio;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
}
