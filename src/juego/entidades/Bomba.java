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
}
