    package org.example.ressources_humaines_projet.Model;

    import jakarta.persistence.*;

    import java.math.BigDecimal;
    import java.util.List;

    @Entity
    @Table(name = "employes")
    public class Employes
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public int id;

        @Column(nullable = false)
        public String nomComplet;

        @Column(nullable = false)
        public String poste;

        @Column(nullable = false)
        public String dateEmbauche;

        @Column(nullable = false, precision = 10, scale = 2)
        public BigDecimal salaire;

        @ManyToOne
        @JoinColumn(name = "departement_id", referencedColumnName = "id", nullable = false)
        public Departement departement;

        @OneToMany(mappedBy = "employe")
        private List<Tache> taches;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNomComplet() {
            return nomComplet;
        }

        public void setNomComplet(String nomComplet) {
            this.nomComplet = nomComplet;
        }

        public String getPoste() {
            return poste;
        }

        public void setPoste(String poste) {
            this.poste = poste;
        }

        public String getDateEmbauche() {
            return dateEmbauche;
        }

        public void setDateEmbauche(String dateEmbauche) {
            this.dateEmbauche = dateEmbauche;
        }

        public BigDecimal getSalaire() {
            return salaire;
        }

        public void setSalaire(BigDecimal salaire) {
            this.salaire = salaire;
        }

        public Departement getDepartement() {
            return departement;
        }

        public void setDepartement(Departement departement) {
            this.departement = departement;
        }

        public List<Tache> getTaches() {
            return taches;
        }

        public void setTaches(List<Tache> taches) {
            this.taches = taches;
        }

        public Integer getDepartementId()
        {
            return departement != null ? departement.getId() : null;
        }
        public Integer setDepartementId(Integer departementId)
        {
            return departement != null ? departement.getId() : null;
        }

    }


