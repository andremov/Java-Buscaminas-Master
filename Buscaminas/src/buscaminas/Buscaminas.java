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
import javax.swing.JOptionPane;
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
    
    
    
    /**
     * Muestra un aviso de perdida y reinicia el juego.
     */
    void usuarioPerdio() {
	JOptionPane.showMessageDialog(this, "Perdió!","Fin del juego",JOptionPane.PLAIN_MESSAGE);
	reiniciar();
    }
    
    
    
    /**
     * Muestra un aviso de ganada y reinicia el juego.
     */
    void usuarioGano() {
	JOptionPane.showMessageDialog(this, "Ganó!","Fin del juego",JOptionPane.PLAIN_MESSAGE);
	reiniciar();
    }
    

    
    
    /**
     * Convierte un int a una String para usar en JButton o JLabel.
     *
     * @param n Numero a convertir.
     * @return String para usar.
     */
    void muestraMinasAlrededor(int x, int y, int n) {
	textoTablero[x][y].setText(""+n);
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
		    casillaSeleccionada(i, j);
		}
	    }
	}
    }

    
    
    /**
     * Pinta la casilla indicada de color rojo.
     * 
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarRojo(int x, int y) {
	textoTablero[x][y].setBackground(Color.red);
    }
    
    
    
    /**
     * Pinta la casilla indicada de color azul.
     * 
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarAzul(int x, int y) {
	textoTablero[x][y].setBackground(Color.blue);
    }
    
    
    
    /**
     * Escondo el boton del tablero.
     * 
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void cambiarBoton(int x, int y) {
	botonesTablero[x][y].setVisible(false);
	textoTablero[x][y].setOpaque(true);
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
     * Reinicia la casilla indicada a su estado inicial.
     * 
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void reiniciarCasilla(int x, int y) {
	botonesTablero[x][y].setVisible(true);
	textoTablero[x][y].setOpaque(false);
	textoTablero[x][y].setText("");
	botonesPresionados[x][y] = 0;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Reinicia el juego.
     */
    void reiniciar() {
	crearMinas();
	reiniciarCasillas();
    }
    
    
    
    /**
     * Reinicia todas las casillas.
     */
    void reiniciarCasillas() {
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		reiniciarCasilla(i,j);
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
		} else {
		    minasTablero[i][j] = 0;
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
    void casillaSeleccionada(int x, int y) {
	if (esPosicionValida(x, y)) {
	    if (botonesPresionados[x][y] == 0) {
		botonesPresionados[x][y] = 1;
		cambiarBoton(x, y);
		
		if (minasTablero[x][y] == 1) {
		    pintarRojo(x, y);
		    
		    usuarioPerdio();
		} else {
		    int minasAlrededor = minasAlrededor(x, y);
		    if (minasAlrededor == 0) {
			pintarAzul(x,y);
			casillasAlrededor(x, y);
		    } else {
			muestraMinasAlrededor(x,y,minasAlrededor);
		    }
		}
	    }
	}
    }
    
    
    
    /**
     * Llama el metodo de casilla seleccionada para todas las casillas alrededor 
     * de la direccion indicada.
     * 
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void casillasAlrededor(int x, int y) {
	casillaSeleccionada(x + 1, y);
	casillaSeleccionada(x, y + 1);
	casillaSeleccionada(x, y - 1);
	casillaSeleccionada(x - 1, y);

	casillaSeleccionada(x - 1, y - 1);
	casillaSeleccionada(x + 1, y - 1);
	casillaSeleccionada(x + 1, y + 1);
	casillaSeleccionada(x - 1, y + 1);
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
		botonesPresionados[i][j] = 0;
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
    
}
