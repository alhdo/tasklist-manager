package xyz.alhdo.tasklist.models;

import java.util.Date;

public class Task {
    private int id;
    private String nom;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private int etat;

    User user;

    public Task() {
    }

    public Task(String nom) {
        this.nom = nom;
    }

    public Task(String nom, String description, Date dateDebut, Date dateFin) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return this.getId() == ((Task) obj).getId();
//    }
}
