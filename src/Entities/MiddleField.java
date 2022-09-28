package Entities;

import java.util.ArrayList;
import java.util.List;

public class MiddleField extends FiledPlayer{
    public MiddleField(Team team, String name) {
        super(0.7,0.3, team, name);
    }
    @Override
    public String toString() {
        return "Midfield Player Nr." + getName() + " from team " + getPlayersTeam().getName();
    }


    @Override
    protected List<Player> getAlliedTargetList() {
        List<Player> targetList = new ArrayList<>();
        for (Player iteratedPlayer: getPlayersTeam().getFieldPlayers()) {
            if(this != iteratedPlayer) {
                targetList.add(iteratedPlayer);
            }
        }
        return targetList;

    }

    @Override
    protected List<Player> getEnemyTargetList() {
        List<Player> targetList = new ArrayList<>();
        targetList.addAll(getPlayersTeam().getOpposingTeam().getMiddleFielders());
        return targetList;
    }

    @Override
    public Player handleBall() throws InterruptedException {
        return pass();
    }
}
