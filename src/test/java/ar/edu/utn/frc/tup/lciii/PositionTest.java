package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testEquals() {
        Ship s = new Ship(new Position(0, 1), ShipStatus.AFLOAT);
        boolean resultado = s.equals(new Ship(new Position(0 ,1), ShipStatus.AFLOAT));
        assertTrue(resultado);
    }
}