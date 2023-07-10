package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BattleShipMatchTest {

    @Test
    void testGetYesNoAnswer() {
        BattleShipMatch bsm = new BattleShipMatch();
        Optional<Method> metodo = ReflectionSupport.findMethod(BattleShipMatch.class, "getYesNoAnswer",
                String.class);
        boolean resultado = false;
        if(metodo.isPresent())
            resultado = (boolean) ReflectionSupport.invokeMethod(metodo.get(), bsm, "y");
        assertTrue(resultado);
    }
}