package Entities;

public class Attacker extends Player{
    public Attacker(Team team, String name) {
        super(0.5,0.5, team, name);
    }
    @Override
    public String toString() {
        return "Attacker Player Nr." + getName() + " from team " + getPlayersTeam().getName();
    }
}
