package juego.entidades;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import juego.util.Constantes;

public class Enemigo {
	private double x;
	private double y;
	private double velocidad;
	private double angulo;   // igual que la Moto: 0 arriba, Math.PI abajo, Math.PI / 2 derecha, -Math.PI / 2 izquierda
	private boolean ponedorDeBombas;
	private int ticksParaBomba;
	
	// Las imágenes se cargan una sola vez y las comparten todos los enemigos
	private static Image enemigo_arriba = Herramientas.cargarImagen("imagenes/enemigoArriba.png");
	private static Image enemigo_abajo = Herramientas.cargarImagen("imagenes/enemigoAbajo.png");
	private static Image enemigo_derecha = Herramientas.cargarImagen("imagenes/enemigoDerecha.png");
	private static Image enemigo_izquierda = Herramientas.cargarImagen("imagenes/enemigoIzquierda.png");



	public Enemigo(double x, double y, double angulo) {
		this.x = x;
		this.y = y;
		this.angulo = angulo;
		this.velocidad = Constantes.ENEMIGO_VELOCIDAD;                 
		this.ponedorDeBombas = Math.random() < Constantes.ENEMIGO_PROBABILIDAD_BOMBA;
		
		// el primer tiempo es al azar, así los ponedores no sueltan todas las bombas a la vez
		this.ticksParaBomba = (int) (Constantes.ENEMIGO_TICKS_BOMBA * (0.3 + 0.7 * Math.random()));
		
	}

	public void dibujar(Entorno entorno) {
		Image imagen;
		if (this.angulo == 0) {
			imagen = enemigo_arriba;
		} else if (this.angulo == Math.PI) {
			imagen = enemigo_abajo;
		} else if (this.angulo == Math.PI / 2) {
			imagen = enemigo_derecha;
		} else {
			imagen = enemigo_izquierda;
		}
		entorno.dibujarImagen(imagen, this.x, this.y, 0, .3);
	}
	


// Movimientos (iguales a los de la Moto)
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
 
	public void moverAbajo() {
		this.y = this.y + velocidad;
		this.angulo = Math.PI;
	}
 
	// Avanza hacia donde está mirando
	public void mover() {
		if (this.angulo == 0) {
			moverArriba();
		} else if (this.angulo == Math.PI) {
			moverAbajo();
		} else if (this.angulo == Math.PI / 2) {
			moverDerecha();
		} else {
			moverIzquierda();
		}
	}
 
	// Pasa a mirar para el lado contrario
	public void darVuelta() {
		if (this.angulo == 0) {
			this.angulo = Math.PI;
		} else if (this.angulo == Math.PI) {
			this.angulo = 0;
		} else if (this.angulo == Math.PI / 2) {
			this.angulo = -Math.PI / 2;
		} else {
			this.angulo = Math.PI / 2;
		}
	}
 
	// Si chocan yendo para lados distintos, los dos dan la vuelta
	public void rebotarCon(Enemigo otro) {
		if (this.angulo != otro.angulo) {
			this.darVuelta();
			otro.darVuelta();
		}
	}
	
	// BOMBAS

 
	// Llamarlo una vez por tick: devuelve true cuando le toca dejar una bomba
	public boolean debeSoltarBomba() {
		if (!ponedorDeBombas) {
			return false;
		}
		ticksParaBomba--;
		if (ticksParaBomba > 0) {
			return false;
		}
		ticksParaBomba = Constantes.ENEMIGO_TICKS_BOMBA;
		return true;
	}
	
	public boolean esPonedorDeBombas() {
		return this.ponedorDeBombas;
	}
	
	// COLISIONES
 
	public boolean colisionaCon(double px, double py, double radio) {
		double dx = this.x - px;
		double dy = this.y - py;
		double suma = Constantes.ENEMIGO_RADIO + radio;
		return dx * dx + dy * dy < suma * suma;
	}
 
	public double getX() {
		return this.x;
	}
 
	public double getY() {
		return this.y;
	}
 
	public double getAngulo() {
		return this.angulo;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
 
	// La bomba que deja se orienta según la calle por la que camina
	public boolean esHorizontal() {
		return this.angulo == Math.PI / 2 || this.angulo == -Math.PI / 2;
	}
}