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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import base.IPantalla;
import base.PanelJuego;
import base.Sprite;

public class PantallaJuegoSegundo implements IPantalla {

	PanelJuego panelJuego;

	private static String RUTA_PERRO_IZQUIERDA = "Imagenes/perro_izq/perro_0.png";
	private static String RUTA_PERRO_DERECHA = "Imagenes/perro_der/perro_0.png";
	private static String RUTA_FONDO = "Imagenes/fondo.jpg";
	private static String RUTA_ESCOPETA = "Imagenes/escopeta/escopeta_0.png";
	private static String RUTA_PATOS_AZULES = "Imagenes/pato_azul/pato_0.png";
	private static String RUTA_PATOS_ROJOS = "Imagenes/pato_rojo/pato_0.png";

	// ancho y alto del cartucho
	private static final int anchoCartucho = 10;
	private static final int altoCartucho = 25;
	// velocidades en los ejes X e Y del cartucho
	private static final int velocidaCartuchoX = 0;
	private static final int velocidadCArtuchoY = -20;

	private ArrayList<Sprite> patos = new ArrayList<>();

	int numeroPatos = 4;

	private BufferedImage imgFondo;
	private Image imagenReescalada;
	Sprite buffer;
	int patosCazados = 0;

	private Sprite scopeto;
	private Sprite perroDER, perroIZQ;
	private Sprite perdigon, patoMuerto;

	// inicio pnatalla
	Color colorLetras = Color.YELLOW;
	int contColorframes = 0;
	static final int CAMBIO_COLOR_INICIO = 5;

	boolean jugando = true;
	boolean derrota = false;

	boolean tocado = false;

	// variables para el contador del tiempo
	double tiempoInicial;
	double tiempoDeJuego;
	private DecimalFormat formatoDecimal; // Formatea la salida.
	Font fuenteTiempo;

	// numero de cartuchos iniciales
	int contadorBalazos = 6;

	public PantallaJuegoSegundo(PanelJuego panelJuego) {
		super();
		this.panelJuego = panelJuego;
		tiempoInicial = System.nanoTime();
	}

	@Override
	public void inicializarPantalla() {

		// aniadimos los patos y los perros que necesitamos

		lanzaPatos();
		perroDER = new Sprite(180, 180, -10, panelJuego.getHeight() - 250, 7, 0, RUTA_PERRO_DERECHA);
		perroIZQ = new Sprite(180, 180, 20, panelJuego.getHeight() - 250, 7, 0, RUTA_PERRO_IZQUIERDA);

		try {
			// para poner la imagende fondo
			imgFondo = ImageIO.read(new File(RUTA_FONDO));
		} catch (IOException e) {
			e.printStackTrace();
		}

		scopeto = new Sprite(150, 100, panelJuego.getWidth() / 2 - 75, panelJuego.getHeight() - 100, RUTA_ESCOPETA);

		fuenteTiempo = new Font("Arial", Font.BOLD, 20);

		tiempoInicial = System.nanoTime();
		tiempoDeJuego = 0;
		formatoDecimal = new DecimalFormat("#.##");

		reescalarImagen();

	}

	public static int generarAleatorio(int numero) {
		Random rd = new Random();
		int aleat = rd.nextInt(numero) + 2;
		return aleat;
	}

	public void lanzaPatos() {
		int posX = panelJuego.getWidth();
		int posY = panelJuego.getHeight();
		for (int i = 0; i < numeroPatos/2; i++) {
			patos.add(new Sprite(80, 80, posX / generarAleatorio(10), posY / generarAleatorio(10),
					generarAleatorio(3) + 1, 0, RUTA_PATOS_AZULES));
			patos.add(new Sprite(80, 80, posX / generarAleatorio(10), posX / generarAleatorio(10),
					generarAleatorio(6) + 1, 0, RUTA_PATOS_ROJOS));
		}

	}

	/**
	 * Metodo que nos actualiza el cronometro
	 */
	public void actualizarCrono() {
		tiempoDeJuego = System.nanoTime() - tiempoInicial;
	}

	/**
	 * Metodo que nos pinta el cronometro
	 * 
	 * @param g
	 */
	private void pintarTiempo(Graphics g) {
		Font f = g.getFont();
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.setFont(fuenteTiempo);
		actualizarCrono();
		g.drawString(formatoDecimal.format(tiempoDeJuego / 1000000000d), panelJuego.getWidth() - 50,
				panelJuego.getHeight() - 10);
		g.setColor(c);
		g.setFont(f);
	}

