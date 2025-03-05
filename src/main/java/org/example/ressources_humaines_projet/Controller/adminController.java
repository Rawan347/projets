package org.example.ressources_humaines_projet.Controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.ressources_humaines_projet.Model.Departement;
import org.example.ressources_humaines_projet.Model.Employes;
import org.example.ressources_humaines_projet.Model.Tache;
import org.example.ressources_humaines_projet.Model.Utilisateurs;
import org.example.ressources_humaines_projet.Classe.DB;
import org.example.ressources_humaines_projet.util.Outils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


public class adminController implements Initializable
{



    DB db = DB.getInstance();

    @FXML
    private Button btnDepartements1;

    @FXML
    private Button btnUtil1;

    @FXML
    private TableView<Departement> tblDepartementD;

    @FXML
    private TableColumn<Departement, Integer> tblDepartementId;

    @FXML
    private TableColumn<Departement, String> tblNomDepartement;

    @FXML
    private TableColumn<Departement, Integer> tblDepartementReesponsableId;


    @FXML
    private Button btnDepartementAjouter;


    @FXML
    public AnchorPane PaneDepartement;

    @FXML
    public Button btnOpenDepartement;

    @FXML
    public TextField txtDepartementNom;

    @FXML
    public TextField txtDepartementResponsableId;

    @FXML
    private Button btnAjouterUtilisateur;

    @FXML
    private Button btnSupprimerUtilisateur;

    @FXML
    private Button btnModifierUtilisateur;

    @FXML
    private TableView<Employes> tblEmployes;

    @FXML
    private TableColumn<Employes, Integer> tblId;

    @FXML
    private TableColumn<Employes, String> tblNomComplet;

    @FXML
    private TableColumn<Employes, String> tblPoste;

    @FXML
    private TableColumn<Employes, Date> tblDate;

    @FXML
    private TableColumn<Employes, BigDecimal> tblSalaire;

    @FXML
    private TableColumn<Employes, Integer> tblDepartement;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField txtDepartement;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPoste;

    @FXML
    private TextField txtSalaire;

    @FXML
    private AnchorPane PaneEmp;

    @FXML
    private AnchorPane anchorUtil;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnDeco;

    @FXML
    private Button btnEmp;

    @FXML
    private ChoiceBox<Integer> cbxDepartement;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnTache;

    @FXML
    private Button btnUtil;


    @FXML
    private TextField txtNomUtilisateur;


    @FXML
    private PasswordField ptMotDePasseUtilisateur;


    @FXML
    private TextField txtRoleUtilisateur;

    @FXML
    private TableView<Utilisateurs> tblUtilisateurs;

    @FXML
    private TableColumn<Utilisateurs, Integer> tblUtilisateursId;

    @FXML
    private TableColumn<Utilisateurs, String> tblUtilisateursMotDePasse;

    @FXML
    private TableColumn<Utilisateurs, String> tblUtilisateursNom;

    @FXML
    private TableColumn<Utilisateurs, String> tblUtilisateurRole;

    @FXML
    private ChoiceBox<Integer> cbxResponsablechoix;


    @FXML
    private Button btnOpenDépartementFromEmploye;

    @FXML
    private Button btnOpenEmployeFromEmploye;

    @FXML
    private Button btnOpenTacheFromEmploye;

    @FXML
    private Button btnOpenUtilisateursFromEmploye;

    @FXML
    private Button btnTacheAjouterFromEmploye;

    @FXML
    private Button btnTacheModifierFromEmploye;

    @FXML
    private Button btnTacheSupprimerFromEmploye;

    @FXML
    private ChoiceBox<String> cbxTacheEtatFromEmploye;

    @FXML
    private ChoiceBox<Integer> cbxTacheIdentifiantEmployeFromEmploye;

    @FXML
    private DatePicker dpTacheDateFromEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheDateFromEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheDescriptionFromEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheEmployeIdFromEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheEtatFromEmploye;

