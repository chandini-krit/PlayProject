package controllers;

import play.db.DB;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.registrationForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrationController extends Controller {
    public static Result showForm() {
        return ok(registrationForm.render());
    }
//    public static Result userDetails(String email) throws SQLException, ClassNotFoundException {
//        try {
//            obj = DatabaseConnection.getInstance();
//            connection = obj.getConnection();
//
//            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users where email=?");
//            stmt.setString(1,email);
//            ResultSet rs = stmt.executeQuery();
//            int id = rs.getInt("id");
//            String name = rs.getString("name");
//            String password = rs.getString("password");
//            String gender = rs.getString("gender");
//            String country = rs.getString("country");
//            String skills = rs.getString("skills");
//            String interests = rs.getString("interests");
//            return ok("User details:\n" + "Name"+name+"\npassword"+password+"\nemail"+email+"\ngender"+gender+
//                    "\ncountry"+country+"\nskills"+skills+"\ninterests"+interests);
//        }catch (SQLException e) {
//            System.out.println(e);
//        }
//        catch (ClassNotFoundException e){
//            System.out.println(e);
//        }
//        finally {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//            }
//        }
//        return ok("no details found");
//    }
    public static Result submitForm() throws SQLException,ClassNotFoundException{
        DatabaseConnection databaseConnection=null;
        Connection connection=null;
        String name = request().body().asFormUrlEncoded().get("name")[0];
        String email = request().body().asFormUrlEncoded().get("email")[0];
        String password = request().body().asFormUrlEncoded().get("password")[0];
        String gender = request().body().asFormUrlEncoded().get("gender")[0];
        String[] skills = request().body().asFormUrlEncoded().get("skills");
        String country = request().body().asFormUrlEncoded().get("country")[0];
        String[] interests = request().body().asFormUrlEncoded().get("interests");
        try {
            databaseConnection =DatabaseConnection.getInstance();
            connection =databaseConnection.getConnection();

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (name , password, email, gender, country , skills, interests) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2,password);
            stmt.setString(3, email);
            stmt.setString(4, gender);
            stmt.setString(5, country);
            String skill="";
            if(skills != null){
                for (int i = 0; i < skills.length; i++) {
                    skill+=skills[i]+",";
                }
            }
            stmt.setString(6,skill);
            String interest="";
            if(interests != null){
                for (int i = 0; i < interests.length; i++) {
                    interest+=interests[i]+",";
                }
            }
            stmt.setString(7, interest);
            stmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        finally {
            if (connection != null && !connection.isClosed())
                connection.close();
        }
        return ok("Registration successful!");
    }

    public static Result viewUsers() throws SQLException,ClassNotFoundException{
        List<User> users = new ArrayList<>();
        DatabaseConnection databaseConnection=null;
        Connection connection=null;
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chandini", "kritter", "kritter");
            databaseConnection=DatabaseConnection.getInstance();
            connection =databaseConnection.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
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
                boolean active = rs.getBoolean("active");


                users.add(new User(id, name,password, email, gender, country, skills, interests, active));
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

        return ok(views.html.userList.render(users));
    }



    public static Result enableUser(int userId,boolean active) throws ClassNotFoundException,SQLException{
        DatabaseConnection databaseConnection=null;
        Connection connection=null;
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chandini", "kritter", "kritter");

            databaseConnection = DatabaseConnection.getInstance();
            connection=databaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET active =  ? where id = ?");
            stmt.setBoolean(1,active);

            stmt.setInt(2, userId);
            stmt.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e){
            System.out.println(e);
        }
        finally {
            if (connection != null && !connection.isClosed())
            connection.close();
        }

        return redirect(routes.RegistrationController.viewUsers());
    }
}

