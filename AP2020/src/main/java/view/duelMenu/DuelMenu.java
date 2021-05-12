package view.duelMenu;

import controller.LoginMenuController;
import controller.database.ReadAndWriteDataBase;
import model.User;
import model.game.MiniGame;
import org.jetbrains.annotations.NotNull;
import view.regexes.DuelMenuRegex;
import view.responses.StartingGameResponses;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class DuelMenu {
    private final Scanner scanner;
    private static DuelMenu duelMenu;

    private DuelMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public static DuelMenu getInstance(Scanner scanner) {
        if (duelMenu == null) duelMenu = new DuelMenu(scanner);
        return duelMenu;
    }

    public void run() {
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            if (DuelMenuRegex.doesItDuelNewPlayerCommand(command))
                duelNewPlayer(command);
            else if (DuelMenuRegex.doesItDuelNewAiCommand(command))
                duelNewAi(command);
            else if (command.matches("help"))
                showHelp();
            else if (command.matches("menu show-current"))
                respond(StartingGameResponses.CURRENT_MENU_DUEL_MENU);
            else if (command.matches("menu exit"))
                return;
            else respond(StartingGameResponses.INVALID_COMMAND);
        }
    }

    private void duelNewPlayer(String command) {
        HashMap<String, String> data = parseDuelNewPlayerData(command);
        String username = data.get("username");
        String rounds = data.get("rounds");
        User currentUser = LoginMenuController.getCurrentUser();
        if (currentUser.getUsername().equals(username))
            respond(StartingGameResponses.THERE_IS_NO_PLAYER_WITH_THIS_USERNAME);
        else {
            User winnerOfMiniGame;
            User rivalUser = ReadAndWriteDataBase.getUser(username + ".json");
            if (rivalUser == null) respond(StartingGameResponses.THERE_IS_NO_PLAYER_WITH_THIS_USERNAME);
            else if (rivalUser.getActiveDeck() == null)
                System.out.println(username + " has no active deck");
            else if (!rivalUser.getActiveDeck().isValid())
                System.out.println(username + "'s deck is invalid");
            else if (rounds.equals("1")) {
                MiniGame miniGame = new MiniGame(currentUser, rivalUser);
                MiniGamesMenu.getInstance(scanner, miniGame).run();
                winnerOfMiniGame = miniGame.getWinner();
                if (winnerOfMiniGame.equals(currentUser))
                    singleRoundGame(currentUser, rivalUser);
                else
                    singleRoundGame(rivalUser, currentUser);
            } else if (rounds.equals("3")) {
                MiniGame miniGame = new MiniGame(currentUser, rivalUser);
                MiniGamesMenu.getInstance(scanner, miniGame).run();
                winnerOfMiniGame = miniGame.getWinner();
                if (winnerOfMiniGame.equals(currentUser))
                    tripleRoundGame(currentUser, rivalUser);
                else
                    tripleRoundGame(rivalUser, currentUser);
            } else respond(StartingGameResponses.NUMBER_OF_ROUNDS_IS_NOT_SUPPORTED);
        }

    }
    public void duelNewAi(String command){
        int numberOfRounds = Integer.parseInt(DuelMenuRegex.getRightMatcherForDuelNewAi(command).group("rounds"));
        if (numberOfRounds == 1)
            singlePlayerOneRoundGame();
        else if (numberOfRounds == 3)
            singlePlayerThreeRoundGame();
        else
            respond(StartingGameResponses.NUMBER_OF_ROUNDS_IS_NOT_SUPPORTED);
    }

    public void singleRoundGame(User player, User rival) {
    }

    public void tripleRoundGame(User player, User rival) {
        //todo
    }

    public void singlePlayerOneRoundGame() {
        //todo
    }
    public void singlePlayerThreeRoundGame(){
        //todo
    }

    private void respond(@NotNull StartingGameResponses response) {
        if (response.equals(StartingGameResponses.INVALID_COMMAND))
            System.out.println("invalid command!");
        else if (response.equals(StartingGameResponses.CURRENT_MENU_DUEL_MENU))
            System.out.println("duel menu");
        else if (response.equals(StartingGameResponses.NUMBER_OF_ROUNDS_IS_NOT_SUPPORTED))
            System.out.println("number of rounds is not supported");
        else if (response.equals(StartingGameResponses.THERE_IS_NO_PLAYER_WITH_THIS_USERNAME))
            System.out.println("there is no player with this username");
    }

    @NotNull
    private HashMap<String, String> parseDuelNewPlayerData(String command) {
        HashMap<String, String> parsedData = new HashMap<>();
        Matcher matcher = DuelMenuRegex.getRightMatcherForDuelNewPlayer(command);
        assert matcher != null;
        if (matcher.find()) {
            parsedData.put("username", matcher.group("username"));
            parsedData.put("rounds", matcher.group("rounds"));
        }
        return parsedData;
    }

    public void showHelp() {
        StringBuilder help = new StringBuilder();
        help.append("duel new --second-player <player2 username> --rounds<1/3>\n");
        help.append("duel new single player\n");
        help.append("menu show-current\n");
        help.append("menu exit\n");
        help.append("\n");
        help.append("shortcut:\n");
        help.append("duel new -s-p <player2 username> -r<1/3>");
        System.out.println(help);
    }
}
