package view.duelMenu;

import model.User;
import model.game.Game;
import view.regexes.OneRoundGameRegexes;
import view.responses.OneRoundGameResponses;

import java.util.Scanner;

public class OneRoundGame {
    private Game game;
    private final Scanner scanner;
    private CurrentPhase currentPhase;

    public OneRoundGame(User firstPlayer, User secondPlayer,Scanner scanner){
        this.scanner = scanner;
    }
    public void run(){
        String command;
        while (true){
            command = scanner.nextLine().trim();
            if (OneRoundGameRegexes.doesItSelectMyMonsterCellCommand(command))
                selectMyMonsterCell(command);
            else if (OneRoundGameRegexes.doesItSelectMySpellCellCommand(command))
                selectMySpellCell(command);
            else if (OneRoundGameRegexes.doesItSelectCardFromMyHandCommand(command))
                selectCardFromMyHand(command);
            else if(OneRoundGameRegexes.doesItSelectMyFieldZoneCommand(command))
                selectMyFieldZone(command);
            else if (OneRoundGameRegexes.doesItSelectOpponentFieldZoneCommand(command))
                selectOpponentFieldZone(command);
            else if (OneRoundGameRegexes.doesItSelectOpponentMonsterCellCommand(command))
                selectOpponentMonsterCell(command);
            else if (OneRoundGameRegexes.doesItSelectOpponentSpellCellCommand(command))
                selectOpponentSpellCell(command);
            else if (OneRoundGameRegexes.doesItSetAttackCommand(command))
                setAttack(command);
            else if (OneRoundGameRegexes.doesItSetDefenseCommand(command))
                setDefense(command);
            else if (command.matches(OneRoundGameRegexes.activeEffect))
                activeEffect();
            else if (command.matches(OneRoundGameRegexes.set))
                set();
            else if (command.matches(OneRoundGameRegexes.attackDirect))
                attackDirect();
            else if (command.matches(OneRoundGameRegexes.deselectCard))
                deselectCard();
            else if (command.matches(OneRoundGameRegexes.flipSummon))
                flipSummon();
            else if (command.matches(OneRoundGameRegexes.attackToOpponentMonster))
                attackToOpponentMonster(command);
            else if (command.matches(OneRoundGameRegexes.showCard))
                showSelectedCard();
            else if (command.matches(OneRoundGameRegexes.showGraveyard))
                showGraveyard();
            else if (command.matches(OneRoundGameRegexes.summon))
                summon();
            else if (command.matches(OneRoundGameRegexes.surrender))
            {
                surrender();
                break;
            }
            else if (command.matches(OneRoundGameRegexes.nextPhase))
                goToNextPhase();
            else
                respond(OneRoundGameResponses.INVALID_COMMAND);
        }
    }
    public void selectCard(String command){

    }
    public void goToNextPhase(){

    }
    public void goToStandByPhase(){

    }
    public void goToMainPhase1(){

    }
    public void goToDrawPhase(){

    }
    public void goToMainPhase2(){

    }
    public void goToBattlePhase(){

    }
    public void goToEndPhase(){

    }
    public void surrender(){

    }
    public void deselectCard(){

    }
    public void summon(){

    }
    public void set(){

    }
    public void flipSummon(){

    }
    public void attackDirect(){

    }
    public void activeEffect(){

    }
    public void showGraveyard(){

    }
    public void showSelectedCard(){

    }
    public void selectMyMonsterCell(String command){

    }
    public void selectMySpellCell(String command){

    }
    public void selectMyFieldZone(String command){

    }
    public void selectCardFromMyHand(String command){

    }
    public void selectOpponentMonsterCell(String command){

    }
    public void selectOpponentSpellCell(String command){

    }
    public void selectOpponentFieldZone(String command){

    }
    public void setAttack(String command){

    }
    public void setDefense(String command){

    }
    public void attackToOpponentMonster(String command){

    }
    public CurrentPhase getCurrentPhase(){
        return currentPhase;
    }
    public void respond(OneRoundGameResponses responses){

    }
}
