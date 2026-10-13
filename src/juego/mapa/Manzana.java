package juego.mapa;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import juego.util.Constantes;

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
		int ancho = Constantes.MANZANA_ANCHO;
		int alto = Constantes.MANZANA_ALTO;
		int separacionAncho = Constantes.SEPARACION_ANCHO;
		int separacionAlto = Constantes.SEPARACION_ALTO;

		for(int i = 0; i < Constantes.MAPA_FILAS; i++) {
			for(int j = 0; j < Constantes.MAPA_COLUMNAS; j++) {
				double posX = Constantes.MAPA_OFFSET_X + (j * separacionAncho);
				double posY = Constantes.MAPA_OFFSET_Y + (i * separacionAlto);
				cuadra[k++] = new Manzana(posX, posY, ancho, alto);

				double offsetX = Constantes.CASA_OFFSET_X;
				double offsetY = Constantes.CASA_OFFSET_Y;

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
