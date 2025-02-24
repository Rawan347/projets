module org.example.Ressources_humaines
{
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.sql;
    requires jakarta.activation;
    requires twilio;


    opens org.example.Ressources_humaines to javafx.fxml;
    exports org.example.Ressources_humaines;
    exports org.example.Ressources_humaines.controller;
    opens org.example.Ressources_humaines.controller to javafx.fxml;

    opens org.example.Ressources_humaines.Model to javafx.fxml, org.hibernate.orm.core;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires java.management;
    requires org.postgresql.jdbc;

    exports org.example.Ressources_humaines.Model;


}