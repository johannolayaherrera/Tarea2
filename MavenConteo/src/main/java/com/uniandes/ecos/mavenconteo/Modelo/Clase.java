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
package com.uniandes.ecos.mavenconteo.Modelo;

/**
 * Clase que representa una clase java
 */
public class Clase {
    
    private String nombre = "";
    private int cantidadMetodos = 0;
    private int lineasCodigo = 0;
    
    /**
     * Asigna el nombre de la clase
     * @param nuevoNombre es el nombre de la clase
     */
    public void ponerNombre(String nuevoNombre){
        nombre = nuevoNombre;
    }
    /**
     * Suma una unidad a la cantidad de métodos que tiene la clase
     */
    public void sumarMetodo(){
        cantidadMetodos++;
    }
    /**
     * Suma una unidad a la cantidad de líneas de código de la clase
     */
    public void sumarLineaCodigo(){
        lineasCodigo++;
    }
    /**
     * Obtiene el nombre de la clase
     * @return el nombre de la clase
     */
    public String darNombre(){
        return nombre;
    }
    /**
     * Obtiene la cantidad de métodos de la clase
     * @return cantidad de métodos de la clase
     */
    public int darCantidadMetodos(){
        return cantidadMetodos;
    }
    /**
     * Obtiene el número de líneas de código de la clase
     * @return número de líneas de código de la clase
     */
    public int darLineasCodigo(){
        return lineasCodigo;
    }
}
