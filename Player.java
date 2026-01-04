public class Player {
    
    private String playerName;
    private String playersTeam;
    private int playerStrength;
    private int playerStamina;
    private int trained;
    private boolean injured;

    Player(String playerName, int playerStrength, int playerStamina, boolean injured, String playersTeam){
        this.playerName = playerName;
        this.playerStrength = playerStrength;
        this.playerStamina = playerStamina;
        this.injured = injured;
        this.playersTeam = playersTeam;
    }

    //setter
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public void setPlayersTeam(String playersTeam){
        this.playersTeam  = playersTeam;
    }

    public void setPlayerStrength(int playerStrength){
        this.playerStrength = playerStrength;
    }

    public void setPlayerStamina(int playerStamina){
        this.playerStamina = playerStamina;
    }

    public void setInjured(boolean injured){
        this.injured = injured;
    }

    public void setTrained(){
        trained++;
    }

    //getter
    public String getPlayerName(){
        return playerName;
    }

    public String getPlayersTeam(){
        return playersTeam;
    }

    public int getPlayerStrength(){
        return playerStrength;
    }

    public int getPlayerStamina(){
        return playerStamina;
    }

    public boolean getInjured(){
        return injured;
    }
    
    public int getTrained(){
        return trained;
    }
    
}
