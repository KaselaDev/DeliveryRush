package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;
import juego.entidades.Moto;
import juego.mapa.Manzana;
import juego.mapa.Casa;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	private Moto moto;
	private Manzana[] cuadra;
	private Casa[] casas;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1150, 900);

		// Inicializar componentes del juego
		inicializarMoto();
		inicializarMapa();

		// Inicia el juego!
		this.entorno.iniciar();
	}

	private void inicializarMoto() {
		this.moto = new Moto(580, 800);
	}

	private void inicializarMapa() {
		Manzana.Mapa mapa = Manzana.generarMapa();
		this.cuadra = mapa.manzanas;
		this.casas = mapa.casas;
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y
	 * por lo tanto es el método más importante de esta clase. Aquí se debe
	 * actualizar el estado interno del juego para simular el paso del tiempo
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		dibujarInterfaz();
		dibujarMapa();
		moto.verificarLimitesPantalla(entorno);
		moto.procesarMovimiento(entorno, cuadra);
		moto.dibujar(entorno);
	}

	private void dibujarInterfaz() {
		entorno.dibujarRectangulo(500, 50, 1300, 100, 0, Color.black);
	}

	private void dibujarMapa() {
		entorno.colorFondo(Color.gray);

		for(Manzana m : this.cuadra) {
			m.dibujar(entorno);
		}

		for(Casa c : this.casas) {
			c.dibujar(entorno);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
