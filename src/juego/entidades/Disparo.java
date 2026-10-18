package juego.entidades;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import juego.mapa.Manzana;
import juego.util.Constantes;

public class Disparo {
    private static final double RADIO = 4;
    private static final int DIAMETRO = 8;

    private double x;
    private double y;
    private double velocidad;
    private double angulo;
	private Image imagen;

    public Disparo(double x, double y, double angulo) {
        this.x = x;
        this.y = y;
        this.angulo = angulo;
        this.velocidad = Constantes.PROYECTIL_VELOCIDAD;
		this.imagen = Herramientas.cargarImagen("imagenes/bala.png");
    }

    public void mover() {
        double anguloCorregido = this.getAnguloMovimiento();
        this.x += this.velocidad * Math.cos(anguloCorregido);
        this.y += this.velocidad * Math.sin(anguloCorregido);
    }

    public void dibujar(Entorno entorno) {
        double anguloCorregido = this.angulo - Math.PI / 2;
        entorno.dibujarImagen(this.imagen, this.x, this.y, anguloCorregido, 0.038);
	}

    private double getAnguloMovimiento() {
        return this.angulo - Math.PI / 2;
    }

    public boolean salioDePantalla(Entorno entorno) {
        return this.x + RADIO < 0 || this.x - RADIO > entorno.ancho()
                || this.y + RADIO < 0 || this.y - RADIO > entorno.alto();
    }

    public boolean chocaConCuadra(Manzana[] cuadras) {
        if (cuadras == null) {
            throw new IllegalArgumentException("La lista de cuadras no puede ser nula.");
        }

        for (Manzana cuadra : cuadras) {
            if (cuadra == null) {
                continue;
            }

            double der = cuadra.getX() + cuadra.getAncho() / 2;
            double izq = cuadra.getX() - cuadra.getAncho() / 2;
            double arr = cuadra.getY() - cuadra.getAlto() / 2 - Constantes.MANZANA_ARRIBA_OFFSET;
            double abj = cuadra.getY() + cuadra.getAlto() / 2;
            double puntoX = Math.max(izq, Math.min(this.x, der));
            double puntoY = Math.max(arr, Math.min(this.y, abj));
            double distanciaX = this.x - puntoX;
            double distanciaY = this.y - puntoY;

            if (distanciaX * distanciaX + distanciaY * distanciaY <= RADIO * RADIO) {
                return true;
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