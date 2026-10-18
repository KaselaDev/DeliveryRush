package juego.mapa;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import juego.entidades.Moto;

public class Pedido {
    private static final int TICKS_LIMITE_PEDIDO = 2000;
    private static final int TICKS_ESPERA_SIGUIENTE = 150;
    private static final int TICKS_POR_SEGUNDO = 100;

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
        double animacion = Math.sin(this.ticksRestantes * 0.1) * 5;
        double distanciaFlotante = 32 + animacion;
        if ("En espera".equals(this.estado)) {
            this.dibujarFlecha(entorno, this.negocioOrigen, distanciaFlotante, Color.YELLOW);
        } else if ("En camino".equals(this.estado)) {
            this.dibujarFlecha(entorno, this.casaDestino, distanciaFlotante, Color.GREEN);
        }
    }

    private void dibujarFlecha(Entorno entorno, Casa casa, double distancia, Color color) {
        double x = casa.x;
        double y = casa.y - distancia;
        entorno.dibujarTriangulo(x, y + 11, 14, 12, Math.PI / 2, color);
        entorno.dibujarRectangulo(x, y, 4, 12, Math.PI, color);
    }

    public boolean interactuar(Moto m) {
        if (m == null) {
            throw new IllegalArgumentException("La moto no puede ser nula.");
        }

        if ("En espera".equals(this.estado) && m.estaCercaDe(this.negocioOrigen)) {
            this.retirar();
        } else if ("En camino".equals(this.estado) && m.estaCercaDe(this.casaDestino)) {
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