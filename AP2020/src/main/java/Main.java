
import controller.*;
import model.card.spell_traps.Trap;
import model.*;
import view.LoginMenu;
import controller.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> a = csvInfoGetter.getCardNames();
        System.out.println("asdfasdf");
        for (String s : a) System.out.println("public void " + s + "(){\n\n}\n");
        LoginMenu.getInstance().run();
    }
}
