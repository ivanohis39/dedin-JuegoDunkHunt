package Pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.IPantalla;
import base.PanelJuego;

public class PantallaDerrota implements IPantalla {
	PanelJuego panelJuego;
	private BufferedImage imgFondo;

	private Image imagenReescalada;

	int puntos;
	int cartuchos;
	double puntosTotales;

	public PantallaDerrota(PanelJuego panelJuego) {
		super();
		this.panelJuego = panelJuego;
	}

	/**
	 * Constructor que utilizamos para poder pintar la pantalla, con los puntos de
	 * muerte de patos y el numero de cartuchos disponibles
	 * 
	 * @param panelJuego
	 * @param puntos
	 * @param cartuchos
	 */
	public PantallaDerrota(PanelJuego panelJuego, int puntos, int cartuchos) {
		super();
		this.puntos = puntos;
		this.panelJuego = panelJuego;
		this.cartuchos = cartuchos;
	}

	@Override
	public void inicializarPantalla() {
		try {
			// para poner la imagende fondo
			imgFondo = ImageIO.read(new File("Imagenes/derrota.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		reescalarImagen();

	}

	/**
	 * Metodo que nos pinta por pantalla la imagen reescalada, y los datos de la
	 * derrota
	 * 
	 * @param grafico
	 */
	public void rellenarFondo(Graphics grafico) {
		grafico.drawImage(imagenReescalada, 0, 0, null);
		Font font = new Font("Arial", Font.BOLD, 40);
		grafico.setFont(font);
		grafico.setColor(Color.YELLOW);
		grafico.drawString("PASARAS MUCHA JAMBRE", panelJuego.getWidth() / 2 - 300, panelJuego.getHeight() / 2 - 180);
		font = new Font("Arial", Font.BOLD, 40);
		grafico.setFont(font);
		grafico.setColor(Color.RED);
		puntosTotales = puntos * 25 * cartuchos;
		grafico.drawString("Bajas: " + puntos, 30, panelJuego.getHeight() - 80);
		grafico.drawString("Puntos totales: " + puntosTotales, 300, panelJuego.getHeight() - 80);
		font = new Font("Arial", Font.BOLD, 30);
		grafico.setFont(font);
		grafico.setColor(Color.BLACK);
		grafico.drawString("HAZ CLIC PARA INTENTARLO DE NUEVO!", panelJuego.getWidth() / 2 - 325,
				panelJuego.getHeight() - 10);
	}

	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);

	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo que hace que al finalizar el nivel, al hacer click en el boton
	 * izquierdo del raton, pasemos al otra pantalla
	 */

	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuego);

	}

	/**
	 * Metodo que nos reescala las imagenes
	 */
	public void reescalarImagen() {
		imagenReescalada = imgFondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		reescalarImagen();

	}

}
