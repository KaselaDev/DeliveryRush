package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import java.awt.Font;

public class MenuPantalla {
	private Entorno entorno;

	public MenuPantalla(Entorno entorno) {
		this.entorno = entorno;
	}

	public void dibujar() {
		// Fondo
		entorno.colorFondo(Color.darkGray);

		// Título
		entorno.cambiarFont("Arial", 40, Color.white);
		entorno.escribirTexto("DELIVERY RUSH", entorno.ancho() / 2, 200);

		// Botones
		dibujarBoton("JUGAR", entorno.ancho() / 2, 350);
		dibujarBoton("SALIR", entorno.ancho() / 2, 450);
	}

	private void dibujarBoton(String texto, double x, double y) {
		entorno.cambiarFont("Arial", 24, Color.white);
		entorno.escribirTexto(texto, x, y);
	}

	public boolean clickEnJugar(int x, int y) {
		return y > 320 && y < 380;
	}

	public boolean clickEnSalir(int x, int y) {
		return y > 420 && y < 480;
	}
}
