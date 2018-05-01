/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Andrés Movilla
 */
public class Buscaminas extends JFrame {
    
    
    
    /**
     * Matriz de botones que corresponden al tablero.
     */
    JButton[][] botonesTablero;
    /**
     * Matriz de enteros que correponden a las minas del tablero.
     */
    int[][] minasTablero;
    
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
	setSize(700,700);
	setLocationRelativeTo(null);
	setLayout(null);
	setTitle("Buscaminas");
	setVisible(true);
	
	
    }
    
}
