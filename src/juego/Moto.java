package juego;


import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;

public class Moto {
	private double x;
	private double y;
	private double velocidad;
	private double angulo;
	private double radioColision;
	public Image motoArriba;
	public Image motoAbajo;
	public Image motoDerecha;
	public Image motoIzquierda;



	public Moto(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 1.5;
		this.angulo = 0;
		this.radioColision = 16;
		this.motoArriba = Herramientas.cargarImagen("imagenes/arriba.png");
		this.motoAbajo = Herramientas.cargarImagen("imagenes/abajo.png");
		this.motoDerecha = Herramientas.cargarImagen("imagenes/derecha.png");
		this.motoIzquierda = Herramientas.cargarImagen("imagenes/izquierda.png");
			
		};
	

	public void dibujar(Entorno entorno) {
		if(this.angulo == 0){
			entorno.dibujarImagen(this.motoArriba, this.x, this.y, 0,0.115);
		}
		else if (this.angulo == Math.PI) {
        entorno.dibujarImagen(this.motoAbajo, this.x, this.y, 0, 0.115);
		}
		else if (this.angulo == Math.PI / 2) {
        
        entorno.dibujarImagen(this.motoDerecha, this.x, this.y, 0, 0.115);
    	} 
    	else if (this.angulo == -Math.PI / 2) {
        
        entorno.dibujarImagen(this.motoIzquierda, this.x, this.y, 0, 0.115);
    	}
	}
	
	//Movimientos de la moto + la dirrecion (?
	public void moverDerecha() {
		this.x = this.x + velocidad;
		this.angulo = Math.PI / 2;
	}
	public void moverIzquierda() {
		this.x = this.x - velocidad;
		this.angulo = -Math.PI / 2;
	}
	public void moverArriba() {
		this.y = this.y - velocidad;
		this.angulo = 0;
	}
	public void moverAbajo()
	{
		this.y = this.y + velocidad;
		this.angulo = Math.PI;
	}

	// la moto choca con una cuadra
    public boolean chocaCon(Manzana a) {
        double bordeDerMoto = this.x + this.radioColision;
        double bordeIzqMoto = this.x - this.radioColision;
        double bordeSupMoto = this.y - this.radioColision;
        double bordeInfMoto = this.y + this.radioColision;

        double bordeDerCuadra = a.getX() + (a.getAncho() / 2);
        double bordeIzqCuadra = a.getX() - (a.getAncho() / 2);
        double bordeArrbCuadra = a.getY() - (a.getAlto() / 2) - 15;
        double bordeAbjCuadra = a.getY() + (a.getAlto() / 2);

        return bordeDerMoto > bordeIzqCuadra && bordeIzqMoto < bordeDerCuadra && 
               bordeInfMoto > bordeArrbCuadra && bordeSupMoto < bordeAbjCuadra;
    }
	
	public boolean colisionaConMapa(Manzana[] cuadras) {
        for (int i = 0; i < cuadras.length; i++) {
            if (cuadras[i] != null && this.chocaCon(cuadras[i])) {
                return true;
            }
        }
        return false;
    }

    // calcula si está cerca de una casa o negocio
    public boolean estaCercaDe(Casa c) {
        double dist = Math.hypot(this.x - c.getX(), this.y - c.getY());
        return dist < 75;
    }

    // calcula el borde de la pantalla
    public void mantenerDentroDePantalla(int anchoPantalla, int altoPantalla, int altoInterfaz) {
        double r = this.radioColision + 10;

        if (this.x < r) {
            this.x = r;
        }
        if (this.x > anchoPantalla - r) {
            this.x = anchoPantalla - r;
        }
        if (this.y < altoInterfaz + r) {
            this.y = altoInterfaz + r;
        }
        if (this.y > altoPantalla - r) {
            this.y = altoPantalla - r;
        }
    }
	
	


	public double getX() { 
		return this.x; 
	}
    public double getY() { 
		return this.y; 
	}
    public void setX(double x) { 
		this.x = x; 
	}
    public void setY(double y) { 
		this.y = y; 
	}
    public double getAngulo() { 
		return this.angulo; 
	}
    public void setAngulo(double angulo) { 
		this.angulo = angulo; 
	}
    public double getRadioColision() { 
		return this.radioColision; 
	}
}
	




