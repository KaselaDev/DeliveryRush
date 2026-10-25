package juego.entidades;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import juego.mapa.Casa;
import juego.mapa.Manzana;
import juego.util.Constantes;

public class Moto {
	private static double DISTANCIA_INTERACCION = 75;
	private double x;
	private double y;
	private double velocidad;
	private double angulo;
	private double radioColision;
	public Image motoArriba;
	public Image motoAbajo;
	public Image motoDerecha;
	public Image motoIzquierda;
	private int vidas;



	public Moto(double x, double y) {
		
		this.x = x;
		this.y = y;
		this.velocidad = Constantes.REPARTIDOR_VELOCIDAD;
		this.angulo = 0;
		this.radioColision = Constantes.REPARTIDOR_RADIO;
		this.motoArriba = Herramientas.cargarImagen("imagenes/arriba.png");
		this.motoAbajo = Herramientas.cargarImagen("imagenes/abajo.png");
		this.motoDerecha = Herramientas.cargarImagen("imagenes/derecha.png");
		this.motoIzquierda = Herramientas.cargarImagen("imagenes/izquierda.png");
		this.vidas = Constantes.REPARTIDOR_VIDAS_INICIALES;

		};
	

	public void dibujar(Entorno entorno) {
		if (this.angulo == 0) {
			entorno.dibujarImagen(this.motoArriba, this.x, this.y, 0, 0.170);
		} else if (this.angulo == Math.PI) {
			entorno.dibujarImagen(this.motoAbajo, this.x, this.y, 0, 0.170);
		} else if (this.angulo == Math.PI / 2) {
			entorno.dibujarImagen(this.motoDerecha, this.x, this.y, 0, 0.170);
		} else if (this.angulo == -Math.PI / 2) {
			entorno.dibujarImagen(this.motoIzquierda, this.x, this.y, 0, 0.170);
		}
	}
	
	// Método para restar una vida cuando choca con un enemigo
	public void perderVida() {
	    if (this.vidas > 0) {
	        this.vidas--;
	    }
	}

	public void InstantKill() {
	    if (this.vidas > 0) {
	        this.vidas = 0;
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
	
	// la moto choca con una cuadra
    public boolean chocaCon(Manzana a) {
    	if (a == null) {
			return false;
		}

		double bordeDerMoto = this.x + this.radioColision;
		double bordeIzqMoto = this.x - this.radioColision;
		double bordeSupMoto = this.y - this.radioColision;
		double bordeInfMoto = this.y + this.radioColision;

		double bordeDerCuadra = a.getX() + (a.getAncho() / 2);
		double bordeIzqCuadra = a.getX() - (a.getAncho() / 2);
		double bordeArrbCuadra = a.getY() - (a.getAlto() / 2) - Constantes.MANZANA_ARRIBA_OFFSET;
		double bordeAbjCuadra = a.getY() + (a.getAlto() / 2);

		return bordeDerMoto > bordeIzqCuadra && bordeIzqMoto < bordeDerCuadra
				&& bordeInfMoto > bordeArrbCuadra && bordeSupMoto < bordeAbjCuadra;
	}
    
    public boolean colisionaConMapa(Manzana[] cuadras) {
		if (cuadras == null) {
			throw new IllegalArgumentException("La lista de cuadras no puede ser nula.");
		}
		for (Manzana cuadra : cuadras) {
			if (this.chocaCon(cuadra)) {
				return true;
			}
		}
		return false;
	}

 // Calcula si está cerca de una casa o negocio.
 	public boolean estaCercaDe(Casa c) {
 		if (c == null) {
 			throw new IllegalArgumentException("La casa no puede ser nula.");
 		}

 		double dist = Math.hypot(this.x - c.getX(), this.y - c.getY());
 		return dist < DISTANCIA_INTERACCION;
 	}
    
 // Mantiene la moto dentro de los límites de la pantalla.
 	public void mantenerDentroDePantalla(int anchoPantalla, int altoPantalla, int altoInterfaz) {
 		if (anchoPantalla <= 0 || altoPantalla <= 0 || altoInterfaz < 0) {
 			throw new IllegalArgumentException("Las dimensiones de pantalla deben ser válidas.");
 		}

 		double r = this.radioColision + 10;
 		this.x = Math.max(r, Math.min(this.x, anchoPantalla - r));
 		this.y = Math.max(altoInterfaz + r, Math.min(this.y, altoPantalla - r));
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
	return this.radioColision;
}
public int getVidas() {
	return this.vidas;
}
}


