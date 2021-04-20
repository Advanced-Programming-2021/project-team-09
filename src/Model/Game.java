package Model;

 class Game
{
    private User player;		
    private User rival;		
    private Deck playerDeck;		
    private Deck rivalDeck;		
    private ArrayList<Card> playerHandCards;		
    private ArrayList<Card> rivalHandCards;		
    private int playerLP;		
    private int rivalLP;		
    private int phaseCounter;		
    private boolean canSummonCard;		
    private Board playerboard;		
    private Board rivalBoard;		
    private boolean canRivalActiveSpell;		

    
    public void playerDrawCard() 		
    {
        
    }		
    
    public void rivalDrawCard() 		
    {
        
    }		
    
    public void changeTurn() 		
    {
        
    }		
    
    public String showTable() 		
    {
        
    }		
    
    public int getNumberOfCardsInHand() 		
    {
        
    }		
    
    public int getNumberOfCardsInHandFromRival() 		
    {
        
    }		
    
    public boolean hasCapacityToDraw() 		
    {
        
    }		
    
    public boolean isMonsterZoneFull() 		
    {
        
    }		
    
    public boolean isSpellZoneFull() 		
    {
        
    }		
    
    public boolean isThereEnoughMonstersToTribute(int amount) 		
    {
        
    }		
    
    public void summonMonster(Card card) 		
    {
        
    }		
    
    public void summonSpell(Card card) 		
    {
        
    }		
    
    public boolean canSummon() 		
    {
        
    }		
    
    public void summonWithTribute(Card card) 		
    {
        
    }		
    
    public void ritualSummon(Card card) 		
    {
        
    }		
    
    public void changePosition(Enum<State> state, int cellNumber) 		
    {
        
    }		
    
    public void attack(int numberOfAttackers, int numberOfDefenders) 		
    {
        
    }		
    
    public void directAttack(int numberOfAttackers) 		
    {
        
    }		
    
    public void increaseHealth(int amount) 		
    {
        
    }		
    
    public void decreaseHealth(int amount) 		
    {
        
    }		
    
    public void increaseRivalHealth(int amount) 		
    {
        
    }		
    
    public void decreaseRivalHealth(int amount) 		
    {
        
    }		
    
    public void activeEffect(int cellNumber) 		
    {
        
    }		
    
    public void activeEffectRival(int cellNumber) 		
    {
        
    }		
    
    public boolean canRivalActiveSpell() 		
    {
        
    }		
    
    public void changePhase() 		
    {
        
    }		
    
    public boolean canRitualSummon() 		
    {
        
    }		
    
    public String getGraveyard() 		
    {
        
    }		
}