	/**
	 * Metodo que nos pinta por pantalla los sprites
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		// pintamos los cuadrados
		for (int i = 0; i < patos.size(); i++) {
			patos.get(i).pintar(g);
		}

		// pintamos the favolous nave
		if (perdigon != null) {
			perdigon.pintar(g);
		}

		perroDER.pintar(g);
		scopeto.pintar(g);
		pintarTiempo(g);

	}

	/**
	 * Metodo que nos pinta las pantallas
	 * 
	 * @param g
	 */
	public void rellenarFondo(Graphics g) {

		if (jugando) {
			g.drawImage(imagenReescalada, 0, 0, null);
			Font font = new Font("Arial", Font.BOLD, 40);
			g.setFont(font);
			g.setColor(Color.RED);
			g.drawString("CARTUCHOS: " + contadorBalazos,30, 50);
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString("PATOS CAZADOS: " + patosCazados, panelJuego.getWidth() - 450, 50);
		} else if (!derrota) {
			PantallaVictoria pantallaVictoria = new PantallaVictoria(panelJuego, patosCazados, contadorBalazos);
			pantallaVictoria.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaVictoria);
		} else {
			PantallaDerrota pantallaDerrota = new PantallaDerrota(panelJuego, patosCazados, contadorBalazos);
			pantallaDerrota.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaDerrota);

		}

	}

	@Override
	public void ejecutarFrame() {
		comprobarColision();
		if (jugando) {
			moverSprites();
		}
	}

	public void moverSprites() {
		// movemos los meteoros por la pantalla
		for (int i = 0; i < patos.size(); i++) {
			patos.get(i).moverPatosDerecha(panelJuego.getWidth(), panelJuego.getHeight());

		}

		for (int i = 0; i < patos.size(); i++) {
			if (patos.get(i).getPosX() >= panelJuego.getWidth()) {
				patos.remove(i);
				numeroPatos--;
			}
			if (numeroPatos == 0) {
				jugando = false;
				derrota = true;
			}

		}

		if (perdigon != null) {
			perdigon.disparar();
			if (perdigon.getAlto() + perdigon.getPosY() <= 0) {
				perdigon = null;
				contadorBalazos--;
				if (contadorBalazos == 0) {
					jugando = false;
					derrota = true;
				}
			}
		}

		perroDER.moverPerro(panelJuego.getWidth());
		if (perroDER.getPosX() >= panelJuego.getWidth()) {
			perroDER = perroIZQ;
		} else if (perroDER.getPosX() <= -200) {
			perroDER = new Sprite(180, 180, -200, panelJuego.getHeight() - 250, 7, 0, RUTA_PERRO_DERECHA);
			;
		}

	}

	public void comprobarColision() {

		for (int i = 0; i < patos.size() && perdigon != null; i++) {
			// empieza en i+1 porque no lo queremos comprobar conmigo mismo
			if (patos.get(i).colisionan(perdigon)) {

				patoMuerto = (new Sprite(80, 80, patos.get(i).getPosX(), patos.get(i).getPosY(), 0, 2,
						"Imagenes/tocado.png"));
				patos.add(patoMuerto);
				patos.set(i, patoMuerto);
				patos.remove(i);
				tocado = true;
				perdigon = null;
				patosCazados++;
				contadorBalazos--;
				numeroPatos--;
				if (0 == numeroPatos) {
					jugando = false;
					derrota = false;
				}
			}
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {

		if (e.getX() > panelJuego.getWidth() / 2) {
			scopeto = new Sprite(150, 100, 300, 200, "Imagenes/escopeta/escopeta_0_der.png");
			scopeto.setPosX(panelJuego.getWidth() / 2 - scopeto.getAncho() / 2);
			scopeto.setPosY((panelJuego.getHeight()) - scopeto.getAlto());

		} else {
			scopeto = new Sprite(150, 100, 300, 200, "Imagenes/escopeta/escopeta_0.png");
			scopeto.setPosX(panelJuego.getWidth() / 2 - scopeto.getAncho() / 2);
			scopeto.setPosY((panelJuego.getHeight()) - scopeto.getAlto());

		}

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		Color c = new Color(0f, 0f, 0f, 0f);
		if (perdigon == null) {
			perdigon = new Sprite(anchoCartucho, altoCartucho, e.getX() - anchoCartucho / 2,
					e.getY() - altoCartucho / 2, velocidaCartuchoX, velocidadCArtuchoY, null);
			perdigon.setColor(c);
		}
		if (e.getX() > panelJuego.getWidth() / 2) {
			scopeto = new Sprite(150, 100, 300, 200, "Imagenes/escopeta/escopeta_1_der.png");
			scopeto.setPosX(panelJuego.getWidth() / 2 - scopeto.getAncho() / 2);
			scopeto.setPosY((panelJuego.getHeight()) - scopeto.getAlto());

		} else {
			scopeto = new Sprite(150, 100, 300, 200, "Imagenes/escopeta/escopeta_1.png");
			scopeto.setPosX(panelJuego.getWidth() / 2 - scopeto.getAncho() / 2);
			scopeto.setPosY((panelJuego.getHeight()) - scopeto.getAlto());

		}
		if (tocado == true) {
			scopeto = new Sprite(250, 200, 300, 200, "Imagenes/allRight.png");
			scopeto.setPosX(panelJuego.getWidth() / 2 - scopeto.getAncho() / 2);
			scopeto.setPosY((panelJuego.getHeight()) - scopeto.getAlto());
			tocado = false;
			perdigon = null;
		}

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
