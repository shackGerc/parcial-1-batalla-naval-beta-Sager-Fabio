package ar.edu.utn.frc.tup.lciii;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Esta clase ofrece los metodos para controlar la ejecución de multiples partidas.
 *
 * Un Match es el resultado de la ejecución de la aplicacion y de
 * haber jugado multiples partidas por parte del usuario.
 *
 */
public class BattleShipMatch {

    /**
     * Expresion regular para validar respouestas yes/no
     */
    private static final String YES_NO_REGEX = "[yYnN]";

    /**
     * Scanner para capturar las entradas del usuario
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Este método muestra por pantalla un mensaje de bienvenida al juego de la Batalla Naval
     *
     * @see System#out
     */
    public void welcomeMessage() {
        System.out.println("Bienvenido al juego de la batalla naval!");
    }

    /**
     * Este método gestiona la creacion del usuario.
     * Pide por pantalla los datos requeridos para la creación del usuario
     * y captura las entradas del usuario.
     * Como resultado retorna el Player creado
     *
     * @see Player#Player()
     * @see Player#setPlayerName(String)
     *
     * @return un nuevo Player
     */
    public Player createNewPlayer() {
        Player player = new Player();
        System.out.println("Ingrese su nombre para empezar a jugar");
        player.setPlayerName(scanner.nextLine());
        return player;
    }

    /**
     * Este metodo gestiona la creacion del jugador app.
     * Como resultado retorna el Player creado
     *
     * @return un nuevo Player
     */
    public Player createAppPlayer() {
        return new Player("APP", 0, 0);
    }

    /**
     * Este metodo controla si el usuario quiere volver a jugar o no.
     * Pide por pantalla los datos requeridos para consultar al usuario si quiere volver a jugar
     * y captura las entradas del usuario.
     * Como resultado retorna la respuesta del usuario como un Boolean
     *
     * @see BattleShipMatch#continuePlaying()
     *
     * @return true si el usuario quiere volver a jugar, false si el usuario NO quiere volver a jugar
     */
    public Boolean wantPlayAgain() {
        Boolean answer = null;
        do {
            System.out.println("¿Quieres volver a jugar? (y/n)");
            String input = scanner.nextLine();
            answer = getYesNoAnswer(input);
        } while (answer == null);
        return answer;
    }

    /**
     * Este metodo propone al jugar abandonar la partida y captura
     * la respuesta del jugador.
     *
     * @see BattleShipMatch#wantPlayAgain()
     *
     * @return true si el jugador quiere seguir jugando, false si NO quiere.
     */
    public Boolean continuePlaying() {
        Boolean answer = null;
        do {
            System.out.println("¿Quieres continuar la partida? (y/n)");
            String input = scanner.nextLine();
            answer = getYesNoAnswer(input);
        } while (answer == null);
        return answer;
    }

    /**
     * Este metodo valida que el parametro de entrada input contenga y, Y, n o N.
     * Si el valor es alguno de esos datos, retorna el valor en forma de Boolean,
     * sino, imprime un error por pantalla y retorna null.
     *
     * @see Pattern#compile(String)
     * @see Pattern#matcher(CharSequence)
     * @see Matcher#matches()
     *
     * @param input el String a validar
     * @return true si input contiene y o Y, false si input contiene n o N, null para lo demas.
     */
    private static Boolean getYesNoAnswer(String input) {
        // TODO: Crear el objeto Pattern a partir de la expresion regular provista
        // TODO: Validar si input hace match con la expresion regular
        // TODO: Si la respuesta hace match, validar si fue yes (y o Y) o no (n o N)
        // TODO: Retornar true si fue yesy, o false si fue no.
        // TODO: Si la respuesta NO hace match, mostrar un mensaje de error al usuario y retornar null

        // TODO: Remember to replace the return statement with the correct object
        Pattern pattern = Pattern.compile(YES_NO_REGEX);
        if(pattern.matcher(input).matches()){
            if(input.equalsIgnoreCase("y"))
                return true;
            else
                return false;
        }
        return null;
    }
}
