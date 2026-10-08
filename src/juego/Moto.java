package juego;

import java.awt.Color;

import entorno.Entorno;

public class Moto {
	double x;
	double y;
	double ancho;
	double alto;
	double velocidad;


	public Moto(double x, double y) {
		this.x = x;
		this.y = y;
		this.ancho =20;
		this.alto = 20;
		this.velocidad = 2;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto,0, Color.black);

	}
	
	public void moverDerecha() {
		this.x = this.x + velocidad;
	}
	public void moverIzquierda() {
		this.x = this.x - velocidad;
	}
	public void moverArriba() {
		this.y = this.y - velocidad;
	}
	public void moverAbajo()
	{
		this.y = this.y + velocidad;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAncho() {
		return ancho; }
	public double getAlto() {
		return alto; }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


