package com.example.minitwitter.view;

import com.example.minitwitter.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login - MiniTwitter")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView(UserService userService) {
        setAlignItems(Alignment.CENTER);

        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Password");
        Button loginBtn = new Button("Login");
        loginBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button registerBtn = new Button("Sign up");

        loginBtn.addClickListener(e -> {
            // Optional: validate credentials
            Notification.show("Logged in!");
            getUI().ifPresent(ui -> ui.navigate("main"));
        });

        registerBtn.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("register"));
        });


        add(new H2("Login"), emailField, passwordField, loginBtn, registerBtn);

        add(new Anchor("hello", "Go back"));
    }
}