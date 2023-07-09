package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BattleShipMatchTest {

    @Test
    void testGetYesNoAnswer() {
        // TODO: Probar este metodo privado
        BattleShipMatch bsm = new BattleShipMatch();
        Optional<Method> metodo = ReflectionSupport.findMethod(BattleShipMatch.class, "getYesNoAnswer");
        boolean resultado = (boolean) ReflectionSupport.invokeMethod(metodo.get(), bsm, "n");
        assertTrue(resultado);
    }
}