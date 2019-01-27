package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {

	private BufferedImage buffer;

	private Color color = Color.WHITE;
	// variables de dimension
	private int ancho;
	private int alto;
	// variables de colocacion
	private int posX;
	private int posY;
	// variables de velocidad
	private int velX;
	private int velY;
	// nos creamos un string para poder pasar cualquier imagen
	private String rutaImagen;

	int contadorPatos, contadorPerros;

	public Sprite() {
		super();
		actualizarBuffer();
	}

	// para la escopeta
	public Sprite(int ancho, int alto, int posX, int posY, String rutaImagen) {
		super();
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer();
	}

	// para la los patos y el perro
	public Sprite(int ancho, int alto, int posX, int posY, int velX, int velY, String rutaImagen) {
		super();
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velX = velX;
		this.velY = velY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer();
	}

	/**
	 * Metodo que recibe el alto y el ancho de la imagen del pato, y hce que se
	 * mueva para la derecha
	 * 
	 * @param width
	 * @param height
	 */
	public void moverPatosDerecha(int width, int height) {
		// eje X
		if (posX + ancho >= width + 200) {
			// le cogemos el valor absoluto para saber el signo de X
			// velX = Math.abs(velX) * -1;
			posX = -100;
		}
		if (posX < 0) {
			velX = Math.abs(velX);
		}
		// eje Y
		if (posY + alto >= height) {
			// le cogemos el valor absoluto para saber el signo de X
			// velY = Math.abs(velY) * -1;
		}
		if (posY < 0) {
			velY = Math.abs(velY);
		}
		posX = posX + velX;
		posY = posY + velY;

	}

	/**
	 * Metodo que recibe el alto y el ancho de la imagen del pato, y hce que se
	 * mueva para la izquierda
	 * 
	 * @param width
	 * @param height
	 */
	public void moverPatosIzquierda(int width, int height) {
		// eje X
		if (posX + ancho >= width + 200) {
			// le cogemos el valor absoluto para saber el signo de X
			velX = Math.abs(velX) * -1;
		}
		if (posX < 0) {
			velX = Math.abs(velX);
		}
		// eje Y
		if (posY + alto >= height) {
			// le cogemos el valor absoluto para saber el signo de X
			// velY = Math.abs(velY) * -1;
		}
		if (posY < 0) {
			velY = Math.abs(velY);
		}
		posX = posX + velX;
		posY = posY + velY;

	}

	/**
	 * Metodo que hace que el perro se pueda mover por la pantalla
	 * 
	 * @param width
	 */
	public void moverPerro(int width) {
		// eje X
		if (posX + ancho >= width + 200) {
			// le cogemos el valor absoluto para saber el signo de X
			velX = Math.abs(velX) * -1;
			// posX = -100;
		}
		if (posX <= -200) {
			velX = Math.abs(velX);
		}
		posX = posX + velX;

	}

	// para saber la posicion del disparo
	public void disparar() {
		posX = posX + velX;
		posY = posY + velY;

	}

	/**
	 * Metodo que recibe como parametro un sprite diferente, para comprobar la
	 * colision de uno con otro
	 * 
	 * @param otroSprite
	 * @return
	 */
	public boolean colisionan(Sprite otroSprite) {
		// Checkeamos si comparten algún espacio a lo ancho:
		boolean colisionAncho = false;
		if (posX < otroSprite.getPosX()) { // El Sprite actual se encuentra más cerca del eje de las X.
			colisionAncho = posX + ancho >= otroSprite.getPosX();
		} else { // El otro Sprite se encuentra más cerca del eje de las X.
			colisionAncho = otroSprite.getPosX() + otroSprite.getAncho() >= posX;
		}

		// Checkeamos si comparten algún espacio a lo alto:
		boolean colisionAlto = false;
		if (posY < otroSprite.getPosY()) {
			colisionAlto = alto > otroSprite.getPosY() - posY;
		} else {
			colisionAlto = otroSprite.getAlto() > posY - otroSprite.getPosY();
		}

		return colisionAncho && colisionAlto;
	}

	/**
	 * Metodo para actializar el buffer
	 */
	public void actualizarBuffer() {

		contadorPatos++;
		contadorPerros++;
		if (contadorPatos == 12) {
			contadorPatos = 0;
		}
		if (contadorPerros == 10) {
			contadorPerros = 0;
		}

		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		// nos creamos un buffer nuevo para poder redimensionar los meteoros y la nave
		try {
			if (!rutaImagen.contains("pato_azul") && !rutaImagen.contains("perro_der")
					&& !rutaImagen.contains("perro_izq") && !rutaImagen.contains("pato_rojo")) {
				BufferedImage imgSprite;
				imgSprite = ImageIO.read(new File(rutaImagen));
				g.drawImage(imgSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
			} else if (rutaImagen.contains("perro_der")) {
				BufferedImage imgSprite;
				imgSprite = ImageIO.read(new File("Imagenes/perro_der/perro_" + contadorPerros + ".png"));
				g.drawImage(imgSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
			} else if (rutaImagen.contains("perro_izq")) {
				BufferedImage imgSprite;
				imgSprite = ImageIO.read(new File("Imagenes/perro_izq/perro_" + contadorPerros + ".png"));
				g.drawImage(imgSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
			} else if (rutaImagen.contains("pato_rojo")) {
				BufferedImage imgSprite;
				imgSprite = ImageIO.read(new File("Imagenes/pato_rojo/pato_" + contadorPatos + ".png"));
				g.drawImage(imgSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
			} else {
				BufferedImage imgSprite;
				imgSprite = ImageIO.read(new File("Imagenes/pato_azul/pato_" + contadorPatos + ".png"));
				g.drawImage(imgSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);

			}

		} catch (Exception e) {
			g.setColor(color);
			g.fillRect(0, 0, ancho, alto);
			g.dispose();
		}

	}

	/**
	 * Pinta por pantalla los elementos del buffer
	 * 
	 * @param g
	 */
	public void pintar(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
		actualizarBuffer();

	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		// volvemos a actualizar el buffer para aniadir el color

		actualizarBuffer();
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

}