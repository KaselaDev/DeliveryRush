package juego.gui.cabecera;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import juego.util.Constantes;

public class Cabecera {
    private static final String FUENTE_PIXEL = cargarFuentePixel();

    private Entorno entorno;
    private int vidaMoto;
    private int pedidosRealizados;
    private int pedidosPerdidos;
    private String estadoPedido;
    private int tiempoRestantePedido;
    private long tiempoJuegoMillis;
    private boolean pausado;
    private int altoInterfaz;
    private Color colorFondo = new Color(1, 35, 31);
    private Color colorCard = new Color(255, 200, 0);
    private int fontSizeL = 16;
    private int fontSizeS = 12;

    public Cabecera(Entorno entorno, int vidaMoto, int pedidosRealizados, int pedidosPerdidos,
            String estadoPedido, int tiempoRestantePedido, long tiempoJuegoMillis, boolean pausado) {
        this.entorno = entorno;
        this.vidaMoto = vidaMoto;
        this.pedidosRealizados = pedidosRealizados;
        this.pedidosPerdidos = pedidosPerdidos;
        this.estadoPedido = estadoPedido;
        this.tiempoRestantePedido = tiempoRestantePedido;
        this.tiempoJuegoMillis = tiempoJuegoMillis;
        this.pausado = pausado;
        this.altoInterfaz = Constantes.LIMITE_INTERFAZ;
    }

    public void dibujarInterfaz() {
		this.entorno.dibujarRectangulo(this.entorno.ancho() / 2, 40, this.entorno.ancho(), this.altoInterfaz, 0, this.colorFondo);
        this.cardTiempo();
		this.cardVidas();
        this.cardPedidosRealizados();
        this.cardPedidosPerdidos();
        this.cardEstadoPedidos();
        this.cardPausa();
	}

    private void decorarCard(int xR, int yR, int anchoR, int altoR) {
        // sombras
        int tamanoSombra = 5;
        this.entorno.dibujarRectangulo(xR, yR + (altoR / 2) - (tamanoSombra / 2), anchoR, tamanoSombra, 0, new Color(0, 0, 0, 100));
        this.entorno.dibujarRectangulo(xR + (anchoR / 2) - (tamanoSombra / 2), yR, tamanoSombra, altoR, 0, new Color(0, 0, 0, 100));

        // esquinas
        int tamanoPunta = 5;
        int x = anchoR / 2 - tamanoPunta / 2;
        int y = altoR / 2 - tamanoPunta / 2;
        this.entorno.dibujarRectangulo(xR - x, yR - y, tamanoPunta, tamanoPunta, 0, this.colorFondo);
        this.entorno.dibujarRectangulo(xR + x, yR - y, tamanoPunta, tamanoPunta, 0, this.colorFondo);
        this.entorno.dibujarRectangulo(xR - x, yR + y, tamanoPunta, tamanoPunta, 0, this.colorFondo);
        this.entorno.dibujarRectangulo(xR + x, yR + y, tamanoPunta, tamanoPunta, 0, this.colorFondo);
    }

