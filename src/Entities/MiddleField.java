package Entities;

public class MiddleField extends Player{
    public MiddleField(Team team, String name) {
        super(0.7,0.3, team, name);
    }
    @Override
    public String toString() {
        return "Midfield Player Nr." + getName() + " from team " + getPlayersTeam().getName();
    }
}
