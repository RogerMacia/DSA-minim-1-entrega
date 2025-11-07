package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlTransient;

public class User {
    private String id;
    private String name;
    private String surname;
    private String DNI;
    private String birthDate;
    private String birthPlace;
    private String address;

    private LinkedList<Prestec> prestects;

    public User() {}
    public User(String name, String surname, String DNI, String birthDate, String birthPlace, String address) {
        this.id = RandomUtils.getId();
        this.name = name;
        this.surname = surname;
        this.DNI = DNI;
        this.setBirthDate(birthDate);
        this.birthPlace = birthPlace;
        this.address = address;
        this.prestects = new LinkedList<>();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDNI() {
        return DNI;
    }
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {    //Format (D)D-(M)M-AAAA o (D)D/(M)M/AAAA
        char separador = birthDate.charAt(birthDate.length() - 5);
        String[] date = new String[0];

        if (separador == '-') {
            date = birthDate.split("-");
        }
        else if (separador == '/') {
            date = birthDate.split("/");
        }

        if  (date.length != 3 || date[2].length() != 4 || date[0].length() > 2 || date[1].length() > 2) throw new IllegalArgumentException("Invalid date format");

        try
        {
            if (Integer.parseInt(date[1]) > 12) throw new IllegalArgumentException("Invalid date format");

            switch (Integer.parseInt(date[1])) {
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:   //Mesos amb 31 dies
                    if (Integer.parseInt(date[0]) > 31) throw new IllegalArgumentException("Invalid date format");
                    break;

                case 4: case 6: case 9: case 11:    //Mesos amb 30 dies
                    if (Integer.parseInt(date[0]) > 30) throw new IllegalArgumentException("Invalid date format");
                    break;

                case 2: //Febrer
                    if (Integer.parseInt(date[0]) > 29 || Integer.parseInt(date[0]) > 28 && Integer.parseInt(date[2]) % 4 != 0) throw new IllegalArgumentException("Invalid date format");
                    break;
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    public String getBirthPlace() {
        return birthPlace;
    }
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient   //Perquè no es mostri al servei API REST
    public LinkedList<Prestec> getPrestects() {
        return prestects;
    }
    public void setPrestects(LinkedList<Prestec> prestect) {
        this.prestects = prestect;
    }

    public void addPrestect(Prestec prestec) {
        this.prestects.add(prestec);
    }
}