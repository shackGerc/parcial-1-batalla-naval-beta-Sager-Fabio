package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testEquals() {
        // TODO: Probar este metodo publico
        Object s = new Ship().setPosition(new Position(0, 1));
        boolean resultado = s.equals(new Ship().setPosition(new Position(0 ,1)));
        assertTrue(resultado);
    }
}