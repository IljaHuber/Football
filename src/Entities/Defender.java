package Entities;

public class Defender extends Player{
    public Defender(Team team, String name) {
        super(0.8,0.2, team, name);
    }
    @Override
    public String toString() {
        return "Defender Player Nr." + getName() + " from team " + getPlayersTeam().getName();
    }
}
