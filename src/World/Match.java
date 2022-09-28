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
    private Player ballPossessingPlayer = null;
    private int ballPosition = 2;
    private int goalOne = 0;
    private int goalTwo = 4;
    private int playTime = 270;
    private Map<Team, Integer> matchScore = new HashMap<>();
    private Map<Team, Integer> goalPosition = new HashMap<>();
    private Map<Team, String> teamSymbolMap = new HashMap<>();
    private Team[] teams;

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

    public static void main(String[] args) {
        boolean game_running = true;
        Team bM = new Team("Bayern ");
        Team vfb = new Team("Stuttgart ");
        Match finalMatch = new Match(bM, vfb);
        finalMatch.setStartingPosition(bM, vfb);
        finalMatch.ballPossessingPlayer = finalMatch.getStartingTeam(bM, vfb).getRandomAttacker();
        for(int i = 0; i < finalMatch.playTime; i++){
            Player passer = finalMatch.ballPossessingPlayer;
            finalMatch.ballPossessingPlayer = finalMatch.ballPossessingPlayer.handleBall(finalMatch.checkBallPosition());
            if (passer.getPlayersTeam() == finalMatch.ballPossessingPlayer.getPlayersTeam()){
                finalMatch.ballPosition += finalMatch.goalPosition.get(passer.getPlayersTeam());
            }
            finalMatch.showMatchState();
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
            goalPosition.put(teamOne, - 1);
            teamSymbolMap.put(teamOne, "x");
            goalPosition.put(teamTwo, 1);
            teamSymbolMap.put(teamTwo, "o");
        }else{
            goalPosition.put(teamOne, 1);
            teamSymbolMap.put(teamTwo, "x");
            goalPosition.put(teamTwo, - 1);
            teamSymbolMap.put(teamOne, "o");
        }
    }
    private boolean checkBallPosition () {
        if ((goalPosition.get(ballPossessingPlayer.getPlayersTeam()) == -1) && (ballPosition == goalOne)) {
            System.out.println("Possible to shoot a Goal ");
            return true;
        } else if ((goalPosition.get(ballPossessingPlayer.getPlayersTeam()) == 1) && (ballPosition == goalTwo)) {
            System.out.println("Possible to shoot a Goal ");
            return true;
        }
        return false;
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

    public void setBallPosition(int ballPosition) {
        this.ballPosition = ballPosition;
    }

    private void showMatchState(){
        int fieldWidth = 11;
        int fieldHeight = 3;
        String[][] matchState = new String[fieldWidth][fieldHeight];
        Map<Player, int[]> playerPositionMap = new HashMap<>();
        for (Team team: teams){
            for(Player player: team.getTeamMembers()){
                int teamsGoalPosition = goalPosition.get(team);
                int middleColumn = 5;
                    if(player instanceof Goalkeeper){
                        int[] pos = new int[]{middleColumn + teamsGoalPosition * 5, 1};
                        playerPositionMap.put(player, pos);
                        matchState[pos[0]][pos[1]] = teamSymbolMap.get(player.getPlayersTeam());
                    } else if (player instanceof Defender) {
                        int[] pos = new int[]{middleColumn + teamsGoalPosition * 4, Integer.parseInt(player.getName())};
                        playerPositionMap.put(player, pos);
                        matchState[pos[0]][pos[1]] = teamSymbolMap.get(player.getPlayersTeam());
                    } else if (player instanceof MiddleField) {
                        if(Integer.parseInt(player.getName()) > 1 ) {
                            int pos[];
                            if(Integer.parseInt(player.getName()) == 2){
                                pos = new int[]{middleColumn + teamsGoalPosition * - 1, Integer.parseInt(player.getName()) - 2};
                            }else{
                                pos = new int[]{middleColumn + teamsGoalPosition * - 1, Integer.parseInt(player.getName()) - 1};
                            }
                            playerPositionMap.put(player, pos);
                            matchState[pos[0]][pos[1]] = teamSymbolMap.get(player.getPlayersTeam());
                        }else {
                            int pos[];
                            if(Integer.parseInt(player.getName()) == 1){
                                pos = new int[]{middleColumn + teamsGoalPosition * 2, Integer.parseInt(player.getName())};
                            }else{
                                pos = new int[]{middleColumn + teamsGoalPosition * 2, Integer.parseInt(player.getName()) + 1};
                            }
                            playerPositionMap.put(player, pos);
                            matchState[pos[0]][pos[1]] = teamSymbolMap.get(player.getPlayersTeam());

                        }
                    } else if (player instanceof Attacker) {
                        int[] pos = new int[]{middleColumn + teamsGoalPosition * -3, Integer.parseInt(player.getName())};
                        playerPositionMap.put(player, pos);
                        matchState[pos[0]][pos[1]] = teamSymbolMap.get(player.getPlayersTeam());
                    }
                }
            }
        for (int j = 0; j < fieldHeight; j++) {
            String row = "";
            for (int i = 0; i < fieldWidth; i++) {
                if (playerPositionMap.get(ballPossessingPlayer)[0] == j && playerPositionMap.get(ballPossessingPlayer)[0] == i){
                    if(matchState[i][j] == null){
                        row += "   ";
                    }else {
                        row += " " + matchState[i][j].toUpperCase() + " ";
                    }
                }else {
                    if (matchState[i][j] == null){
                        row += "   ";
                    }else {
                        row += " " + matchState[i][j] + " ";
                    }
                }
            }
            System.out.println(row + "\n");
        }
    }

}
