package World;

import Entities.Goalkeeper;
import Entities.Player;
import Entities.Team;
import Entities.Defender;
import Entities.Attacker;
import Entities.MiddleField;
import java.util.HashMap;
import java.util.Map;

public class Match {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private Player ballPossessingPlayer = null;
    private int ballPosition = 2;
    private int goalOne = 0;
    private int goalTwo = 4;
    private int playTime = 270;
    private Map<Team, Integer> matchScore = new HashMap<>();
    private Map<Team, Integer> goalPositionMap = new HashMap<>();
    private Map<Team, String> teamSymbolMap = new HashMap<>();
    private Map<String, Team> positionTeamMap = new HashMap<>();
    private Team[] teams;
    private boolean goalConverted = false;

    public Match(Team teamOne, Team teamTwo) {
        this.teams = new Team[]{teamOne, teamTwo};
        teamOne.setOpposingTeam(teamTwo);
        teamTwo.setOpposingTeam(teamOne);
        setScoreToZero();
        for (Team team : teams){
            for (Player player : team.getTeamMembers()){
                player.setMatchPlayed(this);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        boolean game_running = true;
        Team bM = new Team("Bayern");
        Team vfb = new Team("Stuttgart");
        Match finalMatch = new Match(bM, vfb);
        finalMatch.setStartingPosition(bM, vfb);
        finalMatch.ballPossessingPlayer = finalMatch.getStartingTeam(bM, vfb).getRandomMidfield();
        for(int i = 0; i < finalMatch.playTime; i++){
            Player passer = finalMatch.ballPossessingPlayer;
            finalMatch.ballPossessingPlayer = finalMatch.ballPossessingPlayer.handleBall();
        }
        System.out.println();
        finalMatch.printScore();
        System.out.println("Game is over");
    }
    private Team getStartingTeam(Team teamOne, Team teamTwo){
        if (Math.random() < 0.5){
            return teamOne;
        }else{
            return teamTwo;
        }
    }
    private void setStartingPosition(Team teamOne, Team teamTwo) {
        if (Math.random() < 0.5){
            goalPositionMap.put(teamOne, - 1);
            teamSymbolMap.put(teamOne, ANSI_GREEN + "x" + ANSI_RESET);
            positionTeamMap.put("left", teamOne);
            goalPositionMap.put(teamTwo, 1);
            teamSymbolMap.put(teamTwo, ANSI_BLUE + "o" + ANSI_RESET);
            positionTeamMap.put("right", teamTwo);
        }else{
            goalPositionMap.put(teamOne, 1);
            teamSymbolMap.put(teamOne, ANSI_GREEN + "o" + ANSI_RESET);
            positionTeamMap.put("right", teamOne);
            goalPositionMap.put(teamTwo, - 1);
            teamSymbolMap.put(teamTwo, ANSI_BLUE + "x" + ANSI_RESET);
            positionTeamMap.put("left", teamTwo);
        }
    }

    public Map<Team, Integer> getMatchScore() {
        return matchScore;
    }
    public void printScore(){
        for(Team team : matchScore.keySet()){
            System.out.println(team + Integer.toString(matchScore.get(team)));
        }
    }
    private void setScoreToZero () {
        for (Team team : teams) {
            matchScore.put(team, 0);
        }
    }

    public void setGoalConverted(boolean goalConverted) {
        this.goalConverted = goalConverted;
    }

    public void setBallPosition(int ballPosition) {
        this.ballPosition = ballPosition;
    }

    public void showMatchState() throws InterruptedException {
        int sleepTime;
        int passTime = 500;
        int goalTime = 2000;
        String displayMessage = "";
        int fieldWidth = 11;
        int fieldHeight = 4;
        String[][] matchState = new String[fieldWidth][fieldHeight];
        Map<Player, int[]> playerPositionMap = new HashMap<>();
        for (Team team: teams){
            for(Player player: team.getTeamMembers()){
                int teamsGoalPosition = goalPositionMap.get(team);
                int middleColumn = 5;
                int[] pos = new int[2];
                    if(player instanceof Goalkeeper){
                        pos = new int[]{middleColumn + teamsGoalPosition * 5, 1};
                        playerPositionMap.put(player, pos);
                    } else if (player instanceof Defender) {
                        pos = new int[]{middleColumn + teamsGoalPosition * 4, Integer.parseInt(player.getName())};
                        playerPositionMap.put(player, pos);
                    } else if (player instanceof MiddleField) {
                        pos = new int[]{middleColumn + teamsGoalPosition, Integer.parseInt(player.getName())};
                        playerPositionMap.put(player, pos);
                    } else if (player instanceof Attacker) {
                        pos = new int[]{middleColumn + teamsGoalPosition * -3, Integer.parseInt(player.getName())};
                        playerPositionMap.put(player, pos);
                    }
                matchState[pos[0]][pos[1]] = teamSymbolMap.get(player.getPlayersTeam());
                }
            }
        String space = "\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n\n \n \n \n \n \n \n \n \n \n \n \n";
        System.out.println(space);
        String header = "";
        header += "     " + positionTeamMap.get("left")+ " " + matchScore.get(positionTeamMap.get("left")) + " : " + matchScore.get(positionTeamMap.get("right")) + " " + positionTeamMap.get("right") + "\n";
        System.out.println(header);
        if (goalConverted){
            sleepTime = goalTime;
            goalConverted = false;
            displayMessage = ballPossessingPlayer.getPlayersTeam().toString() + " shot a GOOOOOOAAAAALLLL !!!!";
            if(ballPossessingPlayer.getPlayersTeam() == teams[1]){
                displayMessage = ANSI_BLUE + displayMessage + ANSI_RESET;
            }else{
                displayMessage = ANSI_GREEN + displayMessage + ANSI_RESET;
            }
        }
        else {
            sleepTime = passTime;
            displayMessage = ballPossessingPlayer + " has now the ball ";

            if(ballPossessingPlayer.getPlayersTeam() == teams[1]){
                displayMessage = ANSI_BLUE + displayMessage + ANSI_RESET;
            }else{
                displayMessage = ANSI_GREEN + displayMessage + ANSI_RESET;
            }
        }
        for (int j = 0; j < fieldHeight; j++) {
            String row = "";
            int asserted_i = -1;
            int asserted_j = -1;
            for (int i = 0; i < fieldWidth; i++) {
                if ((playerPositionMap.get(ballPossessingPlayer)[0] == i) && (playerPositionMap.get(ballPossessingPlayer)[1] == j)){
                    if(matchState[i][j] == null){
                        row += "   ";
                    }else {
                        row += " " + ANSI_RED + "@" + ANSI_RESET + " ";
                        asserted_i = i;
                        asserted_j = j;
                    }
                }else {
                    if (matchState[i][j] == null){
                        row += "   ";
                    }else {
                        row += " " + matchState[i][j] + " ";
                    }
                }
            }
            if (j == 1){
                row += "        " + displayMessage;
            }
            System.out.println(row + "\n");
        }
        if (goalConverted){

        }
        Thread.sleep(sleepTime);
    }

}
