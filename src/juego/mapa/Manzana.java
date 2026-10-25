package juego.mapa;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Manzana {
	double x;
	double y;
	double ancho;
	double alto;
	Entorno entorno;
	Image imagen;
	Casa[] casas;

	public Manzana(double x, double y, double ancho, double alto, Entorno e) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagen = Herramientas.cargarImagen("imagenes/manzana.png");
		this.entorno = e;
		this.setCasas();
	}
	
	public void setCasas() {
		this.casas = new Casa[4];
		int idNegocio = new Random().nextInt(4);
		double ancho = this.entorno.getWidth() / (9 * 4);
		double alto = this.entorno.getHeight() / (9 * 4);

		double offsetX = 38;
		double offsetY = 25;

		// Casa arriba izquierda
		this.casas[0] = new Casa(x - offsetX, y - offsetY - 5, ancho, alto, 0, idNegocio == 0, this.entorno, "izquierda");
		// Casa arriba derecha
		this.casas[1] = new Casa(x + offsetX, y - offsetY - 5, ancho, alto, 0, idNegocio == 1, this.entorno, "atras");
		// Casa abajo izquierda
		this.casas[2] = new Casa(x - offsetX, y + offsetY - 15, ancho, alto, 0, idNegocio == 2, this.entorno, "derecha");
		// Casa abajo derecha
		this.casas[3] = new Casa(x + offsetX, y + offsetY - 15, ancho, alto, 0, idNegocio == 3, this.entorno, "frente");
	}

	public void dibujar() {
		this.entorno.dibujarImagen(this.imagen, this.x, this.y, 0,0.09);
	}

	//getters para la colision
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

	public Casa[] getCasas() {
		return this.casas;
	}
}