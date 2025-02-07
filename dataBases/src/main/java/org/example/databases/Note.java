package org.example.databases;

import java.time.LocalDateTime;

public class Note {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime created_at;
    private Long user_id;

    public Note(Long id, String title, String text, LocalDateTime created_at, Long user_id) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.created_at = created_at;
        this.user_id = user_id;
    }

    public Note(String title, String text, LocalDateTime created_at, Long user_id) {
        this.title = title;
        this.text = text;
        this.created_at = created_at;
        this.user_id = user_id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
