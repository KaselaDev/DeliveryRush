package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import java.util.Random;
import juego.entidades.Moto;
import juego.mapa.Manzana;
import juego.mapa.Casa;
import juego.mapa.Calle;
import juego.util.Constantes;

public class JuegoPantalla {
	private Entorno entorno;
	private Moto moto;
	private Object[] celdas; // Puede ser Calle o Manzana
	private int filas = 9;
	private int columnas = 9;
	private double altoInterfaz = 80;
	private final Random random = new Random();

	public JuegoPantalla(Entorno entorno) {
		this.entorno = entorno;
		inicializar();
	}

	private void inicializar() {
		this.moto = new Moto(580, 800);
		inicializarMapa();
	}

	// JuegoPantalla.java
	public void dibujar() {
		dibujarInterfaz();
		dibujarMapa();

		// Dibujar moto
		if (this.moto != null) {
			this.moto.dibujar(entorno);
		}
	}

	public void actualizar() {
		if (this.moto != null) {
			verificarLimitesPantalla();
			procesarMovimientoMoto();
		}
	}

	// METODOS AUXILIARES

	private void inicializarMapa() {
		this.celdas = new Object[this.filas * this.columnas];

		double anchoCelda = entorno.ancho() / this.columnas;
		double altoCelda = (entorno.alto() - this.altoInterfaz) / this.filas;

		int indexCelda = 0;

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				double x = (j * anchoCelda) + (anchoCelda / 2);
				double y = (i * altoCelda) + (altoCelda / 2) + this.altoInterfaz;

				if(i%2 != 0) {
					if(j%2 == 0){
						this.celdas[indexCelda++] = new Calle(x, y, anchoCelda, altoCelda, i, j);
					} else {
						this.celdas[indexCelda++] = new Manzana(x, y, anchoCelda, altoCelda, this.entorno);
					}
				} else {
					this.celdas[indexCelda++] = new Calle(x, y, anchoCelda, altoCelda, i, j);
				}
			}
		}
	}

	private void dibujarMapa() {
		// Dibujar celdas (calles o manzanas)
		for (Object celda : this.celdas) {
			if (celda != null) {
				if (celda instanceof Calle) {
					((Calle) celda).dibujar(this.entorno);
				}
			}
		}

		for (Object celda : this.celdas) {
			if (celda != null) {
				if (celda instanceof Manzana) {
					Manzana m = ((Manzana) celda);
					((Manzana) celda).dibujar();
					for(Casa casa : m.getCasas()) {
						casa.dibujar();
					}
				}
			}
		}
	}

	private boolean colisionaConMapa(Moto m) {
		for (Object celda : this.celdas) {
			if (celda != null && celda instanceof Manzana) {
				if (choca(m, (Manzana) celda)) {
					return true;
				}
			}
		}
		return false;
	}

	private void verificarLimitesPantalla() {
		double r = Constantes.REPARTIDOR_RADIO + 10;
		double limiteSuperiorInterfaz = this.altoInterfaz;

		if (this.moto.getX() < r) {
			this.moto.setX(r);
		}
		if (this.moto.getX() > this.entorno.ancho() - r) {
			this.moto.setX(this.entorno.ancho() - r);
		}
		if (this.moto.getY() < limiteSuperiorInterfaz + r) {
			this.moto.setY(limiteSuperiorInterfaz + r);
		}
		if (this.moto.getY() > this.entorno.alto() - r) {
			this.moto.setY(this.entorno.alto() - r);
		}
	}

	private void dibujarInterfaz() {
		this.entorno.dibujarRectangulo(this.entorno.ancho() / 2, 40, this.entorno.ancho(), this.altoInterfaz, 0, Color.BLACK);
		this.entorno.cambiarFont("Arial", 16, Color.WHITE);
		int segundos = this.entorno.tiempo() / 1000;
		this.entorno.escribirTexto("Tiempo: " + segundos + "s", 30, 45);
	}

	private void procesarMovimientoMoto() {
		double xAnterior = this.moto.getX();
		double anguloAnterior = this.moto.getAngulo();

		if (entorno.estaPresionada('d')) {
			this.moto.moverDerecha();
		}
		if (entorno.estaPresionada('a')) {
			this.moto.moverIzquierda();
		}

		if (colisionaConMapa(this.moto)) {
			this.moto.setX(xAnterior);
			this.moto.setAngulo(anguloAnterior);
		}

		double yAnterior = this.moto.getY();
		anguloAnterior = this.moto.getAngulo();

		if (entorno.estaPresionada('w')) {
			this.moto.moverArriba();
		}
		if (entorno.estaPresionada('s')) {
			this.moto.moverAbajo();
		}

		if (colisionaConMapa(this.moto)) {
			this.moto.setY(yAnterior);
			this.moto.setAngulo(anguloAnterior);
		}
	}

	private boolean choca(Moto m, Manzana a) {
		double radioMoto = Constantes.REPARTIDOR_RADIO;
		double bordeDerMoto = m.getX() + radioMoto;
		double bordeIzqMoto = m.getX() - radioMoto;
		double bordeSupMoto = m.getY() - radioMoto;
		double bordeInfMoto = m.getY() + radioMoto;

		double bordeDerCuadra = a.getX() + (a.getAncho() / 2);
		double bordeIzqCuadra = a.getX() - (a.getAncho() / 2);
		double bordeArrbCuadra = a.getY() - (a.getAlto() / 2) - 15;
		double bordeAbjCuadra = a.getY() + (a.getAlto() / 2);

		return bordeDerMoto > bordeIzqCuadra && bordeIzqMoto < bordeDerCuadra && bordeInfMoto > bordeArrbCuadra && bordeSupMoto < bordeAbjCuadra;
	}
}
