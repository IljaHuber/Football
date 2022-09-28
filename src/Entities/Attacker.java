package Entities;

import java.util.ArrayList;
import java.util.List;

public class Attacker extends FiledPlayer{
    double willToShoot;
    public Attacker(Team team, String name, double willToShoot) {
        super(0.5,0.3, team, name);
        this.willToShoot = willToShoot;
    }
    @Override
    public String toString() {
        return "Attacker Player Nr." + getName() + " from team " + getPlayersTeam().getName();
    }


    @Override
    protected List<Player> getAlliedTargetList() {
        List<Player> targetList = new ArrayList<>();
        for (Player iteratedPlayer: getPlayersTeam().getAttackers()) {
            if(this != iteratedPlayer) {
                targetList.add(iteratedPlayer);
            }
        }
        return targetList;
    }

    @Override
    protected List<Player> getEnemyTargetList() {
        List<Player> targetList = new ArrayList<>();
        targetList.addAll(getPlayersTeam().getOpposingTeam().getDefenders());
        return targetList;
    }

    private void logGoal() {
        int newScore = match.getMatchScore().get(this.getPlayersTeam()) + 1;
        match.getMatchScore().put(this.getPlayersTeam(), newScore);
        match.setGoalConverted(true);
    }
    public Player shoot () throws InterruptedException {
        if(Math.random() < conversionProbability) {
            logGoal();
            match.showMatchState();
            match.setBallPosition(2);
            return this.playersTeam.getOpposingTeam().getRandomMidfield();

        }
        else {
            match.showMatchState();
            return this.playersTeam.getOpposingTeam().getGoalkeeper();
        }
    }

    @Override
    public Player handleBall() throws InterruptedException {
        if(Math.random() < willToShoot) {
            return shoot();
        }
        else {
            return pass();
        }
    }
}
