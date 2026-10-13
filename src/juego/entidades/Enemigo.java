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
}
