package Entities;

import World.Match;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private Match match;
    private Team playersTeam;
    private double passProbability;
    private double conversionProbability;
    private String name;
    public Player (double passProbability, double conversionProbability, Team playersTeam, String name) {
        this.passProbability = passProbability;
        this.conversionProbability = conversionProbability;
        this.playersTeam = playersTeam;
        this.name = name;

    }
    public Player pass () {
        if(Math.random() > passProbability) {
            List<Player> teamMembersList = new ArrayList<>();
            for (Player player : playersTeam.getTeamMembers()){
                if((player != this) && !(player instanceof Goalkeeper)){
                    teamMembersList.add(player);
                }
            }
            Player target = teamMembersList.get((int)(Math.random() * 9));
            System.out.println(this + " passed to teammate " + target);
            return target;
        }
        else {
            Team enemyTeam = playersTeam.getOpposingTeam();
            List<Player> enemyFieldPlayers = new ArrayList<>();
            for (Player player : enemyTeam.getTeamMembers()){
                if (!(player instanceof Goalkeeper)){
                    enemyFieldPlayers.add(player);
                }
            }
            Player target = enemyFieldPlayers.get((int)(Math.random() * 10));
            System.out.println(this + " failed his pass. " + target + " has now the ball.");
            return target;
        }
    }
    public Player shoot () {
        if(Math.random() < conversionProbability) {
            logGoal();
            match.setBallPosition(2);
            return this.playersTeam.getOpposingTeam().getRandomAttacker();

        }
        else {
            return this.playersTeam.getOpposingTeam().getGoalkeeper();
        }
    }

    private void logGoal() {
        System.out.println("Goal");
        int newScore = match.getMatchScore().get(this.getPlayersTeam()) + 1;
        match.getMatchScore().put(this.getPlayersTeam(), newScore);
        System.out.println(this + " Shot a goal");
        match.printScore();
    }

    public String getName() {
        return name;
    }

    public Team getPlayersTeam() {
        return playersTeam;
    }
    public Player handleBall (boolean canShoot) {
        if(canShoot) {
            return shoot();
        }
        else{
            return pass();
        }
    }

    public void setMatchPlayed(Match match) {
        this.match = match;
    }
}
