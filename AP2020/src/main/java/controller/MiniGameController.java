package controller;

import model.game.MiniGame;

import java.util.Random;

public class MiniGameController {
    public static int dice() {
        int randomNumber;
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }
    public static void playDice(MiniGame miniGame) {
        int firstUserDice = dice();
        int secondUserDice = dice();
        if (firstUserDice > secondUserDice)
            miniGame.setWinner(miniGame.getFirstUser());
        else if (firstUserDice == secondUserDice)
            playDice(miniGame);
        else
            miniGame.setWinner(miniGame.getSecondUser());
    }
    public static void playCoin(MiniGame miniGame, String playerChoice) {
        String coin = throwCoin();
        if (playerChoice.equals(coin))
            miniGame.setWinner(miniGame.getFirstUser());
        else
            miniGame.setWinner(miniGame.getSecondUser());
    }
    public static String throwCoin() {
        Random rand = new Random();
        int headOrTale = rand.nextInt(2);
        if (headOrTale == 0)
            return "Head";
        else
            return "Tale";
    }
}
