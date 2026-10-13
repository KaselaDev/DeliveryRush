package juego.entidades;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import juego.mapa.Manzana;
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

	public void verificarLimitesPantalla(Entorno entorno) {
		double mitadAncho = this.getAncho() / 2;
		double mitadAlto = this.getAlto() / 2;
		double limiteInterfaz = Constantes.LIMITE_INTERFAZ;

		//Izquierdo
		if (this.x < mitadAncho) {
			this.x = mitadAncho;
		}
		//Derecho
		if (this.x > entorno.ancho() - mitadAncho) {
			this.x = entorno.ancho() - mitadAncho;
		}
		//Arriba
		if (this.y < limiteInterfaz + mitadAlto) {
			this.y = limiteInterfaz + mitadAlto;
		}
		//Abajo
		if (this.y > entorno.alto() - mitadAlto) {
			this.y = entorno.alto() - mitadAlto;
		}
	}

	public void procesarMovimiento(Entorno entorno, Manzana[] cuadra) {
		//Movimiento de la moto eje x
		double xAnterior = this.getX();
		double anguloAnterior = this.getAngulo();

		if(entorno.estaPresionada('d')) {
			this.moverDerecha();
		}
		if(entorno.estaPresionada('a')) {
			this.moverIzquierda();
		}
		//Colision eje x
		for(int i = 0; i < cuadra.length; i++) {
			if(cuadra[i] != null) {
				if(choca(this, cuadra[i])) {
					this.setX(xAnterior);
					this.setAngulo(anguloAnterior);
				}
			}
		}

		//Movimiento de la moto eje y
		double yAnterior = this.getY();
		anguloAnterior = this.getAngulo();

		if(entorno.estaPresionada('w')) {
			this.moverArriba();
		}
		if(entorno.estaPresionada('s')) {
			this.moverAbajo();
		}

		//Colision eje y
		for(int i = 0; i < cuadra.length; i++) {
			if(cuadra[i] != null) {
				if(choca(this, cuadra[i])) {
					this.setY(yAnterior);
					this.setAngulo(anguloAnterior);
				}
			}
		}
	}

	private boolean choca(Moto m, Manzana a) {
		//Cada borde de la moto
		double radioMoto = Constantes.REPARTIDOR_RADIO;
		double bordeDerMoto = m.getX() + radioMoto;
		double bordeIzqMoto = m.getX() - radioMoto;
		double bordeSupMoto = m.getY() - radioMoto;
		double bordeInfMoto = m.getY() + radioMoto;

		//Bordes de la cuadra
		double bordeDerCuadra = a.getX() + (a.getAncho() / 2) ;
		double bordeIzqCuadra = a.getX() - (a.getAncho() / 2) ;
		double bordeArrbCuadra = a.getY() - (a.getAlto() / 2) - Constantes.MANZANA_ARRIBA_OFFSET;
		double bordeAbjCuadra = a.getY() + (a.getAlto() / 2) - Constantes.MANZANA_ABAJO_OFFSET;

		return bordeDerMoto > bordeIzqCuadra && bordeIzqMoto < bordeDerCuadra && bordeInfMoto > bordeArrbCuadra && bordeSupMoto < bordeAbjCuadra;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}



