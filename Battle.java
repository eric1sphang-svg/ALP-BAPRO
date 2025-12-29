import java.util.ArrayList;

public class Battle {

    private Team teamA;
    private Team teamB;
    
    Battle(Team teamA, Team teamB){
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public Team getTeamA(){
        return teamA;
    }
    public Team getTeamB(){
        return teamB;
    }
}
