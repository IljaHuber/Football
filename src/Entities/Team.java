package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {
    private String name;
    private Team opposingTeam;
    private List<Player> teamMembers = new ArrayList<>();
    private List<Attacker> attackers = new ArrayList<>();
    private List<MiddleField> middleFielders = new ArrayList<>();
    private List<Defender> defenders = new ArrayList<>();
    private Goalkeeper goalkeeper = new Goalkeeper(this, Integer.toString(0));
    private List<Player> fieldPlayers = new ArrayList<>();

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
            attackers.add(new Attacker(this, Integer.toString(i), 0.4 + Math.random() * 0.2));
        }
        for (int i = 0; i < 4; i++){
            middleFielders.add(new MiddleField(this, Integer.toString(i)));
        }
        for (int i = 0; i < 3; i++){
            defenders.add(new Defender(this, Integer.toString(i)));
        }
        fieldPlayers.addAll(attackers);
        fieldPlayers.addAll(middleFielders);
        fieldPlayers.addAll(defenders);
        teamMembers.addAll(fieldPlayers);
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

    public List<Player> getFieldPlayers() {
        return fieldPlayers;
    }

    @Override
    public String toString() {
        return this.name;
    }
    public MiddleField getRandomMidfield () {
        return middleFielders.get((int)(Math.random() * 3));
    }
}
