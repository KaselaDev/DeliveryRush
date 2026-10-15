package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import java.util.Random;
import juego.entidades.Moto;
import juego.mapa.Manzana;
import juego.mapa.Casa;
import juego.util.Constantes;

public class JuegoPantalla {
	private Entorno entorno;
	private Moto moto;
	private Manzana[] cuadra;
	private Casa[] casas;
	private int filas = 4;
	private int columnas = 4;
	private double altoInterfaz = 80;

	public JuegoPantalla(Entorno entorno) {
		this.entorno = entorno;
		this.entorno.colorFondo(new Color(60, 60, 60));
		inicializar();
	}

	private void inicializar() {
		this.moto = new Moto(580, 800);
		inicializarMapa();
	}

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
		this.cuadra = new Manzana[this.filas * this.columnas];
		this.casas = new Casa[this.filas * this.columnas * 4];

		double anchoManzana = 180;
		double altoManzana = 110;
		double separacionX = 260;
		double separacionY = 180;

		double inicioX = 185;
		double inicioY = 210;

		double tamanoCasa = 40;
		double offsetX = 40;
		double offsetY = 25;

		int indexCuadra = 0;
		int indexCasa = 0;
		Random random = new Random();

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				double posX = inicioX + (j * separacionX);
				double posY = inicioY + (i * separacionY);

				this.cuadra[indexCuadra++] = new Manzana(posX, posY, anchoManzana, altoManzana);

				int esquinaNegocio = random.nextInt(4);

				this.casas[indexCasa++] = new Casa(posX - offsetX, posY - offsetY, tamanoCasa, tamanoCasa, Math.PI, esquinaNegocio == 0);
				this.casas[indexCasa++] = new Casa(posX + offsetX, posY - offsetY, tamanoCasa, tamanoCasa, Math.PI, esquinaNegocio == 1);
				this.casas[indexCasa++] = new Casa(posX - offsetX, posY + offsetY, tamanoCasa, tamanoCasa, 0, esquinaNegocio == 2);
				this.casas[indexCasa++] = new Casa(posX + offsetX, posY + offsetY, tamanoCasa, tamanoCasa, 0, esquinaNegocio == 3);
			}
		}
	}

	private void dibujarMapa() {
		for (int i = 0; i < this.cuadra.length; i++) {
			if (this.cuadra[i] != null) {
				this.cuadra[i].dibujar(this.entorno);
			}
		}
		for (int i = 0; i < this.casas.length; i++) {
			if (this.casas[i] != null) {
				this.casas[i].dibujar(this.entorno);
			}
		}
	}

	private boolean colisionaConMapa(Moto m) {
		for (int i = 0; i < this.cuadra.length; i++) {
			if (this.cuadra[i] != null && choca(m, this.cuadra[i])) {
				return true;
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
