package pe.edu.upeu.tresenraya.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.tresenraya.modelo.modelo;
import pe.edu.upeu.tresenraya.servicios.servicios;

@Controller
public class control {

    // Conexión con la clase que guarda los datos de las partidas
    @Autowired
    private servicios juegoService;

    // Variables para guardar los nombres de los jugadores
    private String jugador1;
    private String jugador2;

    // Lleva la cuenta de cuántas partidas se han jugado
    private int numeroPartida = 1;

    // Controla de quién es el turno (true = jugador1, false = jugador2)
    private boolean circulo = true;

    // Guarda la partida que se está jugando actualmente
    private modelo partidaActual;

    // Elementos de la pantalla (conectados al FXML):

    // Campos de texto para escribir los nombres
    @FXML private TextField txt_jugador1;
    @FXML private TextField txt_jugador2;

    // Botones principales
    @FXML private Button btn_iniciar;
    @FXML private Button btn_anular;

    // Etiquetas para mostrar información
    @FXML private Label label_turno;
    @FXML private Label label_aviso;

    // Los 9 botones del tablero (3 filas x 3 columnas)
    @FXML private Button btn1_1, btn1_2, btn1_3;
    @FXML private Button btn2_1, btn2_2, btn2_3;
    @FXML private Button btn3_1, btn3_2, btn3_3;

    // Tabla que muestra el historial de partidas
    @FXML private TableView<modelo> tableView;

    // Columnas de la tabla
    @FXML private TableColumn<modelo, String> partidox;
    @FXML private TableColumn<modelo, String> jugador1x;
    @FXML private TableColumn<modelo, String> jugador2x;
    @FXML private TableColumn<modelo, String> ganadorx;
    @FXML private TableColumn<modelo, Number> puntuacionx;
    @FXML private TableColumn<modelo, String> estadox;

    // Método que se ejecuta al iniciar la aplicación
    @FXML
    private void initialize() {
        // Configura cómo se muestran los datos en la tabla
        configurarTabla();

        // Desactiva los botones del juego al inicio
        desactivarBotonesJuego(true);

        // Carga partidas anteriores si existen
        cargarPartidasAnteriores();

        // Pone los números en los botones del juego
        configurarEstilosInicialesBotones();
    }

    // Configura las columnas de la tabla de resultados
    private void configurarTabla() {
        // Cada columna se conecta con una propiedad del modelo
        partidox.setCellValueFactory(cellData -> cellData.getValue().partidoProperty());
        jugador1x.setCellValueFactory(cellData -> cellData.getValue().jugador1Property());
        jugador2x.setCellValueFactory(cellData -> cellData.getValue().jugador2Property());
        ganadorx.setCellValueFactory(cellData -> cellData.getValue().ganadorProperty());
        puntuacionx.setCellValueFactory(cellData -> cellData.getValue().puntosProperty());
        estadox.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
    }

    // Carga las partidas guardadas
    private void cargarPartidasAnteriores() {
        // Obtiene todas las partidas del servicio
        tableView.setItems(FXCollections.observableArrayList(juegoService.findAll()));

        // Si hay partidas, continúa la numeración
        if (!tableView.getItems().isEmpty()) {
            numeroPartida = tableView.getItems().size() + 1;
        }
    }

    // Pone los números 1-9 en los botones del juego
    private void configurarEstilosInicialesBotones() {
        Button[] botones = {btn1_1, btn1_2, btn1_3, btn2_1, btn2_2, btn2_3, btn3_1, btn3_2, btn3_3};
        for (int i = 0; i < botones.length; i++) {
            // Pone números del 1 al 9
            botones[i].setText(String.valueOf(i + 1));

            // Hace el texto transparente (no se ven los números)
            botones[i].setStyle("-fx-text-fill: transparent;");
        }
    }

    // Maneja los clics en los botones
    @FXML
    private void controlClick(ActionEvent event) {
        // Detecta qué botón se presionó
        Button boton = (Button) event.getSource();

        // Decide qué hacer según el botón
        switch (boton.getId()) {
            case "btn_iniciar":
                iniciarPartida();
                break;
            case "btn_anular":
                anularPartida();
                break;
            default:
                manejarMovimiento(boton); // Si fue un botón del juego
                break;
        }
    }

    // Inicia una nueva partida
    private void iniciarPartida() {
        // Obtiene los nombres de los jugadores
        jugador1 = txt_jugador1.getText().trim();
        jugador2 = txt_jugador2.getText().trim();

        // Verifica que los nombres sean válidos
        if (validarJugadores()) {
            // Limpia el tablero
            resetearTableroCompleto();

            // Crea nueva partida
            partidaActual = new modelo(
                    "Partida " + numeroPartida++,
                    jugador1,
                    jugador2,
                    "",
                    0,
                    "Jugando"
            );

            // Guarda la partida
            juegoService.save(partidaActual);
            tableView.getItems().add(partidaActual);

            // Prepara la interfaz
            desactivarBotonesJuego(false);
            btn_iniciar.setDisable(true);
            label_turno.setText("Turno: " + jugador1);
            label_aviso.setText("");
            circulo = true;
        } else {
            // Muestra error si los nombres no son válidos
            mostrarErrorJugadores();
        }
    }

    // Verifica que los nombres sean válidos
    private boolean validarJugadores() {
        return !jugador1.isEmpty() &&
                !jugador2.isEmpty() &&
                !jugador1.equals(jugador2);
    }

