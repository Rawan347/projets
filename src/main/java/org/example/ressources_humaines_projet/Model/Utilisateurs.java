package org.example.ressources_humaines_projet.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "utilisateurs")
public class Utilisateurs
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(nullable = false)
    public String nomUtilisateur;

    @Column(nullable = false)
    public String motDePasse;

    public enum Role
    {
        Admin, Manager, Employe;
    }

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public Role role;

    @OneToMany(mappedBy = "responsable", fetch = FetchType.LAZY)
    private List<Departement> departements;



    public Utilisateurs(int id, String nomUtilisateur, String motDePasse, Role role)
    {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public Utilisateurs() {}

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNomUtilisateur()
    {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur)
    {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse()
    {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse)
    {
        this.motDePasse = motDePasse;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

}
