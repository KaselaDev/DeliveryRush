package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	Moto moto;
	Manzana[] cuadra;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1000, 800);
		
		// Inicializar lo que haga falta para el juego
		// ...
		//Moto
		this.moto = new Moto(400,300);
		
		//Manzana
		this.cuadra = new Manzana[16];
		
		int k = 0;
		int ancho = 175;
		int alto = 100;
		int separacionAncho = 235;
		int separacionAlto = 160;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double posX = 147.5 + (j * separacionAncho);
				double posY = 210 + (i * separacionAlto);
				this.cuadra[k++] = new Manzana(posX, posY, ancho, alto);
			}
		}
		
		
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		
		//Mapa
		entorno.colorFondo(Color.DARK_GRAY);
		for(Manzana m:this.cuadra) {
				m.dibujar(entorno);
			}
		
		
		//Movimiento de la moto
		
		double xAnterior = this.moto.getX();
		double yAnterior = this.moto.getY();
		
		if(entorno.estaPresionada('d')) {
			moto.moverDerecha();
		}
		if(entorno.estaPresionada('a')) {
			moto.moverIzquierda();
		}
		if(entorno.estaPresionada('w')) {
			moto.moverArriba();
		}
		if(entorno.estaPresionada('s')) {
			moto.moverAbajo();
		}
		
		for(int i = 0; i < this.cuadra.length; i++) {
			if(this.cuadra[i] != null) {
				if(choca(this.moto, this.cuadra[i])) {
					this.moto.setX(xAnterior);
					this.moto.setY(yAnterior);
				}
			}
		}
		
		
		
		moto.dibujar(entorno);
		
		
		// interfaz
		entorno.dibujarRectangulo(500, 50, 1000, 100, 0, Color.black);
		
		
	}
	
	//Colision moto con las cuadras
	private boolean choca(Moto m, Manzana a) {
		//Cada borde de la moto
		double bordeDerMoto = m.getX() + (m.getAncho() / 2);
	    double bordeIzqMoto = m.getX() - (m.getAncho() / 2);
	    double bordeSupMoto = m.getY() - (m.getAlto() / 2);
	    double bordeInfMoto = m.getY() + (m.getAlto() / 2);
	    
	    //Bordes de la cuadra
	    double bordeDerCuadra = a.getX() + (a.getAncho() / 2);
	    double bordeIzqCuadra = a.getX() - (a.getAncho() / 2);
	    double bordeArrbCuadra = a.getY() - (a.getAlto() / 2);
	    double bordeAbjCuadra = a.getY() + (a.getAlto() / 2);
	    
	    return bordeDerMoto > bordeIzqCuadra && bordeIzqMoto < bordeDerCuadra && bordeInfMoto > bordeArrbCuadra && bordeSupMoto < bordeAbjCuadra;
	    
	}
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
