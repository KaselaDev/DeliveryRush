package juego.util;

/**
 * Constantes del juego DeliveryRush
 */
public class Constantes {
	// Dimensiones de la ventana
	public static final int VENTANA_ANCHO = 1150;
	public static final int VENTANA_ALTO = 700;
	public static final int LIMITE_INTERFAZ = 80;

	// Estados del juego
	public static final int PANTALLA_MENU = 0;
	public static final int PANTALLA_JUEGO = 1;
	public static final int PANTALLA_GAMEOVER = 2;
	public static final int PANTALLA_VICTORIA = 3;

	// Repartidor
	public static final double REPARTIDOR_VELOCIDAD = 2.0;
	public static final double REPARTIDOR_RADIO = 18.0;
	public static final int REPARTIDOR_VIDAS_INICIALES = 3;

	// Mapa
	public static final int MAPA_FILAS = 4;
	public static final int MAPA_COLUMNAS = 4;
	public static final int MANZANA_ANCHO = 175;
	public static final int MANZANA_ALTO = 100;
	public static final int SEPARACION_ANCHO = 260;
	public static final int SEPARACION_ALTO = 190;
	public static final double MAPA_OFFSET_X = 185.0;
	public static final double MAPA_OFFSET_Y = 220.0;

	// Casas
	public static final int CASA_ANCHO = 50;
	public static final int CASA_ALTO = 50;
	public static final double CASA_OFFSET_X = 45.0;
	public static final double CASA_OFFSET_Y = 25.0;

	// Enemigos
	public static final int ENEMIGO_CANTIDAD_MINIMA = 4;
	public static final double ENEMIGO_VELOCIDAD = 1.5;

	// Proyectiles
	public static final double PROYECTIL_VELOCIDAD = 6.0;
	public static final double PROYECTIL_RADIO = 5.0;

	// Colisiones
	public static final double MANZANA_ARRIBA_OFFSET = 15.0;
	public static final double MANZANA_ABAJO_OFFSET = 10.0;

	// Tiempos
	public static final long TIEMPO_ESPERA_PARPADEO = 2000;
	public static final long TIEMPO_CIERRE_OJOS = 500;
}
