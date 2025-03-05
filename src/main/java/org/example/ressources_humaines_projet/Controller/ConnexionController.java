package org.example.ressources_humaines_projet.Controller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.ressources_humaines_projet.Model.Utilisateurs;
import org.example.ressources_humaines_projet.Classe.DB;
import org.example.ressources_humaines_projet.util.Outils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;


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
        Outils.loadandwait(actionEvent, "Ressources_humaines_projet", "/org/example/Ressources_humaines_projet/employe-view-ressources.fxml");
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes)
        {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    @FXML
    public void connexion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("source");
        EntityManager em = emf.createEntityManager();


        String nom = txtNomConnexion.getText();
        String motDePasse = txtMotDePasseConnexion.getText();
        String motDePasseHache = hashPassword(motDePasse);


        if (nom.isEmpty() || motDePasse.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vérification");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        } else {

            String jpql = "SELECT u FROM Utilisateurs u WHERE u.nomUtilisateur = :nom AND u.motDePasse = :motDePasse";
            TypedQuery<Utilisateurs> query = em.createQuery(jpql, Utilisateurs.class);
            query.setParameter("nom", nom);
            query.setParameter("motDePasse", motDePasseHache);
            List<Utilisateurs> listUtilisateur = query.getResultList();


            if (listUtilisateur.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Vérification");
                alert.setContentText("Utilisateur non enregistré ou informations invalides");
                alert.showAndWait();
            } else {
                Utilisateurs utilisateur = listUtilisateur.get(0);
                String role = String.valueOf(utilisateur.getRole());


                if (role.equals("Admin") && motDePasseHache.equals(utilisateur.getMotDePasse())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Informations");
                    alert.setContentText("Bienvenue: " + utilisateur.getNomUtilisateur());
                    alert.showAndWait();


                    Outils.loadandwait(actionEvent, "Ressources_humaines_projet", "/org/example/Ressources_humaines_projet/admin-view-ressources.fxml");


                } else if (role.equals("Manager") && motDePasseHache.equals(utilisateur.getMotDePasse())) {
                    int utilisateurId = utilisateur.getId();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Informations");
                    alert.setContentText("Bienvenue: " + utilisateur.getNomUtilisateur());
                    alert.showAndWait();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Ressources_humaines_projet/manager-view-ressources.fxml"));
                    Parent root = loader.load();


                    managerController controller = loader.getController();
                    controller.rechercheDepartementParResponsable(utilisateurId);
                    controller.rechercheEmployeTacheEnFonctionDuDepartement(utilisateurId);
                    controller.rechercheEmployeParResponsable(utilisateurId);


                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    if (currentStage != null) {
                        currentStage.hide();
                    }

                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();
                }
            }
        }
        txtNomConnexion.setText("");
        txtMotDePasseConnexion.setText("");
    }




}
