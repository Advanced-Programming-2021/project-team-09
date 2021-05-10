package view.responses;

public class GameMenuResponse {
    private Object obj;
    private GameMenuResponsesEnum gameMenuResponseEnum;

    public GameMenuResponse(Object obj, GameMenuResponsesEnum gameMenuResponseEnum) {
        this.obj = obj;
        this.gameMenuResponseEnum = gameMenuResponseEnum;
    }

    public GameMenuResponse(GameMenuResponsesEnum gameMenuResponseEnum){
        this.gameMenuResponseEnum = gameMenuResponseEnum;
    }

    public Object getObj() {
        return obj;
    }

    public GameMenuResponsesEnum getGameMenuResponseEnum() {
        return gameMenuResponseEnum;
    }
}
