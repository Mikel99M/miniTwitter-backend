package com.example.minitwitter.view;

import com.example.minitwitter.domain.PostDto;
import com.example.minitwitter.exception.PostNotFoundException;
import com.example.minitwitter.exception.UserNotFoundException;
import com.example.minitwitter.service.LikeService;
import com.example.minitwitter.service.PostService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.Collections;
import java.util.List;


@Route("main")
@PageTitle("MiniTwitter - Home")
@PermitAll
//@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private final PostService postService;
    private final LikeService likeService;

    public MainView(PostService postService, LikeService likeService) {

        this.postService = postService;
        this.likeService = likeService;

        List<PostDto> posts = postService.getAllPosts();
        Collections.reverse(posts);

        posts.forEach(post -> {
            Div postCard = new Div();
            postCard.getStyle().set("border", "1px solid #ccc");
            postCard.getStyle().set("padding", "1em");
            postCard.getStyle().set("margin", "0.5em");
            postCard.setWidth("60%");

            Span dateSpan = new Span(post.getDateOfPublication().toString());
            dateSpan.getStyle().set("font-size", "0.75em").set("color", "gray");

            Button likeButton = new Button("like", event -> {
                try {
                    likeService.toggleLikePost(post.getId() , post.getUser().getId());
                } catch (PostNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (UserNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            postCard.add(new Span(post.getUser().getUsername()));
            postCard.add(new Paragraph(post.getText()));
            postCard.add(dateSpan);
            postCard.add(likeButton);

            add(postCard);
        });

        // Enable scrolling
        getStyle().set("overflow", "auto");
    }

}
