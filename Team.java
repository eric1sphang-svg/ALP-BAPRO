import java.util.ArrayList;

public class Team {
    
    private String teamName;
    private int teamPower;
    private int teamScore;
    private int teamWins;
    private int teamLoses;
    private int teamDraw;

    Team(String teamName, int teamScore){
        this.teamName = teamName;
        this.teamPower = 0;
        this.teamScore = 0;
        this.teamWins = 0;
        this.teamLoses = 0;
        this.teamDraw = 0;
    }

    //setter
    public void setTeamPower(ArrayList<Player> playerArrList, String teamName){
        int totalStrength = 0;
        int totalStamina = 0;
        for(int i = 0; i < playerArrList.size(); i++){
            if(playerArrList.get(i).getPlayersTeam().equals(teamName)){
                totalStrength += playerArrList.get(i).getPlayerStrength();
                totalStamina += playerArrList.get(i).getPlayerStamina();
            }
        }
        this.teamPower = (int)(totalStrength*0.8) + (int)(totalStamina*0.2);
    }
    public void setTeamWins(){
        this.teamWins++;
        this.teamScore += 2;
    }
    public void setTeamLoses(){
        this.teamLoses++;
    }
    public void setTeamDraw(){
        this.teamDraw++;
        this.teamScore++;
    }

    //getter
    public String getTeamName(){
        return teamName;
    }
    public int getTeamPower(){
        return teamPower;
    }
    public int getTeamScore(){
        return teamScore;
    }
    public int getTeamWins(){
        return teamWins;
    }
    public int getTeamLoses(){
        return teamLoses;
    }
    public int getTeamDraw(){
        return teamDraw;
    }
}
