package Modelo;

public class AlertaService {

    private static final int UMBRAL_MEDIA = 6000;
    private static final int UMBRAL_ALTA = 9000;
    private static final int UMBRAL_CRITICO = 10000;

    public String calcularEstadoAlerta(double kilometraje) {
        if (kilometraje >= UMBRAL_CRITICO) {
            return "URGENTE/CRÍTICA 🔴";
        } else if (kilometraje >= UMBRAL_ALTA) {
            return "ALTA 🔴";
        } else if (kilometraje >= UMBRAL_MEDIA) {
            return "MEDIA 🟡";
        } else {
            return "BAJA 🟢";
        }
    }
}
