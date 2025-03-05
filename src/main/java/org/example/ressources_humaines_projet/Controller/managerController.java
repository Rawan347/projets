package org.example.ressources_humaines_projet.Controller;

import jakarta.persistence.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.ressources_humaines_projet.Model.Departement;
import org.example.ressources_humaines_projet.Model.Employes;
import org.example.ressources_humaines_projet.Model.Tache;
import org.example.ressources_humaines_projet.Model.Utilisateurs;
import org.example.ressources_humaines_projet.util.Outils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class managerController implements Initializable
{

        @FXML
        private AnchorPane PaneManagerEmploye;

    @FXML
    private TableView<Employes> tblManagerEmploye;

    @FXML
    private TableColumn<Employes, Integer> tblIdManagerEmploye;

    @FXML
    private TableColumn<Employes, String> tblNomCompletManagerEmploye;

    @FXML
    private TableColumn<Employes, String> tblPosteManagerEmploye;

    @FXML
    private TableColumn<Employes, String> tblDateManagerEmploye;

    @FXML
    private TableColumn<Employes, BigDecimal> tblSalaireManagerEmploye;

    @FXML
    private TableColumn<Employes, Integer> tbldentifiantDepartementManagerEmploye;



    @FXML
    private DatePicker dpManagerEmployeDate;

        @FXML
        private TextField txtNomManagerEmploye;

        @FXML
        private TextField txtPosteManagerEmploye;

        @FXML
        private TextField txtSalaireManagerEmploye;

    @FXML
    private Button btnOpenDeconnexionManagerEmploye;

    @FXML
    private Button btnOpenManagerEmploye;

    @FXML
    private Button btnOpenManagerTache;

    @FXML
    private Button btnModifierManagerEmploye;

    @FXML
    private AnchorPane PaneTacheManager;


    @FXML
    private Button btnCloseTacheManager;

    @FXML
    private Button btnOpenTacheEmploye;

    @FXML
    private Button btnOpenTacheManager;

    @FXML
    private Button btnTacheAjouter;

    @FXML
    private Button btnTacheModifier;

    @FXML
    private Button btnTacheSupprimer;


    @FXML
    private TableView<Tache> tblTache;

    @FXML
    private TableColumn<?, ?> tblTacheIdentifiant;

    @FXML
    private TableColumn<?, ?> tblTitreTache;

    @FXML
    private TableColumn<?, ?> tblTacheDescription;

    @FXML
    private TableColumn<?, ?> tblTacheDate;

    @FXML
    private TableColumn<?, ?> tblTacheEtat;

    @FXML
    private TableColumn<?, ?> tblTacheEmployeId;


    @FXML
    private TextField txtTacheTitre;

    @FXML
    private TextField txtTacheDescription;

    @FXML
    private DatePicker dpTacheDate;

    @FXML
    private ChoiceBox<String> cbxTacheEtat;

    @FXML
    private ChoiceBox<Integer> cbxTacheIdentifiantEmploye;

    @FXML
    public void openManagerEmploye(ActionEvent actionEvent)
    {
        PaneManagerEmploye.setVisible(true);
        PaneTacheManager.setVisible(false);
    }

    @FXML
    public void openManagerTache(ActionEvent actionEvent)
    {
        PaneTacheManager.setVisible(true);
        PaneManagerEmploye.setVisible(false);
        initializeChoiceBoxTacheEtat();
        initializeChoiceBoxTacheEmployeId();
    }

    protected void rechercheEmployeParResponsable(int responsableId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            String jpql = "SELECT e FROM Employes e WHERE e.departement.responsable.id = :responsableId";
            TypedQuery<Employes> query = em.createQuery(jpql, Employes.class);
            query.setParameter("responsableId", responsableId);

            List<Employes> listEmployes = query.getResultList();

            if (!listEmployes.isEmpty())
            {
                tblManagerEmploye.setItems(FXCollections.observableArrayList(listEmployes));
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Aucun employé trouvé pour ce responsable");
                alert.showAndWait();
            }
        }
        catch (PersistenceException e)
        {
            e.printStackTrace();
        }
    }



    @FXML
    public void UpdateManagerEmployeRecoverData(MouseEvent mouseEvent)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        Employes employes = tblManagerEmploye.getSelectionModel().getSelectedItem();

        txtNomManagerEmploye.setText(employes.getNomComplet());
        txtPosteManagerEmploye.setText(employes.getPoste());
        txtSalaireManagerEmploye.setText(String.valueOf(employes.getSalaire()));
        dpManagerEmployeDate.setValue(LocalDate.parse(String.valueOf(employes.getDateEmbauche())));
    }



    @FXML
    void UpdateEmploye(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, ParseException
    {

        Employes employe = tblManagerEmploye.getSelectionModel().getSelectedItem();

        if (employe == null) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un enregistrement à modifier");
            alert1.showAndWait();
            return;
        }

        String nomComplet = txtNomManagerEmploye.getText();
        String poste = txtPosteManagerEmploye.getText();
        BigDecimal salaire = new BigDecimal(txtSalaireManagerEmploye.getText());
        String texteDate = String.valueOf(dpManagerEmployeDate.getValue());
        int id = employe.getId();

        if (nomComplet.isEmpty() || poste.isEmpty() || salaire == null || texteDate == null) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Tous les champs doivent être remplis");
            alert1.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Modifier les informations de l'employé ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            EntityManager em = emf.createEntityManager();

            try {
                Employes worker = em.find(Employes.class, id);

                if (worker == null) {
                    throw new Exception("Employé non trouvé");
                }

                em.getTransaction().begin();

                worker.setNomComplet(nomComplet);
                worker.setPoste(poste);
                worker.setSalaire(salaire);
                worker.setDateEmbauche(texteDate);

                em.getTransaction().commit();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Modification effectuée");
                alert.showAndWait();

                txtNomManagerEmploye.setText("");
                txtPosteManagerEmploye.setText("");
                dpManagerEmployeDate.setValue(null);
                txtSalaireManagerEmploye.setText("");

                int responsableId = worker.getDepartement().getResponsable().getId();
                rechercheEmployeParResponsable(responsableId);

            }
            catch (Exception e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }

                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Une erreur s'est produite lors de la mise à jour : " + e.getMessage());
                alert.showAndWait();
            }
            finally
            {
                em.close();
            }
        }
        else
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Modification non effectuée");
            alert.showAndWait();
        }
    }



    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tblIdManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomCompletManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        tblPosteManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("poste"));
        tblDateManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("dateEmbauche"));
        tblSalaireManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("salaire"));
    tbldentifiantDepartementManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("DepartementId"));

        try
        {
            loadEmployee();
            initializeTache();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    protected void loadEmployee() throws SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        try
        {
            String jpql = "SELECT e FROM Employes e";
            TypedQuery<Employes> query = em.createQuery(jpql, Employes.class);

            List<Employes> listEmployes = query.getResultList();


            ObservableList<Employes> listeableEmploye = FXCollections.observableArrayList(listEmployes);

            tblManagerEmploye.setItems(listeableEmploye);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        em.close();
        emf.close();
    }


    public ObservableList<String> getTacheEtats()
    {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("en_cours");
        observableList.add("termine");

        return observableList;
    }

    public void initializeChoiceBoxTacheEtat()
    {
        cbxTacheEtat.setItems(getTacheEtats());
    }

    public ObservableList<Integer> getTacheEmployeId()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();


        String jpql = "SELECT e FROM Employes e";

        TypedQuery<Employes> query = em.createQuery(jpql, Employes.class);
        List<Employes> employesList = query.getResultList();

        ObservableList<Integer> observableList = FXCollections.observableArrayList();
        for (Employes employes : employesList)
        {
            observableList.add(employes.getId());
        }

        return observableList;
    }

    public void initializeChoiceBoxTacheEmployeId()
    {
        cbxTacheIdentifiantEmploye.setItems(getTacheEmployeId());
    }


    @FXML
    public void saveTache(ActionEvent actionEvent) throws ParseException, NoSuchAlgorithmException, SQLException, ClassNotFoundException
    {
        String titre = txtTacheTitre.getText();
        String description = txtTacheDescription.getText();
        String etat = cbxTacheEtat.getValue();
        String texteDate = String.valueOf(dpTacheDate.getValue());
        Integer employeId = cbxTacheIdentifiantEmploye.getValue();

        if (titre.isEmpty() || description.isEmpty() || etat.isEmpty() || texteDate.isEmpty() || employeId == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Souhaitez-vous ajouter cette tâche ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get().equals(ButtonType.OK))
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
            EntityManager em = emf.createEntityManager();


            Employes employe = em.find(Employes.class, employeId);

            if (employe == null)
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Employé inexistant.");
                alert.showAndWait();
                em.close();
                emf.close();
                return;
            }

            try
            {
                em.getTransaction().begin();

                Tache tache = new Tache();
                tache.setTitre(titre);
                tache.setDescription(description);
                tache.setDate_limite(texteDate);
                tache.setEtat(Tache.Etat.valueOf(etat));
                tache.setEmploye(employe);

                em.persist(tache);


                em.getTransaction().commit();


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Tâche ajoutée avec succès.");
                alert.showAndWait();


                int departementId = employe.getDepartement().getId();
                rechercheEmployeTacheEnFonctionDuDepartement(departementId);

                txtTacheTitre.setText("");
                txtTacheDescription.setText("");
                dpTacheDate.setValue(null);
                cbxTacheEtat.setValue(null);
                cbxTacheIdentifiantEmploye.setValue(null);
            }
            catch (Exception e)
            {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Une erreur est survenue lors de l'ajout de la tâche.");
                alert.showAndWait();
            }
            finally
            {
                em.close();
                emf.close();
            }
        }
        else
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Ajout de la tâche annulé.");
            alert.showAndWait();
        }
    }


    @FXML
    public void UpdateTacheRecoverData(MouseEvent mouseEvent)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        Tache tache = tblTache.getSelectionModel().getSelectedItem();

        txtTacheTitre.setText(tache.getTitre());
        txtTacheDescription.setText(tache.getDescription());
        dpTacheDate.setValue(LocalDate.parse(String.valueOf(tache.getDate_limite())));
        cbxTacheEtat.setValue(String.valueOf(tache.getEtat()));
        cbxTacheIdentifiantEmploye.setValue(tache.getEmployeId());
    }

        @FXML
        void UpdateTache(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, ParseException
        {
            Tache tache = tblTache.getSelectionModel().getSelectedItem();


            if (tache == null)
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Veuillez sélectionner un enregistrement à modifier");
                alert1.showAndWait();
                return;
            }


            String titre = txtTacheTitre.getText();
            String description = txtTacheDescription.getText();
            String etat = cbxTacheEtat.getValue();
            String texteDate = String.valueOf(dpTacheDate.getValue());
            Integer employeId = cbxTacheIdentifiantEmploye.getValue();
            int id = tache.getId();

            if (titre.isEmpty() || description.isEmpty() || etat == null || texteDate == null || employeId == null)
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setContentText("Tous les champs doivent être remplis");
                alert1.showAndWait();
                return;
            }


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setContentText("Modifier la tâche ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get().equals(ButtonType.OK))
            {
                EntityManager em = emf.createEntityManager();
                try {

                    Employes employe = em.find(Employes.class, employeId);
                    if (employe == null)
                    {
                        throw new Exception("Employé non trouvé");
                    }

                    Tache task = em.find(Tache.class, id);
                    if (task == null)
                    {
                        throw new Exception("Tâche non trouvée");
                    }

                    em.getTransaction().begin();

                    task.setTitre(titre);
                    task.setDescription(description);
                    task.setDate_limite(texteDate);
                    task.setEtat(Tache.Etat.valueOf(etat));
                    task.setEmploye(employe);


                    em.getTransaction().commit();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setContentText("Modification effectuée");
                    alert.showAndWait();


                    txtTacheTitre.setText("");
                    txtTacheDescription.setText("");
                    dpTacheDate.setValue(null);
                    cbxTacheEtat.setValue(null);
                    cbxTacheIdentifiantEmploye.setValue(null);


                    rechercheEmployeTacheEnFonctionDuDepartement(employe.getDepartement().getId());

                }
                catch (Exception e)
                {

                    if (em.getTransaction().isActive())
                    {
                        em.getTransaction().rollback();
                    }

                    e.printStackTrace();
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Une erreur s'est produite lors de la mise à jour : " + e.getMessage());
                    alert.showAndWait();
                }
                finally
                {
                    em.close();
                }
            }
            else
            {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Modification non effectuée");
                alert.showAndWait();
            }
    }


    @FXML
    public void supprimerTache(ActionEvent actionEvent) throws SQLException, ClassNotFoundException
    {

        Tache tache = tblTache.getSelectionModel().getSelectedItem();

        if (tache == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un enregistrement à supprimer");
            alert1.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Supprimer cette tâche ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
            EntityManager em = emf.createEntityManager();

            String jpql = "SELECT t FROM Tache t WHERE t.id = :id";

            try {
                em.getTransaction().begin();

                Tache task = em.createQuery(jpql, Tache.class)
                        .setParameter("id", tache.getId())
                        .getSingleResult();

                if (task != null)
                {
                    em.remove(task);

                    em.getTransaction().commit();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setContentText("Suppression effectuée");
                    alert.showAndWait();


                    int departementId = task.getEmploye().getDepartement().getId();
                    rechercheEmployeTacheEnFonctionDuDepartement(departementId);
                }
            }
            catch (Exception e)
            {
                if (em.getTransaction().isActive())
                {
                    em.getTransaction().rollback();
                }

                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Une erreur est survenue lors de la suppression.");
                alert.showAndWait();
            }
            finally
            {
                em.close();
                emf.close();
            }
        }
        else
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Suppression non effectuée");
            alert.showAndWait();
        }

        txtTacheTitre.setText("");
        txtTacheDescription.setText("");
        dpTacheDate.setValue(null);
        cbxTacheEtat.setValue(null);
        cbxTacheIdentifiantEmploye.setValue(null);

    }

        private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");

    @FXML
    void rechercheDepartementParResponsable(int utilisateurId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            String jpql = "SELECT d FROM Departement d WHERE d.responsable.id = :utilisateurId";
            TypedQuery<Departement> query = em.createQuery(jpql, Departement.class);
            query.setParameter("utilisateurId", utilisateurId);
            List<Departement> departementList = query.getResultList();

            if (!departementList.isEmpty())
            {
                Departement departement = departementList.get(0);
                rechercheEmployeTacheEnFonctionDuDepartement(departement.getId());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Pas de département associé à cet utilisateur");
                alert.show();
            }
        }
        catch (PersistenceException e)
        {
            e.printStackTrace();
        }
    }

    protected void rechercheEmployeTacheEnFonctionDuDepartement(int departementId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            String jpql = "SELECT t FROM Tache t WHERE t.employe.departement.id = :departementId";
            TypedQuery<Tache> query = em.createQuery(jpql, Tache.class);
            query.setParameter("departementId", departementId);
            List<Tache> listTaches = query.getResultList();

            if (!listTaches.isEmpty())
            {
                tblTache.setItems(FXCollections.observableArrayList(listTaches));
            }
        }
        catch (PersistenceException e)
        {
            e.printStackTrace();
        }
    }


    protected void loadTache() throws SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        try {
            String jpql = "SELECT t FROM Tache t";
            TypedQuery<Tache> query = em.createQuery(jpql, Tache.class);

            List<Tache> listTaches = query.getResultList();


            ObservableList<Tache> listeableTache = FXCollections.observableArrayList(listTaches);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        em.close();
        emf.close();
    }



    public void initializeTache()
    {

        tblTacheIdentifiant.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblTitreTache.setCellValueFactory(new PropertyValueFactory<>("titre"));
        tblTacheDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblTacheDate.setCellValueFactory(new PropertyValueFactory<>("date_limite"));
        tblTacheEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tblTacheEmployeId.setCellValueFactory(new PropertyValueFactory<>("EmployeId"));
    }

    public void Deconnexion(javafx.scene.input.MouseEvent mouseEvent) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setContentText("Voulez-vous vous déconnecter");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.NO))
        {
            alert.setTitle("Déconnexion");
            alert.setContentText("Déconnexion annuler");
        }

        else if (result.get().equals(ButtonType.OK))
        {
            Outils.loadandwait(mouseEvent, "Déconnexion", "/org/example/Ressources_humaines/connexion-view-ressources.fxml");
        }
    }


}
