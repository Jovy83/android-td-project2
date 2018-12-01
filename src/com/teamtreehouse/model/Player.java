package com.teamtreehouse.model;

import java.io.Serializable;
import java.util.Locale;

public class Player implements Comparable<Player>, Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private int heightInInches;
    private boolean hasPreviousExperience;
    private boolean isFreeAgent;

    public Player(String firstName, String lastName, int heightInInches, boolean hasPreviousExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.heightInInches = heightInInches;
        this.hasPreviousExperience = hasPreviousExperience;
        this.isFreeAgent = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public boolean hasPreviousExperience() {
        return hasPreviousExperience;
    }

    public boolean isFreeAgent() {
        return isFreeAgent;
    }

    public void setHasPreviousExperience(boolean hasPreviousExperience) {
        this.hasPreviousExperience = hasPreviousExperience;
    }

    public void setFreeAgent(boolean freeAgent) {
        isFreeAgent = freeAgent;
    }

    @Override
    public int compareTo(Player other) {
        // We always want to sort by last name then first name
        if (equals(other)) {
            return 0;
        }

        int lastNameCmp = lastName.compareTo(other.lastName);
        if (lastNameCmp == 0) {
            return firstName.compareTo(other.firstName);
        }
        return lastNameCmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (heightInInches != player.heightInInches) return false;
        if (hasPreviousExperience != player.hasPreviousExperience) return false;
        if (!firstName.equals(player.firstName)) return false;
        return lastName.equals(player.lastName);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + heightInInches;
        result = 31 * result + (hasPreviousExperience ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String description = String.format(Locale.getDefault(), "%s %s (%d inches - %s)", firstName, lastName, heightInInches, (hasPreviousExperience) ? "experienced" : "inexperienced");
        return description;
    }
}
