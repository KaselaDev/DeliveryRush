package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Disparo {
    private double x;
    private double y;
    private double velocidad;
    private double angulo;
    private Image imagen;

    public Disparo(double x, double y, double angulo) {
        this.x = x;
        this.y = y;
        this.angulo = angulo;
        this.velocidad = 3;
        this.imagen = Herramientas.cargarImagen("imagenes/bala.png");
    }

    public void mover() {
        double anguloCorregido = this.angulo - Math.PI / 2;
        this.x += this.velocidad * Math.cos(anguloCorregido);
        this.y += this.velocidad * Math.sin(anguloCorregido);
    }

    public void dibujar(Entorno entorno) {
        double anguloCorregido = this.angulo - Math.PI / 2;
        entorno.dibujarImagen(this.imagen, this.x, this.y, anguloCorregido, 0.038);
    }

    public boolean salioDePantalla(Entorno entorno) {
        return this.x < 0 || this.x > entorno.ancho() ||   this.y < 0 || this.y > entorno.alto();
    }

    public boolean chocaConCuadra(Manzana[] cuadras) {
        for (int i = 0; i < cuadras.length; i++) {
            if (cuadras[i] != null) {
                double der = cuadras[i].getX() + (cuadras[i].getAncho() / 2);
                double izq = cuadras[i].getX() - (cuadras[i].getAncho() / 2);
                double arr = cuadras[i].getY() - (cuadras[i].getAlto() / 2) - 15;
                double abj = cuadras[i].getY() + (cuadras[i].getAlto() / 2);

                if (this.x > izq && this.x < der && this.y > arr && this.y < abj) {
                    return true;
                }
            }
        }
        return false;
    }

    
    public double getX() { 
        return this.x; 
    }
    public double getY() { 
        return this.y; 
    }
}


