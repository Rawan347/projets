module org.example.Ressources_humaines_projet
{
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.sql;
    requires jakarta.activation;


    opens org.example.ressources_humaines_projet to javafx.fxml;
    exports org.example.ressources_humaines_projet;
    exports org.example.ressources_humaines_projet.Controller;
    opens org.example.ressources_humaines_projet.Controller to javafx.fxml;

    opens org.example.ressources_humaines_projet.Model to javafx.fxml, org.hibernate.orm.core;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires java.management;
    requires org.postgresql.jdbc;

    exports org.example.ressources_humaines_projet.Model;
    exports org.example.ressources_humaines_projet.Classe;
    opens org.example.ressources_humaines_projet.Classe to javafx.fxml;


}