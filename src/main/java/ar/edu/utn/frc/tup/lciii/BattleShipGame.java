package ar.edu.utn.frc.tup.lciii;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase mantiene el estado de un juego.
 *
 * Un juego es una partida de las muchas que puede jugar el Player
 * en una misma corrida del programa.
 *
 */
public class BattleShipGame {

    /**
     * Expresion regular para validar entradas de posiciones
     */
    private static final String POSITION_INPUT_REGEX = "[0-9]{1} [0-9]{1}";

    /**
     * Numero de barcos requeridos para jugar
     */
    private static final Integer FLEET_SIZE = 20;

    /**
     * Scanner para capturar las entradas del usuario
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Jugador asignado al usuario
     */
    private Player player;

    /**
     * Jugador asignado a la app
     */
    private Player appPlayer;

    /**
     * Tablero de la flota del jugador
     */
    private Board playerFleetBoard;

    /**
     * Tablero de marcación de la flota enemiga del jugador
     */
    private Board playerEnemyFleetBoard;

    /**
     * Tablero de la flota de la app
     */
    private Board appFleetBoard;

    /**
     * Tablero de marcación de la flota enemiga de la app
     */
    private Board appEnemyFleetBoard;

    /**
     * Lista de los disparos efectuados por el jugador
     */
    private List<Position> playerShots;

    /**
     * Lista de los disparos efectuados por la app
     */
    private List<Position> appShots;

    /**
     * Lista de los barcos del jugador
     */
    private List<Ship> playerShips;

    /**
     * Lista de los barcos de la app
     */
    private List<Ship> appShips;

    /**
     * Jugador que gano el juego
     */
    private Player winner;
    // TODO: more attributes if necessary

    // TODO: getters & setters...

    // TODO: constructors if necessary...
    public BattleShipGame(Player player, Player appPlayer) {
        this.player = player;
        this.appPlayer = appPlayer;
        this.playerFleetBoard = new Board();
        this.playerEnemyFleetBoard = new Board();
        this.appFleetBoard = new Board();
        this.appEnemyFleetBoard = new Board();
        this.playerShots = new ArrayList<>();
        this.appShots = new ArrayList<>();
        this.playerShips = new ArrayList<>();
        this.appShips = new ArrayList<>();
        this.winner = null;
        this.playerFleetBoard.initBoardFleet();
        this.playerEnemyFleetBoard.initBoardEnemyFleet();
        this.appFleetBoard.initBoardFleet();
        this.appEnemyFleetBoard.initBoardEnemyFleet();
    }

    /**
     * Este metodo genera una lista de posiciones aleatoria para
     * la flota de barcos con la que jugará la App.
     *
     * Este metodo valida que las posiciones de la cada barco de la flota es unica
     * y que se encuentra dentro de los margenes del tablero.
     *
     * Por cada barco de la flota debe agregarlo en la lista "appShips"
     *
     * @see #getPlayerFleetPositions()
     * @see #generateAppShot()
     * @see #getRandomPosition()
     *
     */
    public void generateAppFleetPositions() {
        do {
            Position position = this.getRandomPosition();
            if(isAvailablePosition(appShips, position)) {
                this.appShips.add(new Ship(position, ShipStatus.AFLOAT));
            }
        } while (this.appShips.size() < FLEET_SIZE);
        appFleetBoard.setShipPositions(playerShips);
    }

    /**
     * Este metodo gestiona el pedido de posiciones de cada barco al jugador,
     * y los agrega en la lista "playerShips".
     *
     * Se le pide al usuario por pantalla cada par de coordenadas como
     * dos Enteros separados por un espacio en blanco. Por cada coordenada que el usuario ingresa,
     * debe validarse que este dentro de los margenes del tablero y que NO haya colocado ya
     * otro barco en dicha posicion.
     *
     * Cuando el usuario ha colocado todos los barcos (20 en total),
     * el metodo los posiciona en el tablero del usuario.
     *
     * @see #generateAppFleetPositions()
     * @see #getPlayerShot()
     *
     */
    public void getPlayerFleetPositions() {
        // TODO: Hacer un bucle para pedir las posiciones hasta alcanzar el limite
        // TODO: Mostrar un mensaje por pantalla pidiendo posicionar el barco.
        // TODO: Usar el metodo this.getPosition() para pedir la posicion.
        // TODO: Validar si la posicion esta disponible en la lista de barcos.
        // TODO: Mostrar un mensaje de error comentando que ya establesio esa posicion.
        // TODO: Si esta disponible, crear el barco y agregarlo a la lista de barcos.
        // TODO: Al finalizar el bucle, setear en el board las posiciones de los barcos.
        Position position;
        boolean avaiblePosition;
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            do {
                position = this.getPosition();
                 avaiblePosition = isAvailablePosition(this.playerShips, position);
                if (avaiblePosition)
                    this.playerShips.add(new Ship(position, ShipStatus.AFLOAT));
                else
                    System.out.println("Error, esa posición no esta disponible");
            }while (!avaiblePosition);
        }

