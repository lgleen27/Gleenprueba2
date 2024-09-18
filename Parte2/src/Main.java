public class Main {
    public static void main(String[] args) {
        for (int i=0; i<5; i++) {
            int x=8;
            int valor;
            valor = x*i;
            System.out.println(valor);
            System.out.println("pene");
            System.out.println("klsdjfaklssdasdadj");
        }
        // Crear objeto de la clase AsmFileProcessor para procesar el archivo P1ASM.asm
        AsmFileProcessor procesador = new AsmFileProcessor("P1ASM.asm");

        if (!procesador.archivoAbierto()) {
            return;  // Si no se pudo abrir el archivo, salir con error
        }

        // Procesar el archivo
        procesador.procesarArchivo();
    }
}