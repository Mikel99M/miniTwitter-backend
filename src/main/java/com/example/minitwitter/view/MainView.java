package com.example.minitwitter.view;

import com.example.minitwitter.domain.PostDto;
import com.example.minitwitter.domain.User;
import com.example.minitwitter.exception.PostNotFoundException;
import com.example.minitwitter.exception.UserNotFoundException;
import com.example.minitwitter.repository.UserRepository;
import com.example.minitwitter.service.LikeService;
import com.example.minitwitter.service.PostService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;


import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


@Route("")
@PageTitle("MiniTwitter - Home")
@PermitAll
@Secured("ROLE_USER")
//@CssImportrt("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private final PostService postService;
    private final LikeService likeService;
    private final UserRepository userRepository;

    public MainView(PostService postService, LikeService likeService, UserRepository userRepository) {
        this.postService = postService;
        this.likeService = likeService;
        this.userRepository = userRepository;

        configureLayout();
        add(new H1("MiniTwitter"));
    }

    private void configureLayout() {
        setSizeFull();
        setPadding(true);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        // Enable scrolling
        getStyle().set("overflow", "auto");
    }

    @PostConstruct
    public void init() {
        try {
            User currentUser = getLoggedInUser();
            List<PostDto> posts = postService.getAllPosts();
            Collections.reverse(posts);

            posts.forEach(post -> add(createPostCard(post, currentUser)));

        } catch (UserNotFoundException e) {
            Notification.show("Error: " + e.getMessage());
        }
    }

    private Component createPostCard(PostDto post, User currentUser) {
        Div postCard = new Div();
        postCard.getStyle().set("border", "1px solid #ccc");
        postCard.getStyle().set("padding", "1em");
        postCard.getStyle().set("margin", "0.5em");
        postCard.setWidth("60%");

        Span likeCountSpan = new Span(getLikeCount(post));

        Span dateSpan = new Span(getPublicationDate(post));


        Button likeButton = new Button("like", event -> {
            try {
                likeService.toggleLikePost(post.getId(), getLoggedInUser().getId());
                Notification.show("Liked the post by " + post.getUser().getUsername());
            } catch (PostNotFoundException | UserNotFoundException e) {
                Notification.show("Failed to like the post");
            }
        });

        postCard.add(new Span(post.getUser().getUsername()));
        postCard.add(new Paragraph(post.getText()));
        postCard.add(dateSpan);
        postCard.add(likeButton);
        postCard.add(likeCountSpan);

        return postCard;
    }

    private Component getPublicationDate(PostDto post) {

        LocalDateTime publicationDate = post.getDateOfPublication();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Span dateSpan;
        if (publicationDate.isAfter(LocalDateTime.now().minusHours(24))) {
            dateSpan = new Span(timeFormatter.format(publicationDate));
        } else {
            dateSpan = new Span(dateFormatter.format(publicationDate));
        }

        dateSpan.getStyle().set("font-size", "0.75em").set("color", "gray");
        return dateSpan;
    }

    public User getLoggedInUser() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("No user found with username: " + username));
    }

    private String getLikeCount(PostDto post) {
        try {
            return String.valueOf(likeService.getLikeCountOnPost(post.getId()));
        } catch (Exception e) {
            return "0";
        }
    }

}
