package Entities;

import World.Match;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected Match match;
    protected Team playersTeam;
    protected double passProbability;
    protected double conversionProbability;
    private String name;
    public Player (double passProbability, double conversionProbability, Team playersTeam, String name) {
        this.passProbability = passProbability;
        this.conversionProbability = conversionProbability;
        this.playersTeam = playersTeam;
        this.name = name;

    }

    public Player pass() throws InterruptedException {
        match.showMatchState();
        List<Player> targetList;
        if(Math.random() < passProbability) {
            targetList = getAlliedTargetList();
        }
        else {
            targetList = getEnemyTargetList();
        }
        return targetList.get((int)(Math.random() * targetList.size()));
    }

    public String getName() {
        return name;
    }
    public Team getPlayersTeam() {
        return playersTeam;
    }
    protected abstract List<Player> getAlliedTargetList();
    protected abstract List<Player> getEnemyTargetList();
    public abstract Player handleBall () throws InterruptedException;
    public void setMatchPlayed(Match match) {
        this.match = match;
    }
}
