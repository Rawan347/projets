package org.example.Ressources_humaines.util;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Outils
{

    Node node;

    private void loadPage(Event event, String titre, String pageacharger) throws IOException
    {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource(pageacharger));
        Scene scene=new Scene(root);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.setTitle(titre);

        stage.show();

    }
    public static  void load(Event event, String Titre, String url) throws IOException
    {
        new Outils().loadPage(event,Titre,url);
    }

    private void loadAndWaitPage(Event event, String titre, String pageacharger) throws IOException
    {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource(pageacharger));
        Scene scene=new Scene(root);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.setTitle(titre);

        stage.show();

    }
    public static  void loadandwait(Event event,String Titre,String url) throws IOException
    {
        new Outils().loadAndWaitPage(event,Titre,url);
    }
}
