package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.util.Arrays;

public class LeagueManager {
    public static void main(String[] args) {
        Player[] players = Players.load();
        Arrays.sort(players);
        System.out.printf("There are currently %d registered players.%n", players.length);
        // Your code here!


        Organizer organizer = new Organizer(players);
        organizer.run();
    }
}
