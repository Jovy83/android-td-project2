package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Organizer {

    private BufferedReader reader;
    private Map<String, String> menu;
    private List<Team> allTeams;
    private Player[] allPlayers;

    public Organizer(Player[] allPlayers) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        menu = new TreeMap<>();
        menu.put("Create", "Create a new team");
        menu.put("Add", "Add a player to a team");
        menu.put("Remove", "Remove a player from a team");
        menu.put("Report", "View a report of a team by height. Show team experience level.");
        menu.put("Roster", "View roster");
        menu.put("Quit", "Exits the program");

        allTeams = new ArrayList<>();
        this.allPlayers = allPlayers;
    }

    private String promptAction() throws IOException {
        System.out.println("Menu");
        for (Map.Entry<String, String> option : menu.entrySet()) {
            System.out.printf("%s - %s %n", option.getKey(), option.getValue());
        }
        System.out.print("Select an option: ");
        String choice = reader.readLine();
        return choice.trim().toLowerCase();
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "create":
                        Team team = promptCreateTeam();
                        allTeams.add(team);
                        System.out.printf("%s added. %n%n", team.getTeamName());
                        break;
                    case "add":
                        Player playerToAdd = promptAddPlayer();
                        Team teamForAdding = promptTeam();
                        teamForAdding.addPlayer(playerToAdd);
                        playerToAdd.setFreeAgent(false);
                        System.out.printf("%s added to %s%n", playerToAdd, teamForAdding);
                        break;
                    case "remove":
                        // choose team
                        Team teamForRemoving = promptTeam();
                        // choose player
                        Player playerToRemove = promptRemovePlayerFromTeam(teamForRemoving);
                        teamForRemoving.removePlayer(playerToRemove);
                        playerToRemove.setFreeAgent(true);
                        System.out.printf("%s removed from %s%n", playerToRemove, teamForRemoving);
                        break;
                    case "report":
                        promptReport();
                        break;
                    case "roster":
                        promptRoster();
                        break;
                    case "quit":
                        System.out.println("Thanks for playing");
                        break;
                    default:
                        System.out.println("Unknown choice");
                        break;
                }
            } catch (IOException e) {
                System.out.printf("Problem with input%n");
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Problem with player or team selection. Out of bounds.");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.printf("Illegal argument. Try again%n%n");
            }
        } while (!choice.equals("quit"));
    }

    private Team promptCreateTeam() throws IOException {
        System.out.printf("%n%n%n");
        System.out.print("What is the team name: ");
        String teamName = reader.readLine();
        System.out.print("What is the coach name: ");
        String coachName = reader.readLine();

        return new Team(coachName, teamName);
    }

    private int promptNumberInput() throws IOException {
        System.out.printf("%n");

        String input = reader.readLine();

        int userChoice = -1;
        try {
            userChoice = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            System.out.println("Input must be a number");
        }

        if (userChoice - 1 < 0) {
            throw new IllegalArgumentException("Invalid player or team");
        }

        return userChoice - 1;
    }

    private Player promptAddPlayer() throws IOException, IndexOutOfBoundsException {
        if (!areTeamsAvailable()) {
            throw new IllegalArgumentException("There are no available teams!");
        }

        System.out.printf("%n%n%n");
        System.out.printf("Players that are free agents: %n");

        List<Player> freePlayers = new ArrayList<>();

        for (int i = 0; i < allPlayers.length; i++) {
            // show only free agents
            Player p = allPlayers[i];
            if (p.isFreeAgent()) {
                freePlayers.add(p);
            }
        }

        if (freePlayers.size() <= 0) {
            throw new IllegalArgumentException("There are no available free agents!");
        }

        for (int i = 0; i < freePlayers.size(); i++) {
            System.out.printf("%d.) %s%n", i + 1, freePlayers.get(i));
        }

        System.out.print("Choose player: ");
        return freePlayers.get(promptNumberInput());
    }

    private Team promptTeam() throws IOException, IndexOutOfBoundsException {

        if (allTeams.size() <= 0) {
            throw new IllegalArgumentException("There are no available teams!");
        }

        Collections.sort(allTeams);

        System.out.printf("%n%n%n");
        System.out.println("Available teams: ");
        for (int i = 0; i < allTeams.size(); i++) {
            System.out.printf("%d.) %s%n", i + 1, allTeams.get(i));
        }

        System.out.print("Choose team: ");
        return allTeams.get(promptNumberInput());
    }

    private Player promptRemovePlayerFromTeam(Team team) throws IOException, IndexOutOfBoundsException {
        if (!areTeamsAvailable()) {
            throw new IllegalArgumentException("There are no available teams!");
        }

        if (team.isEmpty()) {
            throw new IllegalArgumentException("There are no available players in this team!");
        }

        team.printRoster();
        System.out.print("Enter player to remove: ");
        return team.getPlayerFromRoster(promptNumberInput());
    }

    private void promptReport() throws IOException {
        System.out.printf("%n%n%n");

        if (areTeamsAvailable()) {
            for (Team t : allTeams) {
                t.printDetailedRoster();
            }
        }
    }

    private void promptRoster() throws IOException {
        System.out.printf("%n%n%n");
        if (areTeamsAvailable()) {
            for (Team t : allTeams) {
                t.printRoster();
            }
        }
    }

    private boolean areTeamsAvailable() {
        if (allTeams.size() <= 0) {
            System.out.println("There are no available teams.");
            return false;
        }
        return true;
    }
}
