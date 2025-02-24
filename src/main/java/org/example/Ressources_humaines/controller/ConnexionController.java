package org.example.Ressources_humaines.controller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.beans.binding.ObjectExpression;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.Ressources_humaines.Model.Utilisateurs;
import org.example.Ressources_humaines.classe.DB;
import org.example.Ressources_humaines.util.Outils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class ConnexionController {
    DB db = DB.getInstance();

    @FXML
    private Button btnEnregistrerConnexion;

    @FXML
    private TextField txtMotDePasseConnexion;

    @FXML
    private TextField txtNomConnexion;

    public void connexionEmploye(ActionEvent actionEvent) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information");
        alert.setContentText("Bienvenue");
        alert.showAndWait();
        Outils.loadandwait(actionEvent, "Ressources_humaines", "/org/example/Ressources_humaines/employe-view-ressources.fxml");
    }

    @FXML
    public void connexion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();

        String nom = txtNomConnexion.getText();
        String motDePasse = txtMotDePasseConnexion.getText();

        if (nom.isEmpty() || motDePasse.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vérification");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        }

        else
        {
            String jpql = "SELECT u FROM Utilisateurs u WHERE u.nomUtilisateur = :nom AND u.motDePasse = :motDePasse";
            TypedQuery<Utilisateurs> query = em.createQuery(jpql, Utilisateurs.class);
            query.setParameter("nom", nom);
            query.setParameter("motDePasse", motDePasse);
            List<Utilisateurs> listUtilisateur = query.getResultList();



            if (listUtilisateur.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vérification");
                alert.setContentText("Utilisateur non enregistré où informations invalident");
                alert.showAndWait();
            }


            Utilisateurs utilisateur = listUtilisateur.get(0);
            String role = String.valueOf(utilisateur.getRole());


            if (!listUtilisateur.isEmpty() && role.equals("Admin"))
            {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Informations");
                alert.setContentText("Bienvenue: " + "Mr " + utilisateur.getNomUtilisateur());
                alert.showAndWait();
                Outils.loadandwait(actionEvent , "Ressources_humaines", "/org/example/Ressources_humaines/admin-view-ressources.fxml");

            }

            else if (!listUtilisateur.isEmpty() && role.equals("Manager"))
            {
                int utilisateurId = utilisateur.getId();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Informations");
                alert.setContentText("Bienvenue: " + "Mr " + utilisateur.getNomUtilisateur());
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Ressources_humaines/manager-view-ressources.fxml"));
                Parent root = loader.load();
                managerController controller = loader.getController();
                controller.rechercheDepartementParResponsable(utilisateurId);
                controller.rechercheEmployeTacheEnFonctionDuDepartement(utilisateurId);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
              Outils.loadandwait(actionEvent, "Ressources_humaines", "/org/example/Ressources_humaines/manager-view-ressources.fxml");

            }
        }

        txtNomConnexion.setText("");
        txtMotDePasseConnexion.setText("");

    }



}
