package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import juego.logica.GestorPantallas;
import juego.pantallas.MenuPantalla;
import juego.pantallas.JuegoPantalla;
import juego.util.Constantes;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	private GestorPantallas gestorPantallas;
	private MenuPantalla menuPantalla;
	private JuegoPantalla juegoPantalla;

	Juego() {
		this.entorno = new Entorno(this, "DeliveryRush", Constantes.VENTANA_ANCHO, Constantes.VENTANA_ALTO);

		this.gestorPantallas = new GestorPantallas();
		this.menuPantalla = new MenuPantalla(entorno, this.gestorPantallas);
		this.juegoPantalla = new JuegoPantalla(entorno, this.gestorPantallas);

		this.entorno.iniciar();
	}

	public void tick() {
		if (gestorPantallas.esMenu()) {
			menuPantalla.actualizar();
			menuPantalla.dibujar();
		} else if (gestorPantallas.esJuego()) {
			juegoPantalla.actualizar();
			juegoPantalla.dibujar();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
