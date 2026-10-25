package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import java.util.Random;
import juego.entidades.Bomba;
import juego.entidades.Disparo;
import juego.entidades.Enemigo;
import juego.entidades.Moto;
import juego.mapa.Manzana;
import juego.mapa.Casa;
import juego.mapa.Calle;
import juego.mapa.Pedido;
import juego.gui.cabecera.Cabecera;
import juego.logica.GestorPantallas;
import juego.util.ButtonsMaster;
import juego.util.Constantes;

public class JuegoPantalla {
	private Entorno entorno;
	private GestorPantallas gestorPantallas;
	private Moto moto;
	private Object[] celdas; // Puede ser Calle o Manzana
	private Manzana[] cuadras;
	private Casa[] casas;
	private Pedido pedidoActual;
	private Disparo disparo;
	private Enemigo[] enemigos;
	private Bomba[] bombas;
	private int[] reaparicionTicks;
	private Random random = new Random();
	private long ultimoDanioMillis;
	private double anchoCelda;
	private double altoCelda;
	private int pedidosEntregados;
	private int pedidosPerdidos;
	private boolean derrota;
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
	private ButtonsMaster botonPausa = new ButtonsMaster(
			1102, Constantes.LIMITE_INTERFAZ / 2, 78, Constantes.LIMITE_INTERFAZ - 24);

	public JuegoPantalla(Entorno entorno, GestorPantallas gp) {
		this.entorno = entorno;
		this.gestorPantallas = gp;
		inicializar();
	}

	private void inicializar() {
		this.moto = new Moto(this.entorno.ancho() / 2, this.entorno.alto() - 35);
		inicializarMapa();
		this.pedidoActual = new Pedido(this.casas);
		this.bombas = new Bomba[Constantes.BOMBA_MAXIMA];
		this.enemigos = new Enemigo[Constantes.ENEMIGO_CANTIDAD_MINIMA];
		this.reaparicionTicks = new int[this.enemigos.length];
		for (int i = 0; i < this.enemigos.length; i++) {
			this.enemigos[i] = this.crearEnemigo();
		}
	}