    // Muestra mensaje de error
    private void mostrarErrorJugadores() {
        label_aviso.setText("INGRESA NOMBRES VÁLIDOS");
        label_aviso.setStyle("-fx-text-fill: white; -fx-background-color: red;");
    }

    // Limpia el tablero completamente
    private void resetearTableroCompleto() {
        Button[] botones = {btn1_1, btn1_2, btn1_3, btn2_1, btn2_2, btn2_3, btn3_1, btn3_2, btn3_3};
        for (int i = 0; i < botones.length; i++) {
            // Vuelve a poner los números 1-9
            botones[i].setText(String.valueOf(i + 1));

            // Hace el texto transparente
            botones[i].setStyle("-fx-text-fill: transparent;");

            // Reactiva los botones
            botones[i].setDisable(false);
        }
    }

    // Anula la partida actual
    private void anularPartida() {
        // Si hay una partida en curso
        if (partidaActual != null && "Jugando".equals(partidaActual.getEstado())) {
            // La marca como anulada
            partidaActual.setGanador("Anulado");
            partidaActual.setPuntos(0);
            partidaActual.setEstado("Anulado");

            // Actualiza en los datos
            juegoService.update(partidaActual, tableView.getItems().size() - 1);
            tableView.refresh();
        }

        // Vuelve a la pantalla inicial
        resetearInterfaz();
    }

    // Restaura la interfaz al estado inicial
    private void resetearInterfaz() {
        // Desactiva los botones del juego
        desactivarBotonesJuego(true);

        // Reactiva el botón de iniciar
        btn_iniciar.setDisable(false);

        // Limpia el turno
        label_turno.setText("");
    }

    // Maneja los movimientos en el tablero
    private void manejarMovimiento(Button boton) {
        // Verifica que el botón esté vacío (tenga un número)
        if (boton.getText().matches("[1-9]")) {
            // Jugador 1 usa "O" (rojo), Jugador 2 usa "X" (azul)
            String simbolo = circulo ? "O" : "X";
            String color = circulo ? "red" : "blue";

            // Cambia el texto y color del botón
            boton.setText(simbolo);
            boton.setStyle("-fx-font-size: 36px; -fx-text-fill: " + color + ";");

            // Cambia el turno
            circulo = !circulo;
            label_turno.setText("Turno: " + (circulo ? jugador1 : jugador2));

            // Verifica si hay ganador o empate
            verificarResultado();
        }
    }

    // Verifica el estado del juego
    private void verificarResultado() {
        if (hayGanador()) {
            // Determina el ganador
            String ganador = circulo ? jugador2 : jugador1;

            // Finaliza la partida
            finalizarPartida(ganador + " Gana", 1);
        } else if (esEmpate()) {
            finalizarPartida("Empate", 0);
        }
    }

    // Verifica si hay tres en raya
    private boolean hayGanador() {
        // Verifica filas
        if (checkLine(btn1_1, btn1_2, btn1_3)) return true;
        if (checkLine(btn2_1, btn2_2, btn2_3)) return true;
        if (checkLine(btn3_1, btn3_2, btn3_3)) return true;

        // Verifica columnas
        if (checkLine(btn1_1, btn2_1, btn3_1)) return true;
        if (checkLine(btn1_2, btn2_2, btn3_2)) return true;
        if (checkLine(btn1_3, btn2_3, btn3_3)) return true;

        // Verifica diagonales
        if (checkLine(btn1_1, btn2_2, btn3_3)) return true;
        return checkLine(btn1_3, btn2_2, btn3_1);
    }

    // Verifica si tres botones tienen el mismo símbolo
    private boolean checkLine(Button b1, Button b2, Button b3) {
        return !b1.getText().matches("[1-9]") &&
                b1.getText().equals(b2.getText()) &&
                b2.getText().equals(b3.getText());
    }

    // Verifica si todos los botones están ocupados
    private boolean esEmpate() {
        return !btn1_1.getText().matches("[1-9]") &&
                !btn1_2.getText().matches("[1-9]") &&
                !btn1_3.getText().matches("[1-9]") &&
                !btn2_1.getText().matches("[1-9]") &&
                !btn2_2.getText().matches("[1-9]") &&
                !btn2_3.getText().matches("[1-9]") &&
                !btn3_1.getText().matches("[1-9]") &&
                !btn3_2.getText().matches("[1-9]") &&
                !btn3_3.getText().matches("[1-9]");
    }

    // Finaliza la partida
    private void finalizarPartida(String resultado, int puntos) {
        if (partidaActual != null) {
            // Actualiza los datos de la partida
            partidaActual.setGanador(resultado);
            partidaActual.setPuntos(puntos);
            partidaActual.setEstado("Terminado");

            // Guarda los cambios
            juegoService.update(partidaActual, tableView.getItems().size() - 1);
            tableView.refresh();

            // Muestra el resultado
            label_turno.setText(resultado);

            // Desactiva los botones del juego
            desactivarBotonesJuego(true);

            // Permite iniciar nueva partida
            btn_iniciar.setDisable(false);
        }
    }

    // Activa/desactiva los botones del juego
    private void desactivarBotonesJuego(boolean desactivar) {
        Button[] botonesJuego = {btn1_1, btn1_2, btn1_3, btn2_1, btn2_2, btn2_3, btn3_1, btn3_2, btn3_3};
        for (Button boton : botonesJuego) {
            boton.setDisable(desactivar);
        }
    }
}