package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;

public class Prestec {
    private String id;
    private String idUser;
    private String idBook;
    private String dataPrestec;
    private String dataDevolucio;
    private Boolean isTramit;

    public Prestec() {}
    public Prestec(String idUser, String idBook, String dataPrestec, String dataDevolucio) {
        this.id = RandomUtils.getId();
        this.idUser = idUser;
        this.idBook = idBook;
        this.setDataPrestec(dataPrestec);
        this.setDataDevolucio(dataDevolucio);   // Falta comprovar que la data és després de la data de prestec!!
        this.isTramit = true;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdBook() {
        return idBook;
    }
    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getDataPrestec() {
        return dataPrestec;
    }
    public void setDataPrestec(String dataPrestec) {
        char separador = dataPrestec.charAt(dataPrestec.length() - 5);
        String[] date = new String[0];

        if (separador == '-') {
            date = dataPrestec.split("-");
        }
        else if (separador == '/') {
            date = dataPrestec.split("/");
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

    public String getDataDevolucio() {
        return dataDevolucio;
    }
    public void setDataDevolucio(String dataDevolucio) {
        char separador = dataDevolucio.charAt(dataDevolucio.length() - 5);
        String[] date = new String[0];

        if (separador == '-') {
            date = dataDevolucio.split("-");
        }
        else if (separador == '/') {
            date = dataDevolucio.split("/");
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

    public Boolean getTramit() {
        return isTramit;
    }
    public void setTramit(Boolean tramit) {
        isTramit = tramit;
    }
}