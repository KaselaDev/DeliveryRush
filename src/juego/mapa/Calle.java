package juego.mapa;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Calle {
	double x;
	double y;
	double ancho;
	double alto;
	boolean invertir;
  Image imagen;

	public Calle(double x, double y, double ancho, double alto, int indexX, int indexY) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
    this.invertir = false;
    this.imagen = this.setImage(indexX, indexY);
	}

  private Image setImage(int i,int  j) {
    if(i == 0 && j == 0) {
      return Herramientas.cargarImagen("imagenes/Calles/5.png");
    }
    if(i == 0 && j == 8) {
      return Herramientas.cargarImagen("imagenes/Calles/6.png");
    }
    if(i == 8 && j == 8) {
      this.invertir = true;
      return Herramientas.cargarImagen("imagenes/Calles/5.png");
    }
    if(i == 8 && j == 0) {
      this.invertir = true;
      return Herramientas.cargarImagen("imagenes/Calles/6.png");
    }
    if(i == 0 && j%2 == 0) {
			this.invertir = true;
			return Herramientas.cargarImagen("imagenes/Calles/3.png");
		}
		if(i == 8 && j%2 == 0) {
			return Herramientas.cargarImagen("imagenes/Calles/3.png");
		}
		if(i%2 == 0 && j == 0) {
			return Herramientas.cargarImagen("imagenes/Calles/4.png");
		}
		if(i%2 == 0 && j == 8) {
			this.invertir = true;
			return Herramientas.cargarImagen("imagenes/Calles/4.png");
		}
		if(i%2 == 0 && j%2 == 0) {
			return Herramientas.cargarImagen("imagenes/Calles/7.png");
		}
		if(i%2 == 0) {
			return Herramientas.cargarImagen("imagenes/Calles/2.png");
		}
		if(i%2 != 0) {
			return Herramientas.cargarImagen("imagenes/Calles/1.png");
		}
		
		return Herramientas.cargarImagen("imagenes/Calles/1.png");
  } 

	public void dibujar(Entorno entorno) {
    double angulo = this.invertir ? Math.PI : 0;
    entorno.dibujarImagen(this.imagen, this.x, this.y, angulo, 1);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}
}
