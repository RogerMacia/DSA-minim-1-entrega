package edu.upc.dsa.models;

import edu.upc.dsa.exceptions.InvalidDateFormatException;
import edu.upc.dsa.util.RandomUtils;
import io.swagger.annotations.ApiModelProperty;

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
        this.setDataDevolucio(dataDevolucio);
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
        char separador;

        try {
            separador = dataPrestec.charAt(dataPrestec.length() - 5);
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidDateFormatException();
        }

        String[] date = new String[0];

        if (separador == '-') {
            date = dataPrestec.split("-");
        } else if (separador == '/') {
            date = dataPrestec.split("/");
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
        this.dataPrestec = dataPrestec;
    }

    public String getDataDevolucio() {
        return dataDevolucio;
    }
    public void setDataDevolucio(String dataDevolucio) {
        char separador;

        try {
            separador = dataDevolucio.charAt(dataDevolucio.length() - 5);
        } catch (StringIndexOutOfBoundsException e) {
            throw new InvalidDateFormatException();
        }

        String[] date = new String[0];

        if (separador == '-') {
            date = dataDevolucio.split("-");
        } else if (separador == '/') {
            date = dataDevolucio.split("/");
        }

        if (date.length != 3 || date[2].length() != 4 || date[0].length() > 2 || date[1].length() > 2)
            throw new InvalidDateFormatException();

        try {
            //  Comprovació que el dia de devolució és més tard que el del préstec
            char separadorPrestec = dataPrestec.charAt(dataPrestec.length() - 5);
            String[] datePrestec = new String[0];

            if (separador == '-') {
                datePrestec = dataPrestec.split("-");
            } else if (separador == '/') {
                datePrestec = dataPrestec.split("/");
            }

            int diaPrestec = Integer.parseInt(datePrestec[0]);
            int mesPrestec = Integer.parseInt(datePrestec[1]);
            int anyPrestec = Integer.parseInt(datePrestec[2]);

            int dia = Integer.parseInt(date[0]);
            int mes = Integer.parseInt(date[1]);
            int any = Integer.parseInt(date[2]);

            if (anyPrestec > any)  throw new IllegalArgumentException();
            else if (anyPrestec == any && mesPrestec > mes)  throw new IllegalArgumentException();
            else if (anyPrestec == any && mesPrestec == mes && diaPrestec > dia)  throw new IllegalArgumentException();
            // Fi de la comprovació de que el dia de devolució sigui més tard que el del préstec


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

        this.dataDevolucio = dataDevolucio;
    }

    public Boolean getTramit() {
        return isTramit;
    }
    public void setTramit(Boolean tramit) {
        isTramit = tramit;
    }
}