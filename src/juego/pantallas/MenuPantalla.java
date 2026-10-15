package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import entorno.Herramientas;
import juego.util.ButtonsMaster;

public class MenuPantalla {
	private Entorno entorno;
	private Image fondo;

	public MenuPantalla(Entorno entorno) {
		this.entorno = entorno;
		this.fondo = Herramientas.cargarImagen("imagenes/backgroundMenu.png");
	}

	public void dibujar() {
		// Fondo con imagen
		if (this.fondo != null) {
			entorno.dibujarImagen(this.fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 1.0);
		} else {
			entorno.colorFondo(Color.white);
		}

		// Título
		entorno.cambiarFont("Arial", 40, Color.black);
		entorno.escribirTexto("DELIVERY RUSH", entorno.ancho() / 2, 200);

		// Botones
		// dibujarBoton("JUGAR", entorno.ancho() / 2, 350);
		// dibujarBoton("SALIR", entorno.ancho() / 2, 450);
		new ButtonsMaster(entorno, 450, 450, 400, 300, "Jugar");
	}
}
