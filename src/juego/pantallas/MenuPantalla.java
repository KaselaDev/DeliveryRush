package juego.pantallas;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;
import juego.util.ButtonsMaster;
import juego.logica.GestorPantallas;

public class MenuPantalla {
	private Entorno entorno;
	private GestorPantallas gestorPantallas;
	private ButtonsMaster botonJugar;
	private ButtonsMaster botonSalir;

	public MenuPantalla(Entorno entorno, GestorPantallas gp) {
		this.entorno = entorno;
		this.gestorPantallas = gp;
	}

	public void dibujar() {
		// Fondo con imagen
		Image fondo = Herramientas.cargarImagen("imagenes/backgroundMenu.png");
		if (fondo != null) {
			this.entorno.dibujarImagen(fondo, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0, 1.0);
		} else {
			this.entorno.colorFondo(Color.white);
		}

		// Título
		Image logo = Herramientas.cargarImagen("imagenes/logo.png");
		this.entorno.dibujarImagen(logo, 180, 120, 0, 0.5);

		// Botones
		double escalaButton = 3;
		int xOffsetButton = this.entorno.getWidth() - 96;
		int yOffsetButton = this.entorno.getHeight() - 80;

		Image jugarButton = Herramientas.cargarImagen("imagenes/jugarButton.png");
		double widthJuegoBoton = jugarButton.getWidth(null) * escalaButton;
		double heightJuegoBoton = jugarButton.getHeight(null) * escalaButton;
		this.botonJugar = new ButtonsMaster(this.entorno, xOffsetButton, yOffsetButton - 72, widthJuegoBoton, heightJuegoBoton, jugarButton);

		Image salirButton = Herramientas.cargarImagen("imagenes/salirButton.png");
		double widthSalirBoton = salirButton.getWidth(null) * escalaButton;
		double heightSalirBoton = salirButton.getHeight(null) * escalaButton;
		this.botonSalir = new ButtonsMaster(this.entorno, xOffsetButton, yOffsetButton, widthSalirBoton, heightSalirBoton, salirButton);
	}

	public void actualizar() {
		 if (this.entorno.sePresionoBoton(this.entorno.BOTON_IZQUIERDO)) {
		 	int clickX = this.entorno.mouseX();
		 	int clickY = this.entorno.mouseY();
		 	if (this.botonJugar != null && this.botonJugar.verificarClick(clickX, clickY)) {
		 		this.accionJuego();
		 	}
		 	if (this.botonSalir != null && this.botonSalir.verificarClick(clickX, clickY)) {
		 		System.exit(0);
		 	}
		 }
	}

	public void accionJuego() {
		double x = this.entorno.getWidth() / 2;
		double y = this.entorno.getHeight() / 2;

		this.entorno.dibujarRectangulo(x, y, 300, 300, 0, new Color(255, 200, 0));
		this.gestorPantallas.cambiarPantalla("juego");
		System.err.println("Jugar");
	}
}
