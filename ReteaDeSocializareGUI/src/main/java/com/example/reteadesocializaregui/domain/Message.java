package com.example.reteadesocializaregui.domain;

import java.time.LocalDateTime;

public class Message extends Entity<Long>{
    private Long idUser1;

    private Long idUser2;

    private LocalDateTime messageFrom;

    private String content;

    public Message(Long idUser1, Long idUser2, String content) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.content = content;
        this.messageFrom = LocalDateTime.now();
    }

    public Long getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Long idUser1) {
        this.idUser1 = idUser1;
    }

    public Long getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Long idUser2) {
        this.idUser2 = idUser2;
    }

    public LocalDateTime getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(LocalDateTime messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String message) {
        this.content = message;
    }
}
