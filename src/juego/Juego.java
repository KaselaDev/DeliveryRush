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
	private boolean juegoIniciado;

	Juego() {
		this.entorno = new Entorno(this, "DeliveryRush", Constantes.VENTANA_ANCHO, Constantes.VENTANA_ALTO);

		this.gestorPantallas = new GestorPantallas();
		this.menuPantalla = new MenuPantalla(entorno, this.gestorPantallas);
		this.juegoPantalla = new JuegoPantalla(entorno, this.gestorPantallas);

		this.entorno.iniciar();
	}

	public void tick() {
		if (this.gestorPantallas.esMenu()) {
			this.juegoIniciado = false;
			this.menuPantalla.actualizar();
			this.menuPantalla.dibujar();
		} else if (this.gestorPantallas.esJuego()) {
			if (!this.juegoIniciado) {
				this.juegoPantalla.reiniciar();
				this.juegoIniciado = true;
			}
			this.juegoPantalla.actualizar();
			this.juegoPantalla.dibujar();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
