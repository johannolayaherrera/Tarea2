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

import java.util.ArrayList;

/**
 * Representa un programa Java
 */
public class Programa {
    private ArrayList<Clase> clases = new ArrayList<Clase>();
    
    /**
     * Agregar una clase a la clase programa
     * @param nueva clase que va a ser agregada
     */
    public void agregarClase(Clase nueva){
        clases.add(nueva);
    }
    /**
     * Obtiene las clases del programa
     * @return lista de las clases del programa
     */
    public ArrayList<Clase> darClases(){
        return clases;
    }
}

