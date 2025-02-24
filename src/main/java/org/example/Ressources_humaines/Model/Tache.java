package org.example.Ressources_humaines.Model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tache")
public class Tache
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(nullable = false)
    public String titre;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public String date_limite;

    public enum Etat
    {
            termine,
            en_cours,
    }

    @Column(name = "etat", nullable = false)
    @Enumerated(EnumType.STRING)
    public Tache.Etat etat;

    @ManyToOne
    @JoinColumn(name = "employe_id", referencedColumnName = "id", nullable = false)
    private Employes employe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_limite() {
        return date_limite;
    }

    public void setDate_limite(String date_limite) {
        this.date_limite = date_limite;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Employes getEmploye() {
        return employe;
    }

    public void setEmploye(Employes employe) {
        this.employe = employe;
    }

    public Integer getEmployeId()
    {
        return employe != null ? employe.getId() : null;
    }

}
