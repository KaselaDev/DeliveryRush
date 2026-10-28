package juego.mapa;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Casa {
	double x;
	double y;
	double ancho;
	double alto;
	Image imagen;
	Entorno entorno;
	private double angulo;
	private String direccion;
	private boolean esNegocio;

	public Casa(double x, double y, double ancho, double alto, Entorno e, String direccion) {
		this(x, y, ancho, alto, 0, false, e, direccion);
	}

	public Casa(double x, double y, double ancho, double alto, double angulo, boolean esNegocio, Entorno e,
			String direccion) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.angulo = angulo;
		this.esNegocio = esNegocio;
		this.entorno = e;
		this.direccion = direccion;
		this.setImage();
	}

	public void setImage() {
		int estiloCasa = this.esNegocio ? new Random().nextInt(4) + 1 : new Random().nextInt(5) + 1; // Genera un número
		String tipo = this.esNegocio ? "negocios" : "casas";
		String imagePath = "imagenes/" + tipo + "/" + estiloCasa + (this.direccion != "frente" ? "-"+this.direccion : "") + ".png";
		this.imagen = Herramientas.cargarImagen(imagePath);
		if (this.imagen == null) {
			System.out.println("No se pudo cargar la imagen: " + imagePath);
			// Cargar imagen de fallback
			this.imagen = Herramientas.cargarImagen("imagenes/casa1.png");
		}
	}

	public void dibujar() {
		this.entorno.dibujarImagen(this.imagen, this.x, this.y, this.angulo, 0.31);
	}

	public boolean isEsNegocio() {
		return esNegocio;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public String getDirecciones() {
		return this.direccion;
	}

	public boolean zonaDeInteraccion(double x, double y) {
		double xZona, yZona;
		double xExpacion = this.entorno.getWidth() / 18;
		double yExpacion = this.entorno.getHeight() / 18;
		int radioZona = 50; // Radio de la zona de entrega
		
		switch (this.direccion) {
			case "frente":
				xZona = this.x;
				yZona = this.y + yExpacion + 16;
				break;
			case "atras":
				xZona = this.x;
				yZona = this.y - yExpacion;
				break;
			case "izquierda":
				xZona = this.x - xExpacion;
				yZona = this.y;
				break;
			case "derecha":
				xZona = this.x + xExpacion;
				yZona = this.y;
				break;
			default:
				xZona = this.x;
				yZona = this.y;
				break;
		}

		double distancia = Math.hypot(x - xZona, y - yZona);
		System.out.println("Pruede interactuar: "+(distancia <= radioZona));
		return distancia <= radioZona;
	}

	public double getZonaInteraccionX() {
		double xExpacion = this.entorno.getWidth() / 18;
		switch (this.direccion) {
			case "frente":
			case "atras":
				return this.x;
			case "izquierda":
				return this.x - xExpacion;
			case "derecha":
				return this.x + xExpacion;
			default:
				return this.x;
		}
	}

	public double getZonaInteraccionY() {
		double yExpacion = this.entorno.getHeight() / 18;
		switch (this.direccion) {
			case "frente":
				return this.y + yExpacion + 16;
			case "atras":
				return this.y - yExpacion;
			case "izquierda":
			case "derecha":
				return this.y;
			default:
				return this.y;
		}
	}

	public void dibujarZonaInteraccion() {
		double xZona, yZona;
		double xExpacion = this.entorno.getWidth() / 18;
		double yExpacion = this.entorno.getHeight() / 18;
		int radioZona = 50;
		Color color = new Color(0, 0, 0);
		
    switch (this.direccion) {
        case "frente":
            xZona = this.x;
            yZona = this.y + yExpacion + 16;
            color = new Color(0, 0, 255, 100);
            break;
        case "atras":
            xZona = this.x;
            yZona = this.y - yExpacion;
            color = new Color(255, 0, 0, 100);
            break;
        case "izquierda":
            xZona = this.x - xExpacion;
            yZona = this.y;
            color = new Color(0, 255, 0, 100);
            break;
        case "derecha":
            xZona = this.x + xExpacion;
            yZona = this.y;
						color = new Color(255, 200, 0, 100);
            break;
        default:
            xZona = this.x;
            yZona = this.y;
            break;
		}
		
		this.entorno.dibujarCirculo(xZona, yZona, radioZona, color);
	}
}
