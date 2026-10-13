package juego.logica;

import juego.util.Constantes;

public class GestorPantallas {
	private int pantallaActual;

	public GestorPantallas() {
		this.pantallaActual = Constantes.PANTALLA_JUEGO; // Temporalmente inicia en juego
	}

	public void cambiarPantalla(int nuevaPantalla) {
		this.pantallaActual = nuevaPantalla;
	}

	public int getPantallaActual() {
		return pantallaActual;
	}

	public boolean esMenu() {
		return pantallaActual == Constantes.PANTALLA_MENU;
	}

	public boolean esJuego() {
		return pantallaActual == Constantes.PANTALLA_JUEGO;
	}

	public boolean esGameOver() {
		return pantallaActual == Constantes.PANTALLA_GAMEOVER;
	}

	public boolean esVictoria() {
		return pantallaActual == Constantes.PANTALLA_VICTORIA;
	}
}
