package juego.entidades;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import juego.util.Constantes;

public class Bomba {
	public double x;
	public double y;
	private double radio;
	private int danio;
	private boolean activa;
	private Image imagen;

	public Bomba(double x, double y) {
		this.x = x;
		this.y = y;
		this.radio = Constantes.BOMBA_RADIO;
		this.danio = Constantes.BOMBA_DANIO;
		this.activa = true;
		// this.imagen = Herramientas.cargarImagen("imagenes/bomba.png");
		this.imagen = null; // Temporalmente null hasta que exista la imagen
	}

	public void dibujar(Entorno entorno) {
		if (this.activa && this.imagen != null) {
			entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.15);
		}
	}

	public boolean verificarColision(double objX, double objY, double objRadio) {
		double distancia = Math.sqrt(Math.pow(this.x - objX, 2) + Math.pow(this.y - objY, 2));
		return distancia < (this.radio + objRadio);
	}

	public void explotar() {
		this.activa = false;
	}

	public boolean isActiva() {
		return activa;
	}

	public int getDanio() {
		return danio;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
