package juego.mapa;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Casa {
    double x;
	double y;
	double ancho;
	double alto;
	Image imagen;



	public Casa(double x, double y, double ancho, double alto) {
		this.x = x;
		this.y = y;
		this.ancho = 50;
		this.alto = 50;
		this.imagen = Herramientas.cargarImagen("imagenes/casa1.png");

        
    }

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.33);
	}

}
