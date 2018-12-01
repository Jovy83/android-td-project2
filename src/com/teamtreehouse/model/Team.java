package com.teamtreehouse.model;

import java.util.*;

public class Team implements Comparable<Team> {

    private String coachName;
    private String teamName;
    private List<Player> roster;

    public Team(String coachName, String teamName) {
        this.coachName = coachName;
        this.teamName = teamName;
        this.roster = new ArrayList<>();
    }

    public String getCoachName() {
        return coachName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void addPlayer(Player player) {
        roster.add(player);
    }

    public void removePlayer(Player player) {
        roster.remove(player);
    }

    public Player getPlayerFromRoster(int position) {
        return roster.get(position);
    }

    public int getRosterCount() {
        return roster.size();
    }

    public void printDetailedRoster() {
        Collections.sort(roster, new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                return player1.getHeightInInches() - player2.getHeightInInches();
            }
        });
        System.out.println("List of players by height:");
        for (Player p : roster) {
            System.out.println(p.toString());
        }
        System.out.printf("The average experience level for this team is %.1f%% %n%n%n", calculateExperienceLevel());
    }

    public void printRoster() {
        System.out.println(this);
        for (int i = 0; i < getRosterCount(); i++) {
            System.out.printf("%d.) %s%n", i + 1, roster.get(i));
        }
        System.out.printf("%n%n%n");
    }

    private double calculateExperienceLevel() {
        int totalPlayers = getRosterCount();
        int experiencedPlayers = 0;
        for (Player p : roster) {
            if (p.hasPreviousExperience()) {
                experiencedPlayers++;
            }
        }
        return ((double)experiencedPlayers / (double)totalPlayers) * 100.0;
    }

    public boolean isEmpty() {
        if (getRosterCount() <= 0) {
            System.out.println("There are no players in this team");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Team %s coached by %s", teamName, coachName);
    }

    @Override
    public int compareTo(Team other) {
        // We always want to sort by last name then first name
        if (equals(other)) {
            return 0;
        }
        return teamName.compareTo(other.teamName);
    }
}
