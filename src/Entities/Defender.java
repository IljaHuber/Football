package Entities;

import java.util.ArrayList;
import java.util.List;

public class Defender extends FiledPlayer{
    public Defender(Team team, String name) {
        super(0.8,0.2, team, name);
    }
    @Override
    public String toString() {
        return "Defender Player Nr." + getName() + " from team " + getPlayersTeam().getName();
    }

    @Override
    protected List<Player> getAlliedTargetList() {
        List<Player> targetList = new ArrayList<>();
        targetList.addAll(getPlayersTeam().getMiddleFielders());
        for (Player iteratedPlayer: getPlayersTeam().getDefenders()) {
            if(this != iteratedPlayer) {
                targetList.add(iteratedPlayer);
            }
        }
        return targetList;
    }

    @Override
    protected List<Player> getEnemyTargetList() {
        List<Player> targetList = new ArrayList<>();
        targetList.addAll(getPlayersTeam().getOpposingTeam().getAttackers());
        return targetList;
    }

    @Override
    public Player handleBall() throws InterruptedException {
        return pass();
    }
}
