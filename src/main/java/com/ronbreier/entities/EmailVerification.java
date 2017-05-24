package com.ronbreier.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by Ron Breier on 4/12/2017.
 */

@Entity
@Table(name="email_verification_tokens")
public class EmailVerification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id", nullable=false)
    private Long tokenId;

    @Column(name="user_id", nullable=false, unique=true)
    private Long userId;

    @Column(name="token", nullable=false)
    private String token;

    @Column(name="date_generated")
    private Timestamp dateGenerated;

    @Column(name="is_user_new")
    private Boolean newUser;

    public Boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(Timestamp dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public String getVerificationUrl(){
        return "/verifyemail/" + this.getToken();
    }

    public EmailVerification() {
        // Zero Arg Constructor to satisfy JPA
    }

    public EmailVerification(User user) {
        // the created date and id will be issued by the DB
        this.userId = user.getUserId();
        this.token = generateVerificationUrl(user);
    }

    @Override
    public String toString() {
        return "EmailVerification{" +
                "tokenId=" + tokenId +
                '}';
    }

    public String generateVerificationUrl(User user){
        StringBuilder token = new StringBuilder(100);
        token.append(System.currentTimeMillis() / 1000L);
        token.append(user.getLastName());
        token.append(makeRandomOfLength(100 - token.toString().length()));

        return token.toString();
    }

    private String makeRandomOfLength(int length){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();

    }
}
