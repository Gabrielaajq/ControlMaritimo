package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 * Clase utilitaria con métodos estáticos para la lectura, 
 * conversión y validación de entradas por consola, 
 * evitando excepciones por formatos inválidos
 */
public final class ConsolaUtils {
    private static final Scanner leer = new Scanner (System.in);
    
    private static final DateTimeFormatter FORMATO_FECHA = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.STRICT); 
    
    private static final DateTimeFormatter FORMATO_FECHA_HORA=
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withResolverStyle(ResolverStyle.STRICT); 
    
    private ConsolaUtils (){
    
    }
    
    /**
     * Solicita y valida un número entero por consola
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @return Número entero válido ingresado
     */
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
    
    /**
     * Solicita un número entero dentro de un rango específico
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @param minimo Valor mínimo permitido
     * @param maximo Valor máximo permitido
     * @return Número entero validado dentro del rango
     */
    public static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= minimo && valor <= maximo) {
                return valor;
            }
            System.out.println("Error: el número debe estar entre " + minimo + " y " + maximo + ".");
        }
        
    }
    
    /**
     * Solicita y valida un número decimal, 
     * aceptando tanto punto como coma decimal
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @return Número de punto flotante válido
     */
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
    
    /**
     * Solicita un número decimal dentro de un rango específico
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @param minimo Valor mínimo permitido
     * @param maximo Valor máximo permitido
     * @return Número decimal validado dentro del rango
     */
    public static double leerDecimal (String mensaje, double minimo, double maximo){
        while(true){
            double valor= leerDecimal(mensaje);
            if( valor >= minimo && valor <= maximo){
                return valor; 
                
            }
            System.out.println(" El número debe estar entre "+ minimo +" y "+ maximo);
        }
    }
    
    /**
     * Solicita una cadena de texto no vacía 
     * con un límite máximo de caracteres
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @param maxLongitud Cantidad máxima de caracteres permitidos
     * @return Cadena limpia sin espacios iniciales ni finales
     */
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
    
    /**
     * Solicita y valida una fecha con formato {@code dd/MM/yyyy}
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @return Instancia {@link LocalDate} correspondiente 
     * a la fecha ingresada
     */
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
    
    /**
     * Solicita y valida una fecha con hora
     * en formato {@code dd/MM/yyyy HH:mm}
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @return Instancia {@link LocalDateTime} correspondiente a la captura
     */
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
    
    /**
     * Permite capturar una cadena opcional durante proceso de edición
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @param maxLongitud Cantidad máxima de caracteres permitidos
     * @return El nuevo texto ingresado, o cadena vacía si se presionó ENTER
     */
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
    
    /**
     * Permite capturar un entero opcional dentro de un
     * rango durante la edición.
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @param minimo Valor mínimo permitido
     * @param maximo Valor máximo permitido
     * @return El nuevo entero ingresado, o {@code null} si se presionó ENTER
     */
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
    
    /**
     * Permite capturar un número decimal opcional dentro
     * de un rango durante la edición
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @param minimo Valor mínimo permitido
     * @param maximo Valor máximo permitido
     * @return El nuevo valor decimal, o {@code null} si se presionó ENTER
     */
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
    
    /**
     * Permite capturar una fecha opcional en formato {@code dd/MM/yyyy}
     * durante la edición
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @return La nueva {@link LocalDate}, o {@code null} si se presionó ENTER
     */
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
    
    /**
     * Permite capturar una fecha y hora opcional
     * en formato {@code dd/MM/yyyy HH:mm}
     *
     * @param mensaje Texto indicativo que se muestra al usuario
     * @return La nueva {@link LocalDateTime}, o {@code null} si se presionó ENTER
     */
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
