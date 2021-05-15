package view.duelMenu;

import controller.GameMenuController;
import model.card.Card;
import model.exceptions.WinnerException;
import model.game.Cell;
import model.game.Game;
import view.LoginMenu;
import view.regexes.OneRoundGameRegexes;
import view.responses.OneRoundGameResponses;

public class SpellSelectMenu extends OneRoundGame {
    private Boolean successful = false;
    private Card card = null;
    private int speed;

    public SpellSelectMenu(Game game) {
        super(game, LoginMenu.getInstance().getScanner());
    }

    public Card run(int speed) throws WinnerException {
        this.speed = speed;
        game.switchReferences();
        System.out.println("Now its " + game.getPlayer().getNickname() + "'s turn .");
        System.out.println(GameMenuController.showTable(game));
        String command;
        while (true) {
            System.out.println("Do you want to select a card ? Y/N");
            command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("y")) {
                System.out.println("You can quit this menu if you want by typing quit .. \nin this way you will choose not to activate a spell.");
                break;
            }
            if (command.equalsIgnoreCase("n")) return null;
            else System.out.println("Invalid command .. please type y (for yes ) and n (for no) .");
        }
        while (true) {
            command = scanner.nextLine();
            if (OneRoundGameRegexes.doesItSelectMyMonsterCellCommand(command))
                super.selectMyMonsterCell(command);
            else if (OneRoundGameRegexes.doesItSelectMySpellCellCommand(command))
                super.selectMySpellCell(command);
            else if (OneRoundGameRegexes.doesItSelectCardFromMyHandCommand(command))
                super.selectCardFromMyHand(command);
            else if (OneRoundGameRegexes.doesItSelectMyFieldZoneCommand(command))
                super.selectMyFieldZone();
            else if (OneRoundGameRegexes.doesItSelectOpponentFieldZoneCommand(command))
                super.selectOpponentFieldZone();
            else if (OneRoundGameRegexes.doesItSelectOpponentMonsterCellCommand(command))
                super.selectOpponentMonsterCell(command);
            else if (OneRoundGameRegexes.doesItSelectOpponentSpellCellCommand(command))
                super.selectOpponentSpellCell(command);
            else if (command.matches(OneRoundGameRegexes.activeEffect))
                activeEffect();
            else if (command.matches(OneRoundGameRegexes.showCard))
                super.showSelectedCard();
            else if (command.equalsIgnoreCase("quit"))
                return null;
            else if (OneRoundGameRegexes.doesItSetAttackCommand(command)
                    || OneRoundGameRegexes.doesItSetDefenseCommand(command)
                    || command.matches(OneRoundGameRegexes.set)
                    || command.matches(OneRoundGameRegexes.attackDirect)
                    || command.matches(OneRoundGameRegexes.deselectCard)
                    || command.matches(OneRoundGameRegexes.flipSummon)
                    || command.matches(OneRoundGameRegexes.attackToOpponentMonster)
                    || command.matches(OneRoundGameRegexes.showGraveyard)
                    || command.matches(OneRoundGameRegexes.summon)
                    || command.matches(OneRoundGameRegexes.surrender)
                    || command.matches(OneRoundGameRegexes.nextPhase))
                youCantDoThisHere();
            else
                respond(OneRoundGameResponses.INVALID_COMMAND);
            if (successful) {
                game.switchReferences();
                System.out.println("Now its " + game.getPlayer().getNickname() + "'s turn .");
                return card;
            }
        }
    }

    @Override
    public void activeEffect() throws WinnerException {
        SelectState selectState = GameMenuController.getSelectState();
        if (selectState == null) respond(OneRoundGameResponses.NO_CARD_IS_SELECTED_YET);
        else if (selectState != SelectState.PLAYER_SPELL) {
            respond(OneRoundGameResponses.ACTIVE_EFFECT_IS_ONLY_FOR_SPELL_CARDS);
        } else { // card is in spell zone
            Cell tempCell = game.getPlayerBoard().getSpellZone(GameMenuController.getCellNumber() - 1);
            Card card = tempCell.getCard();
            if (GameMenuController.hasNotUsedEffect(card.getFeatures())) {
                if (GameMenuController.getSpeed(card.getFeatures()) >= speed) {
                    this.successful = true;
                    this.card = card;
                } else speedIsLow();
            } else respond(OneRoundGameResponses.YOU_HAVE_ALREADY_ACTIVATED_THIS_CARD);
        }
        deselectCard(false);
    }

    private static void speedIsLow() {
        System.out.println("You can't choose this spell to answer .");
    }

    private static void youCantDoThisHere() {
        System.out.println("It’s not your turn to play this kind of moves");
    }
}
