package controller;

import java.util.Collections;

public class ScoreboardController {
    public void showScoreBoard(){
        //TODO
        ReadAndWriteDataBase.getAllUsers().sort(Collections.reverseOrder());
    }
    public CurrentMenu showCurrentMenu(){
        return CurrentMenu.SHOP_MENU;
    }
}
