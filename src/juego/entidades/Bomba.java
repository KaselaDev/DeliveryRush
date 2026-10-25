package juego.entidades;

import entorno.Entorno;

import java.awt.Color;
import java.awt.Image;
import entorno.Herramientas;
import juego.util.Constantes;

public class Bomba {
	private static Image IMAGEN_BOMBA  = Herramientas.cargarImagen("imagenes/tnt.png");
	private static Image IMAGEN_EXPLOSION = Herramientas.cargarImagen("imagenes/explosion.png");
	
	public double x;
	public double y;
	private boolean calleVertical;
	private double anchoExplosion;
	private double altoExplosion;
	private int ticksMecha;
	private int ticksExplosion; 
	private boolean hizoDanio;

	public Bomba(double x, double y, double anchoExplosion, double altoExplosion, boolean calleVertical) {
		this.x = x;
		this.y = y;
		this.calleVertical = calleVertical;
		this.anchoExplosion = anchoExplosion;
		this.altoExplosion = altoExplosion;
		this.ticksMecha = Constantes.BOMBA_TICKS_MECHA;
		this.ticksExplosion = Constantes.BOMBA_TICKS_EXPLOSION;
	}
	
	// Avanza un tick: primero se consume la mecha y después dura la explosión
		public void actualizar() {
			if (this.ticksMecha > 0) {
				this.ticksMecha--;
			} else {
				this.ticksExplosion--;
			}
		}
	 
		public boolean estaExplotando() {
			return this.ticksMecha <= 0;
		}
	 
		// Cuando termin la explosion, el que la tiene la pone en null
		public boolean terminada() {
			return this.ticksExplosion <= 0;
		}
	 
		// true si el círculo (ox, oy, radio) toca la bomba: antes de explotar es la bomba chica,
		// al explotar es todo el rectángulo de la explosión
		public boolean alcanzaA(double ox, double oy, double radio) {
			return Math.abs(ox - this.x) < this.getAncho() / 2 + radio
					&& Math.abs(oy - this.y) < this.getAlto() / 2 + radio;
		}
		
		// Daño que le hace esta bomba al círculo (ox, oy, radio). Lastima una sola vez:
		// si no, mientras la moto siga dentro de la explosión perdería una vida en cada tick.
		// Devuelve 1 (una vida) si le hace daño, 0 si no.
		public int danioA(double ox, double oy, double radio) {
			if (this.hizoDanio || !this.alcanzaA(ox, oy, radio)) {
				return 0;
			}
			this.hizoDanio = true;
			return 1;
		}
		

		public void dibujar(Entorno entorno) {
			double angulo = this.calleVertical ? Math.PI / 2 : 0;
	        if (this.estaExplotando()) {
	            if (IMAGEN_EXPLOSION != null) {
	            	entorno.dibujarImagen(
	            		    IMAGEN_EXPLOSION,
	            		    this.x,
	            		    this.y,
	            		    angulo
	            		);
	            } else {
	                entorno.dibujarRectangulo(
	                    this.x,
	                    this.y,
	                    this.anchoExplosion,
	                    this.altoExplosion,
	                    0,
	                    new Color(255, 120, 0, 170)
	                );
	                }
	        }
	         else {
	        	 boolean advertencia =
	        			    this.ticksMecha <= Constantes.BOMBA_TICKS_ADVERTENCIA
	        			    && (this.ticksMecha / 5) % 2 == 0;

	        			if (advertencia) {
	        			    dibujarAnillo(entorno);
	        			}
	         }
	         
	            if (IMAGEN_BOMBA != null) {
	                entorno.dibujarImagen(
	                    IMAGEN_BOMBA,
	                    this.x,
	                    this.y,
	                    0,
	                    0.6
	                );
	            } else {
	                entorno.dibujarCirculo(
	                    this.x,
	                    this.y,
	                    Constantes.BOMBA_RADIO * 2,
	                    Color.BLACK
	                );
	            }
	        }

private void dibujarAnillo(Entorno entorno) {
    double radio = Constantes.BOMBA_RADIO * 2.5;
    double largo = radio * 0.35;
    Color rojo = new Color(255, 40, 40);

    for (int i = 0; i < 8; i++) {
        double angulo = i * Math.PI / 4;

        double cx = this.x + Math.cos(angulo) * radio;
        double cy = this.y + Math.sin(angulo) * radio;

        entorno.dibujarRectangulo(
            cx,
            cy,
            largo,
            3,
            angulo,
            rojo
        );
    }
}

 
	private double getAncho() {
		return this.estaExplotando() ? this.anchoExplosion : Constantes.BOMBA_RADIO * 2;
	}
 
	private double getAlto() {
		return this.estaExplotando() ? this.altoExplosion : Constantes.BOMBA_RADIO * 2;
	}
 
	public double getX() {
		return this.x;
	}
 
	public double getY() {
		return this.y;
	}
}
