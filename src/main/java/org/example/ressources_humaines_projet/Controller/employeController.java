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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.example.ressources_humaines_projet.Model.Employes;
import org.example.ressources_humaines_projet.Model.Tache;
import org.example.ressources_humaines_projet.util.Outils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class employeController implements Initializable
{
    @FXML
    private Pane PaneAdmin1;

    @FXML
    private AnchorPane PaneTacheManager;

    @FXML
    private Button btnCloseViewEmploye;

    @FXML
    private Label lblEmployeRecherche;

    @FXML
    private TableView<Tache> tblTacheAfficheEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheDateEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheDescriptionEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheEmployeIdEmploye;

    @FXML
    private TableColumn<?, ?> tblTacheEtatEMploye;

    @FXML
    private TableColumn<?, ?> tblTacheIdentifiantEmploye;

    @FXML
    private TableColumn<?, ?> tblTitreTacheEmploye;

    @FXML
    private TextField txtTacheIdEmploye;



    @FXML
    void rechercheTacheParEmploye(ActionEvent mouseEvent) throws SQLException, ClassNotFoundException
    {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
                EntityManager em = emf.createEntityManager();

         String employeIdText = txtTacheIdEmploye.getText();

        if (employeIdText.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ est vide veuillez le remplir");
            alert.showAndWait();
        }

        else
        {
            int employeId;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Recherche");
            alert.setContentText("Voulez-vous effectuée cette recherche?");
            Optional<ButtonType> result = alert.showAndWait();
            alert.showAndWait();

            if (result.get().equals(ButtonType.NO))
            {

                alert.setTitle("Information");
                alert.setContentText("Recherche non effectuée");
                alert.showAndWait();
            }

            else if (result.get().equals(ButtonType.OK))
            {
                employeId = Integer.parseInt(employeIdText);

                String jpql = "SELECT e from Employes e WHERE e.id = :employeId";
                TypedQuery<Employes> query = em.createQuery(jpql, Employes.class);
                query.setParameter("employeId", employeId);
                List<Employes> employesList = query.getResultList();

                Employes emp = employesList.get(0);


                lblEmployeRecherche.setText("Tache de " +emp.getNomComplet());

                alert.setTitle("Information");
                alert.setContentText("Recheche éffectuée");
                alert.showAndWait();

                try
                {
                    loadTacheParEmploye(employeId);
                }

                catch (SQLException | ClassNotFoundException e)
                {
                    throw new RuntimeException(e);
                }

                txtTacheIdEmploye.setText("");
            }

        }


    }

    protected void loadTacheParEmploye(int employeId) throws SQLException, ClassNotFoundException
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT t from Tache t WHERE t.employe.id = :employeId";
        TypedQuery<Tache> query = em.createQuery(jpql, Tache.class);
        query.setParameter("employeId", employeId);
        List<Tache> listTaches = query.getResultList();

        ObservableList<Tache> observableListTache = FXCollections.observableList(listTaches);
        tblTacheAfficheEmploye.setItems(observableListTache);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tblTacheIdentifiantEmploye.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblTitreTacheEmploye.setCellValueFactory(new PropertyValueFactory<>("titre"));
        tblTacheDescriptionEmploye.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblTacheDateEmploye.setCellValueFactory(new PropertyValueFactory<>("date_limite"));
        tblTacheEtatEMploye.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tblTacheEmployeIdEmploye.setCellValueFactory(new PropertyValueFactory<>("EmployeId"));
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
