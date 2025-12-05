package Modelo;

public class AlertaService {

    // Constantes para la interpretación de los rangos de kilometraje
    private static final int UMBRAL_MEDIA = 6000;
    private static final int UMBRAL_ALTA = 9000;
    private static final int UMBRAL_CRITICO = 10000;

    /**
     * Calcula el estado de alerta basado en el kilometraje acumulado.
     * @param kilometraje El kilometraje acumulado desde el punto de referencia.
     * @return El estado de alerta (BAJA, MEDIA, ALTA) incluyendo un emoji para la vista.
     */
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
