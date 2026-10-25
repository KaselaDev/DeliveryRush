package juego.util;

/**
 * Constantes del juego DeliveryRush
 */
public class Constantes {
	// Dimensiones de la ventana
	public static int VENTANA_ANCHO = 1150;
	public static int VENTANA_ALTO = 700;
	public static int LIMITE_INTERFAZ = 80;

	// Estados del juego
	public static int PEDIDOS_PARA_GANAR = 10;

	// Repartidor
	public static double REPARTIDOR_VELOCIDAD = 2.0;
	public static double REPARTIDOR_RADIO = 20.0;
	public static int REPARTIDOR_VIDAS_INICIALES = 3;
	// Milisegundos durante los que la moto no puede volver a perder vida
	public static long REPARTIDOR_INVULNERABLE_MILLIS = 1200;

	// Mapa
	public static int MAPA_FILAS = 4;
	public static int MAPA_COLUMNAS = 4;
	public static int MANZANA_ANCHO = 175;
	public static int MANZANA_ALTO = 100;
	public static int SEPARACION_ANCHO = 260;
	public static int SEPARACION_ALTO = 190;
	public static double MAPA_OFFSET_X = 185.0;
	public static double MAPA_OFFSET_Y = 220.0;

	// Casas
	public static int CASA_ANCHO = 50;
	public static int CASA_ALTO = 50;
	public static double CASA_OFFSET_X = 45.0;
	public static double CASA_OFFSET_Y = 25.0;

	// Enemigos
	public static int ENEMIGO_CANTIDAD_MINIMA = 4;
	public static double ENEMIGO_VELOCIDAD = 1.5;
	public static double ENEMIGO_PROBABILIDAD_BOMBA = 0.3;
	public static int ENEMIGO_TICKS_BOMBA = 180;
	public static double ENEMIGO_RADIO = 18.0;
	public static int ENEMIGO_TICKS_REAPARICION = 120;

	// Proyectiles
	public static double PROYECTIL_VELOCIDAD = 6.0;
	public static double PROYECTIL_RADIO = 5.0;

	// Bombas
	public static int BOMBA_TICKS_MECHA = 120;
	public static int BOMBA_TICKS_EXPLOSION = 200;
	public static int BOMBA_TICKS_ADVERTENCIA = 30;
	public static double BOMBA_RADIO = 15.0;
	public static double BOMBA_EXPLOSION_CELDAS = 2.0;
	public static int BOMBA_MAXIMA = 40;

	// Colisiones
	public static double MANZANA_ARRIBA_OFFSET = 15.0;
	public static double MANZANA_ABAJO_OFFSET = 10.0;

	// Tiempos
	public static long TIEMPO_ESPERA_PARPADEO = 2000;
	public static long TIEMPO_CIERRE_OJOS = 500;
}
