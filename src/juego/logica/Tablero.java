package juego.logica;

import juego.entidades.Bomba;
import juego.entidades.Enemigo;
import juego.entidades.Moto;
import juego.entidades.Proyectil;
import juego.mapa.Casa;
import juego.mapa.Manzana;
import juego.mapa.Negocio;

public class Tablero {
	private Moto moto;
	private Enemigo[] enemigos;
	private Bomba[] bombas;
	private Proyectil[] proyectiles;
	private Manzana[] manzanas;
	private Casa[] casas;
	private Negocio[] negocios;
	private boolean juegoActivo;

	public Tablero() {
		this.juegoActivo = true;
	}

	public void inicializar() {
		// Inicializar moto
		this.moto = new Moto(580, 800);

		// Inicializar mapa
		Manzana.Mapa mapa = Manzana.generarMapa();
		this.manzanas = mapa.manzanas;
		this.casas = mapa.casas;

		// Inicializar enemigos (comentado hasta tener imágenes)
		// this.enemigos = new Enemigo[4];
		// for (int i = 0; i < enemigos.length; i++) {
		// 	this.enemigos[i] = new Enemigo(200 + i * 200, 300);
		// }
		this.enemigos = new Enemigo[0];

		// Inicializar arrays de bombas y proyectiles
		this.bombas = new Bomba[10];
		this.proyectiles = new Proyectil[20];
	}

	public void actualizar() {
		// Lógica de actualización del tablero
	}

	public Moto getMoto() {
		return moto;
	}

	public Enemigo[] getEnemigos() {
		return enemigos;
	}

	public Bomba[] getBombas() {
		return bombas;
	}

	public Proyectil[] getProyectiles() {
		return proyectiles;
	}

	public Manzana[] getManzanas() {
		return manzanas;
	}

	public Casa[] getCasas() {
		return casas;
	}

	public Negocio[] getNegocios() {
		return negocios;
	}

	public boolean isJuegoActivo() {
		return juegoActivo;
	}

	public void setJuegoActivo(boolean juegoActivo) {
		this.juegoActivo = juegoActivo;
	}
}
