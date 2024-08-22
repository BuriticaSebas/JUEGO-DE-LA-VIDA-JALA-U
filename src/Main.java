import java.util.Arrays;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        int valorWidth = capturarAncho(args);
        int valorH = capturarAlto(args);
        int valorG = capturarGeneracion(args);
        int valorV = capturarVelocidad(args);
        String[] patron = capturarPatron(args);
        int valorVe = capturarVecindario(args);

        // Mostrar el valor de las variables y controlando los errores.
        System.out.println("w= " + mostrarError(valorWidth));
        System.out.println("h= " + mostrarError(valorH));
        System.out.println("g= " + mostrarError(valorG));
        System.out.println("s= " + mostrarError(valorV));
        System.out.println("p= " + Arrays.toString(patron));
        System.out.println("n=" + mostrarError(valorVe));




        //Se imprimiran los resultados solo si no hay error
        if (    valorWidth != 24847 && valorWidth != 34857 &&
                valorH != 24847 && valorH != 34857 &&
                valorG != 24847 && valorG != 34857 &&
                valorV != 24847 && valorV != 34857  )
        {


            int[][] tableroListo = generarPatron(patron, valorWidth, valorH);
            System.out.println("Este es el tablero inicial :");
            mostrarPatron(tableroListo);

            System.out.println(" ");
            int generacionActual = 1;
            while (generacionActual <= valorG) {
                System.out.println("Generación " + generacionActual + ":");
                System.out.println(" ");
                int [][] tableroActualizado = aplicarLasReglas(tableroListo,valorWidth,valorH,valorVe);
                System.out.println("Este es el tablero en la siguiente generacion: ");
                mostrarPatron(tableroActualizado);
                System.out.println(" ");

                try {
                    Thread.sleep(valorV);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                tableroListo = aplicarLasReglas(tableroActualizado,valorWidth,valorH,valorVe);
                if (generacionActual >= 9){
                    invertirCelulas(tableroListo,valorWidth,valorH);
                }


                generacionActual++;
            }



        }else {
            System.out.println("Hubo error en los parametros");;
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
            if(!height.startsWith("h=")){
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
            if(!generation.startsWith("g=")){
                return 24847;
            }


            String valueG = generation.replaceAll("[g=]", "");
            return Integer.parseInt(valueG);

        }
        catch (NumberFormatException error) {
            return 24847; // O usar otro código de error si es necesario

        }
        catch (ArrayIndexOutOfBoundsException error) {
            return 34857; // O usar otro código de error si es necesario
        }
    }


    public static int capturarVelocidad(String[] args) {
        try {
            String velocidad = args[3];

            if(!velocidad.startsWith("s=")){
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
            if(!vecindario.startsWith("n=")){
                return 24847;
            }

            String valueVe = vecindario.replaceAll("[n=]", "");
            int vecindarioNumero = Integer.parseInt(valueVe);

            return vecindarioNumero;
        }catch (NumberFormatException error) {
            return 3; // O usar otro código de error si es necesario
        } catch (ArrayIndexOutOfBoundsException error) {
            return 3; // O usar otro código de error si es necesario
        }
    }


    public static String[] capturarPatron(String[] args) {
        try {
            String patronString = args[4];
            if (!patronString.startsWith("p=")){
                return new String[]{"Formato Invalido"};
            }

            String patronStringlimpiado = limpiarDatos(patronString);
            if (patronStringlimpiado.equals("rnd")){
                String[] patronrnd = new String[]{patronStringlimpiado};
                return patronrnd;
            }

            return patronStringlimpiado.split("#");
        } catch (ArrayIndexOutOfBoundsException error) {
            return new String[]{"Error"}; // O retorna un array vacío o con un mensaje de error
        }
    }


    public static int[][] generarPatron(String[] patrones, int fila, int columna) {
        if (patrones.length == 1 && patrones[0].equals("rnd")) {
            // Si el patrón es "rnd", generar y mostrar un patrón aleatorio
            return PatronRandom(fila, columna);

        }

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
        return tablero;
    }


    public static int [][] PatronRandom(int fila, int columna){
        Random valoresRandom = new Random();
        int [][] matrizRandom = new int[fila][columna];
        for (int i = 0;i<matrizRandom.length;i++){
            for(int j= 0;j<matrizRandom[i].length;j++){
                matrizRandom[i][j] = valoresRandom.nextInt(2);
            }
        }
        return matrizRandom;
    }


    public static void mostrarPatron(int [][] patronaImprimir){
        for(int [] filadelPatron : patronaImprimir){
            System.out.println(Arrays.toString(filadelPatron));
        }

    }




    public static int  contadorVecinos(int vecindario, int x, int y, int[][] tablerodeJuego, int anchoTablero, int altoTablero) {
        int contador = 0;
        int[][] coordenadasDeVecindad;

        switch (vecindario) {
            case 1:
                coordenadasDeVecindad = new int[][]{
                        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
                };
                for (int[] coordenada : coordenadasDeVecindad) {
                    int nuevaCoordenadaX = x + coordenada[0];
                    int nuevaCoordenadaY = y + coordenada[1];

                    if (nuevaCoordenadaX >= 0 && nuevaCoordenadaX < altoTablero && nuevaCoordenadaY >= 0 && nuevaCoordenadaY < anchoTablero) {
                        contador += tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY];
                        //System.out.println("aca voy vecindario 1" + contador + tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY]);
                    }
                }

                break;
            case 2:
                coordenadasDeVecindad = new int[][]{
                        {-1, -1}, {-1, 0}, {0, -1},{0,1},{1, 0}, {1, 1}
                };
                int contadorCoordenada = 0;
                for (int[] coordenada : coordenadasDeVecindad) {

                    int nuevaCoordenadaX = x + coordenada[0];
                    int nuevaCoordenadaY = y + coordenada[1];

                    if (nuevaCoordenadaX >= 0 && nuevaCoordenadaX < altoTablero && nuevaCoordenadaY >= 0 && nuevaCoordenadaY < anchoTablero) {
                        contador += tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY];
                        //System.out.println("aca voy vecindario 2:" + "Este es el contador"+ contador+ "Esta es la posicion: "+  tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY]);

                    }
                    contadorCoordenada++;
                    //System.out.println(" ");
                    // System.out.println("Esta es la iteracion: "+ contadorCoordenada);
                }

                break;
            case 3:
                coordenadasDeVecindad = new int[][]{
                        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
                };
                for (int[] coordenada : coordenadasDeVecindad) {
                    int nuevaCoordenadaX = x + coordenada[0];
                    int nuevaCoordenadaY = y + coordenada[1];


                    if (nuevaCoordenadaX >= 0 && nuevaCoordenadaX < altoTablero && nuevaCoordenadaY >= 0 && nuevaCoordenadaY < anchoTablero) {
                        contador += tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY];

                        // System.out.println("aca voy vecindario 3" + contador + tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY]);
                    }
                }
                break;
            case 4:
                coordenadasDeVecindad = new int[][]{
                        {1, -1}, {-1, 1}, {1, -1}, {1, 1}
                };

                for (int[] coordenada : coordenadasDeVecindad) {
                    int nuevaCoordenadaX = x + coordenada[0];
                    int nuevaCoordenadaY = y + coordenada[1];

                    if (nuevaCoordenadaX >= 0 && nuevaCoordenadaX < altoTablero && nuevaCoordenadaY >= 0 && nuevaCoordenadaY < anchoTablero) {
                        contador += tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY];
                        //System.out.println("aca voy vecindario 4:  " + contador + tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY]);

                    }
                }
                break;
            case 5:
                coordenadasDeVecindad = new int[][]{
                        {-1, -1}, {-1, 0}, {-1, 1}, {1, -1}, {1, 0}, {1, 1}
                };
                for (int[] coordenada : coordenadasDeVecindad) {
                    int nuevaCoordenadaX = x + coordenada[0];
                    int nuevaCoordenadaY = y + coordenada[1];

                    if (nuevaCoordenadaX >= 0 && nuevaCoordenadaX < altoTablero && nuevaCoordenadaY >= 0 && nuevaCoordenadaY < anchoTablero) {
                        contador += tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY];
                        //System.out.println("aca voy vecindario 5" + contador + tablerodeJuego[nuevaCoordenadaX][nuevaCoordenadaY]);

                    }
                }
                break;
            default:
                System.out.println("Vecindario no reconocido.");
                break;
        }

        return contador;
    }

    public static int[][] aplicarLasReglas(int[][] tableroDeJuego, int anchoTablero, int altoTablero, int vecindario) {
        int[][] tableroActualizado = new int[altoTablero][anchoTablero];

        for (int i = 0; i < altoTablero; i++) {
            for ( int j = 0 ; j < anchoTablero; j++) {
                int vecinosVivos = contadorVecinos(vecindario, i, j, tableroDeJuego, anchoTablero, altoTablero);

                if (tableroDeJuego[i][j] == 1) { // Célula viva
                    if (vecinosVivos < 2 || vecinosVivos > 3) {
                        tableroActualizado[i][j] = 0; // Muere
                    } else {
                        tableroActualizado[i][j] = 1; // Sobrevive
                    }
                } else { // Célula muerta
                    if (vecinosVivos == 3) {
                        tableroActualizado[i][j] = 1; // Nace
                    } else {
                        tableroActualizado[i][j] = 0; // Permanece muerta
                    }
                }
            }

        }

        // Actualizar el tablero original con el tablero actualizado
        for (int i = 0; i < altoTablero; i++) {
            for (int j = 0; j < anchoTablero; j++) {
                tableroDeJuego[i][j] = tableroActualizado[i][j];
            }
        }

        return tableroActualizado;
    }



    public static int[][] invertirCelulas (int [][] tableroDeJuego, int anchoTablero, int altoTablero){
        int [][] tableroInvertido = new int[altoTablero][anchoTablero];
        for (int i = 0; i < altoTablero; i++) {
            for ( int j = 0 ; j < anchoTablero; j++) {
                if (tableroDeJuego[i][j] == 1) { // Célula viva
                    tableroInvertido[i][j] = 0; // Muere
                } else if (tableroDeJuego[i][j]==0) {
                    tableroInvertido [i][j] = 1;
                }
            }

        }

        for(int [] filaInvertida: tableroInvertido){
            System.out.println(Arrays.toString(filaInvertida));
        }

        return tableroInvertido;

    }

}



