/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;


public final class ConsolaUtils {
    private static final Scanner scaner= new Scanner (System.in);
    
    private static final DateTimeFormatter FORMATO_FECHA= 
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    
    private static final DateTimeFormatter FORMATO_FECHA_HORA=
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
    
    private ConsolaUtils (){
        
    }
    
    public static int leerEntero (String mensaje){
        while(true){
            System.out.println(mensaje);
            String entrada= scaner.nextLine().trim();
            try{
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e){
                System.out.println("Escribe un número entero");
                
            }
        }
    }
    
    public static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= minimo && valor <= maximo) {
                return valor;
            }
            System.out.println("Error: el número debe estar entre " + minimo + " y " + maximo + ".");
        }
        
    }
    
    public static double leerDecimal(String mensaje){
        while(true){
            System.out.println(mensaje);
            String entrada= scaner.nextLine().trim().replace(',', '.');
            if(!entrada.matches("-?\\d+(\\.\\d+)?")){
                System.out.println("Escribe un numero decimal vaido ");
                continue;
            }
            try{
                return Double.parseDouble(entrada);
             
            }catch (NumberFormatException e){
                System.out.println("Escribe un numero decimal valido");
            }
        }
    }
    
    public static double leerDecimal (String mensaje, double minimo, double maximo){
        while(true){
            double valor= leerDecimal(mensaje);
            if( valor >= minimo && valor <=maximo){
                return valor; 
                
            }
            System.out.println(" El numero debe estar entre "+ minimo +" y "+ maximo);
        }
    }
    
    public static String leerTexto(String mensaje) {
        return leerTexto(mensaje, Integer.MAX_VALUE);
    }
    
    public static String leerTexto(String mensaje, int maxLongitud) {
        while (true) {
            System.out.println(mensaje);
            String entrada = scaner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Ingrese un valor");
            } else if (entrada.length() > maxLongitud) {
                System.out.println("El número máximo de caracteres es: " + maxLongitud);
            } else {
                return entrada;
            }
        }
    }
    
    public static LocalDate leerFecha (String mensaje){
        while(true){
            System.out.println(mensaje + "(dd/MM/uuuu): ");
            String entrada= scaner.nextLine().trim();
            try{
                return LocalDate.parse (entrada, FORMATO_FECHA);
            }catch (DateTimeParseException e){
                System.out.println("Fecha incorrecta");
            }
        }
    }
    
    public static LocalDateTime leerFechaHora (String mensaje){
        while (true){
            System.out.println(mensaje+ " (dd/MM/uuuu HH:mm): ");
            String entrada = scaner.nextLine().trim();
            try{
                return LocalDateTime.parse(entrada, FORMATO_FECHA_HORA);
                
            }catch (DateTimeParseException e){
                System.out.println("Fecha o hora incorrectas");
            }
        }
    }
}


