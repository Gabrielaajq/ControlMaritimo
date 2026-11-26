package controlmaritimo;

import controller.SistemaController;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class ControlMaritimo {

    public static void main(String[] args) {
        
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        SistemaController prueba = new SistemaController();
        prueba.iniciar();
    }        
}