    private void cardTiempo() {
        int segundos = (int) (this.tiempoJuegoMillis / 1000);
        int x = 90;
        int y = this.altoInterfaz / 2;
        int ancho = 160;
        int alto = this.altoInterfaz - 24;
        int padding = 8;
        this.entorno.dibujarRectangulo(x, y, ancho, alto, 0, this.colorCard);
        this.decorarCard(x, y, ancho, alto);

        Image icon = Herramientas.cargarImagen("imagenes/reloj.png");    
        this.entorno.dibujarImagen(icon, x - (ancho / 2) + (icon.getWidth(null)) + padding, y, 0, 2.25);

        int xTexto = x - (ancho / 2) + (icon.getWidth(null)) + (padding * 2) + 20;
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeL, Color.WHITE);
        this.entorno.escribirTexto("Tiempo", xTexto, y - 4);
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeS, Color.WHITE);
        this.entorno.escribirTexto(String.format("%02d:%02d", segundos / 60, segundos % 60), xTexto + 20, y + 18);
    }

    private void cardVidas() {
        int x = 250;
        int y = this.altoInterfaz / 2;
        int ancho = 145;
        int alto = this.altoInterfaz - 24;
        int padding = 16;
        this.entorno.dibujarRectangulo(x, y, ancho, alto, 0, this.colorCard);
        this.decorarCard(x, y, ancho, alto);
        
        Image corazonLleno = Herramientas.cargarImagen("imagenes/corazonLleno.png");  
        Image corazonVacio = Herramientas.cargarImagen("imagenes/corazonVacio.png"); 
        int cantidadVidas = this.vidaMoto; 
        
        int xInicial = x - (ancho / 2) + (corazonLleno.getWidth(null)) + padding;
        int espacioEntreCorazones = (corazonLleno.getWidth(null) * 2) + 24;

        for (int i = 0; i < 3; i++) {
            if (i < cantidadVidas) {
                this.entorno.dibujarImagen(corazonLleno, xInicial + (espacioEntreCorazones * i), y - 4, 0, 4);
            } else {
                this.entorno.dibujarImagen(corazonVacio, xInicial + (espacioEntreCorazones * i), y - 4, 0, 4);
            }
        }
    }

    private void cardPedidosRealizados() {
        int x = 420;
        int y = 40;
        int ancho = 180;
        int alto = this.altoInterfaz - 24;
        int padding = 16;
        this.entorno.dibujarRectangulo(x, y, ancho, alto, 0, this.colorCard);
        this.decorarCard(x, y, ancho, alto);

        Image icon = Herramientas.cargarImagen("imagenes/pedido.png");    
        this.entorno.dibujarImagen(icon, x - (ancho / 2) + 4 + padding, y, 0, .05);
        int xTexto = x - (ancho / 2) + (padding * 2) + 10;
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeL, Color.WHITE);
        this.entorno.escribirTexto("Pedidos", xTexto, y - 4);
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeS, Color.WHITE);
        this.entorno.escribirTexto(this.pedidosRealizados+" / " + Constantes.PEDIDOS_PARA_GANAR, xTexto + 20, y + 18);
    }

    private void cardPedidosPerdidos() {
        int x = 617;
        int y = 40;
        int ancho = 200;
        int alto = this.altoInterfaz - 24;
        int padding = 16;
        this.entorno.dibujarRectangulo(x, y, ancho, alto, 0, this.colorCard);
        this.decorarCard(x, y, ancho, alto);

        Image icon = Herramientas.cargarImagen("imagenes/pedidoPerdido.png");    
        this.entorno.dibujarImagen(icon, x - (ancho / 2) + 4 + padding, y, 0, .06);
        int xTexto = x - (ancho / 2) + (padding * 2) + 10;
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeL, Color.WHITE);
        this.entorno.escribirTexto("Perdidos", xTexto, y - 4);
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeS, Color.WHITE);
        this.entorno.escribirTexto(this.pedidosPerdidos+" / 10", xTexto + 20, y + 18);
    }

    private void cardEstadoPedidos() {
        int x = 890;
        int y = 40;
        int ancho = 330;
        int alto = this.altoInterfaz - 24;
        int padding = 16;
        this.entorno.dibujarRectangulo(x, y, ancho, alto, 0, this.colorCard);
        this.decorarCard(x, y, ancho, alto);

        Image icon = Herramientas.cargarImagen("imagenes/notaPedido.png");    
        this.entorno.dibujarImagen(icon, x - (ancho / 2) + 4 + padding, y, 0, 2.25);
        int xTexto = x - (ancho / 2) + (padding * 2) + 10;
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeL, Color.WHITE);
        this.entorno.escribirTexto("Estado del Pedido", xTexto, y - 4);
        this.entorno.cambiarFont(FUENTE_PIXEL, this.fontSizeS, Color.WHITE);
        this.entorno.escribirTexto(this.estadoPedido+" ("+this.tiempoRestantePedido+"s)", xTexto, y + 18);
    }

    private void cardPausa() {
        int x = 1102;
        int y = this.altoInterfaz / 2;
        int ancho = 78;
        int alto = this.altoInterfaz - 24;
        this.entorno.dibujarRectangulo(x, y, ancho, alto, 0, new Color(255, 0, 0));
        this.decorarCard(x, y, ancho, alto);

        Image icon = Herramientas.cargarImagen("imagenes/pausa.png");
        this.entorno.dibujarImagen(icon, x, y - 2, 0, .1);
        if (this.pausado) {
            this.entorno.dibujarRectangulo(x, y, ancho, alto, 0, new Color(0, 0, 0, 40));
        }
    }

    private static String cargarFuentePixel() {
        String recurso = "/fonts/PressStart2P-Regular.ttf";
        try (InputStream archivo = Cabecera.class.getResourceAsStream(recurso)) {
            if (archivo == null) {
                throw new IllegalStateException("No se encontró la fuente pixel: " + recurso);
            }

            Font fuente = Font.createFont(Font.TRUETYPE_FONT, archivo);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuente);
            return fuente.getFamily();
        } catch (IOException | FontFormatException e) {
            throw new IllegalStateException("No se pudo cargar la fuente pixel.", e);
        }
    }


}
