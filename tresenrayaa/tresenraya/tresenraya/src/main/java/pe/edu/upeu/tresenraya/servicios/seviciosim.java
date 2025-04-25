package pe.edu.upeu.tresenraya.servicios;

import org.springframework.stereotype.Service;
import pe.edu.upeu.tresenraya.modelo.modelo;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de servicios.java
 * Usa un ArrayList para guardar las partidas (como si fuera una base de datos)
 */
@Service
public class seviciosim implements servicios {
    // Aquí se guardan todas las partidas
    List<modelo> datos = new ArrayList<>();

    // Guarda una nueva partida
    @Override
    public void save(modelo to) {
        datos.add(to);
    }

    // Devuelve todas las partidas guardadas
    @Override
    public List<modelo> findAll() {
        return datos;
    }

    // Busca una partida por su posición
    @Override
    public modelo findById(int index) {
        return datos.get(index);
    }

    // Actualiza una partida existente
    @Override
    public void update(modelo to, int index) {
        datos.set(index, to);
    }

    // Elimina una partida
    @Override
    public void delete(modelo to) {
        datos.remove(to);
    }

    @Override
    public void deleteById(int index) {
        datos.remove(index);
    }
}