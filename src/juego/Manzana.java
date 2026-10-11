package juego;

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
	double getX() {
		return x;
	}
	double getY() {
		return y;
	}
	double getAncho() {
		return ancho;
	}
	double getAlto() {
		return alto;
	}
	
	

}
