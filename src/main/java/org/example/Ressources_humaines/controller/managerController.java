package org.example.Ressources_humaines.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.Ressources_humaines.Model.Departement;
import org.example.Ressources_humaines.Model.Employes;
import org.example.Ressources_humaines.Model.Tache;

import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void openManagerEmploye(ActionEvent actionEvent) throws SQLException, ClassNotFoundException
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
    void UpdateEmployeManager(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, ParseException
    {

        Employes employes = tblManagerEmploye.getSelectionModel().getSelectedItem();

        if (employes == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un enregistrement à modifier");
            alert1.showAndWait();
            return;
        }

        String nomComplet = txtNomManagerEmploye.getText();
        String poste = txtPosteManagerEmploye.getText();
        BigDecimal salaire = new BigDecimal(txtSalaireManagerEmploye.getText());
        String texteDate = String.valueOf(dpManagerEmployeDate.getValue());
        int id = employes.getId();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT e from Employes e WHERE e.id = :id";


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Modifier?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.OK))
        {
            em.getTransaction().begin();

            Employes worker = em.createQuery(jpql, Employes.class)
                    .setParameter("id", employes.getId())
                    .getSingleResult();

            if (worker != null)
            {
                worker.setNomComplet(nomComplet);
                worker.setPoste(poste);
                worker.setSalaire(salaire);
                worker.setDateEmbauche(texteDate);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Modification effectuée");
                alert.showAndWait();
                em.getTransaction().commit();
                txtNomManagerEmploye.setText("");
                txtPosteManagerEmploye.setText("");
                dpManagerEmployeDate.setValue(null);
                txtSalaireManagerEmploye.setText("");
            }
        }
        else if (result.get().equals(ButtonType.CANCEL))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Modification non effectuée");
            alert.showAndWait();
        }
        em.close();
        emf.close();
    }

    @FXML
    public void rechercheDepartementParResponsable(int responsableId) throws SQLException, ClassNotFoundException
    {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");

        try (EntityManager em = emf.createEntityManager())
        {
            String jpqlResponsable = "SELECT d FROM Departement d WHERE d.responsable.id = :responsableId";
            TypedQuery<Departement> queryResponsable = em.createQuery(jpqlResponsable, Departement.class);
            queryResponsable.setParameter("responsableId", responsableId);

            List<Departement> departements = queryResponsable.getResultList();


            if (departements.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Aucun département trouvé pour ce responsable.");
                alert.showAndWait();
                return;
            }


            Departement departement = departements.get(0);
            int departementId = departement.getId();


            try {
                loadEmployeParDepartement(departementId, em);
            }
            catch (Exception e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors du chargement des employés: " + e.getMessage());
                alert.showAndWait();
            }

        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    protected void loadEmployeParDepartement(int departementId, EntityManager em) throws SQLException, ClassNotFoundException
    {
        String jpql = "SELECT e FROM Employes e WHERE e.departement.id = :departementId";
        TypedQuery<Employes> query = em.createQuery(jpql, Employes.class);
        query.setParameter("departementId", departementId);
        List<Employes> employesList = query.getResultList();

        if (employesList.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucun Employé");
            alert.setContentText("Aucun employé trouvé dans ce département.");
            alert.showAndWait();
        }
        else
        {
            ObservableList<Employes> observableListEmployes = FXCollections.observableList(employesList);
            tblManagerEmploye.setItems(observableListEmployes);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tblIdManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomCompletManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        tblPosteManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("poste"));
        tblDateManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("dateEmbauche"));
        tblSalaireManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        tbldentifiantDepartementManagerEmploye.setCellValueFactory(new PropertyValueFactory<>("departementId"));

        initializeTache();
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String titre = txtTacheTitre.getText();
        String description = txtTacheDescription.getText();
        String etat = cbxTacheEtat.getValue();
        String texteDate = String.valueOf(dpTacheDate.getValue());
        Integer EmployeId = cbxTacheIdentifiantEmploye.getValue();


        if (titre.isEmpty() || description.isEmpty() || etat.isEmpty() || texteDate.isEmpty() || EmployeId == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        }

        Employes identifiant = em.find(Employes.class, EmployeId);

        String jpql = "SELECT COUNT(e) FROM Employes e WHERE e.id = :EmployeId";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("EmployeId", EmployeId);
        Long count = query.getSingleResult();


        if (identifiant == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText("Employe inexistant");
            alert.showAndWait();
        }

        else
        {

            if (count > 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("employe existant");
            alert.showAndWait();
        }

            em.getTransaction().begin();

            Tache tache = new Tache();
            tache.setTitre(titre);
            tache.setDescription(description);
            tache.setDate_limite(texteDate);
            tache.setEtat(Tache.Etat.valueOf(etat));
            tache.setEmploye(identifiant);

            em.persist(tache);

            em.getTransaction().commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Tâche Enregistré avec succès");
            alert.showAndWait();
        }

        txtTacheTitre.setText("");
        txtTacheDescription.setText("");
        dpTacheDate.setValue(null);
        cbxTacheEtat.setValue(null);
        cbxTacheIdentifiantEmploye.setValue(null);
        em.close();
        emf.close();

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


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT t FROM Tache t WHERE t.id = :id";


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Modifier?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.OK))
        {
            Employes resp = em.find(Employes.class, employeId);
            em.getTransaction().begin();

            Tache task = em.createQuery(jpql, Tache.class)
                    .setParameter("id", tache.getId())
                    .getSingleResult();

            if (task != null)
            {
                task.setTitre(titre);
                task.setDescription(description);
                task.setDate_limite(texteDate);
                task.setEtat(Tache.Etat.valueOf(etat));
                task.setEmploye(resp);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Modification effectuée");
                alert.showAndWait();
                em.getTransaction().commit();
                txtTacheTitre.setText("");
                txtTacheDescription.setText("");
                dpTacheDate.setValue(null);
                cbxTacheEtat.setValue(null);
                cbxTacheIdentifiantEmploye.setValue(null);
            }

        }
        else if (result.get().equals(ButtonType.CANCEL))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Modification non effectuée");
            alert.showAndWait();
        }
        em.close();
        emf.close();
    }

    @FXML
    public void supprimerTache(ActionEvent actionEvent) throws SQLException, ClassNotFoundException
    {
        Tache tache = tblTache.getSelectionModel().getSelectedItem();

        if (tache == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un enregistrement à modifier");
            alert1.showAndWait();
            return;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT t from Tache t WHERE t.id = :id";


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Supprimer?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.OK))
        {
            em.getTransaction().begin();

            Tache task = em.createQuery(jpql, Tache.class)
                    .setParameter("id", tache.getId())
                    .getSingleResult();

            if (task != null)
            {
                em.remove(task);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Suppression effectuée");
                alert.showAndWait();
                em.getTransaction().commit();
            }
        }
        else if (result.get().equals(ButtonType.CANCEL))
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
        em.close();
        emf.close();
    }



    @FXML
    public void rechercheEmployeTacheEnFonctionDuDepartement(int utilisateurId) throws SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        try (EntityManager em = emf.createEntityManager())
        {
            String jpqlResponsable = "SELECT d FROM Departement d WHERE d.responsable.id = :responsableId";
            TypedQuery<Departement> queryResponsable = em.createQuery(jpqlResponsable, Departement.class);
            queryResponsable.setParameter("responsableId", utilisateurId);

            List<Departement> departements = queryResponsable.getResultList();

            if (departements.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Aucun département trouvé pour ce responsable.");
                alert.showAndWait();
                return;
            }

            Departement departement = departements.get(0);
            int departementId = departement.getId();


            String jpqlEmployes = "SELECT e FROM Employes e WHERE e.departement.id = :departementId";
            TypedQuery<Employes> queryEmployes = em.createQuery(jpqlEmployes, Employes.class);
            queryEmployes.setParameter("departementId", departementId);
            List<Employes> employesList = queryEmployes.getResultList();

            if (employesList.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Aucun Employé");
                alert.setContentText("Aucun employé trouvé dans ce département.");
                alert.showAndWait();
            }
            else
            {

                for (Employes employe : employesList)
                {
                    loadTachesParEmploye(employe);
                }
            }
        }
    }


    protected void loadTachesParEmploye(Employes employe) throws SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        try (EntityManager em = emf.createEntityManager())
        {
            String jpqlTaches = "SELECT t FROM Tache t WHERE t.employe.id = :employeId";
            TypedQuery<Tache> queryTaches = em.createQuery(jpqlTaches, Tache.class);
            queryTaches.setParameter("employeId", employe.getId());

            List<Tache> tachesList = queryTaches.getResultList();

            if (!tachesList.isEmpty())
            {
                ObservableList<Tache> observableListTaches = FXCollections.observableList(tachesList);
                tblTache.setItems(observableListTaches);
            }
        }
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



}
