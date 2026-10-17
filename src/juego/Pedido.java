package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Pedido {
    private Casa negocioOrigen;
    private Casa casaDestino;
    private String estado; // "En espera", "En camino", "Entregado", "Perdido"
    private int ticksRestantes;
    private int ticksEsperaNuevo;

    private Image flechaRetiro;
    private Image flechaEntrega;

    public Pedido(Casa negocioOrigen, Casa casaDestino) {
        this.negocioOrigen = negocioOrigen;
        this.casaDestino = casaDestino;
        this.estado = "En espera";
        this.ticksRestantes = 2000;
        this.ticksEsperaNuevo = 150; 
        this.flechaRetiro = Herramientas.cargarImagen("imagenes/flechaAmarilla.png");
        this.flechaEntrega = Herramientas.cargarImagen("imagenes/flechaVerde.png");
    }

    public Pedido(Casa[] casas) {
        Random rand = new Random();

        int nRetiro = rand.nextInt(casas.length);
        while (!casas[nRetiro].getEsNegocio()) {
            nRetiro = rand.nextInt(casas.length);
        }

        int nEntrega = rand.nextInt(casas.length);
        while (casas[nEntrega].getEsNegocio()) {
            nEntrega = rand.nextInt(casas.length);
        }

        this.negocioOrigen = casas[nRetiro];
        this.casaDestino = casas[nEntrega];
        this.estado = "En espera";
        this.ticksRestantes = 2000;
        this.ticksEsperaNuevo = 150;
        this.flechaRetiro = Herramientas.cargarImagen("imagenes/flechaAmarilla.png");
        this.flechaEntrega = Herramientas.cargarImagen("imagenes/flechaVerde.png");
    }

    public void actualizarTiempo() {
        if (this.estado.equals("En espera") || this.estado.equals("En camino")) {
            this.ticksRestantes--;
            if (this.ticksRestantes <= 0) {
                this.estado = "Perdido";
            }
        } else {
            // Si ya está "Entregado" o "Perdido", descontamos la pausa antes del próximo pedido
            this.ticksEsperaNuevo--;
        }
    }

    public void dibujarIndicador(Entorno entorno) {
        double animacion = Math.sin(this.ticksRestantes * 0.1) * 5;
        double distanciaFlotante = 32 + animacion;
        double escalaFlecha = 0.07;
        if (this.estado.equals("En espera") && this.flechaRetiro != null) {
            entorno.dibujarImagen(this.flechaRetiro, this.negocioOrigen.getX(), this.negocioOrigen.getY() - distanciaFlotante, 0, escalaFlecha);
        } 
        else if (this.estado.equals("En camino") && this.flechaEntrega != null) {
            entorno.dibujarImagen(this.flechaEntrega, this.casaDestino.getX(), this.casaDestino.getY() - distanciaFlotante, 0, escalaFlecha);
        }
    }

    public boolean interactuar(Moto m) {
        if (this.estado.equals("En espera") && m.estaCercaDe(this.negocioOrigen)) {
            this.retirar();
        } 
        else if (this.estado.equals("En camino") && m.estaCercaDe(this.casaDestino)) {
            this.entregar();
            return true; 
        }
        return false;
    }

    // Acciones para cambiar de estado
    public void retirar() {
        if (this.estado.equals("En espera")) {
            this.estado = "En camino";
            this.ticksRestantes = 2000; // reinicio de tiempo para ir a entregar
        }
    }

    public void entregar() {
        if (this.estado.equals("En camino")) {
            this.estado = "Entregado";
        }
    }

    public void perder() {
        this.estado = "Perdido";
    }




    // Getters
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
        return this.ticksRestantes / 100; 
    }
    public boolean listoParaSiguiente() { 
        return this.ticksEsperaNuevo <= 0; 
    }
}