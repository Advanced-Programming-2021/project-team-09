package view.duelMenu;

import model.User;

public class WinnerStatus {
    private User winner;
    private User loser;
    private int winnerLP;
    private int loserLP;

    public WinnerStatus(User winner, User loser, int winnerLP, int loserLP){
        setLoser(loser);
        setWinner(winner);
        setWinnerLP(winnerLP);
        setLoserLP(loserLP);
    }

    public void setLoser(User loser) {
        this.loser = loser;
    }

    public void setWinner(User winner){
        this.winner = winner;
    }

    public void setLoserLP(int loserLP) {
        this.loserLP = loserLP;
    }

    public void setWinnerLP(int winnerLP) {
        this.winnerLP = winnerLP;
    }

    public int getLoserLP() {
        return loserLP;
    }

    public int getWinnerLP() {
        return winnerLP;
    }

    public User getLoser() {
        return loser;
    }

    public User getWinner() {
        return winner;
    }
}
