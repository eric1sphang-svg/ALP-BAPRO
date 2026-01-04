import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TeamManager {

    Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    public ArrayList<Team> teamArrList = new ArrayList<>();
    public ArrayList<Player> playerArrList = new ArrayList<>();
    public ArrayList<Battle> match = new ArrayList<>();

    public void mainMenu(){
        boolean loop = true;
        while(loop){
            System.out.println("==== SPORTS LEAGUE MANAGEMENT ====");
            System.out.println("1. Add a team");
            System.out.println("2. Add a player");
            System.out.println("3. Train a player");
            System.out.println("4. Transfer a player");
            System.out.println("5. Modify player strength/stamina");
            System.out.println("6. Adjust tactics");
            System.out.println("7. Battle (min. 4 teams)");
            int choice;
            while(true){
                try {
                    System.out.print("Choose an option: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if(choice > 0 && choice < 8){
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Choice. Try again!");
                }
            }
            switch(choice){
                case 1:
                    addTeam();
                    break;
                case 2:
                    addPlayer();
                    break;
                case 3:
                    trainPlayer();
                    break;
                case 4:
                    transferPlayer();
                    break;
                case 5:
                    modifyPlayerStats();
                    break;
                case 6:
                    adjustTactics();
                    break;
                case 7:
                    battle();
                    break;
                default:
                    System.out.println("invalid Choice. Try again!");
                    break;
            }
        }
    }

    public void addTeam(){
        if(teamArrList.size() > 8){
            System.out.println("Quota for teams is full!");
            return;
        }

        boolean loop = true;

        String name = "";
        while(loop){
            System.out.print("Team name: ");
            name = sc.nextLine();

            char[] splitName = name.toLowerCase().toCharArray();

            for(int i = 0; i < splitName.length; i++){
                if(splitName[i] >= 'a' || splitName[i] <= 'z'){
                    loop = false;
                }
            }

            if(loop){
                System.out.println("Invalid Team Name. Letters Only!");
            }
        }
        System.out.println("Team has been added!");

        Team team = new Team(name, 0);
        teamArrList.add(team);
    }

    public void addPlayer(){
        boolean loop = true;
        String teamName = "";
        while(loop){
            System.out.print("Choose a team: ");
            teamName = sc.nextLine();
            for(int i = 0; i < teamArrList.size(); i++){
                if(teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)){
                    loop = false;
                }
            }
            if(loop){
                System.out.println("There is no such team! Try again!");
            }
        }

        loop = true;
        String name = "";
        while(loop){
            System.out.print("Player's name: ");
            name = sc.nextLine();

            char[] splitName = name.toLowerCase().toCharArray();

            for(int i = 0; i < splitName.length; i++){
                if(splitName[i] >= 'a' || splitName[i] <= 'z'){
                    loop = false;
                }
            }

            if(loop){
                System.out.println("Invalid Player's Name. Letters Only!");
            }
        }

        loop = true;
        int strength = 0;
        while(loop){
            try {
                System.out.print("Player's Strength (1-100): ");
                strength = sc.nextInt();
                sc.nextLine();
                if(strength > 0 && strength <= 100){
                    loop = false;
                } else{
                    System.out.println("Enter the right amount!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Player's Strength. Try again!");
            }
            
        }
        
        loop = true;
        int stamina = 0;
        while(loop){
            try {
                System.out.print("Player's Stamina (50-200): ");
                stamina = sc.nextInt();
                sc.nextLine();
                if(stamina >= 50 && stamina <= 200){
                    loop = false;
                } else{
                    System.out.println("Enter the right amount!");
                }
            } catch (Exception e) {
                System.out.println("Invalid Player's Stamina. Try again!");
            }
        }

        System.out.println("Player " + name + " has been added to the " + teamName + "'s roster");

        Player player = new Player(name, strength, stamina, false, teamName);
        playerArrList.add(player);
    }

    //bisa aja kalau sudah train, bisa di train lagi misal player 1 sudah train tapi nanti bisa pilih train untuk player 1 lagi
    public void trainPlayer(){
        if(playerArrList.size() <= 0){
            System.out.println("No players added yet!");
            return;
        }

        for(int j = 0; j < 5; j++){
            System.out.println("Chance to train: " + j);

            boolean loop = true;
            String teamName = "";
            while(loop){
                System.out.print("Select a team: ");
                teamName = sc.nextLine();
                for(int i = 0; i < teamArrList.size(); i++){
                    if(teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)){
                        loop = false;
                    }
                }
                if(loop){
                    System.out.println("There is no such team! Try again!");
                }
            }

            loop = true;
            int index = 0;
            String name = "";
            while(loop){
                System.out.print("Select a player to train");
                name = sc.nextLine();

                //check if there is a name
                index = 0;
                boolean continueOuterLoop = true;
                for(int i = 0; i < playerArrList.size(); i++){
                    if(playerArrList.get(i).getPlayerName().equalsIgnoreCase(name)){
                        continueOuterLoop = false;
                        index = i;
                    }
                }
                if(continueOuterLoop){
                    continue;
                }

                //check if the name is in the right team name
                if(!playerArrList.get(index).getPlayersTeam().equalsIgnoreCase(teamName)){
                    System.out.println("This player is not on " + teamName + "'s team");
                    continue;
                }
            }
            
            int num = rand.nextInt(1, 11);
            int strength = rand.nextInt(3, 6);
            int stamina = rand.nextInt(5, 9);
            if(num <= 2){
                System.out.println(name + " is exhausted!");
            } else{
                System.out.println(name + " has been trained! +" + strength + " | +" + stamina);
                playerArrList.get(index).setPlayerStrength(strength);
                playerArrList.get(index).setPlayerStamina(stamina);
            }

            loop = true;
            boolean breakLoop = false;
            while(loop){
                System.out.println("Do you want to train another player? (yes/no): ");
                String choice = sc.nextLine();
                if(choice.equalsIgnoreCase("no")){
                    breakLoop = true;
                    break;
                } else if(!choice.equalsIgnoreCase("yes")){
                    System.out.println("Invalid input! Try again!");
                } else{
                    break;
                }
            }
            if(breakLoop){
                break;
            }
        }
    }

    public void transferPlayer(){
        if(teamArrList.size() <= 1){
            System.out.println("No teams to transfer to!");
            return;
        }

        boolean outerLoop = true;
        while(outerLoop){
            int indexTeam = 0;
            boolean loop = true;
            String teamName = "";
            while(loop){
                System.out.print("Select a team: ");
                teamName = sc.nextLine();
                for(int i = 0; i < teamArrList.size(); i++){
                    if(teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)){
                        indexTeam = i;
                        loop = false;
                    }
                }
                if(loop){
                    System.out.println("There is no such team! Try again!");
                }
            }
            
            int indexPlayer = 0;
            String name = "";
            loop = true;
            while(loop){
                System.out.print("Select a player to transfer: ");
                name = sc.nextLine();

                //check if there is a name
                indexPlayer = 0;
                boolean continueOuterLoop = true;
                for(int i = 0; i < playerArrList.size(); i++){
                    if(playerArrList.get(i).getPlayerName().equalsIgnoreCase(name)){
                        continueOuterLoop = false;
                        indexPlayer = i;
                    }
                }
                if(continueOuterLoop){
                    continue;
                }

                //check if the name is in the right team name
                if(!playerArrList.get(indexPlayer).getPlayersTeam().equalsIgnoreCase(teamName)){
                    System.out.println("This player is not on " + teamName + "'s team");
                    continue;
                }
            }

            System.out.print("select a team for the player to be transfered to: ");
            String otherTeamName = sc.nextLine();
            if(!teamArrList.get(indexTeam).getTeamName().equalsIgnoreCase(otherTeamName)){
                System.out.println("There is no such team!");
                return;
            }

            while(true){
                System.out.print("Are you sure? (yes/no): ");
                String choice = sc.nextLine();

                if(choice.equalsIgnoreCase("yes")){
                    int num = rand.nextInt(1, 11);
                    int num1 = rand.nextInt(1, 11);
                    if(num <= 2){
                        System.out.println(name + " doesn't want to be transferred!");
                    } else if(num1 == 1){
                        System.out.println(otherTeamName + " don't agree with the transfer!");
                    } else{
                        System.out.println(name + " from " + teamName + " has been transferred to " + otherTeamName);
                        playerArrList.get(indexPlayer).setPlayersTeam(otherTeamName);
                    }
                    break;
                } else if(choice.equalsIgnoreCase("no")){
                    break;
                } else{
                    System.out.println("Invalid Input! Try Again!");
                }
            }
            
            loop = true;
            while(loop){
                System.out.print("Do you want to transfer another player? (yes/no): ");
                String choice = sc.nextLine();
                if(choice.equalsIgnoreCase("no")){
                    outerLoop = false;
                    break;
                } else if(choice.equalsIgnoreCase("yes")){
                    break;
                } else{
                    System.out.println("Invalid input! Try again!");
                }
            }
        }
    }

    public void modifyPlayerStats(){
        boolean loop = true;
        String teamName = "";
        while(loop){
            System.out.print("Select a team: ");
            teamName = sc.nextLine();
            for(int i = 0; i < teamArrList.size(); i++){
                if(teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)){
                    loop = false;
                }
            }
            if(loop){
                System.out.println("There is no such team! Try again!");
            }
        }

        int indexPlayer = 0;
        String name = "";
        loop = true;
        while(loop){
            System.out.print("Select a player to modify stats: ");
            name = sc.nextLine();

            //check if there is a name
            indexPlayer = 0;
            boolean continueOuterLoop = true;
            for(int i = 0; i < playerArrList.size(); i++){
                if(playerArrList.get(i).getPlayerName().equalsIgnoreCase(name)){
                    continueOuterLoop = false;
                    indexPlayer = i;
                }
            }
            if(continueOuterLoop){
                continue;
            }

            //check if the name is in the right team name
            if(!playerArrList.get(indexPlayer).getPlayersTeam().equalsIgnoreCase(teamName)){
                System.out.println("This player is not on " + teamName + "'s team");
                continue;
            }
        }

        boolean outerLoop = true;
        while(outerLoop){
            loop = true;
            int choice = 0;
            while(loop){
                System.out.println("=== Modify Stats ===");
                System.out.println("1. Strength");
                System.out.println("2. Stamina");
                try {
                    System.out.print("Choose an Option");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if(choice < 1 || choice > 2){
                        System.out.println("Invalid Option! Try again!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Option! Try again!");
                }
            }

            if(choice == 1){
                int strength = 0;
                while(true){
                    System.out.print("Enter Strength (1-100): ");
                    strength = sc.nextInt();
                    sc.nextLine();
                    if(strength > 0 && strength <= 100){
                        break;
                    } else{
                        System.out.println("Enter the right amount!");
                    }
                }
                System.out.println(name + "'s new stats: " + strength + " strength");
                playerArrList.get(indexPlayer).setPlayerStrength(strength);
            } else{
                int stamina = 0;
                while(true){
                    System.out.print("Enter Stamina (50-200): ");
                    stamina = sc.nextInt();
                    sc.nextLine();
                    if(stamina >= 50 && stamina <= 200){
                        break;
                    } else{
                        System.out.println("Enter the right amount!");
                    }
                }
                System.out.println(name + "'s new stats: " + stamina + " stamina");
                playerArrList.get(indexPlayer).setPlayerStamina(stamina);
            }

            loop = true;
            while(loop){
                System.out.print("Do you want to Modify another stats? (yes/no): ");
                String choiceModify = sc.nextLine();
                if(choiceModify.equalsIgnoreCase("no")){
                    outerLoop = false;
                    break;
                } else if(choiceModify.equalsIgnoreCase("yes")){
                    break;
                } else{
                    System.out.println("Invalid input! Try again!");
                }
            }
        }
    }

    public void adjustTactics(){
        boolean loop = true;
        String teamName = "";
        while(loop){
            System.out.print("Select a team: ");
            teamName = sc.nextLine();
            for(int i = 0; i < teamArrList.size(); i++){
                if(teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)){
                    loop = false;
                }
            }
            if(loop){
                System.out.println("There is no such team! Try again!");
            }
        }

        loop = true;
        while(loop){
            System.out.println("Rules:");
            System.out.println("1. Use '-' to seperate");
            System.out.println("2. Numbers must add up to 10");
            System.out.println("3. Only use 1 '-' minimum and 2 '-' maximum");
            System.out.println("ex: 5-5; ex: 4-4-2");
            System.out.println("Choose tactics: ");
            String tactics = sc.nextLine();
            char[] splitTactics = tactics.toCharArray();

            //check if input only use 1 '-' minimum and 2 '-' maximum
            if(splitTactics.length > 5 || splitTactics.length < 3){
                System.out.println("Enter the right input! Use 1 '-' minimum and 2 '-' maximum");
                continue; 
            }
            
            //check if there's "-" and the odd index is a number and the number adds up to 10
            boolean checkNum = false;
            boolean checkDash = false;
            int total = 0;
            for(int i = 0; i < splitTactics.length; i++){
                if(i % 2 != 0){ //ganjil
                    if(splitTactics[i] >= '0' && splitTactics[i] <= '9'){
                        total += Character.getNumericValue(splitTactics[i]);
                        checkNum = true;
                    }
                } else{ //genap
                    if(splitTactics[i] == '-'){
                        checkDash = true;
                    }
                }
            }

            if(!checkNum){
                System.out.println("Enter the right input! Use numbers!");
                continue;
            }
            if(!checkDash){
                System.out.println("Enter the right input! Use '-' to seperate!");
                continue;
            }
            if(total != 10){
                System.out.println("Enter the right input! Numbers must add up to 10");
                continue;
            }

        }
    }

    private int currentMatch = 0;
    public void battle(){
        currentMatch = 0;
        if(teamArrList.size() < 4){
            System.out.println("Not enough teams!");
            return;
        }
        
        System.out.println("=== The Sports League has BEGUN! ===");
        System.out.println("Teams:");
        for(int i = 0; i < teamArrList.size(); i++){
            System.out.println((i+1) + ". " + teamArrList.get(i).getTeamName());
        }

        boolean outerLoop = true;
        while(outerLoop){
            boolean loop = true;
            int choice = 0;
            while(loop){
                System.out.println("==========================");
                System.out.println("1. Match Fixture");
                System.out.println("2. Team Scores");
                System.out.println("3. League Standings");
                System.out.println("4. Battle");
                System.out.println("==========================");
                try {
                    System.out.print("Choose an Option");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if(choice < 1 || choice > 2){
                        System.out.println("Invalid Option! Try again!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Option! Try again!");
                }
            }

            switch(choice){
                case 1:
                    matchFixture();
                    break;

                case 2:
                    teamScore();
                    break;

                case 3:
                    leagueStandings();
                    break;

                case 4:
                    startBattle();
                    break;
            
                default:
                    System.out.println("Invalid Option! Try again!");
                    break;
            }
        }
    }

    private void matchFixture(){
        match.clear();

        for(int i = 0; i < teamArrList.size(); i++){
            for(int j = 0; j < teamArrList.size(); j++){
                if(i == j){
                    continue;
                }
                match.add(new Battle(teamArrList.get(i), teamArrList.get(j)));
            }
        }
        
        for(int i = 0; i < match.size(); i++){
            System.out.println(match.get(i).getTeamA().getTeamName() + " vs " + match.get(i).getTeamB().getTeamName());
        }
    }

    private void teamScore(){
        System.out.println("List Scores: ");
        for(Team team : teamArrList){
            System.out.println(team.getTeamName() + ": " + team.getTeamScore());
        }
    }

    private void leagueStandings(){
        for(Team team : teamArrList){
            System.out.println(team.getTeamName() + ": ");
            System.out.println("Wins    : " + team.getTeamWins());
            System.out.println("Losses  : " + team.getTeamLoses());
            System.out.println("Draw    : " + team.getTeamDraw());
            System.out.println("Score   : " + team.getTeamScore());
        }
    }

    private void startBattle(){
        System.out.println("Match " + (currentMatch+1) + ":");
        System.out.println(match.get(currentMatch).getTeamA().getTeamName() + " vs " + match.get(currentMatch).getTeamB().getTeamName());
        boolean loop = true;
        String choice = "";
        while(loop){
            System.out.print("Do you want to start battle? (yes/no): ");
            choice = sc.nextLine();
            if(choice.equalsIgnoreCase("no")){
                break;
            } else if(choice.equalsIgnoreCase("yes")){
                break;
            } else{
                System.out.println("Invalid input! Try again!");
            }
        }

        if(choice.equalsIgnoreCase("no")){
            return;
        }

        String teamAName = match.get(currentMatch).getTeamA().getTeamName();
        String teamBName = match.get(currentMatch).getTeamB().getTeamName();
        int teamAPower = match.get(currentMatch).getTeamA().getTeamPower();
        int teamBPower = match.get(currentMatch).getTeamB().getTeamPower(); 

        match.get(currentMatch).getTeamA().setTeamPower(playerArrList, teamAName);
        match.get(currentMatch).getTeamB().setTeamPower(playerArrList, teamBName);
        
        System.out.println(teamAName + " (" + teamAPower + ") VS " + teamBName + " (" + teamBPower+ ")");

        int powerDiff = match.get(currentMatch).getTeamA().getTeamPower() - match.get(currentMatch).getTeamB().getTeamPower();
        if(powerDiff < 0){
            System.out.println("Team " + teamBName + " wins! Score +2");
            for(Team team : teamArrList){
                if(team.getTeamName().equalsIgnoreCase(teamBName)){
                    team.setTeamWins();
                    team.setTeamLoses();
                }
            }
            
        } else if(powerDiff == 0){
            System.out.println("Draw! Score +1 for both teams!");
            for(Team team : teamArrList){
                if(team.getTeamName().equalsIgnoreCase(teamBName)){
                    team.setTeamDraw();
                }
            }
            for(Team team : teamArrList){
                if(team.getTeamName().equalsIgnoreCase(teamAName)){
                    team.setTeamDraw();
                }
            }
        } else{
            System.out.println("Team " + teamAName + " wins! Score +2");
            for(Team team : teamArrList){
                if(team.getTeamName().equalsIgnoreCase(teamAName)){
                    team.setTeamWins();
                    team.setTeamLoses();
                }
            }
        }

        currentMatch += 1;
    }

}
