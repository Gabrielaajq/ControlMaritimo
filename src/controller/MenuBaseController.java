package controller;

import java.util.ArrayList;
import java.util.List;
import utils.ConsolaUtils;

/**
 *  Esta clase de tipo abstraca, va a ayudar a la impresión en consola
 *  de manera dinámica (en transcurso del programa), y filtrar por
 *  controladores u opciones elegidas (NO modifica estrcuturas)
 */
public abstract class MenuBaseController implements MenuRol {
    
    private final String titulo;
    private final List<OpcionMenu> opciones = new ArrayList<>();

    public MenuBaseController(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Se registran opciones nuevas dentro del menú del rol
     * 
     * @param titulo = nombre descriptivo que se mostrará
     * @param controlador = la instancia del controlador asociado
     */
    protected void agregarOpcion(String titulo, CrudController controlador) {
        opciones.add(new OpcionMenu(titulo, controlador));
    }
    
    @Override
    public void iniciarMenu() {
        int opcionSalir = opciones.size() + 1;
        int opcion;

        do {
            System.out.println("\n=== " + titulo + " ===");
            for (int i = 0; i < opciones.size(); i++) {
                System.out.printf("%d. %s%n", (i + 1), opciones.get(i).getTitulo());
            }
            System.out.printf("%d. Cerrar sesión%n", opcionSalir);

            opcion = ConsolaUtils.leerEntero("Seleccione una opción: ", 1, opcionSalir);

            if (opcion >= 1 && opcion <= opciones.size()) {
                opciones.get(opcion - 1).getControlador().iniciarMenu();
            } else if (opcion == opcionSalir) {
                System.out.println("Sesión cerrada.");
            }
        } while (opcion != opcionSalir);
    }
    
}
