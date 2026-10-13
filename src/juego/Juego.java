package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import juego.logica.GestorPantallas;
import juego.logica.Tablero;
import juego.pantallas.MenuPantalla;
import juego.pantallas.JuegoPantalla;
import juego.util.Constantes;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	private GestorPantallas gestorPantallas;
	private Tablero tablero;
	private MenuPantalla menuPantalla;
	private JuegoPantalla juegoPantalla;

	Juego() {
		this.entorno = new Entorno(this, "DeliveryRush", Constantes.VENTANA_ANCHO, Constantes.VENTANA_ALTO);

		this.gestorPantallas = new GestorPantallas();
		this.tablero = new Tablero();
		this.menuPantalla = new MenuPantalla(entorno);
		this.juegoPantalla = new JuegoPantalla(entorno, tablero);

		this.tablero.inicializar();

		this.entorno.iniciar();
	}

	public void tick() {
		if (gestorPantallas.esMenu()) {
			menuPantalla.dibujar();
			procesarMenu();
		} else if (gestorPantallas.esJuego()) {
			juegoPantalla.actualizar();
			juegoPantalla.dibujar();
		} else if (gestorPantallas.esGameOver()) {
			// Pantalla game over
		} else if (gestorPantallas.esVictoria()) {
			// Pantalla victoria
		}
	}

	private void procesarMenu() {
		// Procesamiento del menú - pendiente de implementar
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