        for (Ship s: playerShips) {
            playerFleetBoard.setShipOnBoard(s.getPosition());
        }
    }

    /**
     * Este metodo gestiona la acción de disparar por parte del usuario.
     * Cuando el usuario estableció el disparo, debe agregarlo a la lista de
     * disparos realizados "playerShots" y cargarlo en su board de la flota enemiga "playerEnemyFleetBoard"
     * según haya derribado un barco o encontrado agua.
     *
     * Si el disparo alcanza un barco enemigo, se debe cambiar el barco de dicha posicion a ShipStatus.SUNKEN
     * mediante el metodo de Ship "sinkShip()"
     *
     * Se le pide al usuario por pantalla cada par de coordenadas como
     * dos Enteros separados por un espacio en blanco. Por cada coordenada que el usuario ingresa,
     * debe validarse que este dentro de los margenes del tablero.
     *
     * @see #getPlayerFleetPositions()
     *
     */
    public void getPlayerShot() {
        Position position = null;
        Boolean avaible;
        do {
            System.out.println("Donde quiere disparar?");
            avaible = isAvailableShot(playerShots, position);
            position = this.getPosition();
            if(avaible) {
                this.playerShots.add(position);
            } else {
                System.out.println("Ya disparó a esa posición.!" +
                        System.lineSeparator() + "Elija otra posicion...");
            }
        } while (!avaible);
        // TODO: Preguntar si la posicion del disparo impacto un barco enemigo.
        // TODO: Setear segun hubo un impacto o no, agua o un barco, en el tablero de marcacion de la flota enemiga
    }

    /**
     * Este metodo genera de manera aleatoria un disparo por parte de la app.
     * El metodo genera dos enteros entre 0 y 9 para definir las coordenadas
     * donde efectuará el disparo.
     *
     * El metodo valida que el disparo no se haya hecho antes de cargarlo en
     * la lista de disparos de la app.
     *
     * Cuando la app estableció el disparo, debe agregarlo a la lista de
     * disparos realizados "appShots" y cargarlo en su board de la flota enemiga "appEnemyFleetBoard"
     * según haya derribado un barco o encontrado agua.
     *
     * Si el disparo alcanza un barco enemigo, se debe cambiar el barco de dicha posicion a ShipStatus.SUNKEN
     * mediante el metodo de "ship.sinkShip()"
     *
     * @see #generateAppFleetPositions()
     * @see #getRandomPosition()
     */
    public void generateAppShot() {
        Position randomShot;
        Boolean existeEseDisparo = true;
        // TODO: Generar una posicion random para el disparo de la app usando el metodo getRandomPosition()
        // TODO: Validar si aun no se uso esa posicion en la lista de disparos de la app.
        // TODO: Si el disparo ya se hizo, volver a generar disparos hasta que salga alguno valido
        // TODO: Cuando el disparo no haya sido usado antes, agregarlo a la lista de disparos de la app
        do{
            randomShot = getRandomPosition();
        if(isAvailableShot(this.appShots, randomShot))
            existeEseDisparo = false;

        } while(existeEseDisparo);

        if(this.impactEnemyShip(playerShips, randomShot)) {
            appEnemyFleetBoard.setShipOnBoard(randomShot);
        } else {
            appEnemyFleetBoard.setWaterOnBoard(randomShot);
        }
    }

    /**
     * Este metodo imprime por pantalla el estado del juego, que incluye
     * cuantos barcos tiene cada jugar a flote y cuantos hundidos
     *
     * @see Player
     * @see #playerShips
     * @see #appShips
     * @see #player
     * @see #appPlayer
     *
     */
    public void printGameStatus() {
        // TODO: Imprimir por pantalla el status del juego
        // TODO: Incluir barcos flotando y hundidos de cada jugador
        int sinkedPlayerShips = 0;
        int aFloatPlayerShips;
        int sinkedAppShips = 0;
        int aFloatAppShips;

        for (int i = 0; i < playerShips.size(); i++) {
            if(playerShips.get(i).getShipStatus() == ShipStatus.SUNKEN)
                sinkedPlayerShips++;
            if(appShips.get(i).getShipStatus() == ShipStatus.SUNKEN)
                sinkedAppShips++;
        }
        aFloatPlayerShips = playerShips.size()-sinkedPlayerShips;
        aFloatAppShips = playerShips.size()-sinkedAppShips;
        if(!isFinish()){
            System.out.println("Todavía no hay ganador");
        }
        System.out.println("---"+player.getPlayerName() + "---");
        System.out.println("Barcos: " + playerShips.size());
        System.out.println("Barcos hundidos: " + sinkedPlayerShips);
        System.out.println("Barcos a flote: " + aFloatPlayerShips);
        System.out.println("---"+ "Computadora" + "---");
        System.out.println("Barcos: " + appShips.size());
        System.out.println("Barcos hundidos: " + sinkedAppShips);
        System.out.println("Barcos a flote: " + aFloatAppShips);
    }

    /**
     * Este metodo dibuja los tableros del Player junto al titulo de cada uno.
     *
     * @see Board#drawBoard()
     *
     */
    public void drawPlayerBoards() {
        System.out.println("TU FLOTA" + System.lineSeparator());
        // TODO: Dibujar el tablero del usuario
        playerFleetBoard.drawBoard();
        System.out.println("FLOTA ENEMIGA" + System.lineSeparator());
        // TODO: Dibujar el tablero de marcacion de la flota enemiga del usurio
        playerEnemyFleetBoard.drawBoard();
    }

    /**
     * Este metodo muestra un mensaje de finalizacion de la partida,
     * muestra el nombre del ganador, el puntaje obtenido en esta partida
     * y los puntajes acumulados a traves de las partidas jugadas.
     *
     * @see System#out
     */
    public void goodbyeMessage() {
        // TODO: Imprimir por pantalla un mensaje de despedida
        System.out.println("Ganador: " + winner.getPlayerName());

    }

    /**
     * Este metodo calcula los puntos obtenidos por cada jugador en esta partida
     * y se los suma a los que ya traia de otras partidas.
     *
     * @see Player
     * @see #playerShips
     * @see #appShips
     * @see #player
     * @see #appPlayer
     *
     */
    public void calculateScores() {
        // TODO: Se debe completar este metodo
        // TODO: Calcular los puntos obtenidos por cada jugador en este juego
        // TODO: Sumar los puntos al score de cada jugardor
        // TODO: Sumar la partida ganada al jugador que ganó

        for (int i = 0; i < 20; i++) {
            if(appShips.get(i).getShipStatus() == ShipStatus.SUNKEN){
                player.setScore(player.getScore() + 1);
            }
            if(playerShips.get(i).getShipStatus() == ShipStatus.SUNKEN){
                appPlayer.setScore(appPlayer.getScore() + 1);
            }
        }
        if(validateSunkenFleet(playerShips)){
            appPlayer.setScore(appPlayer.getScore() + 20);
        }
        if(validateSunkenFleet(appShips)){
            player.setScore(player.getScore() + 20);
        }
    }

    /**
     * Este metodo verifica si hubo un impacto con el disparo,
     * si el disparo impacto, hunde el barco y retorna true.
     * Si el disparo no impacto retorna false
     *
     * @param fleetEnemyShips Lista de barcos de la flota enemiga
     * @param shot
     *
     * @see Position#equals(Object)
     * @see Ship#sinkShip()
     *
     * @return true si el disparo impacta, false si no lo hace.
     */
    private Boolean impactEnemyShip(List<Ship> fleetEnemyShips, Position shot) {
        // TODO: Recorrer la lista de Ships pasada por parametro y validar si existe un barco en la posicion pasada por parametro
        // TODO: Si existe un barco en esa posicion, hundir el barco el metodo sinkShip()
        // TODO: Retornar true si se hundio un barco, o false si no se hizo.
        for (Ship s: fleetEnemyShips) {
            if(s.getPosition().equals(shot)) {
                s.sinkShip();
                return true;
            }
        }
        // TODO: Remember to replace the return statement with the correct object
        return false;
    }

    /**
     * Este metodo define si el juego terminó.
     * El juego termina cuando uno de los dos jugadores (El player o la app)
     * a hundido todos los barcos del contrario.
     *
     * Cuando el juego termina, este metodo setea en el atributo winner quien ganó.
     *
     * @see #validateSunkenFleet(List)
     * @see #winner
     *
     * @return true si el juego terminó, false si aun no hay un ganador
     */
    public Boolean isFinish() {
        // TODO: Validar si todos lo barcos de algun jugador fueron undidos
        // TODO: Si algun jugador ya perdio todos sus barcos, setear el ganador en winner
        // TODO: Retornar true si hubo un ganador, o false si no lo hubo
        if(validateSunkenFleet(playerShips)) {
            winner = player;
            return true;
        }
        else if(validateSunkenFleet(appShips)) {
            winner = player;
            return  true;
        }
        // TODO: Remember to replace the return statement with the correct object
        return false;
    }

    /**
     * Este metodo valida si aun queda algun barco a flote en la flota,
     * para determinar si toda la flota fue hundida.
     *
     * @param fleet
     *
     * @see Ship#getShipStatus()
     * @see ShipStatus
     *
     * @return true si toda la flota fue hundida, flase si al menos queda un barco a flote.
     */
    private Boolean validateSunkenFleet(List<Ship> fleet) {
        // TODO: Recorrer la lista de barcos y validar si todo fueron undidos
        // TODO: Retornar true si todos fueron undidos o false si al menos queda un barco a flote
        int sinkedShips = 0;
        for (Ship s: fleet) {
            if(s.getShipStatus() == ShipStatus.SUNKEN)
                sinkedShips++;
        }
        // TODO: Remember to replace the return statement with the correct object
        return sinkedShips == fleet.size();
    }

    /**
     * Este metodo gestiona la acción de pedir coordenads al usuario.
     *
     * Se le pide al usuario por pantalla cada par de coordenadas como
     * dos Enteros separados por un espacio en blanco. Por cada coordenada que el usuario ingresa,
     * debe validarse que este dentro de los margenes del tablero.
     *
     * @see #isValidPositionInput(String)
     * @see Position#Position()
     * @see String#split(String)
     *
     * @return La posicion elegida por el usuario.
     */
    private Position getPosition() {
        Position position = null;
        do {
            System.out.println("Ingrese una coordenada en un formato de dos numeros " +
                    "enteros entre 0 y 9 separados por un espacio en blanco.");

            String input = scanner.nextLine();

            if (isValidPositionInput(input)) {
                //TODO: Separar los enteros de input en dos Integers y crear Position
                position = new Position();
                String[] posiciones = input.split("\\s+");
                position.setColumn(Integer.parseInt(posiciones[0]));
                position.setRow(Integer.parseInt(posiciones[1]));
            } else {
                // TODO: Mostrar un mensaje de error sobre como ingreso los datos
                System.out.println("Error, debe ingresar una posición valida");

            }
        } while(position == null);
        return position;
    }

    /**
     * Este metodo valida que la entrada del usuario como String este en el formato establecido
     * de dos numeros enteros entre 0 y 9 separados por un espacio en blanco.
     *
     * @see Pattern#compile(String)
     * @see Pattern#matcher(CharSequence)
     * @see Matcher#matches()
     *
     * @param input La entrada que se capturo del usuario
     * @return true si el formato es valido, false si no lo es
     */
    private Boolean isValidPositionInput(String input) {
        Pattern pattern = Pattern.compile(POSITION_INPUT_REGEX);
        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            return false;
        }
        // TODO: Remember to replace the return statement with the correct object
        //return null;
    }

    /**
     * Este metodo retorna una posición random que puede ser usada
     * para representar una posicion de un barco o un disparo random de la app.
     *
     * @see Random
     * @see Random#nextInt(int)
     * @see Position
     *
     * @return la posición del disparo random.
     */
    private Position getRandomPosition() {
        // TODO: Generar 2 numeros random entre 0 y 9 para establecer la row y column
        // TODO: Crear la Posicion a partir de la row y column
        // TODO: Retornar la Position
        Random r = new Random();
        Position randomPosition = new Position();
        randomPosition.setRow(r.nextInt(9));
        randomPosition.setColumn(r.nextInt(9));
        // TODO: Remember to replace the return statement with the correct object
        return randomPosition;
    }

    /**
     * Este metodo valida que la posición nueva no exista en la lista de posiciones que ya fueron cargadas
     * para eso recibe por parametro la lista donde hará la busqueda y la posicion a buscar.
     *
     * El metodo otorga un mecanismo de validacion de que un objeto del tipo Position
     * no existe en una lista del tipo List<Position>
     *
     * @see List#contains(Object)
     * @see Position#equals(Object)
     *
     * @param listShots la lista donde se hará la busqueda
     * @param position La nueva posicion a buscar
     * @return true si la posición no existe en la lista, false si ya existe esa posicion.
     */
    private Boolean isAvailableShot(List<Position> listShots, Position position) {
        // TODO: Validar que la lista de posiciones NO tenga la posision indicada
        return !listShots.contains(position);
        // TODO: Remember to replace the return statement with the correct object
    }

    /**
     * Este metodo valida que la posición pasada por parametro no exista dentro de las
     * posiciones de los barcos de la lista "listToCheck".
     *
     * @param listToCheck la lista donde se hará la busqueda
     * @param position La nueva posicion a buscar
     *
     * @see List#contains(Object)
     * @see Ship#equals(Object)
     *
     * @return true si la posición no existe en la lista, false si ya existe esa posicion.
     */
    private Boolean isAvailablePosition(List<Ship> listToCheck, Position position) {
        // TODO: Validar que la lista de Ship NO tenga un Ship con la posision indicada
        Ship s = new Ship();
        s.setPosition(position);
        return !listToCheck.contains(s);
        // TODO: Remember to replace the return statement with the correct object
    }

}