    @FXML
    private TableView<Tache> tblTacheFromEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheIdentifiantFromEmploye;

    @FXML
    private TableColumn<?, ?> tblTitreTacheFromEmploye;

    @FXML
    private TextField txtTacheDescriptionFromEmploye;

    @FXML
    private TextField txtTacheTitreFromEmploye;

    @FXML
    private AnchorPane PaneTacheAdminFromEmploye;

    @FXML
    public void openPaneEmp(ActionEvent actionEvent)
    {
        initializeChoiceBoxDepartement();
        PaneEmp.setVisible(true);
        anchorUtil.setVisible(false);
        PaneDepartement.setVisible(false);
        PaneTacheAdminFromEmploye.setVisible(false);
    }

    @FXML
    public void openPaneUtilisateurs()
    {
        anchorUtil.setVisible(true);
        PaneEmp.setVisible(false);
        PaneDepartement.setVisible(false);
        PaneTacheAdminFromEmploye.setVisible(false);
    }

    @FXML
    public void openPaneDepartement(ActionEvent actionEvent)
    {
        initializeChoiceBox();
        PaneDepartement.setVisible(true);
        anchorUtil.setVisible(false);
        PaneEmp.setVisible(false);
        PaneTacheAdminFromEmploye.setVisible(false);
    }

    @FXML
    public void openPaneAdminTache()
    {
        PaneTacheAdminFromEmploye.setVisible(true);
        anchorUtil.setVisible(false);
        PaneEmp.setVisible(false);
        PaneDepartement.setVisible(false);
        initializeChoiceBoxTacheEtat();
        initializeChoiceBoxTacheEmployeId();
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hashBytes = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes)
        {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    @FXML
    public void saveUtilisateurs(ActionEvent actionEvent) throws ParseException, NoSuchAlgorithmException, SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();


        String nomUtilisateur = txtNomUtilisateur.getText();

        String motDePasseUtilisateur = ptMotDePasseUtilisateur.getText();

        String role = txtRoleUtilisateur.getText();

        if (nomUtilisateur.isEmpty() || motDePasseUtilisateur.isEmpty() || role.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        }

        else
        {
            em.getTransaction().begin();

            Utilisateurs utilisateurs = new Utilisateurs();
            utilisateurs.setNomUtilisateur(nomUtilisateur);
            utilisateurs.setMotDePasse(hashPassword(motDePasseUtilisateur));
            utilisateurs.setRole(Utilisateurs.Role.valueOf(role));

            em.persist(utilisateurs);

            em.getTransaction().commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Information Enregistré avec succès");
            alert.showAndWait();
            txtNomUtilisateur.setText("");
            ptMotDePasseUtilisateur.setText("");
            loadUtilisateurs();
        }
    }

    @FXML
    public void supprimerUtilisateur(ActionEvent actionEvent) throws SQLException, ClassNotFoundException
    {
        Utilisateurs utilisateurs = tblUtilisateurs.getSelectionModel().getSelectedItem();

        if (utilisateurs == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un utilisateur à supprimer");
            alert1.showAndWait();
            return;
        }

        int responsableId = utilisateurs.getId();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpqlCount = "SELECT COUNT(d) FROM Departement d WHERE d.responsable.id = :responsableId";
        TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
        queryCount.setParameter("responsableId", responsableId);
        Long count = queryCount.getSingleResult();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Supprimer?");
        Optional<ButtonType> result = alert.showAndWait();


                if (count != 0)
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setContentText("Vous ne pouvez pas supprimer cet utilisateur car il est responsable d'un département");
                    alert1.showAndWait();
                }

                else if (result.get().equals(ButtonType.CANCEL))
                {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Suppression non effectuée");
                    alert.showAndWait();
                }

