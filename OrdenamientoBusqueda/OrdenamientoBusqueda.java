import java.util.Scanner;

//Estrategia de aprendizaje: Algoritmos de ordenamiento y búsqueda
public class OrdenamientoBusqueda {

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Algoritmos de ordenamiento y búsqueda ===");

        //Se le pide al usuario el tamaño del arreglo y se llena con palabras.
        int tamano = leerNumero("Ingrese la cantidad de palabras: ");
        while (tamano <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            tamano = leerNumero("Ingrese la cantidad de palabras: ");
        }

        String[] palabras = new String[tamano];
        for (int i = 0; i < palabras.length; i++) {
            System.out.print("Palabra " + (i + 1) + ": ");
            palabras[i] = entrada.nextLine().trim();
        }

        //Se guarda una copia del arreglo original para poder probar otros algoritmos.
        String[] original = new String[tamano];
        for (int i = 0; i < tamano; i++) {
            original[i] = palabras[i];
        }

        int opcion = -1;
        while (opcion != 0) {
            System.out.println();
            System.out.print("Arreglo: ");
            imprimirArreglo(palabras);

            System.out.println("--- Menú de ordenamiento ---");
            System.out.println("1. Selección");
            System.out.println("2. Inserción");
            System.out.println("3. Burbuja");
            System.out.println("4. Mezcla");
            System.out.println("5. Rápido");
            System.out.println("0. Salir");
            opcion = leerNumero("Opción: ");

            if (opcion == 0) {
                break;
            } else if (opcion == 1) {
                ordenamientoSeleccion(palabras);
            } else if (opcion == 2) {
                ordenamientoInsercion(palabras);
            } else if (opcion == 3) {
                ordenamientoBurbuja(palabras);
            } else if (opcion == 4) {
                ordenamientoMezcla(palabras, 0, palabras.length - 1);
            } else if (opcion == 5) {
                ordenamientoRapido(palabras, 0, palabras.length - 1);
            } else {
                System.out.println("Opción inválida.");
                continue;
            }

            System.out.print("Arreglo ordenado: ");
            imprimirArreglo(palabras);

            //Una vez ordenado el arreglo, se permite buscar palabras.
            int opcionBusqueda = 1;
            while (opcionBusqueda == 1) {
                System.out.print("Palabra a buscar: ");
                String buscada = entrada.nextLine().trim();
                busquedaBinaria(palabras, buscada);
                opcionBusqueda = leerNumero("¿Desea buscar otra palabra? (1 = Sí, 2 = No): ");
            }

            //Se restaura el arreglo original.
            for (int i = 0; i < tamano; i++) {
                palabras[i] = original[i];
            }
        }

