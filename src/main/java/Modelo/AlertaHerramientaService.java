/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author paniu
 */
public class AlertaHerramientaService {

    private static final double LIMITE_ALTA = 1000.0;
    private static final double LIMITE_MEDIA = 800.0;

    public String calcularEstadoAlerta(double horasAcumuladas) {
        if (horasAcumuladas >= LIMITE_ALTA) {
            return "🔴 ALTA - MANTENIMIENTO URGENTE";
        } else if (horasAcumuladas >= LIMITE_MEDIA) {
            return "🟡 MEDIA - Revisión Próxima";
        } else {
            return "🟢 BAJA - Uso Normal";
        }
    }
}