	public void dibujar() {
		dibujarMapa();

		if (this.pedidoActual != null) {
			this.pedidoActual.dibujarIndicador(this.entorno);
		}
		if (this.disparo != null) {
			this.disparo.dibujar(this.entorno);
		}
		for (Bomba bomba : this.bombas) {
			if (bomba != null) {
				bomba.dibujar(this.entorno);
			}
		}
		for (Enemigo enemigo : this.enemigos) {
			if (enemigo != null) {
				enemigo.dibujar(this.entorno);
			}
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
		if (this.derrota) {
			this.dibujarDerrota();
		}
	}

	public void actualizar() {
		if (this.victoria || this.derrota) {
			if (this.derrota && this.entorno.sePresiono(this.entorno.TECLA_ESCAPE)) {
				this.gestorPantallas.cambiarPantalla("menu");
			}
			if (this.derrota && this.entorno.sePresiono('r')) {
				this.reiniciar();
			}
			return;
		}

		if (!this.relojIniciado) {
			this.tiempoInicio = this.entorno.tiempo();
			this.relojIniciado = true;
		}

		if (this.moto.getVidas() == 0) {
			this.derrota = true;
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
		actualizarEnemigos();
		actualizarBombas();
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

		this.anchoCelda = entorno.ancho() / this.columnas;
		this.altoCelda = (entorno.alto() - this.altoInterfaz) / this.filas;

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

	private Enemigo crearEnemigo() {
		for (int intento = 0; intento < 200; intento++) {
			int index = this.random.nextInt(this.celdas.length);
			if (!(this.celdas[index] instanceof Calle)) {
				continue;
			}
			int fila = index / this.columnas;
			int columna = index % this.columnas;
			double x = (columna * this.anchoCelda) + (this.anchoCelda / 2);
			double y = (fila * this.altoCelda) + (this.altoCelda / 2) + this.altoInterfaz;
			if (Math.hypot(x - this.moto.getX(), y - this.moto.getY()) < this.anchoCelda * 2) {
				continue;
			}
			// los enemigos caminan en paralelo a la calle de la celda elegida
			double angulo;
			if (fila % 2 == 0) {
				angulo = this.random.nextBoolean() ? Math.PI / 2 : -Math.PI / 2;
			} else {
				angulo = this.random.nextBoolean() ? 0 : Math.PI;
			}
			return new Enemigo(x, y, angulo);
		}
		return new Enemigo(this.entorno.ancho() / 2, this.altoInterfaz + this.altoCelda / 2, Math.PI);
	}

	private Bomba crearBomba(Enemigo enemigo) {
		double anchoExplosion;
		double altoExplosion;
		if (enemigo.esHorizontal()) {
			anchoExplosion = this.anchoCelda * Constantes.BOMBA_EXPLOSION_CELDAS;
			altoExplosion = this.altoCelda;
		} else {
			anchoExplosion = this.anchoCelda;
			altoExplosion = this.altoCelda * Constantes.BOMBA_EXPLOSION_CELDAS;
		}
		return new Bomba(enemigo.getX(), enemigo.getY(), anchoExplosion, altoExplosion,
				!enemigo.esHorizontal());
	}

	private void agregarBomba(Bomba bomba) {
		for (int i = 0; i < this.bombas.length; i++) {
			if (this.bombas[i] == null) {
				this.bombas[i] = bomba;
				return;
			}
		}
	}

	private void actualizarEnemigos() {
		for (int i = 0; i < this.enemigos.length; i++) {
			Enemigo enemigo = this.enemigos[i];
			if (enemigo == null) {
				if (this.reaparicionTicks[i] > 0) {
					this.reaparicionTicks[i]--; // el enemigo muerto no vuelve enseguida
				} else {
					this.enemigos[i] = this.crearEnemigo();
				}
				continue;
			}
			double xAnterior = enemigo.getX();
			double yAnterior = enemigo.getY();
			double anguloAnterior = enemigo.getAngulo();

			enemigo.mover();

			if (this.enemigoColisionaConMapa(enemigo) || this.enemigoFueraDePantalla(enemigo)) {
				enemigo.setX(xAnterior);
				enemigo.setY(yAnterior);
				enemigo.setAngulo(anguloAnterior);
				enemigo.darVuelta();
			}

			if (enemigo.debeSoltarBomba()) {
				this.agregarBomba(this.crearBomba(enemigo));
			}

			if (enemigo.colisionaCon(this.moto.getX(), this.moto.getY(), this.moto.getRadioColision())) {
				this.hacerDanioMoto();
			}
		}

		// si dos enemigos chocan entre sí, los dos dan la vuelta
		for (int i = 0; i < this.enemigos.length; i++) {
			for (int j = i + 1; j < this.enemigos.length; j++) {
				Enemigo enemigoA = this.enemigos[i];
				Enemigo enemigoB = this.enemigos[j];
				if (enemigoA != null && enemigoB != null
						&& enemigoA.colisionaCon(enemigoB.getX(), enemigoB.getY(), Constantes.ENEMIGO_RADIO)) {
					enemigoA.rebotarCon(enemigoB);
				}
			}
		}
	}

	private boolean enemigoColisionaConMapa(Enemigo enemigo) {
		double radio = Constantes.ENEMIGO_RADIO;
		double bordeDer = enemigo.getX() + radio;
		double bordeIzq = enemigo.getX() - radio;
		double bordeArr = enemigo.getY() - radio;
		double bordeAbj = enemigo.getY() + radio;
		for (Manzana cuadra : this.cuadras) {
			if (cuadra == null) {
				continue;
			}
			if (bordeDer > cuadra.getX() - cuadra.getAncho() / 2
					&& bordeIzq < cuadra.getX() + cuadra.getAncho() / 2
					&& bordeAbj > cuadra.getY() - cuadra.getAlto() / 2 - Constantes.MANZANA_ARRIBA_OFFSET
					&& bordeArr < cuadra.getY() + cuadra.getAlto() / 2) {
				return true;
			}
		}
		return false;
	}

	private boolean enemigoFueraDePantalla(Enemigo enemigo) {
		double radio = Constantes.ENEMIGO_RADIO;
		return enemigo.getX() - radio < 0 || enemigo.getX() + radio > this.entorno.ancho()
				|| enemigo.getY() - radio < this.altoInterfaz
				|| enemigo.getY() + radio > this.entorno.alto();
	}

	private void actualizarBombas() {
		for (int i = 0; i < this.bombas.length; i++) {
			Bomba bomba = this.bombas[i];
			if (bomba == null) {
				continue;
			}
			bomba.actualizar();
			if (bomba.terminada()) {
				this.bombas[i] = null;
				continue;
			}
			if (bomba.estaExplotando()) {
				if (bomba.danioA(this.moto.getX(), this.moto.getY(), this.moto.getRadioColision()) > 0) {
					this.moto.InstantKill();
				}
				for (int j = 0; j < this.enemigos.length; j++) {
					Enemigo enemigo = this.enemigos[j];
					if (enemigo != null && bomba.alcanzaA(enemigo.getX(), enemigo.getY(),
							Constantes.ENEMIGO_RADIO)) {
						this.enemigos[j] = null; // la explosión también destruye enemigos
						this.reaparicionTicks[j] = Constantes.ENEMIGO_TICKS_REAPARICION;
					}
				}
			}
		}
	}

	private void hacerDanioMoto() {
		long ahora = this.entorno.tiempo();
		if (this.ultimoDanioMillis != 0
				&& ahora - this.ultimoDanioMillis < Constantes.REPARTIDOR_INVULNERABLE_MILLIS) {
			return;
		}
		this.ultimoDanioMillis = ahora;
		this.moto.perderVida();
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

			// el disparo mata al primer enemigo que toca y se destruye
			for (int i = 0; i < this.enemigos.length && this.disparo != null; i++) {
				Enemigo enemigo = this.enemigos[i];
				if (enemigo != null && enemigo.colisionaCon(this.disparo.getX(), this.disparo.getY(),
						this.disparo.getRadioColision())) {
					this.enemigos[i] = null;
					this.reaparicionTicks[i] = Constantes.ENEMIGO_TICKS_REAPARICION;
					this.disparo = null;
				}
			}

			if (this.disparo != null
					&& (this.disparo.salioDePantalla(this.entorno) || this.disparo.chocaConCuadra(this.cuadras))) {
				this.disparo = null;
			}
		}
	}

	private void dibujarDerrota() {
		int centroX = this.entorno.ancho() / 2;
		int centroY = this.entorno.alto() / 2;
		int textX = centroX - 300 + 32;
		this.entorno.dibujarRectangulo(centroX, centroY, this.entorno.ancho(), this.entorno.alto(), 0,
				new Color(0, 0, 0, 180));
		this.entorno.dibujarRectangulo(centroX, centroY, 600, 280, 0, new Color(60, 0, 0, 245));
		this.entorno.cambiarFont("Arial", 40, Color.WHITE, this.entorno.NEGRITA);
		this.entorno.escribirTexto("¡PARTIDA PERDIDA!", textX, centroY - 70);
		this.entorno.cambiarFont("Arial", 24, Color.RED);
		this.entorno.escribirTexto("¡Te quedaste sin vidas!", textX, centroY);
		this.entorno.cambiarFont("Arial", 18, new Color(200, 200, 200));
		this.entorno.escribirTexto("Presiona ESC para volver al menú", textX, centroY + 40);
		this.entorno.cambiarFont("Arial", 18, new Color(200, 200, 200));
		this.entorno.escribirTexto("Presiona R para reiniciar", textX, centroY + 80);
	}

	private void reiniciar() {
		this.derrota = false;
		this.victoria = false;
		this.pausado = false;
		this.relojIniciado = false;
		this.tiempoPausado = 0;
		this.pedidosEntregados = 0;
		this.pedidosPerdidos = 0;
		this.ultimoDanioMillis = 0;
		
		// Limpiar arrays
		this.bombas = new Bomba[Constantes.BOMBA_MAXIMA];
		this.disparo = null;
		
		// Reiniciar enemigos
		for (int i = 0; i < this.enemigos.length; i++) {
			this.enemigos[i] = this.crearEnemigo();
			this.reaparicionTicks[i] = 0;
		}
		
		// Reiniciar moto
		this.moto = new Moto(this.entorno.ancho() / 2, this.entorno.alto() - 35);
		
		// Reiniciar pedido
		this.pedidoActual = new Pedido(this.casas);
	}

}
