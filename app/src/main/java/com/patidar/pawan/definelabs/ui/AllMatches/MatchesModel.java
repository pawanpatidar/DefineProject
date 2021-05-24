package com.patidar.pawan.definelabs.ui.AllMatches;

public class MatchesModel {

    private String  id;
    private String vanue;
    private String location;
    private String address;
    private boolean isMatched;

    public MatchesModel(String id, String vanue , String address,boolean isMatched){
        this.address= address;
        this.id = id;
        this.vanue = vanue;
        this.isMatched = isMatched;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public String getVanue() {
        return vanue;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }
}
