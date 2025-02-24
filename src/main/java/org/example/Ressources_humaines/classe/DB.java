package org.example.Ressources_humaines.classe;
import java.sql.*;
public class DB
{

    private static DB instance;

    private PreparedStatement pstm;

    private int ok;

    private ResultSet rs;

    public Connection conn = null;

    public PreparedStatement getPstm()
    {
        return pstm;
    }

    public void setPstm(PreparedStatement pstm)
    {
        this.pstm = pstm;
    }

    private DB()
    {
        try
        {
            String url = ("jdbc:postgresql://localhost:5432/gestion_ressources_humaines");
            String user = "postgres";
            String password = "root";

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null)
            {
                System.out.println("Connexion réussie");
            }

            else
            {
                System.out.println("Échec de la connexion");
            }
        }

        catch (Exception e)
        {
            System.out.println("Cause de l'erreur: " + e);
        }
    }

    public static DB getInstance()
    {
        if (instance == null)
        {
            instance = new DB();
        }
        return instance;
    }


    public void initPrepare(String sql) throws SQLException, ClassNotFoundException
    {
        try
        {
            pstm = conn.prepareStatement(sql);
        }

        catch (Exception e)
        {
            System.out.println("Cause de l'erreur" + e);
        }
    }

    public ResultSet ExecuteSelect() throws SQLException, ClassNotFoundException
    {
        rs = null;
        try
        {
            rs = pstm.executeQuery();
        }

        catch (Exception e)
        {
            System.out.println("Cause de l'erreur" + e);
        }
        return rs;
    }

    public int executeMaj()
    {
        try
        {
            ok = pstm.executeUpdate();
        }

        catch (Exception e)
        {
            System.out.println("Cause de l'erreur: " + e);
        }

        return ok;
    }

    public void closeConnection() throws SQLException
    {
        try
        {
            if (conn != null)
            {
                conn.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("Cause de l'erreur: " + e);
        }

    }

}
