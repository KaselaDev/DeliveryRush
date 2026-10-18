package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import juego.entidades.Disparo;
import juego.entidades.Moto;
import juego.mapa.Manzana;
import juego.mapa.Casa;
import juego.mapa.Calle;
import juego.mapa.Pedido;

public class JuegoPantalla {
	private Entorno entorno;
	private Moto moto;
	private Object[] celdas; // Puede ser Calle o Manzana
	private Manzana[] cuadras;
	private Casa[] casas;
	private Pedido pedidoActual;
	private Disparo disparo;
	private int pedidosEntregados;
	private int pedidosPerdidos;
	private int filas = 9;
	private int columnas = 9;
	private double altoInterfaz = 80;

	public JuegoPantalla(Entorno entorno) {
		this.entorno = entorno;
		inicializar();
	}

	private void inicializar() {
		this.moto = new Moto(this.entorno.ancho() / 2, this.entorno.alto() - 35);
		inicializarMapa();
		this.pedidoActual = new Pedido(this.casas);
	}

	public void dibujar() {
		dibujarMapa();

		if (this.pedidoActual != null) {
			this.pedidoActual.dibujarIndicador(this.entorno);
		}
		if (this.disparo != null) {
			this.disparo.dibujar(this.entorno);
		}
		if (this.moto != null) {
			this.moto.dibujar(this.entorno);
		}
		dibujarInterfaz();
	}

	public void actualizar() {
		if (this.pedidoActual != null) {
			String estadoAnterior = this.pedidoActual.getEstado();
			this.pedidoActual.actualizarTiempo();
			if (!"Perdido".equals(estadoAnterior) && "Perdido".equals(this.pedidoActual.getEstado())) {
				this.pedidosPerdidos++;
			}

			if (this.pedidoActual.listoParaSiguiente()) {
				this.pedidoActual = new Pedido(this.casas);
			}

			if (this.entorno.sePresionoBoton(this.entorno.BOTON_DERECHO)
					&& this.pedidoActual.interactuar(this.moto)) {
				this.pedidosEntregados++;
			}
		}

		procesarMovimientoMoto();
		this.moto.mantenerDentroDePantalla(
				this.entorno.ancho(), this.entorno.alto(), (int) this.altoInterfaz);
		procesarDisparo();
	}

	// METODOS AUXILIARES

	private void inicializarMapa() {
		this.celdas = new Object[this.filas * this.columnas];
		this.cuadras = new Manzana[this.celdas.length];
		this.casas = new Casa[this.celdas.length * 4];

		double anchoCelda = entorno.ancho() / this.columnas;
		double altoCelda = (entorno.alto() - this.altoInterfaz) / this.filas;

		int indexCelda = 0;
		int indexCuadra = 0;
		int indexCasa = 0;

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				double x = (j * anchoCelda) + (anchoCelda / 2);
				double y = (i * altoCelda) + (altoCelda / 2) + this.altoInterfaz;

				if(i%2 != 0) {
					if(j%2 == 0){
						this.celdas[indexCelda++] = new Calle(x, y, anchoCelda, altoCelda, i, j);
					} else {
						Manzana manzana = new Manzana(x, y, anchoCelda, altoCelda, this.entorno);
						this.celdas[indexCelda++] = manzana;
						this.cuadras[indexCuadra++] = manzana;
						for (Casa casa : manzana.getCasas()) {
							this.casas[indexCasa++] = casa;
						}
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
		return m.colisionaConMapa(this.cuadras);
	}

	private void dibujarInterfaz() {
		this.entorno.dibujarRectangulo(this.entorno.ancho() / 2, 40, this.entorno.ancho(), this.altoInterfaz, 0, Color.BLACK);
		this.entorno.cambiarFont("Arial", 16, Color.WHITE);
		int segundos = this.entorno.tiempo() / 1000;
		String textoEstado = this.pedidoActual.getEstado();
		if ("En espera".equals(textoEstado) || "En camino".equals(textoEstado)) {
			textoEstado += " (" + this.pedidoActual.getSegundosRestantes() + "s)";
		}

		this.entorno.escribirTexto(
				"Tiempo: " + segundos + "s  |  Entregados: " + this.pedidosEntregados
						+ "  |  Perdidos: " + this.pedidosPerdidos + "  |  Pedido: " + textoEstado,
				30, 45);
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

		if (this.colisionaConMapa(this.moto)) {
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

		if (this.colisionaConMapa(this.moto)) {
			this.moto.setY(yAnterior);
			this.moto.setAngulo(anguloAnterior);
		}
	}

	private void procesarDisparo() {
		if (this.entorno.sePresionoBoton(this.entorno.BOTON_IZQUIERDO) && this.disparo == null) {
			this.disparo = new Disparo(this.moto.getX(), this.moto.getY(), this.moto.getAngulo());
		}

		if (this.disparo != null) {
			this.disparo.mover();
			if (this.disparo.salioDePantalla(this.entorno) || this.disparo.chocaConCuadra(this.cuadras)) {
				this.disparo = null;
			}
		}
	}

}
