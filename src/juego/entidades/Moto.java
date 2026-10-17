package juego.entidades;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import juego.util.Constantes;

public class Moto {
	public double x;
	public double y;
	double velocidad;
	double angulo;
	public Image motoArriba;
	public Image motoAbajo;
	public Image motoDerecha;
	public Image motoIzquierda;



	public Moto(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = Constantes.REPARTIDOR_VELOCIDAD;
		this.angulo = 0;
		this.motoArriba = Herramientas.cargarImagen("imagenes/arriba.png");
		this.motoAbajo = Herramientas.cargarImagen("imagenes/abajo.png");
		this.motoDerecha = Herramientas.cargarImagen("imagenes/derecha.png");
		this.motoIzquierda = Herramientas.cargarImagen("imagenes/izquierda.png");

		};
	
	public void dibujar(Entorno entorno) {
		if (this.angulo == 0) {
			entorno.dibujarImagen(this.motoArriba, this.x, this.y, 0, 0.130);
		} else if (this.angulo == Math.PI) {
			entorno.dibujarImagen(this.motoAbajo, this.x, this.y, 0, 0.130);
		} else if (this.angulo == Math.PI / 2) {
			entorno.dibujarImagen(this.motoDerecha, this.x, this.y, 0, 0.130);
		} else if (this.angulo == -Math.PI / 2) {
			entorno.dibujarImagen(this.motoIzquierda, this.x, this.y, 0, 0.130);
		}
	}
	
	//Movimientos de la moto + la dirrecion (?
	public void moverDerecha() {
		this.x = this.x + velocidad;
		this.angulo = Math.PI / 2;
	}
	public void moverIzquierda() {
		this.x = this.x - velocidad;
		this.angulo = -Math.PI / 2;
	}
	public void moverArriba() {
		this.y = this.y - velocidad;
		this.angulo = 0;
	}
	public void moverAbajo()
	{
		this.y = this.y + velocidad;
		this.angulo = Math.PI;
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
		if (this.angulo == Math.PI / 2 || this.angulo == -Math.PI / 2) {
			return 60; 
	}
		return 30;
	}
	public double getAlto() {
		if (this.angulo == Math.PI / 2 || this.angulo == -Math.PI / 2) {
			return 25;
	 }
	 	return 45;
	}
	public double getAngulo(){
		return angulo;
	}
	public void setAngulo(double angulo){
		this.angulo = angulo;
	}

	public double getRadioColision() {
		return Constantes.REPARTIDOR_RADIO;
	}
}


