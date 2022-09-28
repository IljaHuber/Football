package Entities;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private Team opposingTeam;
    private List<Player> teamMembers = new ArrayList<>();
    private List<Attacker> attackers = new ArrayList<>();
    private List<MiddleField> middleFielders = new ArrayList<>();
    private List<Defender> defenders = new ArrayList<>();
    private Goalkeeper goalkeeper = new Goalkeeper(this, Integer.toString(0));

    public Team(String name) {
        this.name = name;
        fillPlayerList();
    }

    public List<Player> getTeamMembers() {
        return teamMembers;
    }

    public List<Attacker> getAttackers() {
        return attackers;
    }

    public List<MiddleField> getMiddleFielders() {
        return middleFielders;
    }

    public List<Defender> getDefenders() {
        return defenders;
    }

    public Goalkeeper getGoalkeeper() {
        return goalkeeper;
    }

    private void fillPlayerList() {
        for (int i = 0; i < 3; i++){
            attackers.add(new Attacker(this, Integer.toString(i)));
        }
        for (int i = 0; i < 4; i++){
            middleFielders.add(new MiddleField(this, Integer.toString(i)));
        }
        for (int i = 0; i < 3; i++){
            defenders.add(new Defender(this, Integer.toString(i)));
        }
        teamMembers.addAll(attackers);
        teamMembers.addAll(middleFielders);
        teamMembers.addAll(defenders);
        teamMembers.add(goalkeeper);
    }

    public void setOpposingTeam(Team opposingTeam) {
        this.opposingTeam = opposingTeam;
    }

    public Team getOpposingTeam() {
        return opposingTeam;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    public Attacker getRandomAttacker () {
        return attackers.get((int)(Math.random() * 3));
    }
}
