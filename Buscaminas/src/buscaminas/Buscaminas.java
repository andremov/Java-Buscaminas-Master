/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Andrés Movilla
 */
public class Buscaminas extends JFrame implements ActionListener {

    /**
     * Matriz de JButton que corresponden al tablero.
     */
    JButton[][] botonesTablero;
    /**
     * Matriz de JLabel que corresponden a las minas en el tablero.
     */
    JLabel[][] textoTablero;
    /**
     * Matriz de int que correponden a la informacion del tablero.
     */
    int[][] minasTablero;
    /**
     * Numero de casillas en cada fila y columna del tablero.
     */
    int tamanoTablero = 10;
    /**
     * Matriz de int que corresponden a los botones presionados.
     */
    int[][] botonesPresionados;
    /**
     * Numero de minas en el tablero.
     */
    int totalMinas = 0;
    /**
     *
     */
    JPanel juego;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	new Buscaminas();
    }

    /**
     * Crea la ventana del juego.
     */
    public Buscaminas() {
	setSize(700, 700);
	setLocationRelativeTo(null);
	setLayout(null);
	setTitle("Buscaminas");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);

	crearBotones();
	agregarBotones();
	crearMinas();

	setVisible(true);

    }

    /**
     * Convierte un int a una String para usar en JButton o JLabel.
     *
     * @param n Numero a convertir.
     * @return String para usar.
     */
    String convertirInt(int n) {
	return "" + n;
    }

    /**
     * Maneja cuando se unde un boton de la ventana.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		if (botonesTablero[i][j] == e.getSource()) {
		    clicIzquierdoBoton(i, j);
		}
	    }
	}
    }

    /**
     * Crea las minas en el tablero.
     */
    void crearMinas() {
	minasTablero = new int[tamanoTablero][tamanoTablero];
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		if (numeroAleatorio() < 3) {
		    minasTablero[i][j] = 1;
		}
	    }
	}
    }

    /**
     * Llama todos los metodos relacionados con presionar un boton.
     *
     * @param x Posicion X del boton presionado.
     * @param y Posicion Y del boton presionado.
     */
    void clicIzquierdoBoton(int x, int y) {
	if (esPosicionValida(x, y)) {
	    if (botonesPresionados[x][y] == 0) {
		botonesPresionados[x][y] = 1;
		cambiarBoton(x, y);
		int minasAlrededor = minasAlrededor(x, y);
		System.out.println(minasAlrededor);
		if (minasAlrededor == 0) {
		    clicIzquierdoBoton(x + 1, y);
		    clicIzquierdoBoton(x, y + 1);
		    clicIzquierdoBoton(x, y - 1);
		    clicIzquierdoBoton(x - 1, y);

		    clicIzquierdoBoton(x - 1, y - 1);
		    clicIzquierdoBoton(x + 1, y - 1);
		    clicIzquierdoBoton(x + 1, y + 1);
		    clicIzquierdoBoton(x - 1, y + 1);
		}
	    }
	}
    }

    /**
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void cambiarBoton(int x, int y) {
	botonesTablero[x][y].setVisible(false);
	textoTablero[x][y].setOpaque(true);

	if (minasTablero[x][y] == 1) {
	    textoTablero[x][y].setBackground(Color.red);
	} else {
	    int minasAlrededor = minasAlrededor(x, y);
	    if (minasAlrededor == 0) {
		textoTablero[x][y].setBackground(Color.blue);
	    } else {
		textoTablero[x][y].setText(convertirInt(minasAlrededor));
	    }
	}
    }

    /**
     * Agrega los JButton y JLabel previamente creados a la ventana.
     */
    void agregarBotones() {
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		textoTablero[i][j].setLocation(20 + (j * 60), 20 + (i * 60));
		textoTablero[i][j].setSize(60, 60);
		textoTablero[i][j].setHorizontalAlignment(SwingConstants.CENTER);
		add(textoTablero[i][j]);

		botonesTablero[i][j].setLocation(20 + (j * 60), 20 + (i * 60));
		botonesTablero[i][j].setSize(60, 60);
		botonesTablero[i][j].addActionListener(this);
		add(botonesTablero[i][j]);
	    }
	}
    }

    /**
     * Crear matriz de JButton y JLabel.
     */
    void crearBotones() {
	botonesPresionados = new int[tamanoTablero][tamanoTablero];
	botonesTablero = new JButton[tamanoTablero][tamanoTablero];
	textoTablero = new JLabel[tamanoTablero][tamanoTablero];
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		botonesTablero[i][j] = new JButton();
		textoTablero[i][j] = new JLabel();
	    }
	}
    }

    /**
     * Cuenta las minas alrededor de la casilla indicada.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     * @return Numero de minas alrededor de la casilla indicada.
     */
    int minasAlrededor(int x, int y) {
	int m = 0;
	for (int dx = -1; dx <= 1; dx++) {
	    for (int dy = -1; dy <= 1; dy++) {
		if (esPosicionValida(x + dx, y + dy)) {
		    if (minasTablero[x + dx][y + dy] == 1) {
			m = m + 1;
		    }
		}
	    }
	}
	return m;
    }

    /**
     * Indica si una posicion se encuentra dentro del tablero
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     * @return Retorna true si es una posicion valida, de lo contrario, falso.
     */
    boolean esPosicionValida(int x, int y) {
	return (x >= 0 && x < tamanoTablero && y >= 0 && y < tamanoTablero);
    }

    /**
     * Genera un numero aleatorio del 1 al 10.
     *
     * @return Numero generado.
     */
    int numeroAleatorio() {
	return new java.util.Random().nextInt(10) + 1;
    }
}
