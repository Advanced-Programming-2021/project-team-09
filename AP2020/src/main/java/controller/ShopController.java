package controller;
import model.User;
import model.card.Card;

public class ShopController {
    public void showAllCards(){
        System.out.println(csvInfoGetter.getCardNames());
    }
    public CurrentMenu showCurrentMenu(){
        return CurrentMenu.SHOP_MENU;
    }
    public void BuyCard(String cardName){
        User user = LoginMenuController.getCurrentUser();
        if(csvInfoGetter.cardNameExists(cardName)){
            if(csvInfoGetter.getPriceByCardName(cardName)<=user.getBalance()){
                user.addCard(csvInfoGetter.getCardByName(cardName));
                ReadAndWriteDataBase.updateUser(user);
            }
            else System.out.println("not enough money");
        }
        else {
            System.out.println("there is no card with this name");
        }
    }
}
