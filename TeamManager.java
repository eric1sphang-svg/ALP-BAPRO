import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TeamManager {
    static boolean playerpass = true;
    static boolean maxfixture = false;
    static boolean sudahpunyataktik=false;
    static int matchaicounter=0;

    Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    public ArrayList<Team> teamArrList = new ArrayList<>();
    public ArrayList<Player> playerArrList = new ArrayList<>();
    public ArrayList<Battle> match = new ArrayList<>();
    public ArrayList<Battle> matchAI = new ArrayList<>();

    public void mainMenu() {
        boolean loop = true;
        while (loop) {
            int choice;
            while (true) {
                try {
                    System.out.println("==== SPORTS LEAGUE MANAGEMENT ====");
                    System.out.println("1. Add a team (1 word)");
                    System.out.println("2. Add a player (1 word)");
                    System.out.println("3. Team's List");
                    System.out.println("4. Train a player");
                    System.out.println("5. Transfer a player");
                    System.out.println("6. Modify player strength/stamina");
                    System.out.println("7. Adjust tactics");
                    System.out.println("8. Battle (min. 4 teams)");
                    System.out.println("9. Save & Exit");
                    System.out.print("Choose an option: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice > 0 && choice < 10) {
                        break;
                    } else{
                        System.out.println("Invalid Choice. Try again!\n");
                    }
                } catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Invalid Choice. Try again!\n");
                }
            }
            switch (choice) {
                case 1:
                    addTeam();
                    break;
                case 2:
                    addPlayer();
                    break;
                case 3:
                    showTeams();
                    System.out.println();
                    break;
                case 4: 
                    trainPlayer();
                    break;
                case 5:
                    transferPlayer();
                    break;
                case 6:
                    modifyPlayerStats();
                    break;
                case 7:
                    adjustTactics();
                    break;
                case 8:
                    playercounter();
                    if(!playerpass){
                        break;
                    }

                    sudahtaktik();
                    if (!sudahpunyataktik) {
                        break;
                    }

                    battle();
                    break;
                case 9:
                    saveProgress();
                    loop = false;
                    break;
                default:
                    System.out.println("invalid Choice. Try again!");
                    break;
            }
        }
    }

    private void showTeams(){
        System.out.println("Team's List: ");
        
        int i = 1;
        for (Team tm : teamArrList) {
            System.out.println(i + ". " + tm.getTeamName());
            i++;
        }
    }
    public void addTeam() {
        if (teamArrList.size() > 8) {
            System.out.println("Quota for teams is full!\n");
            return;
        }

        boolean loop = true;

        String name = "";
        while (loop) {
            System.out.print("Team name: ");
            name = sc.nextLine();

            char[] splitName = name.toLowerCase().toCharArray();

            //check if name is one word and not a number/symbol
            boolean valid = true;
            for (int i = 0; i < splitName.length; i++) {
                if (splitName[i] < 'a' || splitName[i] > 'z') {
                    valid = false;
                }
            }
            if (!valid || name.isEmpty()) {
                System.out.println("Invalid Team Name. Letters Only!");
                continue;
            }

            //check if the team name exists
            boolean exist = false;
            for(int i = 0; i < teamArrList.size(); i++){
                if(teamArrList.get(i).getTeamName().equalsIgnoreCase(name)){
                    exist = true;
                }
            }
            if(exist){
                System.out.println("Team name already exist! Pick another team name!");
                continue;
            }

            loop = false;
        }
        System.out.println("Team has been added!\n");

        Team team = new Team(name, 0);
        teamArrList.add(team);
    }

    public void addPlayer() {
        if (teamArrList.size() <= 0) {
            System.out.println("No teams added yet!\n");
            return;
        }
        System.out.println();
        showTeams();

        //check if there's team
        boolean loop = true;
        String teamName = "";
        while (loop) {
            System.out.print("Choose a team (enter team's name): ");
            teamName = sc.nextLine();
            for (int i = 0; i < teamArrList.size(); i++) {
                if (teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)) {
                    loop = false;
                }
            }
            if (loop) {
                System.out.println("There is no such team! Try again!");
            }
        }

        loop = true;
        String name = "";
        while (loop) {
            System.out.print("Player's name (1 word): ");
            name = sc.nextLine();

            char[] splitName = name.toLowerCase().toCharArray();

            //check if name is one word and not a number/symbol
            boolean valid = true;
            for (int i = 0; i < splitName.length; i++) {
                if (splitName[i] < 'a' || splitName[i] > 'z') {
                    valid = false;
                }
            }
            if (!valid || name.isEmpty()) {
                System.out.println("Invalid Team Name. Letters Only!");
                continue;
            }

            //check if the player name exists
            boolean exist = false;
            for(int i = 0; i < playerArrList.size(); i++){
                if(playerArrList.get(i).getPlayersTeam().equalsIgnoreCase(teamName) && playerArrList.get(i).getPlayerName().equals(name)){
                    exist = true;
                    break;
                }
            }
            if(exist){
                System.out.println("Player's name on this team already exist! Pick another name!");
                continue;
            }

            loop = false;
        }

        loop = true;
        int strength = 0;
        while (loop) {
            try {
                System.out.print("Player's Strength (1-100): ");
                strength = sc.nextInt();
                sc.nextLine();
                if (strength > 0 && strength <= 100) {
                    loop = false;
                } else {
                    System.out.println("Enter the right amount!");
                }
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid Player's Strength. Try again!");
            }

        }

        loop = true;
        int stamina = 0;
        while (loop) {
            try {
                System.out.print("Player's Stamina (50-200): ");
                stamina = sc.nextInt();
                sc.nextLine();
                if (stamina >= 50 && stamina <= 200) {
                    loop = false;
                } else {
                    System.out.println("Enter the right amount!");
                }
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid Player's Stamina. Try again!");
            }
        }

        System.out.println("Player " + name + " has been added to the " + teamName + "'s roster");

        Player player = new Player(name, strength, stamina, false, teamName);
        playerArrList.add(player);
    }

    public void trainPlayer() {
        if (playerArrList.size() <= 0 || teamArrList.size() <= 0) {
            System.out.println("No teams/players added yet!\n");
            return;
        }

        System.out.println();
        showTeams();

        boolean outerLoop = true;
        while(outerLoop){

            boolean loop = true;
            String teamName = "";
            while (loop) {
                System.out.print("Select a team: ");
                teamName = sc.nextLine();
                for (int i = 0; i < teamArrList.size(); i++) {
                    if (teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)) {
                        loop = false;
                    }
                }
                if (loop) {
                    System.out.println("There is no such team! Try again!");
                }
            }

            showPlayers(teamName);

            loop = true;
            int index = 0;
            String name = "";
            while (loop) {
                System.out.print("Select a player to train: ");
                name = sc.nextLine();

                for(int i = 0; i < playerArrList.size(); i++){
                    if(playerArrList.get(i).getPlayerName().equalsIgnoreCase(name) && playerArrList.get(i).getPlayersTeam().equalsIgnoreCase(teamName)){
                        index = i;
                        loop = false;
                        break;
                    }
                }

                if(loop){
                    System.out.println("Invalid player name/player is not on this team!");
                }
            }

            if(playerArrList.get(index).getTrained() >= 5){
                System.out.println(playerArrList.get(index).getPlayerName() + " has already used all 5 training chances.");
            } else{

                playerArrList.get(index).setTrained();

                int roll = rand.nextInt(1, 11);
                int strength = rand.nextInt(3, 6);
                int stamina = rand.nextInt(5,9);

                if (roll <= 2) {
                    System.out.println(name + " is exhausted! Training failed!");
                } else {
                    playerArrList.get(index).setPlayerStrength(playerArrList.get(index).getPlayerStrength() + strength);
                    playerArrList.get(index).setPlayerStamina(playerArrList.get(index).getPlayerStamina() + stamina);

                    System.out.println(name + " has been trained! +" + strength + " | +" + stamina);
                }
                
                System.out.println("Remaining training chances: " + (5 - playerArrList.get(index).getTrained()));
            }

            loop = true;
            String choice = "";
            while(loop){
                System.out.print("Do you want to train another player? (yes/no): ");
                choice = sc.nextLine();

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

    public void transferPlayer() {
        if (teamArrList.size() <= 1 || playerArrList.size() <= 1) {
            System.out.println("No teams/players to transfer!\n");
            return;
        }

        System.out.println();
        showTeams();

        boolean outerLoop = true;
        while (outerLoop) {
            int indexTeam = 0;
            boolean loop = true;
            String teamName = "";
            while (loop) {
                System.out.print("Select a team: ");
                teamName = sc.nextLine();
                for (int i = 0; i < teamArrList.size(); i++) {
                    if (teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)) {
                        indexTeam = i;
                        loop = false;
                    }
                }
                if (loop) {
                    System.out.println("There is no such team! Try again!");
                }
            }

            showPlayers(teamName);

            int indexPlayer = 0;
            String name = "";
            loop = true;
            while (loop) {
                System.out.print("Select a player to transfer: ");
                name = sc.nextLine();

                //check if there is a name
                indexPlayer = 0;
                boolean continueOuterLoop = true;
                for (int i = 0; i < playerArrList.size(); i++) {
                    if (playerArrList.get(i).getPlayerName().equalsIgnoreCase(name)) {
                        continueOuterLoop = false;
                        indexPlayer = i;
                    }
                }
                if (continueOuterLoop) {
                    System.out.println("Enter the right players name!");
                    continue;
                }

                //check if the name is in the right team name
                if (!playerArrList.get(indexPlayer).getPlayersTeam().equalsIgnoreCase(teamName)) {
                    System.out.println("This player is not on " + teamName + "'s team");
                    continue;
                }

                loop = false;
            }

            int indexOtherTeam = -1;
            String otherTeamName = "";
            while(true){
                System.out.print("select a team for the player to be transfered to: ");
                otherTeamName = sc.nextLine();
                
                for(int i = 0; i < teamArrList.size(); i++){
                    if (teamArrList.get(i).getTeamName().equalsIgnoreCase(otherTeamName)) {
                        indexOtherTeam = i;
                        break;
                    }
                }

                if(indexOtherTeam == -1){
                    System.out.println("There is no such team!");
                    continue;
                }

                if(indexOtherTeam == indexTeam){
                    System.out.println("Player is already in this team!");
                    continue;
                }

                break;
            }

            while (true) {
                System.out.print("Are you sure? (yes/no): ");
                String choice = sc.nextLine();

                if (choice.equalsIgnoreCase("yes")) {
                    int num = rand.nextInt(1, 11);
                    int num1 = rand.nextInt(1, 11);
                    if (num <= 2) {
                        System.out.println(name + " doesn't want to be transferred!");
                    } else if (num1 == 1) {
                        System.out.println(otherTeamName + " don't agree with the transfer!");
                    } else {
                        System.out.println(name + " from " + teamName + " has been transferred to " + otherTeamName);
                        playerArrList.get(indexPlayer).setPlayersTeam(otherTeamName);
                    }
                    break;
                } else if (choice.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println("Invalid Input! Try Again!");
                }
            }

            loop = true;
            while (loop) {
                System.out.print("Do you want to transfer another player? (yes/no): ");
                String choice = sc.nextLine();
                if (choice.equalsIgnoreCase("no")) {
                    outerLoop = false;
                    break;
                } else if (choice.equalsIgnoreCase("yes")) {
                    break;
                } else {
                    System.out.println("Invalid input! Try again!");
                }
            }
        }
    }

    private void showPlayers(String teamName){
        System.out.println("Player's list:");

        int i = 1;
        for (Player p : playerArrList) {
            if(p.getPlayersTeam().equalsIgnoreCase(teamName)){
                System.out.println(i + ". " + p.getPlayerName() + ": " + p.getPlayerStrength() + " Strength | " + p.getPlayerStamina() + " Stamina");
                i++;
            }
        }
    }
    public void modifyPlayerStats() {
        if (playerArrList.size() <= 0 || teamArrList.size() <= 0) {
            System.out.println("No teams/players added yet!\n");
            return;
        }

        System.out.println();
        showTeams();

        boolean loop = true;
        String teamName = "";
        while (loop) {
            System.out.print("Select a team: ");
            teamName = sc.nextLine();
            for (int i = 0; i < teamArrList.size(); i++) {
                if (teamArrList.get(i).getTeamName().equalsIgnoreCase(teamName)) {
                    loop = false;
                }
            }
            if (loop) {
                System.out.println("There is no such team! Try again!");
            }
        }

        showPlayers(teamName);

        int indexPlayer = 0;
        String name = "";
        loop = true;
        while (loop) {
            System.out.print("Select a player to modify stats (enter player's name): ");
            name = sc.nextLine();

            //check if there is a name
            indexPlayer = 0;
            boolean continueOuterLoop = true;
            for (int i = 0; i < playerArrList.size(); i++) {
                if (playerArrList.get(i).getPlayerName().equalsIgnoreCase(name)) {
                    continueOuterLoop = false;
                    indexPlayer = i;
                }
            }
            if (continueOuterLoop) {
                System.out.println("Enter the right players name!");
                continue;
            }

            //check if the name is in the right team name
            if (!playerArrList.get(indexPlayer).getPlayersTeam().equalsIgnoreCase(teamName)) {
                System.out.println("This player is not on " + teamName + "'s team");
                continue;
            }

            loop = false;
        }

        boolean outerLoop = true;
        while (outerLoop) {
            loop = true;
            int choice = 0;
            while (loop) {
                System.out.println("=== Modify Stats ===");
                System.out.println("1. Strength");
                System.out.println("2. Stamina");
                try {
                    System.out.print("Choose an Option: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > 2) {
                        System.out.println("Invalid Option! Try again!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Option! Try again!");
                }
            }

            if (choice == 1) {
                int strength = 0;
                while (true) {
                    System.out.print("Enter Strength (1-100): ");
                    strength = sc.nextInt();
                    sc.nextLine();
                    if (strength > 0 && strength <= 100) {
                        break;
                    } else {
                        System.out.println("Enter the right amount!");
                    }
                }
                System.out.println(name + "'s new stats: " + strength + " strength");
                playerArrList.get(indexPlayer).setPlayerStrength(strength);
            } else {
                int stamina = 0;
                while (true) {
                    System.out.print("Enter Stamina (50-200): ");
                    stamina = sc.nextInt();
                    sc.nextLine();
                    if (stamina >= 50 && stamina <= 200) {
                        break;
                    } else {
                        System.out.println("Enter the right amount!");
                    }
                }
                System.out.println(name + "'s new stats: " + stamina + " stamina");
                playerArrList.get(indexPlayer).setPlayerStamina(stamina);
            }

            loop = true;
            while (loop) {
                System.out.print("Do you want to Modify another stats? (yes/no): ");
                String choiceModify = sc.nextLine();
                if (choiceModify.equalsIgnoreCase("no")) {
                    outerLoop = false;
                    break;
                } else if (choiceModify.equalsIgnoreCase("yes")) {
                    break;
                } else {
                    System.out.println("Invalid input! Try again!");
                }
            }
        }
    }

    public void adjustTactics() {
        if (teamArrList.size() <= 0) {
            System.out.println("No teams added yet!\n");
            return;
        }

        System.out.println();
        showTeams();

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

        loop = true;
        String tactics = "";
        while(loop){
            System.out.println("Rules:");
            System.out.println("1. Use '-' to seperate");
            System.out.println("2. Numbers must add up to 10");
            System.out.println("3. Only use 1 '-' minimum and 2 '-' maximum");
            System.out.println("ex: 5-5; ex: 4-4-2");
            System.out.print("Choose tactics: ");

            tactics = sc.nextLine();
            char[] splitTactics = tactics.toCharArray();

            //check if input only use 1 '-' minimum and 2 '-' maximum
            if(splitTactics.length > 5 || splitTactics.length < 3){
                System.out.println("Enter the right input! Use 1 '-' minimum and 2 '-' maximum");
                continue; 
            }
            
            //check if there's "-" and the odd index is a number and the number adds up to 10
            boolean valid = true;
            int dashCount = 0;
            int total = 0;

            for(int i = 0; i < splitTactics.length; i++){
                if(i % 2 == 0){ //genap (posisi angka)
                    if(splitTactics[i] >= '0' && splitTactics[i] <= '9'){
                        total += Character.getNumericValue(splitTactics[i]);
                    } else{
                        valid = false;
                        break;
                    }
                } else{ //ganjil (posisi '-')
                    if (splitTactics[i] == '-') {
                        dashCount++;
                    } else {
                        valid = false;
                        break;
                    }
                }
            }

            if(!valid){
                System.out.println("Enter the right input! Use numbers and '-' only!");
                continue;
            }

            if(dashCount < 1 || dashCount > 2){
                System.out.println("Enter the right input! Use 1 or 2 '-' only to seperate!");
                continue;
            }

            if(total != 10){
                System.out.println("Enter the right input! Numbers must add up to 10");
                continue;
            }
            
            //if valid
            loop = false;
        }

        boolean famousFormation1 = tactics.equals("4-4-2");
        boolean famousFormation2 = tactics.equals("4-3-3");
        boolean famousFormation3 = tactics.equals("3-5-2");

        int statsPower = 0;
        if (famousFormation1) {
            statsPower = rand.nextInt(330, 401);
        }
        else if (famousFormation2) {
            statsPower = rand.nextInt(300, 371);
        }
        else if (famousFormation3) {
            statsPower = rand.nextInt(280, 351);
        }
        else {
            statsPower = rand.nextInt(250, 301);
        }

        teamArrList.get(indexTeam).setStatspower(statsPower);
        teamArrList.get(indexTeam).sudahstrats();
        System.out.println(teamName + " uses " + tactics + " strategy!\n");
    }

    private int currentMatch = 0;
    public void battle() {
        currentMatch = 0;
        if (teamArrList.size() < 4 || playerArrList.size() < 44) {
            System.out.println("Not enough teams/players!\n");
            return;
        }

        System.out.println("=== The Sports League has BEGUN! ===");
        showTeams();

        boolean outerLoop = true;
        while (outerLoop) {
            boolean loop = true;
            int choice = 0;
            while (loop) {
                System.out.println("==========================");
                System.out.println("1. Match Fixture");
                System.out.println("2. Team Scores");
                System.out.println("3. League Standings");
                System.out.println("4. Battle");
                System.out.println("5. Save & Exit");
                System.out.println("==========================");
                try {
                    System.out.print("Choose an Option");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice <1 || choice > 4){
                        System.out.println("Invalid Option! Try again!");
                    } else {
                        if (!maxfixture) {
                            if (choice > 2) {
                                System.out.println("No Standings yet, Run Fixture First");
                            } else {
                                loop=false;
                            }
                        } else {
                            loop=false;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Option! Try again!");
                }
            }
            switch (choice) {
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
                    battleAI();
                    startBattle();
                    break;

                case 5:
                    saveProgress();
                    break;

                default:
                    System.out.println("Invalid Option! Try again!");
                    break;
            }
        }
    }

    private void matchFixture() {
        match.clear();

        for (int i = 0; i < teamArrList.size(); i++) {
            for (int j = 0; j < teamArrList.size(); j++) {
                if (i == j) {
                    continue;
                }
                match.add(new Battle(teamArrList.get(i), teamArrList.get(j)));
            }
        }

        for (int i = 0; i < match.size(); i++) {
            System.out.println(match.get(i).getTeamA().getTeamName() + " vs " + match.get(i).getTeamB().getTeamName());
        }
        maxfixture=true;
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
            System.out.println("Score   : " + team.getTeamScore()+"\n");
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
        
        Team teamA = match.get(currentMatch).getTeamA();
        Team teamB = match.get(currentMatch).getTeamB();

        String teamAName = teamA.getTeamName();
        String teamBName = teamB.getTeamName();

        teamA.setTeamPower(playerArrList, teamAName);
        teamB.setTeamPower(playerArrList, teamBName);

        int teamAPower = teamA.getTeamPower();
        int statspowerA = teamA.getStatspower();
        teamAPower = (int)(teamAPower*0.6) + (int)(statspowerA*0.4);

        int teamBPower = teamB.getTeamPower();
        int statspowerB = teamB.getStatspower();
        teamBPower = (int)(teamBPower*0.6) + (int)(statspowerB*0.4);

        
        
        System.out.println(teamAName + " (" + teamAPower + ") VS " + teamBName + " (" + teamBPower+ ")");

        int powerDiff = teamAPower - teamBPower;
        if(powerDiff < 0){
            System.out.println("Team " + teamBName + " wins! Score +2");
            teamB.setTeamWins();
            teamA.setTeamLoses();
            
        } else if(powerDiff == 0){
            System.out.println("Draw! Score +1 for both teams!");
            teamA.setTeamDraw();
            teamB.setTeamDraw();
        } else{
            System.out.println("Team " + teamAName + " wins! Score +2");
            teamA.setTeamWins();
            teamB.setTeamLoses();
        }

        currentMatch += 1;
    }

    private void battleAI(){
        System.out.println("Do you want to play VS AI?");
        String pilih = sc.next() + sc.nextLine();

        String timUser="";
        if (pilih.equalsIgnoreCase("Yes")) {
            System.out.println("You have to choose your best team: ");
            for (int i = 0; i < teamArrList.size(); i++) {
                System.out.println((i + 1) + ". " + teamArrList.get(i).getTeamName());
            }

            boolean ulang = true;
            while (ulang) {
                try {
                    System.out.print("Choose your team: ");
                    String pilihTim = sc.next() + sc.nextLine();
                    boolean ulangs = true;
                    for (int i = 0; i < teamArrList.size(); i++) {
                        if (pilihTim.equalsIgnoreCase(teamArrList.get(i).getTeamName())) {
                            ulangs = false;
                            System.out.println("Team has been selected");
                            timUser = teamArrList.get(i).getTeamName();
                        }
                    }
                    if (ulangs) {
                        System.out.println("Team not found! Try again!\n");
                    } else {
                        ulang = false;
                    }
                } catch (Exception c) {
                    System.out.println("Invalid Input! Try again!");
                }
            }

            boolean salah = true;
            String choice= "";
            while (salah) {
                System.out.println("Pilih difficulty:\nEasy\nModerate\nHard");
                choice = sc.next() + sc.nextLine();
                if (choice.equalsIgnoreCase("easy") || choice.equalsIgnoreCase("moderate") || choice.equalsIgnoreCase("hard")) {
                    salah = false;
                    choice.toLowerCase();
                }
            }

            int aiStrength = 0;
            int aiStamina = 0;
            int aiPower = 0;
            int aiStrats = 0;
            String aiTeamName = "";
                switch (choice) {
                    case "easy":
                        aiTeamName = "AI FC EASY";
                        for (int i = 0; i < 11; i++){
                            aiStrength += rand.nextInt(1,26);
                            aiStamina += rand.nextInt(50,91);
                        }
                        aiStrats = rand.nextInt(250,260);
                        break;
                    case "moderate":
                        aiTeamName = "AI FC MODERATE";
                        for (int i = 0; i < 11; i++){
                            aiStrength += rand.nextInt(26,76);
                            aiStamina += rand.nextInt(91,151);
                        }
                        aiStrats = rand.nextInt(300,371);
                        break;
                    case "hard":
                        aiTeamName = "AI FC HARDCORE";
                        for (int i = 0; i < 11; i++){
                            aiStrength += rand.nextInt(76,101);
                            aiStamina += rand.nextInt(151,201);
                        }
                        aiStrats = rand.nextInt(371,401);
                        break;
                }
                aiPower = (int)(aiStrength * 0.8) + (int)(aiStamina * 0.2);
                aiPower = (int)(aiPower * 0.6) + (int)(aiStrats * 0.4);

            System.out.println("You are facing " + aiTeamName);
            matchAI.get(matchaicounter).getTeamA().setTeamPower(playerArrList, timUser);

            int userpower = match.get(matchaicounter).getTeamA().getTeamPower();
            int statspowerA = match.get(currentMatch).getTeamA().getStatspower();

            userpower = (int)(userpower * 0.6) + (int)(statspowerA * 0.4);
            if (userpower < aiPower) {
                System.out.println("Unfortunately you are beaten by " + aiTeamName);
            } else if (userpower > aiPower) {
                System.out.println("YOU BEAT " + aiTeamName + "!!!");
            }
        }
    }

    public void playercounter(){
        int[] playercount = new int [8];
        for (int i = 0; i < teamArrList.size(); i++){
            for (Player player : playerArrList){
                if (player.getPlayersTeam().equalsIgnoreCase(teamArrList.get(i).getTeamName())){
                    playercount[i] += 1;
                }
            }
        }
        for (int i = 0; i < teamArrList.size(); i++){
            if (playercount[i] < 11){
                System.out.println("Need min. 11 players in a team to start battle!");
                playerpass = false;
                break;
            }
        }
    }

    public void sudahtaktik(){
        sudahpunyataktik=true;
        for (Team team : teamArrList){
            boolean sementara = team.strats();
            if (!sementara){
                System.out.println(team.getTeamName()+" have not decide what tactics to use!");
                sudahpunyataktik=false;
                break;
            }
        }
    }

    /*
    format save:
    team arrlist size
    namatim,score,wins,loses,draw,statsPower,punyaStrats
    player arrlist size
    nama,strength,stamina,injured,team,trained
    */
    public void saveProgress() {
        try (FileWriter fw = new FileWriter("savegame.txt")){

            fw.write(teamArrList.size() + "\n"); // team size
            for (Team t : teamArrList) { // Format: namatim,score,wins,loses,draw,statsPower,punyaStrats
                fw.write(
                    t.getTeamName() + "," + 
                    t.getTeamScore() + "," + 
                    t.getTeamWins() + "," + 
                    t.getTeamLoses() + "," + 
                    t.getTeamDraw() + "," + 
                    t.getStatspower() + "," + 
                    t.strats() + "\n"
                );
            }

            fw.write(playerArrList.size() + "\n");
            for (Player p : playerArrList) {// Format: nama,strength,stamina,injured,team,trained
                fw.write(
                    p.getPlayerName() + "," +       //0
                    p.getPlayerStrength() + "," +   //1
                    p.getPlayerStamina() + "," +    //2
                    p.getInjured() + "," +          //3
                    p.getPlayersTeam() + "," +      //4
                    p.getTrained()  + "\n"          //5
                );
            }

            System.out.println("Progress successfully saved to savegame.txt!");
            
        } catch (IOException e) {
            System.out.println("Error saving progress: " + e.getMessage());
        }
    }

    public void loadProgress() {
        File savefile = new File("savegame.txt");
        if (!savefile.exists()) {
            System.out.println("No save file found!\n");
            return;
        }

        try (Scanner fileScanner = new Scanner(savefile)) {
            teamArrList.clear();
            playerArrList.clear();

            int teamCount = Integer.parseInt(fileScanner.nextLine());
            for (int i = 0; i < teamCount; i++) {
                String[] data = fileScanner.nextLine().split(",");
                
                Team t = new Team(
                    data[0],                     
                    Integer.parseInt(data[1]),   
                    Integer.parseInt(data[2]),   
                    Integer.parseInt(data[3]),   
                    Integer.parseInt(data[4]),   
                    Integer.parseInt(data[5]),   
                    Boolean.parseBoolean(data[6])
                );

                teamArrList.add(t);
            }

            int playerCount = Integer.parseInt(fileScanner.nextLine());
            for (int i = 0; i < playerCount; i++) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                Player p = new Player(
                    parts[0],
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Boolean.parseBoolean(parts[3]),
                    parts[4]
                );

                int trainedCount = Integer.parseInt(parts[5]);
                for(int j = 0; j < trainedCount; j++) { 
                    p.setTrained(); 
                }

                playerArrList.add(p);
            }

            System.out.println("Progress loaded successfully! " + teamArrList.size() + " teams and " + playerArrList.size() + " players loaded.");

        } catch (Exception e) {
            System.out.println("Error loading progress: " + e.getMessage());
        }
    }
}