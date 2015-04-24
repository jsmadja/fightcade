package fr.kaddath.fightcade.server.interfaces.rest.controller;

public class Attendance {
    public String player;
    public String rom;
    public String country;

    public Attendance() {
    }

    public Attendance(String player, String rom, String country) {
        this.player = player;
        this.rom = rom;
        this.country = country;
    }
}
