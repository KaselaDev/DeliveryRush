package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import juego.entidades.Disparo;
import juego.entidades.Moto;
import juego.mapa.Manzana;
import juego.mapa.Casa;
import juego.mapa.Calle;
import juego.mapa.Pedido;
import juego.gui.cabecera.Cabecera;
import juego.util.ButtonsMaster;
import juego.util.Constantes;

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
	private boolean pausado;
	private boolean relojIniciado;
	private boolean victoria;
	private long tiempoFinalMillis;
	private int tiempoInicio;
	private int inicioPausa;
	private long tiempoPausado;
	private final ButtonsMaster botonPausa = new ButtonsMaster(
			1102, Constantes.LIMITE_INTERFAZ / 2, 78, Constantes.LIMITE_INTERFAZ - 24);

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
		if (this.pausado) {
			this.dibujarPausa();
		}
		Cabecera cabecera = new Cabecera(this.entorno, this.moto.getVidas(), this.pedidosEntregados,
				this.pedidosPerdidos, this.pedidoActual.getEstado(),
				this.pedidoActual.getSegundosRestantes(), this.getTiempoJuegoMillis(), this.pausado);
		cabecera.dibujarInterfaz();
		if (this.victoria) {
			this.dibujarVictoria();
		}
	}

	public void actualizar() {
		if (this.victoria) {
			return;
		}

		if (!this.relojIniciado) {
			this.tiempoInicio = this.entorno.tiempo();
			this.relojIniciado = true;
		}

		boolean escapePresionado = this.entorno.sePresiono(this.entorno.TECLA_ESCAPE);
		boolean clickPausa = this.entorno.sePresionoBoton(this.entorno.BOTON_IZQUIERDO)
				&& this.botonPausa.verificarClick(this.entorno.mouseX(), this.entorno.mouseY());
		if (escapePresionado || clickPausa) {
			this.alternarPausa();
		}
		if (this.pausado) {
			return;
		}

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
				if (this.pedidosEntregados >= Constantes.PEDIDOS_PARA_GANAR) {
					this.tiempoFinalMillis = this.getTiempoJuegoMillis();
					this.victoria = true;
				}
			}
		}

		procesarMovimientoMoto();
		this.moto.mantenerDentroDePantalla(
				this.entorno.ancho(), this.entorno.alto(), (int) this.altoInterfaz);
		procesarDisparo(!clickPausa);
	}

	private void alternarPausa() {
		if (this.pausado) {
			this.tiempoPausado += this.entorno.tiempo() - this.inicioPausa;
			this.pausado = false;
		} else {
			this.inicioPausa = this.entorno.tiempo();
			this.pausado = true;
		}
	}

	private long getTiempoJuegoMillis() {
		if (this.victoria) {
			return this.tiempoFinalMillis;
		}
		int tiempoActual = this.pausado ? this.inicioPausa : this.entorno.tiempo();
		return Math.max(0L, (long) tiempoActual - this.tiempoInicio - this.tiempoPausado);
	}

	private void dibujarVictoria() {
		int centroX = this.entorno.ancho() / 2;
		int centroY = this.entorno.alto() / 2;
		long segundos = this.tiempoFinalMillis / 1000;
		this.entorno.dibujarRectangulo(centroX, centroY, this.entorno.ancho(), this.entorno.alto(), 0,
				new Color(0, 0, 0, 180));
		this.entorno.dibujarRectangulo(centroX, centroY, 560, 210, 0, new Color(1, 35, 31, 245));
		this.entorno.cambiarFont("Arial", 40, Color.WHITE, this.entorno.NEGRITA);
		this.entorno.escribirTexto("¡GANASTE!", centroX - 105, centroY - 25);
		this.entorno.cambiarFont("Arial", 24, Color.YELLOW);
		this.entorno.escribirTexto(String.format("Tiempo: %02d:%02d", segundos / 60, segundos % 60),
				centroX - 105, centroY + 35);
	}

	private void dibujarPausa() {
		int centroX = this.entorno.ancho() / 2;
		int centroY = this.entorno.alto() / 2;
		this.entorno.dibujarRectangulo(centroX, centroY, this.entorno.ancho(), this.entorno.alto(), 0,
				new Color(0, 0, 0, 70));
		this.entorno.dibujarRectangulo(centroX, centroY, 520, 170, 0, new Color(1, 35, 31, 245));
		this.entorno.cambiarFont("Arial", 34, Color.WHITE, this.entorno.NEGRITA);
		this.entorno.escribirTexto("JUEGO EN PAUSA", centroX - 150, centroY - 8);
		this.entorno.cambiarFont("Arial", 18, Color.YELLOW);
		this.entorno.escribirTexto("Presiona ESC para continuar", centroX - 130, centroY + 35);
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

	private void procesarDisparo(boolean permitirNuevoDisparo) {
		if (permitirNuevoDisparo && this.entorno.sePresionoBoton(this.entorno.BOTON_IZQUIERDO)
				&& this.disparo == null) {
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
