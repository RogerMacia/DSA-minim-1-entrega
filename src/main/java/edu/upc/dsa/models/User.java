package edu.upc.dsa.models;

import edu.upc.dsa.exceptions.InvalidDateFormatException;
import edu.upc.dsa.util.RandomUtils;
import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedList;

public class User {
    @ApiModelProperty(hidden = true)
    private String id;
    private String name;
    private String surname;
    private String dni;
    private String birthDate;
    private String birthPlace;
    private String address;

    @ApiModelProperty(hidden = true)
    private LinkedList<Prestec> prestects;

    public User() {}
    public User(String name, String surname, String dni, String birthDate, String birthPlace, String address) {
        this.id = RandomUtils.getId();
        this.name = name;
        this.surname = surname;
        this.dni = dni;
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

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {    //Format (D)D-(M)M-AAAA o (D)D/(M)M/AAAA
        char separador;

        try {
            separador = birthDate.charAt(birthDate.length() - 5);
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidDateFormatException();
        }

        String[] date = new String[0];

        if (separador == '-') {
            date = birthDate.split("-");
        } else if (separador == '/') {
            date = birthDate.split("/");
        }

        if (date.length != 3 || date[2].length() != 4 || date[0].length() > 2 || date[1].length() > 2)
            throw new InvalidDateFormatException();

        try {
            int dia = Integer.parseInt(date[0]);
            int mes = Integer.parseInt(date[1]);
            int any = Integer.parseInt(date[2]);

            if (mes > 12) throw new InvalidDateFormatException();

            switch (mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:   //Mesos amb 31 dies
                    if (dia > 31) throw new InvalidDateFormatException();
                    break;

                case 4:
                case 6:
                case 9:
                case 11:    //Mesos amb 30 dies
                    if (dia > 30) throw new InvalidDateFormatException();
                    break;

                case 2: //Febrer
                    if (dia > 29 || dia > 28 && any % 4 != 0)
                        throw new InvalidDateFormatException();
                    break;
            }
        } catch (NumberFormatException e) {
            throw new InvalidDateFormatException();
        }

        this.birthDate = birthDate;
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
    @XmlTransient
    public void setPrestects(LinkedList<Prestec> prestect) {
        this.prestects = prestect;
    }

    public void addPrestect(Prestec prestec) {
        this.prestects.add(prestec);
    }
}