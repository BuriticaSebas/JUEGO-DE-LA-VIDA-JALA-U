import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int valorWidth = capturarAncho(args);
        int valorH = capturarAlto(args);
        int valorG = capturarGeneracion(args);
        int valorV = capturarVelocidad(args);
        String[] patron = capturarPatron(args);
        int valorVe = capturarVecindario(args);



        // Mostrar resultados, manejando errores si es necesario
        System.out.println("w= " + mostrarError(valorWidth));
        System.out.println("h= " + mostrarError(valorH));
        System.out.println("g=500 " + mostrarError(valorG) );
        System.out.println("s= " +  mostrarError(valorV));
        System.out.println("p= "+ Arrays.toString(patron));
        System.out.println("n="+ mostrarError(valorVe));


        int contadorGeneracion = 0;

        while(contadorGeneracion <= valorG){
            if (valorWidth != 24847 && valorWidth != 34857 &&
                    valorH != 24847 && valorH != 34857 &&
                    valorG != 24847 && valorG != 34857 &&
                    valorV != 24847 && valorV != 34857) {
                System.out.println(" ");
                System.out.println("Esta es la generacion: "+ contadorGeneracion);
                System.out.println(" ");
                mostrarPatron(patron, valorWidth, valorH);
            }

            try {
                Thread.sleep(valorV);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            contadorGeneracion++;
        }




    }




    public static String mostrarError(int valor) {
        if (valor == 24847) {
            return "INVALIDO";
        } else if (valor == 34857) {
            return "NO PRESENTE";
        }
        return String.valueOf(valor); // Si no hay error, retorna el valor como String
    }

    public static String limpiarDatos(String datos) {
        return datos.replace("p=", "").replace("\"", "").replaceAll("\\s+", "");
    }

    public static int capturarAncho(String[] args) {
        try {
            String width = args[0];
            if(!width.startsWith("w=")){
                return 24847;
            }
            String valueW = width.replaceAll("[w=]", "");
            if (!valueW.equals("10") && !valueW.equals("20") && !valueW.equals("40") && !valueW.equals("80")) {
                throw new NumberFormatException("Valor de ancho no válido: " + valueW);
            }
            return Integer.parseInt(valueW);
        } catch (NumberFormatException error) {
            return 24847; // Valor especial para "INVALIDO"
        } catch (ArrayIndexOutOfBoundsException error) {
            return 34857; // Valor especial para "NO PRESENTE"
        }
    }

    public static int capturarAlto(String[] args) {
        try {
            String height = args[1];
            if(!height.startsWith("w=")){
                return 24847;
            }
            String valueH = height.replaceAll("[h=]", "");
            if (!valueH.equals("10") && !valueH.equals("20") && !valueH.equals("40")) {
                throw new NumberFormatException("Valor de ancho no válido: " + valueH);
            }

            return Integer.parseInt(valueH);
        } catch (NumberFormatException error) {
            return 24847; // O usar otro código de error si es necesario
        } catch (ArrayIndexOutOfBoundsException error) {
            return 34857; // O usar otro código de error si es necesario
        }
    }

    public static int capturarGeneracion(String[] args) {
        try {
            String generation = args[2];
            if(!generation.startsWith("w=")){
                return 24847;
            }
            String valueG = generation.replaceAll("[g=]", "");
            return Integer.parseInt(valueG);
        } catch (NumberFormatException error) {
            return 24847; // O usar otro código de error si es necesario
        } catch (ArrayIndexOutOfBoundsException error) {
            return 34857; // O usar otro código de error si es necesario
        }
    }

    public static int capturarVelocidad(String[] args) {
        try {
            String velocidad = args[3];
            if(!velocidad.startsWith("w=")){
                return 24847;
            }
            String valueV = velocidad.replaceAll("[s=]", "");
            return Integer.parseInt(valueV);
        } catch (NumberFormatException error) {
            return 24847; // O usar otro código de error si es necesario
        } catch (ArrayIndexOutOfBoundsException error) {
            return 34857; // O usar otro código de error si es necesario
        }
    }

    public static int capturarVecindario(String[] args) {
        try {
            String vecindario = args[5];
            if(!vecindario.startsWith("w=")){
                return 24847;
            }
            String valueVe = vecindario.replaceAll("[n=]", "");
            return Integer.parseInt(valueVe);
        }catch (NumberFormatException error) {
            return 3; // O usar otro código de error si es necesario
        } catch (ArrayIndexOutOfBoundsException error) {
            return 3; // O usar otro código de error si es necesario
        }
    }

    public static String[] capturarPatron(String[] args) {
        try {
            String patronString = args[4];
            if(!patronString.startsWith("p=")){
                return new String[]{"FORMATO INVALIDO"};
            }
            String wFormatStringLimpiado = limpiarDatos(patronString);

            return wFormatStringLimpiado.split("#");
        } catch (ArrayIndexOutOfBoundsException error) {
            return new String[]{"Error"}; // O retorna un array vacío o con un mensaje de error
        }
    }


    public static void mostrarPatron(String[] patrones, int fila, int columna) {
        int[][] tablero = new int[fila][columna];
        int contadorFilas = 0;
        int contadorColumnas = 0;
        for (String fil : patrones) {
            for (int i = 0; i < fil.length(); i++) {
                int valor = Integer.parseInt(fil.substring(i, i + 1));
                tablero[contadorFilas][contadorColumnas] = valor;
                contadorColumnas++;
            }
            contadorFilas++;
            contadorColumnas = 0;
        }
        for (int[] filaTablero : tablero) {
            System.out.println(Arrays.toString(filaTablero));
        }
    }
}


