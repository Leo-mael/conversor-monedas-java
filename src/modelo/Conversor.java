package modelo;

import java.util.HashMap;
import java.util.Map;

public class Conversor {
    private final Map<String, Double> conversion_rates = new HashMap<>();

    public double ToConvert(String moneda1){
        return conversion_rates.get(moneda1);
    }
    public double ToConvert2(String moneda1, String moneda2, double cantidad){
        double moneda11 = conversion_rates.get(moneda1);
        double moneda22 = conversion_rates.get(moneda2);
        double convertido = (cantidad*moneda22)/moneda11;
        return convertido;
    }
}
