package controller;

import model.User;

public class ProfileController {
    public static String changePassword(String oldPassword, String newPassword) {
        User user = LoginMenuController.getCurrentUser();
        if (user.getPassword().equals(oldPassword)) {
            if (!oldPassword.equals(newPassword)) {
                user.changePassword(newPassword);
                ReadAndWriteDataBase.updateUser(user);
                return "password changed successfully!";
            } else return "please enter a new password";
        } else return "current password is invalid";
    }

    public static String changeNickname(String nickname){
        User user = LoginMenuController.getCurrentUser();
        if (!LoginMenuController.doesNicknameExists(nickname)){
            user.changeNickname(nickname);
            ReadAndWriteDataBase.updateUser(user);
            return "nick name changed successfully!";
        } else return "user with nickname " + nickname + " exits";
    }

    public static void logout(){
        LoginMenuController.logout();
    }

 


}
