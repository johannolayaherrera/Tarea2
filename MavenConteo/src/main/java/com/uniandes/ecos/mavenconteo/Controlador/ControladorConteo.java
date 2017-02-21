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
package com.uniandes.ecos.mavenconteo.Controlador;

import com.uniandes.ecos.mavenconteo.Modelo.*;
import com.uniandes.ecos.mavenconteo.Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Clase que controla la interfaz y el modelo. 
 * Es la encargada del proceso lógico de la aplicación
 */
public class ControladorConteo {
    private Programa programa = new Programa();
    private VistaConteo vista ;
    private BuscarArchivos buscador = new BuscarArchivos();
    private ArrayList<String> archivosJava = new ArrayList<String>();
    private ArrayList<String> prueba = new ArrayList<String>();
    private String[] palabrasReservadas = {"public","abstract","abstract","continue","for","new","switch","assert","default","goto","package","synchronized","do","if","private","this","break","implements","protected","throw","byte","else","import","public","throws","case","enum","instanceof","return","transient","catch","extends","try","final","interface","static","void","class","finally","strictfp","volatile","const","float","native","super","while"};
    
    /**
     * Constructor del controlador
     * @param vista Clase que crea la interfaz de usuario
     * @param programa Modelo de la aplicación
     */
    public ControladorConteo(VistaConteo vista, Programa programa) {
        this.vista = vista;
        this.programa = programa;
        this.vista.addCalcular(new calcular());
    }
    /**
     * Método que se ejecuta al iniciar la aplicación
     * @param args 
     */
    public static void main(String[] args) {
        // TODO code application logic here
        VistaConteo vista  = new VistaConteo();
        Programa modelo = new Programa();
        ControladorConteo controlador = new ControladorConteo(vista,modelo);
    }
    /**
     * Clase para manejar el evento de la vista desde el controlador
     * carga el modelo y la vista
     */
    class calcular implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                cargarModelo(vista.obtenerRuta(), programa);
                
                String mensaje = "==================================== \n";
                mensaje += "PSP1 - Universidad de los Andes - ECOS \n";
                mensaje += "El proyecto tiene las siguientes clases :\n";
                mensaje += "==================================== \n";
                
                for (Clase clase : programa.darClases()){
                    mensaje += "Clase : " + clase.darNombre() + "\n";
                    mensaje += "Cantidad de métodos : " + String.valueOf(clase.darCantidadMetodos()) + "\n";
                    mensaje += "Cantidad total de líneas (LOC} : " + String.valueOf(clase.darLineasCodigo()) + "\n";
                    mensaje += "==================================== \n";
                }
                
