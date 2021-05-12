package view.duelMenu;

import controller.GameMenuController;
import model.User;
import model.game.Game;
import org.jetbrains.annotations.NotNull;
import view.regexes.OneRoundGameRegexes;
import view.responses.OneRoundGameResponses;

import java.util.ArrayList;
import java.util.Scanner;

public class OneRoundGame {
    private Game game;
    private final Scanner scanner;
    private Phase currentPhase;

    public OneRoundGame(User firstPlayer, User secondPlayer,Scanner scanner) throws CloneNotSupportedException {
        game = new Game(firstPlayer, secondPlayer);
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

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void goToNextPhase(){
        if (currentPhase.equals(Phase.STANDBY_PHASE))
            goToMainPhase1();
        else if (currentPhase.equals(Phase.MAIN_PHASE1)){
            goToDrawPhase();
            //todo add card to hand
        }

        else if (currentPhase.equals(Phase.DRAW_PHASE))
            goToMainPhase2();
        else if (currentPhase.equals(Phase.MAIN_PHASE2))
            goToBattlePhase();
        else if (currentPhase.equals(Phase.BATTLE_PHASE))
            goToEndPhase();
        else if (currentPhase.equals(Phase.END_PHASE))
            goToStandByPhase();
    }
    public void goToStandByPhase(){
        setCurrentPhase(Phase.STANDBY_PHASE);
        respond(OneRoundGameResponses.STANDBY_PHASE);
    }
    public void goToMainPhase1(){
        setCurrentPhase(Phase.MAIN_PHASE1);
        respond(OneRoundGameResponses.MAIN_PHASE1);
    }
    public void goToDrawPhase(){
        setCurrentPhase(Phase.DRAW_PHASE);
        respond(OneRoundGameResponses.DRAW_PHASE);
    }
    public void goToMainPhase2(){
        setCurrentPhase(Phase.MAIN_PHASE2);
        respond(OneRoundGameResponses.MAIN_PHASE2);
    }
    public void goToBattlePhase(){
        setCurrentPhase(Phase.BATTLE_PHASE);
        respond(OneRoundGameResponses.BATTLE_PHASE);
    }
    public void goToEndPhase(){
        setCurrentPhase(Phase.END_PHASE);
        respond(OneRoundGameResponses.END_PHASE);
        //todo print "its <next player nickname> turn
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
    public ArrayList<Phase> getAllowedPhaseForSummonSetChangePositionActiveEffect(){
        ArrayList<Phase> allowedPhaseForSummon = new ArrayList<>();
        allowedPhaseForSummon.add(Phase.MAIN_PHASE1);
        allowedPhaseForSummon.add(Phase.MAIN_PHASE2);
        return allowedPhaseForSummon;
    }
    public ArrayList<Phase> getAllowedPhaseForSelect(){
        ArrayList<Phase> allowedPhaseForSelect = new ArrayList<>();
        allowedPhaseForSelect.add(Phase.MAIN_PHASE2);
        allowedPhaseForSelect.add(Phase.MAIN_PHASE2);
        allowedPhaseForSelect.add(Phase.BATTLE_PHASE);
        return allowedPhaseForSelect;
    }
    public ArrayList<Phase> getAllowedPhaseForAttack(){
        ArrayList<Phase> allowedPhaseForAttack = new ArrayList<>();
        allowedPhaseForAttack.add(Phase.BATTLE_PHASE);
        return allowedPhaseForAttack;
    }
    public boolean doesActionAllowedInCurrentPhases(@NotNull ArrayList<Phase> allowedPhases){
        for (Phase phase: allowedPhases) {
            if (getCurrentPhase().equals(phase))
                return true;
        }
        return false;
    }
    public Phase getCurrentPhase(){
        return currentPhase;
    }
    public void respond(OneRoundGameResponses responses){
        if (responses.equals(OneRoundGameResponses.INVALID_COMMAND))
            System.out.println("invalid command!");
        else if (responses.equals(OneRoundGameResponses.INVALID_SELECTION))
            System.out.println("invalid selection");
        else if (responses.equals(OneRoundGameResponses.ACTION_NOT_ALLOWED_IN_THIS_PHASE))
            System.out.println("action is not allowed in this phase");
        else if (responses.equals(OneRoundGameResponses.ACTIVE_EFFECT_IS_ONLY_FOR_SPELL_CARDS))
            System.out.println("active effect is only for spell card");
        else if (responses.equals(OneRoundGameResponses.BOTH_YOU_AND_YOUR_OPPONENT_MONSTER_CARDS_ARE_DESTROYED_AND_NO_ONE_RECEIVES_DAMAGE))
            System.out.println("both you and your opponent monster cards are destroyed and no one receives damage");
        else if (responses.equals(OneRoundGameResponses.CARD_DESELECTED))
            System.out.println("card deselected");
        else if (responses.equals(OneRoundGameResponses.CARD_IS_NOT_VISIBLE))
            System.out.println("card is not visible");
        else if (responses.equals(OneRoundGameResponses.CARD_SELECTED))
            System.out.println("card selected");
        else if (responses.equals(OneRoundGameResponses.DO_YOU_WANT_TO_ACTIVATE_YOUR_TRAP_AND_SPELL))
            System.out.println("do you want to activate your trap and spell?");
        else if (responses.equals(OneRoundGameResponses.FLIP_SUMMONED_SUCCESSFULLY))
            System.out.println("flip-summoned successfully");
        else if (responses.equals(OneRoundGameResponses.GRAVE_YARD_EMPTY))
            System.out.println("graveyard empty");
        else if (responses.equals(OneRoundGameResponses.ITS_NOT_YOUR_TURN_TO_PLAY_THIS_KIND_OF_MOVES))
            System.out.println("it's not your turn to play this kind of moves");
        else if (responses.equals(OneRoundGameResponses.MONSTER_CARD_POSITION_CHANGED_SUCCESSFULLY))
            System.out.println("monster card position changed successfully");
        else if (responses.equals(OneRoundGameResponses.MONSTER_CARD_ZONE_IS_FULL))
            System.out.println("monster card zone is full");
        else if (responses.equals(OneRoundGameResponses.NO_CARD_FOUND_IN_GIVEN_POSITION))
            System.out.println("no card found in given position");
        else if (responses.equals(OneRoundGameResponses.NO_CARD_IS_DESTROYED))
            System.out.println("no card is destroyed");
        else if (responses.equals(OneRoundGameResponses.NO_CARD_IS_SELECTED_YET))
            System.out.println("no card is selected yet");
        else if (responses.equals(OneRoundGameResponses.PREPARATIONS_OF_THIS_SPELL_ARE_NOT_DONE_YET))
            System.out.println("preparations of this spell are not done yet");
        else if (responses.equals(OneRoundGameResponses.THE_DEFENSE_POSITION_MONSTER_IS_DESTROYED))
            System.out.println("the defense position monster is destroyed");
        else if (responses.equals(OneRoundGameResponses.SELECTED_MONSTERS_LEVEL_DONT_MATCH_WITH_RITUAL_MONSTER))
            System.out.println("selected monsters level don't match with ritual monster");
        else if (responses.equals(OneRoundGameResponses.SPELL_ACTIVATED))
            System.out.println("spell activated");
        else if (responses.equals(OneRoundGameResponses.SPELL_CARD_ZONE_IS_FULL))
            System.out.println("spell card zone is full");
        else if (responses.equals(OneRoundGameResponses.SPELL_OR_TRAP_ACTIVATED))
            System.out.println("spell or trap activated");
        else if (responses.equals(OneRoundGameResponses.SUMMONED_SUCCESSFULLY))
            System.out.println("summoned successfully");
        else if (responses.equals(OneRoundGameResponses.THERE_ARE_NOT_ENOUGH_CARDS_FOR_TRIBUTE))
            System.out.println("there are not enough cards for tribute");
        else if (responses.equals(OneRoundGameResponses.THERE_IS_NO_CARD_TO_ATTACK_HERE))
            System.out.println("there is no card to attack here");
        else if (responses.equals(OneRoundGameResponses.THERE_IS_NO_WAY_YOU_COULD_RITUAL_SUMMON_A_MONSTER))
            System.out.println("there is no way you could ritual summon a monster");
        else if (responses.equals(OneRoundGameResponses.THERE_IS_NO_WAY_YOU_COULD_SPECIAL_SUMMON_A_MONSTER))
            System.out.println("there is no way you could special summon a monster");
        else if (responses.equals(OneRoundGameResponses.THERE_NO_MONSTERS_ON_ONE_OF_THIS_ADDRESSES))
            System.out.println("there no monsters on one this addresses");
        else if (responses.equals(OneRoundGameResponses.THERE_NO_MONSTERS_ON_THIS_ADDRESS))
            System.out.println("there no monster on this address");
        else if (responses.equals(OneRoundGameResponses.THIS_CARD_ALREADY_ATTACKED))
            System.out.println("this card already attacked");
        else if (responses.equals(OneRoundGameResponses.THIS_CARD_IS_ALREADY_IN_THE_WANTED_POSITION))
            System.out.println("this card is already in the wanted position");
        else if (responses.equals(OneRoundGameResponses.YOU_ALREADY_CHANGED_THIS_CARD_POSITION_THIS_TURN))
            System.out.println("you already changed this card position this turn");
        else if (responses.equals(OneRoundGameResponses.YOU_ALREADY_SUMMONED_OR_SET_ON_THIS_TURN))
            System.out.println("you already summoned/set on this turn");
        else if (responses.equals(OneRoundGameResponses.YOU_CANT_ACTIVE_AN_EFFECT_ON_THIS_TURN))
            System.out.println("you can't active an effect on this turn");
        else if (responses.equals(OneRoundGameResponses.YOU_CANT_ATTACK_WITH_THIS_CARD))
            System.out.println("you can't attack with this card");
        else if (responses.equals(OneRoundGameResponses.YOU_CANT_CHANGE_THIS_CARDS_POSITION))
            System.out.println("you can't change this cards position");
        else if (responses.equals(OneRoundGameResponses.YOU_CANT_FLIP_SUMMON_THIS_CARD))
            System.out.println("you can't flip summon this card");
        else if (responses.equals(OneRoundGameResponses.YOU_CANT_SET_THIS_CARD))
            System.out.println("you can't set this card");
        else if (responses.equals(OneRoundGameResponses.YOU_CANT_SUMMON_THIS_CARD))
            System.out.println("you can't summon this card");
        else if (responses.equals(OneRoundGameResponses.YOU_HAVE_ALREADY_ACTIVATED_THIS_CARD))
            System.out.println("you have already activated this card");
        else if (responses.equals(OneRoundGameResponses.YOU_SHOULD_RITUAL_SUMMON_RIGHT_NOW))
            System.out.println("you should ritual summon right now");
        else if (responses.equals(OneRoundGameResponses.YOU_SHOULD_SPECIAL_SUMMON_RIGHT_NOW))
            System.out.println("you should special summon right now");
        else if (responses.equals(OneRoundGameResponses.STANDBY_PHASE))
            System.out.println("phase: standby phase");
        else if (responses.equals(OneRoundGameResponses.MAIN_PHASE1))
            System.out.println("phase: main phase 1");
        else if (responses.equals(OneRoundGameResponses.MAIN_PHASE2))
            System.out.println("phase: main phase 2");
        else if (responses.equals(OneRoundGameResponses.DRAW_PHASE))
            System.out.println("phase: draw phase");
        else if (responses.equals(OneRoundGameResponses.BATTLE_PHASE))
            System.out.println("phase: battle phase");
        else if (responses.equals(OneRoundGameResponses.END_PHASE))
            System.out.println("phase: end phase");
    }
}
