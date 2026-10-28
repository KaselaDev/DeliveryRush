package juego.mapa;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;
import juego.entidades.Moto;

public class Pedido {
    private static int TICKS_LIMITE_PEDIDO = 2000;
    private static int TICKS_ESPERA_SIGUIENTE = 150;
    private static int TICKS_POR_SEGUNDO = 100;

    private Casa negocioOrigen;
    private Casa casaDestino;
    private String estado;
    private int ticksRestantes;
    private int ticksEsperaNuevo;

    public Pedido(Casa negocioOrigen, Casa casaDestino) {
        if (negocioOrigen == null || !negocioOrigen.isEsNegocio()) {
            throw new IllegalArgumentException("El origen del pedido debe ser un negocio.");
        }
        if (casaDestino == null || casaDestino.isEsNegocio()) {
            throw new IllegalArgumentException("El destino del pedido debe ser una casa.");
        }

        this.negocioOrigen = negocioOrigen;
        this.casaDestino = casaDestino;
        this.inicializarEstado();
    }

    public Pedido(Casa[] casas) {
        this(seleccionarCasa(casas, true), seleccionarCasa(casas, false));
    }

    private void inicializarEstado() {
        this.estado = "En espera";
        this.ticksRestantes = TICKS_LIMITE_PEDIDO;
        this.ticksEsperaNuevo = TICKS_ESPERA_SIGUIENTE;
    }

    private static Casa seleccionarCasa(Casa[] casas, boolean esNegocio) {
        if (casas == null) {
            throw new IllegalArgumentException("La lista de casas no puede ser nula.");
        }

        int cantidad = 0;
        for (Casa casa : casas) {
            if (casa != null && casa.isEsNegocio() == esNegocio) {
                cantidad++;
            }
        }
        if (cantidad == 0) {
            throw new IllegalArgumentException(
                    esNegocio ? "Debe haber al menos un negocio." : "Debe haber al menos una casa de destino.");
        }

        int indiceElegido = new Random().nextInt(cantidad);
        for (Casa casa : casas) {
            if (casa != null && casa.isEsNegocio() == esNegocio && indiceElegido-- == 0) {
                return casa;
            }
        }

        throw new IllegalStateException("No se pudo seleccionar una casa válida.");
    }

    public void actualizarTiempo() {
        if (this.estaActivo()) {
            this.ticksRestantes--;
            if (this.ticksRestantes <= 0) {
                this.estado = "Perdido";
            }
        } else if (this.ticksEsperaNuevo > 0) {
            this.ticksEsperaNuevo--;
        }
    }

    public void dibujarIndicador(Entorno entorno) {
        if ("En espera".equals(this.estado)) {
            this.dibujarFlecha(entorno, this.negocioOrigen, "imagenes/flechaAmarilla.png");
        } else if ("En camino".equals(this.estado)) {
            this.dibujarFlecha(entorno, this.casaDestino, "imagenes/flechaVerde.png");
        }
    }

    private void dibujarFlecha(Entorno entorno, Casa casa, String imagenPath) {
        double x = casa.getZonaInteraccionX();
        double y = casa.getZonaInteraccionY();
        Image flecha = Herramientas.cargarImagen(imagenPath);
        
        double angulo = 0;
        switch(casa.getDirecciones()) {
            case "frente":
                angulo = Math.PI; // Apunta hacia abajo
                break;
            case "atras":
                angulo = 0; // Apunta hacia arriba
                break;
            case "izquierda":
                angulo = -Math.PI / 2; // Apunta hacia la derecha
                break;
            case "derecha":
                angulo = Math.PI / 2; // Apunta hacia la izquierda
                break;
            default:
                angulo = 0;
                break;
        }
        
        if (flecha != null) {
            entorno.dibujarImagen(flecha, x, y, angulo, 2.25);
        }
    }

    public boolean interactuar(Moto m) {
        if (m == null) {
            throw new IllegalArgumentException("La moto no puede ser nula.");
        }

        if ("En espera".equals(this.estado) && this.negocioOrigen.zonaDeInteraccion(m.getX(), m.getY())) {
            this.retirar();
        } else if ("En camino".equals(this.estado) && this.casaDestino.zonaDeInteraccion(m.getX(), m.getY())) {
            this.entregar();
            return true;
        }
        return false;
    }

    private boolean estaActivo() {
        return "En espera".equals(this.estado) || "En camino".equals(this.estado);
    }

    public void retirar() {
        if ("En espera".equals(this.estado)) {
            this.estado = "En camino";
            this.ticksRestantes = TICKS_LIMITE_PEDIDO;
        }
    }

    public void entregar() {
        if ("En camino".equals(this.estado)) {
            this.estado = "Entregado";
        }
    }

    public void perder() {
        if (this.estaActivo()) {
            this.estado = "Perdido";
        }
    }

    public String getEstado() {
        return this.estado;
    }

    public Casa getNegocioOrigen() {
        return this.negocioOrigen;
    }

    public Casa getCasaDestino() {
        return this.casaDestino;
    }

    public int getSegundosRestantes() {
        return Math.max(0, this.ticksRestantes) / TICKS_POR_SEGUNDO;
    }

    public boolean listoParaSiguiente() {
        return this.ticksEsperaNuevo <= 0;
    }
}