                int total = 0 ;
                for (Clase clase : programa.darClases()){
                    total += clase.darLineasCodigo();
                }
                mensaje += "Cantidad total de líneas del proyecto es : " + String.valueOf(total);
                vista.mostrarError(mensaje);
            } 
            catch (Exception ex) {
                vista.mostrarError("Ha ocurrido un error al cargar el archivo : " + ex.getMessage());
            }
        }
    }

    /**
     * Carga el modelo a partir del directorio de código fuente de un proyecto
     *
     * @param ruta ruta del directorio
     * @param modelo es el modelo que se va a cargar
     */
    private void cargarModelo(String ruta, Programa programa) throws Exception {
        File archivoRaiz = new File(ruta);
        archivosJava = buscador.obtenerArchivosJava(archivoRaiz,"java");

        for (String archivo : archivosJava) {
            Clase claseActual = new Clase();
            BufferedReader br = null;
            FileReader fr = null;

            try {

                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                String lineaActual;

                br = new BufferedReader(new FileReader(archivo));

                /* Se carga el programa según estandar de código y de conteo
                 * JavaTM  LOC counting standard y Estandar de Código
                 */
                while ((lineaActual = br.readLine()) != null) {
                    lineaActual = lineaActual.trim();
                    if (seCuenta(lineaActual)) {
                        prueba.add(lineaActual);
                        claseActual.sumarLineaCodigo();
                    }
                    if (esClase(lineaActual,claseActual.darNombre())) {
                        claseActual.ponerNombre(obtenerNombreClase(lineaActual));
                    }
                    if (esMetodo(lineaActual)) {
                        claseActual.sumarMetodo();
                    }
                }

            } 
            catch (IOException e) {
                vista.mostrarError("Ha ocurrido un error al cargar el archivo : " + e.getMessage());
            }
            //Se agrega la clase al programa
            programa.agregarClase(claseActual);
        }
    }
    
    /**
     * Determina si la línea se cuenta según el estandar de conteo
     * @param lineaActual Línea que se lee del archivo .java
     * @return boolean que indica si la línea se cuenta
     */
    private boolean seCuenta(String lineaActual) {
        if (!lineaActual.isEmpty()) {

            String palabraInicial = lineaActual.split(" ")[0];
            /* Si la palabra inicial finaliza con un ( entonces quiere decir que está unido con este simbolo y
            se debe separar */
            if ((palabraInicial.charAt(palabraInicial.length()-1)=='(') || (palabraInicial.charAt(palabraInicial.length()-1)=='{')){
                palabraInicial = palabraInicial.substring(0,palabraInicial.length()-1);
            }
            char ultimoCaracter = lineaActual.charAt(lineaActual.length() - 1);
            
            //Valida si la palabra inicial es reservada
            for (String palabra : palabrasReservadas) {
                if (palabra.equals(palabraInicial)) {
                    return true;
                }
            }
            if ((ultimoCaracter == ';') || (ultimoCaracter == ':')) {
                return true;
            }
            return false;
        } 
        else {
            return false;
        }
    }
    /**
     * Determina sí la línea que se está leyendo corresponde a una clase
     * @param lineaActual Línea que se lee del archivo .java
     * @param nombreClase Nombre que tiene actualmente la clase (Esto para el caso de que tenga clases internas)
     * @return boolean que indica si la línea corresponde a una clase
     */
    private boolean esClase(String lineaActual, String nombreClase) {
        String anterior = "";
        if (!tieneTexto(lineaActual)){
            for (String palabra : lineaActual.split(" ")){
                /*Si la palabra anterior es class y ya tiene una clase principal entonces
                 no se cuenta como una clase */
                if (anterior.equals("class")&& nombreClase.equals("")){
                    return true;
                }
                anterior =palabra;
            }
        }
        return false;
    }
    /**
     * Obtiene el nombre de la clase principal a partir de un archivo .java
     * @param lineaActual Línea que se lee del archivo .java
     * @return retorna el nombre de la clase
     */
    private String obtenerNombreClase(String lineaActual) {
        String anterior = "";
        if (!tieneTexto(lineaActual)){
            for (String palabra : lineaActual.split(" ")){
                if (anterior.equals("class")){
                    //Valida que el nombre de la clase no este pegado al corchete de apertura
                    if (palabra.charAt(palabra.length()-1) == '{'){
                        palabra = palabra.substring(0, palabra.length()-1);
                        return palabra;
                    }
                    return palabra;
                }
                anterior =palabra;
            }
        }
        return anterior;
    }

    /**
     * Determina si la línea que fue leida corresponde a un método
     * @param lineaActual Línea que se lee del archivo .java
     * @return boolean que indica si la línea corresponde a un método
     */
    private boolean esMetodo(String lineaActual) {
        if (!tieneTexto(lineaActual)) {
            if (lineaActual.contains("(") && (lineaActual.contains(")"))
                    && (lineaActual.charAt(lineaActual.length() - 1) == '{')) {
            /* Se toma que un método tiene dos palabras obligatorias una de acceso y 
            * otra de retorno además de los simbolos anteriormente descritos se valida
            * que se trata de un método */

                //Obtengo la cadena que está antes del parentesis de apertura
                int indice = 0;
                for (int i = 0; (i < lineaActual.length()-1) && (indice <= 0); i++) {
                    if (lineaActual.charAt(i) == '(') {
                        indice = i;
                    }
                }
                //Obtengo el texto antes del simbolo (
                lineaActual = lineaActual.substring(0, indice - 1);

                //Se verifica si tengo almenos dos palabras 
                String[] palabras = lineaActual.split(" ");
                if (palabras.length > 1) {
                    //Se verifica que la palabra antes del simbolo ( no sea una palabra reservada
                    for (String palabra : palabrasReservadas) {
                        if (palabras[palabras.length - 1].equals(palabra)) {
                            return false;
                        }
                    }
                    
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    /**
     * Determina si la línea contiene una cadena de texto
     * @param lineaActual es la línea del archivo .java
     * @return boolean que indica si la línea tiene una cadena de texto
     */
    private boolean tieneTexto(String lineaActual) {
        for (int i = 0 ; i< lineaActual.length();i++){
            if (lineaActual.charAt(i)=='"'){
                return true;
            }
        }
        return false;
    }
}
