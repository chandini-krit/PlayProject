// LoginController.java

package controllers;

import play.mvc.*;
import play.mvc.Http.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import views.html.*;


public class LoginController extends Controller {

    public static Result login() {
        return ok(views.html.login.render());
    }

    public static Result authenticate() throws SQLException,ClassNotFoundException{
        String username = request().body().asFormUrlEncoded().get("username")[0];
        String password = request().body().asFormUrlEncoded().get("password")[0];
        String email = request().body().asFormUrlEncoded().get("email")[0];
        if (isValidCredentials(username, password,email)) {
            return redirect(routes.LoginController.getUserDetails(email));
        }
        flash("error", "Invalid username or password");
        return redirect(routes.LoginController.login());
        }
    public static Result getUserDetails(String emailId) throws SQLException {
//        String emailId = request().body().asFormUrlEncoded().get("email")[0];
        List<User> users = new ArrayList<>();
        DatabaseConnection databaseConnection=null;
        Connection connection=null;
        try {
            databaseConnection=DatabaseConnection.getInstance();
            connection =databaseConnection.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users where email=?");
            stmt.setString(1,emailId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String country = rs.getString("country");
                String skills = rs.getString("skills");
                String interests = rs.getString("interests");
                if (rs.getInt("active")==1) {
                    return ok("User details:\n" + "Name : " + name + "\npassword : " +
                            password + "\nemail : " + emailId + "\ngender : " + gender +
                            "\ncountry : " + country + "\nskills : " + skills +
                            "\ninterests : " + interests);
                }
                else{
                    flash("error", "Accout created but disabled by admin");
                    return redirect(routes.LoginController.login());
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e){
            System.out.println(e);
        }
        finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return ok("Report!!!!!");
    }

//    public static Result userDetails(String emailId) throws SQLException, ClassNotFoundException {
//        DatabaseConnection obj = null;
//        Connection connection = null;
//        try {
//            obj = DatabaseConnection.getInstance();
//            connection = obj.getConnection();
//
//            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users where email=? and password=? and name=?");
//            stmt.setString(1, emailId);
//            ResultSet rs = stmt.executeQuery();
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            String password = rs.getString("password");
//            String gender = rs.getString("gender");
//            String country = rs.getString("country");
//            String skills = rs.getString("skills");
//            String interests = rs.getString("interests");
//            return ok("User details:\n" + "Name" + name + "\npassword" + password + "\nemail" + emailId + "\ngender" + gender +
//                    "\ncountry" + country + "\nskills" + skills + "\ninterests" + interests);
//        } catch (SQLException e) {
//            System.out.println(e);
//        } catch (ClassNotFoundException e) {
//            System.out.println(e);
//        } finally {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//            }
//        }
//        return ok("no details found");
//    }
    private static boolean isValidCredentials(String username, String passWord,String emailAddress) {
        DatabaseConnection databaseConnection=null;
        Connection connection=null;
        boolean row=false;
        try {

            databaseConnection = DatabaseConnection.getInstance();
            connection = databaseConnection.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT EXISTS(SELECT * FROM users where name = ? and email = ? and password = ?);");
            stmt.setString(1,username);
            stmt.setString(2,emailAddress);
            stmt.setString(3,passWord);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                row=rs.getBoolean(1);
//                String email=rs.getString("email");
//                if(email!=null)

                    return true;
            }
        } catch (
                SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            if (connection != null ) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return  false;
    }


}
