package juego.entidades;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import juego.util.Constantes;

public class Enemigo {
	public double x;
	public double y;
	private double velocidad;
	private final Image imagen;

	public Enemigo(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = Constantes.ENEMIGO_VELOCIDAD;
		// this.imagen = Herramientas.cargarImagen("imagenes/enemigo.png");
		this.imagen = null; // Temporalmente null hasta que exista la imagen
	}

	public void dibujar(Entorno entorno) {
		if (this.imagen != null) {
			entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.1);
		}
	}

	public void moverHacia(double targetX, double targetY) {
		double dx = targetX - this.x;
		double dy = targetY - this.y;
		double distancia = Math.sqrt(dx * dx + dy * dy);

		if (distancia > 0) {
			this.x += (dx / distancia) * this.velocidad;
			this.y += (dy / distancia) * this.velocidad;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