                else
                {
                    em.getTransaction().begin();
                    utilisateurs = em.find(Utilisateurs.class, utilisateurs.getId());

                    if (utilisateurs != null)
                    {
                        em.remove(utilisateurs);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setContentText("Suppression effectuée");
                        alert.showAndWait();
                        em.getTransaction().commit();
                    }
                    loadUtilisateurs();
                    txtNomUtilisateur.setText("");
                    ptMotDePasseUtilisateur.setText("");
                }

            em.close();
            emf.close();
        }


    @FXML
    public void UpdateUtilisateur(MouseEvent mouseEvent)
    {
        Utilisateurs utilisateurs = tblUtilisateurs.getSelectionModel().getSelectedItem();
        txtNomUtilisateur.setText(utilisateurs.getNomUtilisateur());
        ptMotDePasseUtilisateur.setText(utilisateurs.getMotDePasse());
        txtRoleUtilisateur.setText(String.valueOf(utilisateurs.getRole()));
    }

    @FXML
    void UpdateUtilisateurs2(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException
    {

        Utilisateurs utilisateurs = tblUtilisateurs.getSelectionModel().getSelectedItem();

        if (utilisateurs == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un utilisateur à modifier");
            alert1.showAndWait();
            return;
        }

        String nomUtil = txtNomUtilisateur.getText();
        String motPasse = ptMotDePasseUtilisateur.getText();
        String role = txtRoleUtilisateur.getText();


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT u from Utilisateurs u WHERE u.id = :id";


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Modifier?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.OK))
        {
            int id = utilisateurs.getId();
            em.getTransaction().begin();

            Utilisateurs user = em.createQuery(jpql, Utilisateurs.class)
                    .setParameter("id", utilisateurs.getId())
                    .getSingleResult();

            if (user != null)
            {
                user.setNomUtilisateur(nomUtil);
                user.setMotDePasse(String.valueOf(hashPassword(motPasse)));
                user.setRole(Utilisateurs.Role.valueOf(role));

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Modification effectuée");
                alert.showAndWait();
                em.getTransaction().commit();
            }
            loadUtilisateurs();
        }

        else if (result.get().equals(ButtonType.CANCEL))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Modification non effectuée");
            alert.showAndWait();
        }
        txtNomUtilisateur.setText("");
        ptMotDePasseUtilisateur.setText("");
        txtRoleUtilisateur.setText("Manager");
        em.close();
        emf.close();
    }


    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tblUtilisateursId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblUtilisateursNom.setCellValueFactory(new PropertyValueFactory<>("nomUtilisateur"));
        tblUtilisateursMotDePasse.setCellValueFactory(new PropertyValueFactory<>("motDePasse"));
        tblUtilisateurRole.setCellValueFactory(new PropertyValueFactory<>("role"));


        try
        {
            loadUtilisateurs();
            initializeDepartement();
            initializeEmployee();
            initializeTache();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

    }


    protected void loadUtilisateurs() throws SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT u from Utilisateurs u";
        TypedQuery<Utilisateurs> query = em.createQuery(jpql, Utilisateurs.class);

        List<Utilisateurs> listUtilisateurs = query.getResultList();

        ObservableList<Utilisateurs> listeable = FXCollections.observableArrayList(listUtilisateurs);

        tblUtilisateurs.setItems(listeable);

        em.close();
        emf.close();
    }


        public ObservableList<Integer> getUtilisateurIds()
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
            EntityManager em = emf.createEntityManager();


            String jpql = "SELECT u FROM Utilisateurs u";

            TypedQuery<Utilisateurs> query = em.createQuery(jpql, Utilisateurs.class);
            List<Utilisateurs> utilisateursList = query.getResultList();

            ObservableList<Integer> observableList = FXCollections.observableArrayList();
            for (Utilisateurs utilisateur : utilisateursList)
            {
                observableList.add(utilisateur.getId());
            }

            return observableList;
    }

    public void initializeChoiceBox()
    {
        cbxResponsablechoix.setItems(getUtilisateurIds());
    }

    @FXML
    public void saveDepartements(ActionEvent actionEvent) throws ParseException, NoSuchAlgorithmException, SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();


        String nomDepartement = txtDepartementNom.getText();
        Integer responsableId = cbxResponsablechoix.getValue();

        if (nomDepartement.isEmpty() || responsableId == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }

        String jpqlCount = "SELECT COUNT(d) FROM Departement d WHERE d.responsable.id = :responsableId";
        TypedQuery<Long> queryCount = em.createQuery(jpqlCount, Long.class);
        queryCount.setParameter("responsableId", responsableId);
        Long count = queryCount.getSingleResult();

        if (count > 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Cet utilisateur est déja responsable d'un département.");
            alert.showAndWait();
            return;
        }


        Utilisateurs responsable = em.find(Utilisateurs.class, responsableId);
        if (responsable == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Utilisateur inexistant");
            alert.showAndWait();
            return;
        }


        em.getTransaction().begin();
        Departement departement = new Departement();
        departement.setNom(nomDepartement);
        departement.setResponsable(responsable);

        em.persist(departement);
        em.getTransaction().commit();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Département enregistré avec succès");
        alert.showAndWait();

        txtDepartementNom.setText("");
        cbxResponsablechoix.setValue(null);

        loadDepartement();

        em.close();
        emf.close();
    }


    @FXML
    public void supprimerDepartement(ActionEvent actionEvent) throws SQLException, ClassNotFoundException
    {
        Departement departement = tblDepartementD.getSelectionModel().getSelectedItem();
        Employes employes = tblEmployes.getSelectionModel().getSelectedItem();

        if (departement == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un département à supprimer");
            alert1.showAndWait();
            return;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT e from Employes e WHERE departement.id = :departement";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Supprimer?");
        Optional<ButtonType> result = alert.showAndWait();


        if (result.get().equals(ButtonType.CANCEL))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Suppression non effectuée");
            alert.showAndWait();
        }

        else if (result.get().equals(ButtonType.OK))
        {
            alert.setTitle("Message");
            alert.setContentText("Voulez-vous annuler l'affectation du responsable et supprimer le département et tous les employés?");
            result = alert.showAndWait();

            if (result.get().equals(ButtonType.CANCEL))
            {
                alert.setContentText("Suppresion annulé");
                alert.showAndWait();
            }

            else if (result.get().equals(ButtonType.OK))
            {
                em.getTransaction().begin();

                String jpqlEmployees = "SELECT e FROM Employes e WHERE e.departement.id = :departementId";
                List<Employes> employees = em.createQuery(jpqlEmployees, Employes.class)
                        .setParameter("departementId", departement.getId())
                        .getResultList();

                for (Employes employee : employees)
                {
                    em.remove(employee);
                }

                departement.setResponsableId(null);
                departement = em.find(Departement.class, departement.getId());

                if (departement != null)
                {
                    em.remove(departement);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setContentText("Suppression effectuée");
                    alert.showAndWait();
                }

                em.getTransaction().commit();
                loadDepartement();
                loadEmployee();

            }
        }

        txtDepartementNom.setText("");
        cbxResponsablechoix.setValue(null);
        em.close();
        emf.close();
    }

    @FXML
    public void UpdateDepartementRecoverData(MouseEvent mouseEvent)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        Departement departement = tblDepartementD.getSelectionModel().getSelectedItem();
        txtDepartementNom.setText(departement.getNom());
        cbxResponsablechoix.setValue(departement.getResponsableId());

    }

    @FXML
    void UpdateDepartement(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException
    {

        Departement departement = tblDepartementD.getSelectionModel().getSelectedItem();

        if (departement == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un enregistrement à modifier");
            alert1.showAndWait();
            return;
        }

        String nomDept = txtDepartementNom.getText();
        Integer responsableId = cbxResponsablechoix.getValue();
        int id = departement.getId();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT d from Departement d WHERE d.id = :id";


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Modifier?");
        Optional<ButtonType> result = alert.showAndWait();

          if (result.get().equals(ButtonType.CANCEL))
    {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Modification non effectuée");
        alert.showAndWait();
    }

        else if (result.get().equals(ButtonType.OK))
        {
            Utilisateurs responsable = em.find(Utilisateurs.class, responsableId);
            em.getTransaction().begin();

            Departement dept = em.createQuery(jpql, Departement.class)
                    .setParameter("id", departement.getId())
                    .getSingleResult();

            if (dept != null)
            {
                dept.setNom(nomDept);
                dept.setResponsable(responsable);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Modification effectuée");
                alert.showAndWait();
                em.getTransaction().commit();
                cbxResponsablechoix.setValue(null);
                txtDepartementNom.setText("");
            }
          loadDepartement();
        }

        em.close();
        emf.close();
    }


    public void initializeDepartement()
    {
        tblDepartementId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomDepartement.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tblDepartementReesponsableId.setCellValueFactory(new PropertyValueFactory<>("ResponsableId"));


        try
        {
            loadDepartement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    protected void loadDepartement() throws SQLException, ClassNotFoundException
    {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        try {
            String jpql = "SELECT d FROM Departement d";
            TypedQuery<Departement> query = em.createQuery(jpql, Departement.class);

            List<Departement> listDepartement = query.getResultList();


            ObservableList<Departement> listeableDepartement = FXCollections.observableArrayList(listDepartement);

            tblDepartementD.setItems(listeableDepartement);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        em.close();
        emf.close();
    }


    public ObservableList<Integer> getDepartementsIds()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();


        String jpql = "SELECT d FROM Departement d";

        TypedQuery<Departement> query = em.createQuery(jpql, Departement.class);
        List<Departement> departementslist = query.getResultList();

        ObservableList<Integer> observableList = FXCollections.observableArrayList();
        for (Departement departement : departementslist)
        {
            observableList.add(departement.getId());
        }

        return observableList;
    }

    public void initializeChoiceBoxDepartement()
    {
        cbxDepartement.setItems(getDepartementsIds());
    }

    @FXML
    public void saveEmployee(ActionEvent actionEvent) throws ParseException, NoSuchAlgorithmException, SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String nomComplet = txtNom.getText();
        String poste = txtPoste.getText();
        BigDecimal salaire = new BigDecimal(txtSalaire.getText());
        String texteDate = String.valueOf(dpDate.getValue());
        Integer departementId = cbxDepartement.getValue();

        if (nomComplet.isEmpty() || poste.isEmpty() || texteDate == null || departementId == null || salaire.compareTo(BigDecimal.ZERO) < 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        }

        Departement identifiant = em.find(Departement.class, departementId);

        if (identifiant == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Département inexistant");
            alert.showAndWait();
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setContentText("Département existant");
            alert.showAndWait();

            em.getTransaction().begin();

            Employes employes = new Employes();
            employes.setNomComplet(nomComplet);
            employes.setPoste(poste);
            employes.setSalaire(salaire);
            employes.setDateEmbauche(texteDate);
            employes.setDepartement(identifiant);

            em.persist(employes);

            em.getTransaction().commit();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Information Enregistré avec succès");
            alert.showAndWait();

            txtNom.setText("");
            txtPoste.setText("");
            txtSalaire.setText("");
            dpDate.setValue(null);
            cbxDepartement.setValue(null);
        }

        txtNom.setText("");
        txtPoste.setText("");
        txtSalaire.setText("");
        dpDate.setValue(null);
        cbxDepartement.setValue(null);
        loadEmployee();

        em.close();
        emf.close();

    }


    @FXML
    public void supprimerEmploye(ActionEvent actionEvent) throws SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        Employes employes = tblEmployes.getSelectionModel().getSelectedItem();

        if (employes == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un département à supprimer");
            alert1.showAndWait();
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Suppresion complète de l'employé et de ses tâches?");
        Optional<ButtonType> result = alert.showAndWait();


        if (result.get().equals(ButtonType.CANCEL))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Suppression non effectuée");
            alert.showAndWait();
        }

            else if (result.get().equals(ButtonType.OK))
            {
                em.getTransaction().begin();

                String jpqlTache = "SELECT t FROM Tache t WHERE t.employe.id = :employe_id";
                List<Tache> taches = em.createQuery(jpqlTache, Tache.class)
                        .setParameter("employe_id", employes.getId())
                        .getResultList();

                for (Tache tache : taches)
                {
                    em.remove(tache);
                }

                employes.setDepartementId(null);
                employes = em.find(Employes.class, employes.getId());

                if (employes != null)
                {
                    em.remove(employes);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setContentText("Suppression effectuée");
                    alert.showAndWait();
                    em.getTransaction().commit();
                }
                loadEmployee();
                loadTache();
            }

        txtNom.setText("");
        txtPoste.setText("");
        txtSalaire.setText("");
        dpDate.setValue(null);
        cbxDepartement.setValue(null);
        em.close();
        emf.close();
    }

    @FXML
    public void UpdateEmployeRecoverData(MouseEvent mouseEvent)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        Employes employes = tblEmployes.getSelectionModel().getSelectedItem();

        txtNom.setText(employes.getNomComplet());
        txtPoste.setText(employes.getPoste());
        txtSalaire.setText(String.valueOf(employes.getSalaire()));
        dpDate.setValue(LocalDate.parse(String.valueOf(employes.getDateEmbauche())));
        cbxDepartement.setValue(employes.getDepartementId());
    }

    @FXML
    void UpdateEmploye(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, ParseException
    {

        Employes employes = tblEmployes.getSelectionModel().getSelectedItem();

        if (employes == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un enregistrement à modifier");
            alert1.showAndWait();
            return;
        }

        String nomComplet = txtNom.getText();
        String poste = txtPoste.getText();
        BigDecimal salaire = new BigDecimal(txtSalaire.getText());
        String texteDate = String.valueOf(dpDate.getValue());
        Integer departementId = cbxDepartement.getValue();
        int id = employes.getId();



        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT e from Employes e WHERE e.id = :id";


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setContentText("Modifier?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(ButtonType.CANCEL))
    {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Modification non effectuée");
        alert.showAndWait();
    }

        else if (result.get().equals(ButtonType.OK))
        {
            Departement assignment = em.find(Departement.class, departementId);
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
                worker.setDepartement(assignment);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("Modification effectuée");
                alert.showAndWait();
                em.getTransaction().commit();
                txtNom.setText("");
                txtPoste.setText("");
                txtSalaire.setText("");
                dpDate.setValue(null);
                cbxDepartement.setValue(null);
            }
           loadEmployee();
        }
        em.close();
        emf.close();
    }



    public void initializeEmployee()
    {
        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomComplet.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        tblPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        tblDate.setCellValueFactory(new PropertyValueFactory<>("dateEmbauche"));
        tblSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        tblDepartement.setCellValueFactory(new PropertyValueFactory<>("DepartementId"));

        try
        {
            loadEmployee();
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

            tblEmployes.setItems(listeableEmploye);
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
        cbxTacheEtatFromEmploye.setItems(getTacheEtats());
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
        cbxTacheIdentifiantEmployeFromEmploye.setItems(getTacheEmployeId());
    }


    @FXML
    public void saveTache(ActionEvent actionEvent) throws ParseException, NoSuchAlgorithmException, SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String titre = txtTacheTitreFromEmploye.getText();
        String description = txtTacheDescriptionFromEmploye.getText();
        String etat = cbxTacheEtatFromEmploye.getValue();
        String texteDate = String.valueOf(dpTacheDateFromEmploye.getValue());
        Integer EmployeId = cbxTacheIdentifiantEmployeFromEmploye.getValue();


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

        txtTacheTitreFromEmploye.setText("");
        txtTacheDescriptionFromEmploye.setText("");
        dpTacheDateFromEmploye.setValue(null);
        cbxTacheEtatFromEmploye.setValue(null);
        cbxTacheIdentifiantEmployeFromEmploye.setValue(null);
        loadTache();
        em.close();
        emf.close();

    }

    @FXML
    public void UpdateTacheRecoverData(MouseEvent mouseEvent)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        Tache tache = tblTacheFromEmploye.getSelectionModel().getSelectedItem();

        if (tache == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un département à supprimer");
            alert1.showAndWait();
            return;
        }

        txtTacheTitreFromEmploye.setText(tache.getTitre());
        txtTacheDescriptionFromEmploye.setText(tache.getDescription());
        dpTacheDateFromEmploye.setValue(LocalDate.parse(String.valueOf(tache.getDate_limite())));
        cbxTacheEtatFromEmploye.setValue(String.valueOf(tache.getEtat()));
        cbxTacheIdentifiantEmployeFromEmploye.setValue(tache.getEmployeId());
    }

    @FXML
    void UpdateTache(ActionEvent event) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, ParseException
    {

        Tache tache = tblTacheFromEmploye.getSelectionModel().getSelectedItem();

        if (tache == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un enregistrement à modifier");
            alert1.showAndWait();
            return;
        }


        String titre = txtTacheTitreFromEmploye.getText();
        String description = txtTacheDescriptionFromEmploye.getText();
        String etat = cbxTacheEtatFromEmploye.getValue();
        String texteDate = String.valueOf(dpTacheDateFromEmploye.getValue());
        Integer employeId = cbxTacheIdentifiantEmployeFromEmploye.getValue();
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
                txtTacheTitreFromEmploye.setText("");
                txtTacheDescriptionFromEmploye.setText("");
                dpTacheDateFromEmploye.setValue(null);
                cbxTacheEtatFromEmploye.setValue(null);
                cbxTacheIdentifiantEmployeFromEmploye.setValue(null);
            }
            loadTache();
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
        Tache tache = tblTacheFromEmploye.getSelectionModel().getSelectedItem();

        if (tache == null)
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Veuillez sélectionner un département à supprimer");
            alert1.showAndWait();
            return;
        }

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
            loadTache();
        }
        else if (result.get().equals(ButtonType.CANCEL))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Suppression non effectuée");
            alert.showAndWait();
        }

        txtTacheTitreFromEmploye.setText("");
        txtTacheDescriptionFromEmploye.setText("");
        dpTacheDateFromEmploye.setValue(null);
        cbxTacheEtatFromEmploye.setValue(null);
        cbxTacheIdentifiantEmployeFromEmploye.setValue(null);
        em.close();
        emf.close();
    }



    public void initializeTache()
    {

        tblTacheIdentifiantFromEmploye.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblTitreTacheFromEmploye.setCellValueFactory(new PropertyValueFactory<>("titre"));
        tblTacheDescriptionFromEmploye.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblTacheDateFromEmploye.setCellValueFactory(new PropertyValueFactory<>("date_limite"));
        tblTacheEtatFromEmploye.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tblTacheEmployeIdFromEmploye.setCellValueFactory(new PropertyValueFactory<>("EmployeId"));


        try
        {
            loadTache();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    protected void loadTache() throws SQLException, ClassNotFoundException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        try {
            String jpql = "SELECT t FROM Tache t";
            TypedQuery<Tache> query = em.createQuery(jpql, Tache.class);

            List<Tache> listTaches = query.getResultList();


            ObservableList<Tache> listeableTache = FXCollections.observableArrayList(listTaches);

            tblTacheFromEmploye.setItems(listeableTache);
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        emf.close();

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
            Outils.loadandwait(mouseEvent, "Déconnexion", "/org/example/Ressources_humaines_projet/connexion-view-ressources.fxml");
        }
    }


}