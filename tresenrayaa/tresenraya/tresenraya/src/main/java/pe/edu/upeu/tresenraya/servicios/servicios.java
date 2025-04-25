package pe.edu.upeu.tresenraya.servicios;

import pe.edu.upeu.tresenraya.modelo.modelo;
import java.util.List;


public interface servicios {
    // Guarda una nueva partida
    public void save(modelo to);

    // Obtiene todas las partidas
    public List<modelo> findAll();

    // Busca una partida por su posición
    public modelo findById(int index);

    // Actualiza una partida existente
    public void update(modelo to, int index);

    // Elimina una partida
    public void delete(modelo to);
    public void deleteById(int index);
}
