package com.example.minitwitter.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("hello")
@PageTitle("MiniTwitter")
@AnonymousAllowed
public class HelloView extends VerticalLayout {

    public HelloView() {

        setSizeFull();
        setPadding(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        add(new H1("MiniTwitter"));

        add(new Anchor("login", "Already have an account? Login"));
        add(new Anchor("register", "Don't have an account? Register"));

    }
}
