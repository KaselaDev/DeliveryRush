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
	Casa[] casas;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1150, 900);
		
		// Inicializar lo que haga falta para el juego
		// ...
		//Moto
		this.moto = new Moto(580,800);

		//Casa
		this.casas = new Casa[64];
		int c = 0;
		//Manzana
		this.cuadra = new Manzana[16];

		int k = 0;
		int ancho = 175;
		int alto = 100;
		int separacionAncho = 260;
		int separacionAlto = 190;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double posX = 185 + (j * separacionAncho);
				double posY = 220 + (i * separacionAlto);
				this.cuadra[k++] = new Manzana(posX, posY, ancho, alto);

				double offsetX = 45; 
        		double offsetY = 25;

				// Casa arriba izquierda
				this.casas[c++] = new Casa(posX - offsetX, posY - offsetY, 0, 0);
				// Casa arriba derecha
				this.casas[c++] = new Casa(posX + offsetX, posY - offsetY, 0 ,0);
				// Casa abajo izquierda
				this.casas[c++] = new Casa(posX - offsetX, posY + offsetY, 0, 0);
				// Casa abajo derecha
				this.casas[c++] = new Casa(posX + offsetX, posY + offsetY, 0, 0);
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
		// interfaz
		entorno.dibujarRectangulo(500, 50, 1300, 100, 0, Color.black);
		//Mapa
		entorno.colorFondo(Color.gray);
		
		for(Manzana m:this.cuadra) {
				m.dibujar(entorno);
			}
		
		for(Casa c: this.casas){
			c.dibujar(entorno);
		}
		
		
		//Movimiento de la moto
		
		double xAnterior = this.moto.getX();
		double yAnterior = this.moto.getY();
		double anguloAnterior = this.moto.getAngulo();
		
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
		


		//Colision con limite de pantalla

		double mitadAncho = this.moto.getAncho() / 2;
		double mitadAlto = this.moto.getAlto() / 2;
		double limiteInterfaz = 100;

		//Izquierdo
		if (this.moto.x < mitadAncho) {
    		this.moto.x = mitadAncho;
		}
		//Derecho
		if (this.moto.x > entorno.ancho() - mitadAncho) {
    		this.moto.x = entorno.ancho() - mitadAncho;
		}
		//Arriba
		if (this.moto.y < limiteInterfaz + mitadAlto) {
    		this.moto.y = limiteInterfaz + mitadAlto;
		}
		//Abajo
		if (this.moto.y > entorno.alto() - mitadAlto) {
    		this.moto.y = entorno.alto() - mitadAlto;
		}
	



		for(int i = 0; i < this.cuadra.length; i++) {
			if(this.cuadra[i] != null) {
				if(choca(this.moto, this.cuadra[i])) {
					this.moto.setX(xAnterior);
					this.moto.setY(yAnterior);
					this.moto.setAngulo(anguloAnterior);
				}
			}
		}
		
		


		moto.dibujar(entorno);

		

		
		
		
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
	    double bordeArrbCuadra = a.getY() - (a.getAlto() / 2) - 15;
	    double bordeAbjCuadra = a.getY() + (a.getAlto() / 2);
	    
	    return bordeDerMoto > bordeIzqCuadra && bordeIzqMoto < bordeDerCuadra && bordeInfMoto > bordeArrbCuadra && bordeSupMoto < bordeAbjCuadra;
	    
	}
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
