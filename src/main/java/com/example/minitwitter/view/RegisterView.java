package com.example.minitwitter.view;

import com.example.minitwitter.domain.UserRegistrationDto;
import com.example.minitwitter.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("register")
@PageTitle("Register - MiniTwitter")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView(UserService userService) {
        setAlignItems(Alignment.CENTER);

        TextField usernameField = new TextField("Username");

        TextField emailField = new TextField("Email");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("Password");
        passwordField.setClearButtonVisible(true);
        passwordField.setPrefixComponent(VaadinIcon.LOCK.create());
//        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setHelperText(
                "A password must be at least 8 characters. It has to have at least one letter and one digit.");
        passwordField.setPattern("^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$");
        passwordField.setErrorMessage("Not a valid password");

        DatePicker dobPicker = new DatePicker("Date of Birth");

        Button registerBtn = new Button("Register");

        registerBtn.addClickListener(e -> {
            UserRegistrationDto dto = UserRegistrationDto.builder()
                    .userName(usernameField.getValue())
                    .email(emailField.getValue())
                    .password(passwordField.getValue())
                    .birthDate(dobPicker.getValue())
                    .build();
            userService.registerUser(dto);
            Notification.show("Registered successfully!");
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        add(new H2("Register"), usernameField, emailField, passwordField, dobPicker, registerBtn);

        add(new Anchor("hello", "Go back"));
    }
}
