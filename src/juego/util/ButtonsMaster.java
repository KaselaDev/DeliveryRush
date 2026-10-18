package juego.util;

import entorno.Entorno;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ButtonsMaster {
	private static final Font FUENTE = new Font("Arial", Font.PLAIN, 24);
	private static final BufferedImage AUXILIAR = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

	private double x;
	private double y;
	private double height;
	private double width;
	private boolean estaActivado;
	private String texto;
	private Color color;
	private Image imagen;

	public ButtonsMaster(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texto = "";
		this.estaActivado = true;
		this.color = new Color(0, 0, 0, 0);
	}

	public ButtonsMaster(Entorno entorno, double x, double y, double width, double height, String texto) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texto = texto;
		this.estaActivado = true;
		this.color = new Color(0, 0, 0, 0);
		dibujar(entorno);
	}

	public ButtonsMaster(Entorno entorno, double x, double y, double width, double height, String texto, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texto = texto;
		this.estaActivado = true;
		this.color = color;
		dibujar(entorno);
	}

	public ButtonsMaster(Entorno entorno, double x, double y, String texto, Image imagen) {
		this(entorno, x, y, texto, imagen, 1.0);
	}

	public ButtonsMaster(Entorno entorno, double x, double y, String texto, Image imagen, double escala) {
		this.x = x;
		this.y = y;
		this.texto = texto;
		this.estaActivado = true;
		this.color = new Color(0, 0, 0, 0);
		this.imagen = imagen;
		this.width = Math.max(imagen.getWidth(null), 0) * escala;
		this.height = Math.max(imagen.getHeight(null), 0) * escala;
		dibujar(entorno);
	}

	private void dibujar(Entorno entorno) {
		if (this.estaActivado) {
			if (this.imagen != null) {
				entorno.dibujarImagen(this.imagen, this.x, this.y, 0, escalaParaBoton());
			} else {
				entorno.dibujarRectangulo(this.x, this.y, this.width, this.height, 0, this.color);
			}

			entorno.cambiarFont("Arial", 24, Color.white);
			Graphics2D g = AUXILIAR.createGraphics();
			g.setFont(FUENTE);
			FontMetrics fm = g.getFontMetrics();
			g.dispose();
			double textoX = this.x - fm.stringWidth(this.texto) / 2.0;
			double textoY = this.y + (fm.getAscent() - fm.getDescent()) / 2.0;
			entorno.escribirTexto(this.texto, textoX, textoY);
		}
	}

	private double escalaParaBoton() {
		int imgAncho = this.imagen.getWidth(null);
		int imgAlto = this.imagen.getHeight(null);
		if (imgAncho <= 0 || imgAlto <= 0) {
			return 1.0;
		}
		return Math.min(this.width / imgAncho, this.height / imgAlto);
	}

	public boolean verificarClick(int clickX, int clickY) {
		if (!this.estaActivado) {
			return false;
		}

		double mitadAncho = this.width / 2;
		double mitadAlto = this.height / 2;

		double xMin = this.x - mitadAncho;
		double xMax = this.x + mitadAncho;
		double yMin = this.y - mitadAlto;
		double yMax = this.y + mitadAlto;

		return clickX >= xMin && clickX <= xMax && clickY >= yMin && clickY <= yMax;
	}

	// Getters y Setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public boolean isEstaActivado() {
		return estaActivado;
	}

	public void setEstaActivado(boolean estaActivado) {
		this.estaActivado = estaActivado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
}
