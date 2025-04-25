package pe.edu.upeu.tresenraya.modelo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class modelo {
    // Nombre o número de la partida (ej: "Partida 1")
    private final SimpleStringProperty partido;

    // Nombre del primer jugador
    private final SimpleStringProperty jugador1;

    // Nombre del segundo jugador
    private final SimpleStringProperty jugador2;

    // Nombre del ganador o "Empate"/"Anulado"
    private final SimpleStringProperty ganador;

    // Puntos (1 para ganador, 0 para empate/anulado)
    private final SimpleIntegerProperty puntos;

    // Estado: "Jugando", "Terminado", "Anulado"
    private final SimpleStringProperty estado;

    // Constructor para crear nueva partida
    public modelo(String partido, String jugador1, String jugador2,
                  String ganador, int puntos, String estado) {
        this.partido = new SimpleStringProperty(partido);
        this.jugador1 = new SimpleStringProperty(jugador1);
        this.jugador2 = new SimpleStringProperty(jugador2);
        this.ganador = new SimpleStringProperty(ganador);
        this.puntos = new SimpleIntegerProperty(puntos);
        this.estado = new SimpleStringProperty(estado);
    }

    // Métodos para JavaFX (permiten mostrar datos en la tabla)

    public SimpleStringProperty partidoProperty() { return partido; }
    public SimpleStringProperty jugador1Property() { return jugador1; }
    public SimpleStringProperty jugador2Property() { return jugador2; }
    public SimpleStringProperty ganadorProperty() { return ganador; }
    public SimpleIntegerProperty puntosProperty() { return puntos; }
    public SimpleStringProperty estadoProperty() { return estado; }

    // Métodos para obtener valores

    public String getPartido() { return partido.get(); }
    public String getJugador1() { return jugador1.get(); }
    public String getJugador2() { return jugador2.get(); }
    public String getGanador() { return ganador.get(); }
    public int getPuntos() { return puntos.get(); }
    public String getEstado() { return estado.get(); }

    // Métodos para cambiar valores

    public void setPartido(String partido) { this.partido.set(partido); }
    public void setJugador1(String jugador1) { this.jugador1.set(jugador1); }
    public void setJugador2(String jugador2) { this.jugador2.set(jugador2); }
    public void setGanador(String ganador) { this.ganador.set(ganador); }
    public void setPuntos(int puntos) { this.puntos.set(puntos); }
    public void setEstado(String estado) { this.estado.set(estado); }
}
