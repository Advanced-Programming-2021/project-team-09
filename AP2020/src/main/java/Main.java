import controller.EffectController.MonsterEffectController;
import controller.EffectController.SpellEffectController;
import controller.GameMenuController;
import controller.database.CSVInfoGetter;
import model.card.Card;
import model.game.Game;
import view.LoginMenu;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        LoginMenu.getInstance().run();
//        ArrayList<String> names = CSVInfoGetter.getCardNames();
//        for (String s : names) {
//            try {
//                Method method = MonsterEffectController.class.getDeclaredMethod(GameMenuController.trimName(s), Game.class, Card.class);
//            } catch (NoSuchMethodException e) {
//                System.out.println(s );
//            }
//            try {
//                Method method = SpellEffectController.class.getDeclaredMethod(GameMenuController.trimName(s), Game.class, Card.class);
//                method.invoke(null, null, null);
//            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
//            }
//        }
    }
}