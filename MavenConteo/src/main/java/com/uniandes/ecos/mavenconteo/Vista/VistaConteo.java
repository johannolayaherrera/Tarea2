/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Conceptos Avanzados de Ingeniería de Software
 *
 * Proyecto Conteo Maven
 * Tarea 2
 * Autor: Johann De Jesus Olaya Herrera
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.uniandes.ecos.mavenconteo.Vista;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;


import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;



/**
 * Interfaz de usuario
 */
public class VistaConteo extends JFrame{
    private JLabel lblRuta ;
    private JTextField tbfRuta;
    private JButton btnCalcular;
    
    /**
     * Construye la interfaz de usuario
     */
    public VistaConteo(){
        setTitle("PSP - Universidad de los Andes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,1));
        setSize(600,200);
        lblRuta = new JLabel("Por favor ingrese la ruta del archivo que desea cargar.");
        tbfRuta = new JTextField();
        btnCalcular = new JButton("Calcular");
        add(lblRuta);
        add(tbfRuta);
        add(btnCalcular);     
        setVisible(true);
    }

    /**
     * Obtiene la ruta del archivo de texto plano
     * @return 
     */
    public String obtenerRuta() {
        return tbfRuta.getText();
    }

    /**
     * Muestra el resultado
     * @param resultado corresponde al resultado de la media y la desviación estandar
     */
    public void setLblResultado(String resultado) {
        JOptionPane.showMessageDialog(this, resultado);
        System.exit(HIDE_ON_CLOSE);
    }
    
    /**
     * Envento del botón calcular que será manejado en el controlador
     * @param calcular 
     */
    public void addCalcular(ActionListener calcular){
        btnCalcular.addActionListener(calcular);
    }

    /**
     * Muestra mensaje de error
     * @param mensaje es el mensaje que va a mostrarse
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
        System.exit(HIDE_ON_CLOSE);
    }
}
