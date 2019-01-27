package base;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author ivan hisad
 */

public class VentanaPrincipal {

	JFrame frame = new JFrame("SUPER DUCK HUNT");
	PanelJuego panelJuego;

	public VentanaPrincipal() {
		frame.setBounds(100, 100, 800, 600);
		frame.setLayout(new GridLayout(1, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void inicializarComponentes() {
		panelJuego = new PanelJuego();
		frame.add(panelJuego);

	}

	// inicializa todos los componentes de la ventana principal
	public void inicializar() {
		frame.setVisible(true);
		inicializarComponentes();
	}
}
