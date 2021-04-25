package controller;
import model.User;

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
            if(user.hasEnoughBalance(csvInfoGetter.getPriceByCardName(cardName))){
                user.addCard(csvInfoGetter.getCardByName(cardName));
                user.decreaseBalance(csvInfoGetter.getPriceByCardName(cardName));
                ReadAndWriteDataBase.updateUser(user);
            }
            else System.out.println("not enough money");
        }
        else {
            System.out.println("there is no card with this name");
        }
    }
}
