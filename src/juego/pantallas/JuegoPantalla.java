package juego.pantallas;

import entorno.Entorno;
import java.awt.Color;
import juego.logica.Tablero;
import juego.util.Constantes;

public class JuegoPantalla {
	private Entorno entorno;
	private Tablero tablero;

	public JuegoPantalla(Entorno entorno, Tablero tablero) {
		this.entorno = entorno;
		this.tablero = tablero;
	}

	public void dibujar() {
		// Interfaz superior
		entorno.dibujarRectangulo(entorno.ancho() / 2, Constantes.LIMITE_INTERFAZ / 2, entorno.ancho(), Constantes.LIMITE_INTERFAZ, 0, Color.black);

		// Fondo del mapa
		entorno.colorFondo(Color.gray);

		// Dibujar moto
		if (tablero.getMoto() != null) {
			tablero.getMoto().dibujar(entorno);
		}
	}

	public void actualizar() {
		if (tablero.getMoto() != null) {
			tablero.getMoto().verificarLimitesPantalla(entorno);
			tablero.getMoto().procesarMovimiento(entorno, tablero.getManzanas());
		}
		tablero.actualizar();
	}
}
