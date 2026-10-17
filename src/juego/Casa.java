package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Casa {
    private double x;
	private double y;
	private double ancho;
	private double alto;
	private double angulo;
	private boolean esNegocio;
	private Image imagen;



	public Casa(double x, double y, double ancho, double alto, double angulo, boolean esNegocio) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.angulo = angulo;
		this.esNegocio = esNegocio;

		if(this.esNegocio){
			this.imagen = Herramientas.cargarImagen("imagenes/tienda.png");
		} else{
			this.imagen = Herramientas.cargarImagen("imagenes/casa1.png");
		}

        
    }

	public void dibujar(Entorno entorno) {
    if (this.imagen != null) {
        if (this.esNegocio) {
            // Si es negocio, usamos una escala más chica (ej: 0.08) para que encaje bien
            entorno.dibujarImagen(this.imagen, this.x, this.y, this.angulo, 0.15);
        } else {
            // Las casas normales usan su escala habitual
            entorno.dibujarImagen(this.imagen, this.x, this.y, this.angulo, 0.25);
        }
    }
}

	public double getX() { 
		return this.x; 
	}
    public double getY() {
		 return this.y; 
	}
    public double getAncho() { 
		return this.ancho; 
	}
    public double getAlto() { 
		return this.alto; 
	}
    public boolean getEsNegocio() { 
		return this.esNegocio; 
	}
	
}
