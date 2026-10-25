package juego.logica;

public class GestorPantallas {
	private String pantallaActual;

	public GestorPantallas() {
		this.pantallaActual = "menu";
	}

	public void cambiarPantalla(String nuevaPantalla) {
		this.pantallaActual = nuevaPantalla;
	}

	public String getPantallaActual() {
		return pantallaActual;
	}

	public boolean esMenu() {
		return pantallaActual == "menu";
	}

	public boolean esJuego() {
		return pantallaActual == "juego";
	}
}
