/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Andrés Movilla
 */
public class Buscaminas extends JFrame implements Runnable {

    /**
     * Icono de mina.
     */
    ImageIcon imagenMina;

    /**
     * Icono de mina.
     */
    ImageIcon imagenBandera;

    /**
     * Tamaño de una casilla.
     */
    int tamanoCasilla = 55;

    /**
     * Espacio entre casillas.
     */
    int espacioCasilla = 5;
    
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
     * Numero de casillas seleccionadas.
     */
    int casillasSeleccionadas = 0;

    /**
     * Banderas plantadas
     */
    int banderasPlantadas = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	new Thread(new Buscaminas()).start();
    }

    /**
     * Crea la ventana del juego.
     */
    public Buscaminas() {
	int size = tamanoTablero * (espacioCasilla + tamanoCasilla);
	setSize(size + 12, size + 36);
	setLocationRelativeTo(null);
	setLayout(null);
	setTitle("Buscaminas ");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);

	imagenMina = cargarImagen("mina.png");
	imagenBandera = cargarImagen("bandera.png");

	addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		int[] p = buscarBoton(e.getX(), e.getY());
		if (p != null) {
		    if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
			casillaBandera(p[0], p[1]);
		    } else if (javax.swing.SwingUtilities.isLeftMouseButton(e)) {
			casillaAbrir(p[0], p[1]);
		    }
		}
	    }
	});

	crearCasillas();
	agregarCasillas();
	crearMinas();

	setVisible(true);
    }

    /**
     * Carga imagen.
     *
     * @param path Direccion del archivo imagen.
     * @return Imagen cargada de la direccion.
     */
    ImageIcon cargarImagen(String path) {
	try {
	    BufferedImage img1 = javax.imageio.ImageIO.read(new java.io.File(path));
	    BufferedImage img2 = new BufferedImage(tamanoCasilla, tamanoCasilla, BufferedImage.TYPE_INT_ARGB);
	    java.awt.Graphics g = img2.getGraphics();
	    g.drawImage(img1, 0, 0, tamanoCasilla, tamanoCasilla, null);
	    g.dispose();

	    return new ImageIcon(img2);
	} catch (Exception e) {
	    return null;
	}
    }

    /**
     * Crea un color con la informacion dada.
     *
     * @param hue Tinte del color a crear.
     * @param saturation Saturacion del color a crear.
     * @param brightness Brillo del color a crear.
     * @return Color creado.
     */
    public Color crearColor(double hue, double saturation, double brightness) {
	return Color.getHSBColor((float) hue / 360f, (float) saturation / 100f, (float) brightness / 100f);
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
	JOptionPane.showMessageDialog(this, "Perdió!", "Fin del juego", JOptionPane.PLAIN_MESSAGE);
	reiniciar();
    }

    /**
     * Muestra un aviso de ganada y reinicia el juego.
     */
    void usuarioGano() {
	JOptionPane.showMessageDialog(this, "Ganó!", "Fin del juego", JOptionPane.PLAIN_MESSAGE);
	reiniciar();
    }

    /**
     * Convierte un int a una String para usar en JButton o JLabel.
     *
     * @param n Numero a convertir.
     * @return String para usar.
     */
    void muestraMinasAlrededor(int x, int y, int n) {
	textoTablero[x][y].setText("" + n);
    }

    /**
     *
     */
    @Override
    public void run() {
	while (true) {

	    String t = this.getTitle();
	    String t2 = t.substring(1);
	    setTitle(t2 + t.substring(0, 1));

	    try {
		Thread.sleep(100);
	    } catch (Exception e) {
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
	textoTablero[x][y].setBackground(crearColor(359.3, 70.6, 100));
    }

    /**
     * Pinta la casilla indicada de color azul.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarAzul(int x, int y) {
	textoTablero[x][y].setBackground(crearColor(209, 24.3, 100));
    }

    /**
     * Pinta la casilla indicada de color verde.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarVerde(int x, int y) {
	textoTablero[x][y].setBackground(crearColor(90, 43.9, 100));
    }

    /**
     * Pinta la casilla indicada de color predeterminado.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void pintarPlano(int x, int y) {
	textoTablero[x][y].setBackground(crearColor(210, 43.9, 100));

    }

    /**
     * Muestra una mina en la casilla indicada.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void mostrarMina(int x, int y) {
	textoTablero[x][y].setIcon(imagenMina);
    }

    /**
     * Pone la imagen de la bandera de la casilla.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void plantarBandera(int x, int y) {
	textoTablero[x][y].setIcon(imagenBandera);
    }

    /**
     * Quita la imagen de la bandera de la casilla.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void quitarBandera(int x, int y) {
	textoTablero[x][y].setIcon(null);
    }

    /**
     * Encontrar boton.
     *
     * @param x Posicion X para buscar boton.
     * @param y Posicion Y para buscar boton.
     * @return La direccion i y direccion j del boton.
     */
    int[] buscarBoton(int x, int y) {

	int rx = 0, ry = 0;
	boolean found = false;
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		int dx = x - textoTablero[i][j].getX() - getInsets().left;
		int dy = y - textoTablero[i][j].getY() - getInsets().top;
		if (dx >= 0 && dy >= 0 && dx <= tamanoCasilla && dy <= tamanoCasilla) {
		    rx = i;
		    ry = j;
		    found = true;

		}
	    }
	}

	return found ? new int[]{rx, ry} : null;
    }

    /**
     * Agrega los JButton y JLabel previamente creados a la ventana.
     */
    void agregarCasillas() {
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		textoTablero[i][j].setLocation(
			((j + 1) * espacioCasilla) + (j * tamanoCasilla),
			((i + 1) * espacioCasilla) + (i * tamanoCasilla)
		);
		textoTablero[i][j].setSize(tamanoCasilla, tamanoCasilla);
		textoTablero[i][j].setHorizontalAlignment(SwingConstants.CENTER);
		textoTablero[i][j].setOpaque(true);
		pintarPlano(i, j);
		add(textoTablero[i][j]);
	    }
	}
    }

    /*
	PUNTOS
     */
    
    /**
     * Reinicia la casilla indicada a su estado inicial.
     *
     * @param x Posicion X de la casilla indicada.
     * @param y Posicion Y de la casilla indicada.
     */
    void reiniciarCasilla(int x, int y) {
	pintarPlano(x, y);
	textoTablero[x][y].setText("");
	textoTablero[x][y].setIcon(null);
	botonesPresionados[x][y] = 0;
    }

    /**
     * Reinicia el juego.
     */
    void reiniciar() {
	totalMinas = 0;
	casillasSeleccionadas = 0;
	banderasPlantadas = 0;
	crearMinas();
	reiniciarCasillas();
    }

    /**
     * Reinicia todas las casillas.
     */
    void reiniciarCasillas() {
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		reiniciarCasilla(i, j);
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
		    totalMinas = totalMinas + 1;
		} else {
		    minasTablero[i][j] = 0;
		}
	    }
	}
    }

    /**
     * Revisa si el usuario ganó.
     */
    void revisarGanar() {
	if (casillasSeleccionadas + banderasPlantadas == tamanoTablero * tamanoTablero) {
	    mostrarTablero();
	    usuarioGano();
	}
    }

    /**
     * Muestra todas las minas en el tablero.
     */
    void mostrarTablero() {
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
		if (minasTablero[i][j] == 1) {
		    mostrarMina(i, j);
		    if (botonesPresionados[i][j] != 2) {
			pintarRojo(i, j);
		    } else {
			pintarVerde(i, j);
		    }
		}
	    }
	}
    }

    /**
     * Llama todos los metodos relacionados con abrir una casilla.
     *
     * @param x Posicion X del boton presionado.
     * @param y Posicion Y del boton presionado.
     */
    void casillaAbrir(int x, int y) {
	if (esPosicionValida(x, y)) {
	    if (botonesPresionados[x][y] == 0) {
		botonesPresionados[x][y] = 1;

		if (minasTablero[x][y] == 1) {
		    pintarRojo(x, y);
		    mostrarMina(x, y);
		    usuarioPerdio();
		} else {
		    casillasSeleccionadas = casillasSeleccionadas + 1;

		    int minasAlrededor = minasAlrededor(x, y);
		    if (minasAlrededor == 0) {
			pintarAzul(x, y);
			casillasAlrededor(x, y);
		    } else {
			pintarAzul(x, y);
			muestraMinasAlrededor(x, y, minasAlrededor);
		    }
		    revisarGanar();
		}
	    }
	}
    }

    /**
     * Llama todos los metodos relacionados con banderear una casilla.
     *
     * @param x Posicion X del boton presionado.
     * @param y Posicion Y del boton presionado.
     */
    void casillaBandera(int x, int y) {
	if (esPosicionValida(x, y)) {
	    if (botonesPresionados[x][y] == 0 && banderasPlantadas < totalMinas) {
		botonesPresionados[x][y] = 2;
		banderasPlantadas = banderasPlantadas + 1;
		plantarBandera(x, y);
		revisarGanar();
	    } else if (botonesPresionados[x][y] == 2) {
		botonesPresionados[x][y] = 0;
		banderasPlantadas = banderasPlantadas - 1;
		quitarBandera(x, y);
	    }
	}
    }

    /**
     * Abre todas las casillas alrededor de la direccion indicada.
     *
     * @param x Posicion X de casilla indicada.
     * @param y Posicion Y de casilla indicada.
     */
    void casillasAlrededor(int x, int y) {
	casillaAbrir(x + 1, y);
	casillaAbrir(x, y + 1);
	casillaAbrir(x, y - 1);
	casillaAbrir(x - 1, y);

	casillaAbrir(x - 1, y - 1);
	casillaAbrir(x + 1, y - 1);
	casillaAbrir(x + 1, y + 1);
	casillaAbrir(x - 1, y + 1);
    }

    /**
     * Crea la matriz de JLabel.
     */
    void crearCasillas() {
	botonesPresionados = new int[tamanoTablero][tamanoTablero];
	textoTablero = new JLabel[tamanoTablero][tamanoTablero];
	for (int i = 0; i < tamanoTablero; i++) {
	    for (int j = 0; j < tamanoTablero; j++) {
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
