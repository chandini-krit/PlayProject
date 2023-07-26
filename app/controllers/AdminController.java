package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin;

public class AdminController extends Controller {

    public static Result adminPage() {
        return ok(admin.render());
    }

    public static Result authenticateAdmin() {
        String email = request().body().asFormUrlEncoded().get("email")[0];
        String password = request().body().asFormUrlEncoded().get("password")[0];

        if (isValidAdminCredentials(email, password)) {
            return redirect(routes.RegistrationController.viewUsers());
        } else {
            flash("error", "!!! Invalid email or password !!!");
            return redirect(routes.AdminController.adminPage());
        }
    }

    private static boolean isValidAdminCredentials(String email, String password) {
        return email.equals("admin333@gmail.com") && password.equals("admin333");
    }
}

