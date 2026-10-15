package juego.entidades;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;
import juego.util.Constantes;

public class Proyectil {
	public double x;
	public double y;
	private double velocidad;
	private double angulo;
	private double radio;
	private boolean activo;
	private Image imagen;

	public Proyectil(double x, double y, double angulo) {
		this.x = x;
		this.y = y;
		this.velocidad = Constantes.PROYECTIL_VELOCIDAD;
		this.angulo = angulo;
		this.radio = Constantes.PROYECTIL_RADIO;
		this.activo = true;
		// this.imagen = Herramientas.cargarImagen("imagenes/proyectil.png");
		this.imagen = null; // Temporalmente null hasta que exista la imagen
	}

	public void dibujar(Entorno entorno) {
		if (this.activo && this.imagen != null) {
			entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.1);
		}
	}

	public void mover() {
		this.x += Math.cos(this.angulo) * this.velocidad;
		this.y += Math.sin(this.angulo) * this.velocidad;
	}

	public boolean verificarColision(double objX, double objY, double objRadio) {
		double distancia = Math.sqrt(Math.pow(this.x - objX, 2) + Math.pow(this.y - objY, 2));
		return distancia < (this.radio + objRadio);
	}

	public void desactivar() {
		this.activo = false;
	}

	public boolean isActivo() {
		return activo;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
