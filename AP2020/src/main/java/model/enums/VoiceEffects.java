package model.enums;

import javafx.scene.media.Media;
import view.graphics.Menu;

public enum VoiceEffects {
    ERROR(Menu.getVoice("Error","mp3")),
    CLICK(Menu.getVoice("Click","mp3")),
    KEYBOARD_HIT(Menu.getVoice("KeyPress","mp3")),
    CARD_FLIP(Menu.getVoice("CardFlip","mp3")),
    COIN_DROP(Menu.getVoice("CoinDrop","mp3"));
    javafx.scene.media.Media media;
    VoiceEffects(javafx.scene.media.Media media) {
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }
}
