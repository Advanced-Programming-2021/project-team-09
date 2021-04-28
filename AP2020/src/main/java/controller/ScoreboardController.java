package controller;


import model.User;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardController {
    private ArrayList<User> sortedUsersByRank = ReadAndWriteDataBase.getAllUsers();
    public String showScoreBoard(){
        StringBuilder showingScoreboard = new StringBuilder();
        int[] rank = new int[sortedUsersByRank.size()];
        for (int i = 0; i < sortedUsersByRank.size(); i++) {
            if(rank[i] == 0){
                rank[i] = i +1;
            }
            if(sortedUsersByRank.get(i).getScore()==sortedUsersByRank.get(i +1).getScore()){
                rank[i +1] = rank[i];
            }
            showingScoreboard.append(rank[i] +"- "+ sortedUsersByRank.get(i).getNickname()+": "+sortedUsersByRank.get(i).getScore()+"\n");
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
                    Collections.swap(sortedUsersByRank,j,j + 1);
                }
                if(sortedUsersByRank.get(j).getScore()==sortedUsersByRank.get(j + 1).getScore()){
                    if(sortedUsersByRank.get(j).getNickname().compareToIgnoreCase(sortedUsersByRank.get(j+1).getNickname()) > 0){
                        Collections.swap(sortedUsersByRank, j , j + 1);
                    }
                }
            }
        }
    }
}
