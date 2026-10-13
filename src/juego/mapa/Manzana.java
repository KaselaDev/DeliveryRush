package juego.mapa;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Manzana {
	double x;
	double y;
	double ancho;
	double alto;
	Image imagen;

	public Manzana(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.imagen = Herramientas.cargarImagen("imagenes/manzana.png");
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.green);
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0,0.1);
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

	/**
	 * Genera un array de manzanas y casas configurado en una cuadrícula 4x4
	 * @return Un objeto Mapa que contiene los arrays de manzanas y casas
	 */
	public static Mapa generarMapa() {
		Manzana[] cuadra = new Manzana[16];
		Casa[] casas = new Casa[64];
		int c = 0;
		int k = 0;
		int ancho = 175;
		int alto = 100;
		int separacionAncho = 260;
		int separacionAlto = 190;

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double posX = 185 + (j * separacionAncho);
				double posY = 220 + (i * separacionAlto);
				cuadra[k++] = new Manzana(posX, posY, ancho, alto);

				double offsetX = 45;
				double offsetY = 25;

				// Casa arriba izquierda
				casas[c++] = new Casa(posX - offsetX, posY - offsetY, 0, 0);
				// Casa arriba derecha
				casas[c++] = new Casa(posX + offsetX, posY - offsetY, 0 ,0);
				// Casa abajo izquierda
				casas[c++] = new Casa(posX - offsetX, posY + offsetY, 0, 0);
				// Casa abajo derecha
				casas[c++] = new Casa(posX + offsetX, posY + offsetY, 0, 0);
			}
		}

		return new Mapa(cuadra, casas);
	}

	/**
	 * Clase auxiliar para contener ambos arrays del mapa
	 */
	public static class Mapa {
		public Manzana[] manzanas;
		public Casa[] casas;

		public Mapa(Manzana[] manzanas, Casa[] casas) {
			this.manzanas = manzanas;
			this.casas = casas;
		}
	}
}
