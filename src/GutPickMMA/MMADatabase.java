
package GutPickMMA;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class MMADatabase{
    private Connection connect;
    private String username;
    private String password;
    private String url;

    public MMADatabase(String username, String password, String url) {
            try{
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                System.out.println("Drivers loaded successfully");              
                connect = getConnection( username,password,url);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }finally{
                System.out.println("Connection to database establised succesffully.");
            }
    }
    private Connection getConnection(String username, String password, String url){
        try{    
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Successfully establised connection");
            return connection;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }  
    }
    public boolean addFighter(RosterMetrics fighter,String sql){
        try(PreparedStatement ps = connect.prepareStatement(sql)){
            
            ps.setInt(1,fighter.getRanking());
            ps.setString(2,fighter.getFighterName());
            ps.setString(3, fighter.getWeightDivision());
            ps.setString(4, fighter.getFighterRecord());
            ps.setString(5, fighter.getNickName());
            ps.setDouble(6, fighter.getOdds());
            ps.setString(7, fighter.getPlaceOfBirth());
            ps.setString(8, fighter.getDateOfBirth());
            ps.setInt(9, fighter.getAge());
            ps.setString(10,fighter.getHeight());
            ps.setString(11, fighter.getWeight());
            ps.setString(12, fighter.getDebut());
            ps.setString(13, fighter.getWinsViaKO());
            ps.setString(14,fighter.getWinsViaDecision());
            ps.setString(15,fighter.getWinsViaSubmission());
            
            if(ps.executeUpdate() > 1){
                 return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
