package controller;


import model.User;

import java.util.ArrayList;

public class ScoreboardController {
    private ArrayList<User> sortedUsersByRank = ReadAndWriteDataBase.getAllUsers();
    public String showScoreBoard(){
        StringBuilder showingScoreboard = new StringBuilder();
        for (User user: sortedUsersByRank) {
            //ToDo
        }
         return showingScoreboard.toString();
    }
    public CurrentMenu showCurrentMenu(){
        return CurrentMenu.SHOP_MENU;
    }
    public void sortUsersByRank(){

        int numberOfUsers = sortedUsersByRank.size();
        for (int i = 0; i < numberOfUsers - 1; i++) {
            for (int j = 0; j < numberOfUsers - i - 1; j++) {
                if(sortedUsersByRank.get(j).getScore() > sortedUsersByRank.get(j + 1).getScore()){
                    //ToDo
                }
            }
        }
    }
}
