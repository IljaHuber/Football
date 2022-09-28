package Entities;

public class Goalkeeper extends Player {
    public Goalkeeper(Team team, String name) {
        super(0.9,0.1, team, name );
    }

    @Override
    public String toString() {
        return "Goalkeeper Player Nr." + getName() + "from team " + getPlayersTeam().getName();
    }
}