        System.out.println("Fin del programa.");
    }

    //Lee un número entero y vuelve a pedirlo si el usuario escribe algo inválido.
    public static int leerNumero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número.");
            }
        }
    }

    public static void imprimirArreglo(String[] palabras) {
        for (int i = 0; i < palabras.length; i++) {
            System.out.print(palabras[i] + " ");
        }
        System.out.println();
    }

    //Ordenamiento por selección
    //Complejidad de tiempo: O(n^2), porque tiene dos ciclos anidados que recorren el arreglo.
    public static void ordenamientoSeleccion(String[] palabras) {
        //Se declaran algunas variables de control necesarias.
        int n = palabras.length;
        int menor;
        String temp;

        //El ciclo externo se usa para recorrer todo el arreglo.
        for (int i = 0; i < n - 1; i++) {
            menor = i;

            //El ciclo interno recorre la porción restante del arreglo para buscar la palabra menor.
            //Se usa compareToIgnoreCase porque los Strings no se pueden comparar con < o >.
            for (int j = i + 1; j < n; j++) {
                if (palabras[j].compareToIgnoreCase(palabras[menor]) < 0) {
                    menor = j;
                }
            }

            //Se intercambia la casilla actual con la palabra menor encontrada.
            temp = palabras[i];
            palabras[i] = palabras[menor];
            palabras[menor] = temp;
        }
    }

    //Ordenamiento por inserción
    //Complejidad de tiempo: O(n^2), porque cada palabra puede tener que desplazar a todas las anteriores.
    //Si el arreglo ya está ordenado es O(n), porque no se desplaza nada.
    public static void ordenamientoInsercion(String[] palabras) {
        int n = palabras.length;

        //El ciclo externo toma cada palabra a partir de la segunda para insertarla en su lugar.
        for (int i = 1; i < n; i++) {
            String actual = palabras[i];
            int j = i - 1;

            //Se desplazan hacia la derecha todas las palabras mayores que la actual.
            while (j >= 0 && palabras[j].compareToIgnoreCase(actual) > 0) {
                palabras[j + 1] = palabras[j];
                j--;
            }

            //Se inserta la palabra en el espacio que quedó libre.
            palabras[j + 1] = actual;
        }
    }

    //Ordenamiento por burbuja
    //Complejidad de tiempo: O(n^2), porque compara cada pareja de vecinos en varias pasadas.
    public static void ordenamientoBurbuja(String[] palabras) {
        int n = palabras.length;
        String temp;

        //El ciclo externo controla las pasadas. En cada una, la palabra mayor queda al final.
        for (int i = 0; i < n - 1; i++) {
            boolean huboCambio = false;

            //El ciclo interno compara cada palabra con su vecina y las intercambia si están en desorden.
            for (int j = 0; j < n - 1 - i; j++) {
                if (palabras[j].compareToIgnoreCase(palabras[j + 1]) > 0) {
                    temp = palabras[j];
                    palabras[j] = palabras[j + 1];
                    palabras[j + 1] = temp;
                    huboCambio = true;
                }
            }

            //Si no hubo cambios en una pasada, el arreglo ya está ordenado.
            if (!huboCambio) {
                break;
            }
        }
    }

    //Ordenamiento por mezcla
    //Divide el arreglo a la mitad hasta tener partes de una palabra y luego las va mezclando ordenadas.
    //Complejidad de tiempo: O(n log n), porque el arreglo se divide log n veces y en cada nivel
    //se recorren las n palabras al mezclar.
    public static void ordenamientoMezcla(String[] palabras, int inicio, int fin) {
        //Si la parte tiene una sola palabra, ya está ordenada.
        if (inicio >= fin) {
            return;
        }

        //Se divide la parte en dos mitades y se ordena cada una.
        int medio = (inicio + fin) / 2;
        ordenamientoMezcla(palabras, inicio, medio);
        ordenamientoMezcla(palabras, medio + 1, fin);

        //Se mezclan las dos mitades ya ordenadas.
        mezclar(palabras, inicio, medio, fin);
    }

    public static void mezclar(String[] palabras, int inicio, int medio, int fin) {
        //Se crea un arreglo temporal para guardar el resultado de la mezcla.
        String[] temporal = new String[fin - inicio + 1];
        int izq = inicio;
        int der = medio + 1;
        int k = 0;

        //Se comparan las palabras de ambas mitades y se pasa la menor al arreglo temporal.
        while (izq <= medio && der <= fin) {
            if (palabras[izq].compareToIgnoreCase(palabras[der]) <= 0) {
                temporal[k] = palabras[izq];
                izq++;
            } else {
                temporal[k] = palabras[der];
                der++;
            }
            k++;
        }

        //Se copian las palabras que quedaron en la mitad izquierda.
        while (izq <= medio) {
            temporal[k] = palabras[izq];
            izq++;
            k++;
        }

        //Se copian las palabras que quedaron en la mitad derecha.
        while (der <= fin) {
            temporal[k] = palabras[der];
            der++;
            k++;
        }

        //Se pasa el resultado al arreglo original.
        for (int i = 0; i < temporal.length; i++) {
            palabras[inicio + i] = temporal[i];
        }
    }

    //Ordenamiento rápido
    //Se elige un pivote y se acomodan las palabras menores a su izquierda y las mayores a su derecha.
    //Luego se repite el proceso con cada lado.
    //Complejidad de tiempo: O(n log n) en promedio. En el peor caso es O(n^2), cuando el pivote
    //siempre queda en un extremo.
    public static void ordenamientoRapido(String[] palabras, int inicio, int fin) {
        //Si la parte tiene una sola palabra, ya está ordenada.
        if (inicio >= fin) {
            return;
        }

        //Se acomoda la parte alrededor del pivote y se obtiene su posición final.
        int posicionPivote = particionar(palabras, inicio, fin);

        //Se ordenan los lados izquierdo y derecho del pivote.
        ordenamientoRapido(palabras, inicio, posicionPivote - 1);
        ordenamientoRapido(palabras, posicionPivote + 1, fin);
    }

    public static int particionar(String[] palabras, int inicio, int fin) {
        String temp;

        //Se toma la palabra del centro como pivote y se pasa al final.
        int medio = (inicio + fin) / 2;
        temp = palabras[medio];
        palabras[medio] = palabras[fin];
        palabras[fin] = temp;
        String pivote = palabras[fin];

        //i indica dónde se va a colocar la siguiente palabra menor que el pivote.
        int i = inicio;
        for (int j = inicio; j < fin; j++) {
            if (palabras[j].compareToIgnoreCase(pivote) < 0) {
                temp = palabras[i];
                palabras[i] = palabras[j];
                palabras[j] = temp;
                i++;
            }
        }

        //Se coloca el pivote en su posición final.
        temp = palabras[i];
        palabras[i] = palabras[fin];
        palabras[fin] = temp;
        return i;
    }

    //Búsqueda binaria
    //Complejidad de tiempo: O(log n). En cada vuelta del ciclo se descarta la mitad del
    //arreglo, así que el número de comparaciones crece muy poco aunque el arreglo sea grande.
    public static void busquedaBinaria(String[] palabras, String buscada) {
        //Se declaran e inicializan las variables de control necesarias.
        int limiteMenor = 0;
        int limiteMayor = palabras.length - 1;

        //El ciclo se repite mientras los límites no se traslapen.
        while (limiteMenor <= limiteMayor) {
            //Se calcula el pivote en el centro del intervalo.
            int pivote = (limiteMenor + limiteMayor) / 2;
            int comparacion = buscada.compareToIgnoreCase(palabras[pivote]);

            //Si la palabra del pivote es la buscada, se indica la casilla y se termina.
            if (comparacion == 0) {
                System.out.println("La palabra se encontró en la casilla: " + pivote);
                return;
            }

            //Si la palabra buscada es menor, se descarta la mitad derecha; si es mayor, la izquierda.
            if (comparacion < 0) {
                limiteMayor = pivote - 1;
            } else {
                limiteMenor = pivote + 1;
            }
        }

        //Si los límites se traslaparon, la palabra no está en el arreglo.
        System.out.println("La palabra no se encontró.");
    }
}
