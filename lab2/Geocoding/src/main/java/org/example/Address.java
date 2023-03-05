package org.example;

public class Address {
    private String road;
    private String city;
    private String state;
    private String zio;
    private String houseNumber;

    public Address(String road, String city, String state, String zio, String houseNumber){
        this.road = road;
        this.city = city;
        this.state = state;
        this.zio = zio;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address[" +
                "road='" + road + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zio='" + zio + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ']';
    }
}
