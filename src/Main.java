import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Conversor;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        String API = ""; // <- coloca tu api key
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String direccion = "https://v6.exchangerate-api.com/v6/" +
                API + "/latest/PEN";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            //System.out.println(json);

            Conversor convertidor = gson.fromJson(json, Conversor.class);
            boolean cont = true;
            while(cont) {
                System.out.println("""
                    **********************************************
                    Sea bienvenido/a al Conversor de Moneda =]
                    
                    1) Dólar =>> Peso argentino
                    2) Peso argentino =>> Dólar
                    3) Dólar =>> Real brasileño
                    4) Real brasileño =>> Dólar
                    5) Dólar =>> Peso colombiano
                    6) Peso colombiano =>> Dólar
                    7) Ingresa moneda 1 =>> moneda 2
                    8) Salir
                    
                    Elija una opción válida:
                    **********************************************
                    """);

                var opcion = lectura.nextLine();
                if(opcion.equalsIgnoreCase("salir"))
                    break;

                switch (opcion){
                    case "1":
                        imprimir(convertidor, "USD", "ARS");
                        break;
                    case "2":
                        imprimir(convertidor, "ARS", "USD");
                        break;
                    case "3":
                        imprimir(convertidor, "USD", "BRL");
                        break;
                    case "4":
                        imprimir(convertidor, "BRL", "USD");
                        break;
                    case "5":
                        imprimir(convertidor, "USD", "COP");
                        break;
                    case "6":
                        imprimir(convertidor, "COP", "USD");
                        break;
                    case "7":
                        try {
                            Scanner scan = new Scanner(System.in);
                            System.out.println("Ingresar la primera moneda (Abreviado ej: USD, PEN, ARS): ");
                            String coin1 = scan.nextLine().toUpperCase();
                            System.out.println("Ingresar la segunda moneda (Abreviado ej: USD, PEN, ARS): ");
                            String coin2 = scan.nextLine().toUpperCase();
                            imprimir(convertidor, coin1, coin2);
                        }catch (Exception e){
                            System.out.println("Ingrese monedas existentes");
                        }
                        break;
                    case "8":
                        cont = false;
                        break;
                    default:
                        System.out.println("No es una opcion valida, vuelve a intentarlo");
                }

            }

        } catch (Exception e) {
            System.out.println("Ah ocurrido un error");
        }
    }
    public static void imprimir(Conversor convertidor, String moneda1, String moneda2){
        try {
            System.out.println("Ingresar monto: ");
            Scanner cin = new Scanner(System.in);
            double monto = cin.nextDouble();
            var valorFinal = convertidor.ToConvert2(moneda1, moneda2, monto);
            System.out.println("El valor de " + monto + " [" + moneda1 + "] corresponde al valor final de = >>> " + valorFinal + " [" + moneda2 + "]");
        }catch (Exception e){
            System.out.println("Por favor ingrese el monto en numeros");
        }
    }
}