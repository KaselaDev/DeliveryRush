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

		// Dibujar manzanas
		for (var m : tablero.getManzanas()) {
			if (m != null) {
				m.dibujar(entorno);
			}
		}

		// Dibujar casas
		for (var c : tablero.getCasas()) {
			if (c != null) {
				c.dibujar(entorno);
			}
		}

		// Dibujar moto
		if (tablero.getMoto() != null) {
			tablero.getMoto().dibujar(entorno);
		}

		// Dibujar enemigos
		for (var e : tablero.getEnemigos()) {
			if (e != null) {
				e.dibujar(entorno);
			}
		}

		// Dibujar bombas
		for (var b : tablero.getBombas()) {
			if (b != null && b.isActiva()) {
				b.dibujar(entorno);
			}
		}

		// Dibujar proyectiles
		for (var p : tablero.getProyectiles()) {
			if (p != null && p.isActivo()) {
				p.dibujar(entorno);
			}
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
