package juego;


import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	// Variables y métodos propios de cada grupo
	private Moto moto;
	private Manzana[] cuadra;
	private Casa[] casas;
	private Disparo disparo;
	// private Enemigo[] enemigos;


	private Pedido pedidoActual;
	private int vidas = 3;
	private int pedidosEntregados = 0;
	private int pedidosPerdidos = 0;
	private int enemigosEliminados = 0;


	private int anchoPantalla = 1150;
    private int altoPantalla = 700;
    private int altoInterfaz = 80;
    private int filas = 3;
    private int columnas = 4;
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", this.anchoPantalla, this.altoPantalla);
		
		// Inicializar lo que haga falta para el juego
	
		//Moto
		this.moto = new Moto(this.anchoPantalla / 2, this.altoPantalla - 35);

		inicializarMapa();
		this.pedidoActual = new Pedido(this.casas);

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
		entorno.colorFondo(Color.gray);
		dibujarMapa();

		// gestion del epdido
       if (this.pedidoActual != null) {
            String estadoAnterior = this.pedidoActual.getEstado();
            this.pedidoActual.actualizarTiempo();
            this.pedidoActual.dibujarIndicador(this.entorno);

            // sumo uno a perdido por el tiempo
            if (!estadoAnterior.equals("Perdido") && this.pedidoActual.getEstado().equals("Perdido")) {
                this.pedidosPerdidos++;
            }

            // aparece siguiente pedido
            if (this.pedidoActual.listoParaSiguiente()) {
                this.pedidoActual = new Pedido(this.casas);
            }
        }

        // agarrar y entregar pedido con click derecho
        if (this.entorno.sePresionoBoton(this.entorno.BOTON_DERECHO) && this.pedidoActual != null) {
            if (this.pedidoActual.interactuar(this.moto)) {
                this.pedidosEntregados++;
            }
        }

        // agarrar o entregar pedido con click derecho
        if (this.entorno.sePresionoBoton(this.entorno.BOTON_DERECHO) && this.pedidoActual != null) {
            if (this.pedidoActual.interactuar(this.moto)) {
                this.pedidosEntregados++;
            }
        }
		
		
		//Movimiento de la moto eje x
		double xAnterior = this.moto.getX();
		double anguloAnterior = this.moto.getAngulo();

		if(entorno.estaPresionada('d')) {
			moto.moverDerecha();
		}
		if(entorno.estaPresionada('a')) {
			moto.moverIzquierda();
		}
		//Colision eje x
		if (this.moto.colisionaConMapa(this.cuadra)) {
   			this.moto.setX(xAnterior);
    		this.moto.setAngulo(anguloAnterior);
		}

		//Movimiento de la moto eje y
		double yAnterior = this.moto.getY();
		anguloAnterior = this.moto.getAngulo();

		if(entorno.estaPresionada('w')) {
			moto.moverArriba();
		}
		if(entorno.estaPresionada('s')) {
			moto.moverAbajo();
		}

		//Colision eje y
		if (this.moto.colisionaConMapa(this.cuadra)) {
			this.moto.setY(yAnterior);
			this.moto.setAngulo(anguloAnterior);
		}
		
		this.moto.mantenerDentroDePantalla(this.anchoPantalla, this.altoPantalla, this.altoInterfaz);

		// Disparo moto
		if (this.entorno.sePresionoBoton(this.entorno.BOTON_IZQUIERDO) && this.disparo == null) {
            this.disparo = new Disparo(this.moto.getX(), this.moto.getY(), this.moto.getAngulo());
        }

        if (this.disparo != null) {
            this.disparo.mover();
            this.disparo.dibujar(this.entorno);

            if (this.disparo.salioDePantalla(this.entorno) || this.disparo.chocaConCuadra(this.cuadra)) {
                this.disparo = null;
            }
        }
		dibujarInterfaz();
		moto.dibujar(entorno);
		

		

		
		
		
	}

	//METODOS AUXILIARES

	private void inicializarMapa() {
        this.cuadra = new Manzana[this.filas * this.columnas];
        this.casas = new Casa[this.filas * this.columnas * 4];

        double anchoManzana = 180;
        double altoManzana = 110;
        double separacionX = 260; // Deja calles verticales de 80px
        double separacionY = 180; // Deja calles horizontales de 70px
        
        double inicioX = 185;
        double inicioY = 210;

        double tamanoCasa = 40;
        double offsetX = 40;
        double offsetY = 25;

        int indexCuadra = 0;
        int indexCasa = 0;
        Random random = new Random();

        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                double posX = inicioX + (j * separacionX);
                double posY = inicioY + (i * separacionY);

                this.cuadra[indexCuadra++] = new Manzana(posX, posY, anchoManzana, altoManzana);

                // Elijo al azar cuál de las 4 esquinas va a ser el negocio
                int esquinaNegocio = random.nextInt(4);

                // 0: Casa arriba-izquierda
                this.casas[indexCasa++] = new Casa(posX - offsetX, posY - offsetY, tamanoCasa, tamanoCasa, Math.PI, esquinaNegocio == 0);
                // 1: Casa arriba-derecha
                this.casas[indexCasa++] = new Casa(posX + offsetX, posY - offsetY, tamanoCasa, tamanoCasa, Math.PI, esquinaNegocio == 1);
                // 2: Casa abajo-izquierda
                this.casas[indexCasa++] = new Casa(posX - offsetX, posY + offsetY, tamanoCasa, tamanoCasa, 0, esquinaNegocio == 2);
                // 3: Casa abajo-derecha
                this.casas[indexCasa++] = new Casa(posX + offsetX, posY + offsetY, tamanoCasa, tamanoCasa, 0, esquinaNegocio == 3);
            }
        }
    }


	private void dibujarMapa() {
        for (int i = 0; i < this.cuadra.length; i++) {
            if (this.cuadra[i] != null) {
                this.cuadra[i].dibujar(this.entorno);
            }
        }
        for (int i = 0; i < this.casas.length; i++) {
            if (this.casas[i] != null) {
                this.casas[i].dibujar(this.entorno);
            }
        }
    }

	


	
	private void dibujarInterfaz() {
        this.entorno.dibujarRectangulo(this.entorno.ancho() / 2, 40, this.entorno.ancho(), this.altoInterfaz, 0, Color.BLACK);
        this.entorno.cambiarFont("Arial", 16, Color.WHITE);
        
        int segundos = this.entorno.tiempo() / 1000;
        String textoEstado = this.pedidoActual.getEstado();
        
        if (textoEstado.equals("En espera") || textoEstado.equals("En camino")) {
            textoEstado += " (" + this.pedidoActual.getSegundosRestantes() + "s)";
        }

        this.entorno.escribirTexto(
            "Tiempo: " + segundos + "s  |  Entregados: " + this.pedidosEntregados + 
            "  |  Perdidos: " + this.pedidosPerdidos + "  |  Pedido: " + textoEstado, 
            30, 45
        );
    }


	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
