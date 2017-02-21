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

import java.io.File;
import java.util.ArrayList;

/**
 * Clase que busca archivos de un directorio y extensión especifica
 * 
 */
public class BuscarArchivos {

    private ArrayList<String> lista = new ArrayList<String>();

    /**
     * Obtiene una lista con los archivos que fueron encontrados en el directorio especificado con 
     * la extensión que fue suminitrada
     * @param directorio Directorio donde se encuentran los archivos que se desean obtener
     * @param extension Extensión de los archivos que se desean obtener
     * @return Lista de los archivos encontrados
     */
    public ArrayList<String> obtenerArchivosJava(File directorio, String extension) {

        try {

            File[] archivos = directorio.listFiles();

            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    obtenerArchivosJava(archivo, extension);
                } 
                else if (obtenerExtension(archivo.getCanonicalPath()).equals(extension)) {
                    lista.add(archivo.getCanonicalPath());
                }
            }
            return lista;
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * Obtiene la extensión de un archivo
     * @param nombreArchivo es el nombre del archivo del cúal se quiere conocer la extensión
     * @return retorna la extensión del archivo
     */
    public String obtenerExtension(String nombreArchivo) {
        int index = nombreArchivo.lastIndexOf('.');
        if (index == -1) {
            return "";
        } 
        else {
            return nombreArchivo.substring(index + 1);
        }
    }

}
