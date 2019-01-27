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
import base.Sprite;

public class PantallaVictoria implements IPantalla {
	PanelJuego panelJuego;
	private BufferedImage imgFondo;

	PantallaJuego pantallaJuego;

	private Image imagenReescalada;

	int puntos;
	int cartuchos;
	double puntosTotales;

	/**
	 * Constrcutor que recibe el panel, las muertes de patos, y los cartuchos que
	 * quedan
	 * 
	 * @param panelJuego
	 * @param puntos
	 * @param cartuchos
	 */
	public PantallaVictoria(PanelJuego panelJuego, int puntos, int cartuchos) {
		super();
		this.panelJuego = panelJuego;
		this.puntos = puntos;
		this.cartuchos = cartuchos;
	}

	public PantallaVictoria(PanelJuego panelJuego) {
		super();
		this.panelJuego = panelJuego;
	}

	@Override
	public void inicializarPantalla() {
		try {
			// para poner la imagende fondo
			imgFondo = ImageIO.read(new File("Imagenes/victoria.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		reescalarImagen();

	}

	/**
	 * Metodo que rellena el fondo de la pantalla con una imagen reescalada y ademas
	 * pinta los puntos obtenidos
	 * 
	 * @param grafico
	 */

	public void rellenarFondo(Graphics grafico) {
		grafico.drawImage(imagenReescalada, 0, 0, null);
		Font font = new Font("Arial", Font.BOLD, 40);
		grafico.setFont(font);
		grafico.setColor(Color.BLACK);
		grafico.drawString("TODO UN KILLER", panelJuego.getWidth() / 2 - 200, panelJuego.getHeight() / 2 - 20);
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
				panelJuego.getHeight() - 20);

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

	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaJuegoSegundo pantallaJuegoSegundo = new PantallaJuegoSegundo(panelJuego);
		pantallaJuegoSegundo.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuegoSegundo);

	}

	public void reescalarImagen() {

		imagenReescalada = imgFondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);

	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		reescalarImagen();

	}

}
