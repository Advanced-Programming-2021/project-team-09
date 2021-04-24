import controller.*;
import model.card.spell_traps.Trap;

public class Main {
    public static void main(String[] args) {
        System.out.println(((Trap)csvInfoGetter.getCardByName("Negate Attack")).getTrapType());
    }
}
