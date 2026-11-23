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
    private static final Scanner leer = new Scanner (System.in);
    
    private static final DateTimeFormatter FORMATO_FECHA = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.STRICT); 
    
    private static final DateTimeFormatter FORMATO_FECHA_HORA=
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withResolverStyle(ResolverStyle.STRICT); 
    
    private ConsolaUtils (){
    
    }
    
    public static int leerEntero (String mensaje){
        while(true){
            System.out.println(mensaje);
            String entrada= leer.nextLine().trim();
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
            String entrada= leer.nextLine().trim().replace(',', '.');
            if(!entrada.matches("-?\\d+(\\.\\d+)?")){
                System.out.println("Escribe un número decimal valido ");
                continue;
            }
            try{
                return Double.parseDouble(entrada);
             
            }catch (NumberFormatException e){
                System.out.println("Escribe un número decimal valido");
            }
        }
    }
    
    public static double leerDecimal (String mensaje, double minimo, double maximo){
        while(true){
            double valor= leerDecimal(mensaje);
            if( valor >= minimo && valor <=maximo){
                return valor; 
                
            }
            System.out.println(" El número debe estar entre "+ minimo +" y "+ maximo);
        }
    }
    
    
    public static String leerTexto(String mensaje, int maxLongitud) {
        while (true) {
            System.out.println(mensaje);
            String entrada = leer.nextLine().trim();
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
            System.out.println(mensaje + "(dd/MM/yyyy): ");
            String entrada= leer.nextLine().trim();
            try{
                return LocalDate.parse (entrada, FORMATO_FECHA);
            }catch (DateTimeParseException e){
                System.out.println("Fecha incorrecta");
            }
        }
    }
    
    public static LocalDateTime leerFechaHora (String mensaje){
        while (true){
            System.out.println(mensaje+ " (dd/MM/yyyy HH:mm): ");
            String entrada = leer.nextLine().trim();
            try{
                return LocalDateTime.parse(entrada, FORMATO_FECHA_HORA);
                
            }catch (DateTimeParseException e){
                System.out.println("Fecha o hora incorrectas");
            }
        }
    }
    
    public static String leerTextoOpcional(String mensaje, int maxLongitud) {
        while(true) {
            System.out.println(mensaje);
            String entrada = leer.nextLine().trim();
            if (entrada.isEmpty()) {
                return "";
            } else if (entrada.length() > maxLongitud) {
                System.out.println("El número máximo de caracteres es: " + maxLongitud);
            } else {
                return entrada;
            }
        }
    }

    public static Integer leerEnteroOpcional(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.println(mensaje);
            String entrada = leer.nextLine().trim();
            if (entrada.isEmpty()) {
                return null; 
            }
            try {
                int valor = Integer.parseInt(entrada);
                if(valor >= minimo && valor <= maximo) {
                    return valor;
                }
                System.out.println("Error: el número debe estar entre " + minimo + " y " + maximo + ".");
                
            }catch(NumberFormatException e) {
                System.out.println("Escribe un número entero o presiona Enter para mantener el actual.");
            }
        }
    }
    
    public static Double leerDecimalOpcional(String mensaje, double minimo, double maximo) {
        while (true) {
            System.out.println(mensaje);
            String entrada = leer.nextLine().trim().replace(',', '.');
            if (entrada.isEmpty()) {
                return null;
            }
            if (!entrada.matches("-?\\d+(\\.\\d+)?")) {
                System.out.println("Escribe un número decimal válido o presiona Enter para mantener el actual.");
                continue;
            }
            try {
                double valor = Double.parseDouble(entrada);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
                System.out.println("El número debe estar entre " + minimo + " y " + maximo + ".");
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número decimal válido.");
            }
        }
    }
    
    public static LocalDate leerFechaOpcional(String mensaje) {
        while(true) {
            System.out.println(mensaje + "(dd/MM/yyyy o ENTER para mantener): ");
            String entrada = leer.nextLine().trim();
            if (entrada.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(entrada, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha incorrecta. Usa el formato dd/MM/yyyy.");
            }
        }
    }
    
    public static LocalDateTime leerFechaHoraOpcional(String mensaje) {
        while (true) {
            System.out.println(mensaje + "(dd/MM/yyyy HH:mm o ENTER para mantener): ");
            String entrada = leer.nextLine().trim();
            if (entrada.isEmpty()) {
                return null;
            }
            try {
                return LocalDateTime.parse(entrada, FORMATO_FECHA_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha u hora incorrectas. Usa el formato dd/MM/yyyy HH:mm.");
            }
        }
    }
    
}
