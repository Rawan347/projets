package org.example.ressources_humaines_projet.Model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "departement")
public class Departement
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(nullable = false)
    public String nom;

    @OneToMany(mappedBy = "departement")
    public List<Employes> employes;

    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "id", nullable = false)
    private Utilisateurs responsable;

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

    public List<Employes> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employes> employes) {
        this.employes = employes;
    }

    public Utilisateurs getResponsable() {
        return responsable;
    }

    public void setResponsable(Utilisateurs responsable) {
        this.responsable = responsable;
    }

    public Integer getResponsableId()
    {
        return responsable != null ? responsable.getId() : null;
    }

    public Integer setResponsableId(Integer id)
    {
        return responsable != null ? responsable.getId() : null;
    }
}


