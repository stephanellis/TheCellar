package com.ronbreier.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ronbreier.forms.AddBeerForm;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ron.breier on 4/18/2017.
 */

@Entity
@Table(name="user_beer_links")
public class UserBeerLink implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_beer_link_id", nullable=false)
    @JsonProperty("user_beer_link_id")
    private Long userBeerLinkId;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @Column(name="count", nullable=false)
    private Long count;

    public UserBeerLink(){
        // Zero Arg Constructor to satisfy JPA
    }

    public UserBeerLink(User user, Beer beer, Long count){
        this.user = user;
        this.beer = beer;
        this.count = count;
    }

    public Long getUserBeerLinkId() {
        return userBeerLinkId;
    }

    public void setUserBeerLinkId(Long userBeerLinkId) {
        this.userBeerLinkId = userBeerLinkId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void editBeer(AddBeerForm form){
        this.beer.setName(form.getBeerName());
        this.beer.setAbv(form.getAbv());
        this.beer.setBrewer(form.getBrewer());
        this.beer.setStyle(form.getStyle());
        this.setCount(form.getCount());
    }

    public void mapToExitingBeer(Beer beer){
        this.beer = beer;
    }

    public UserBeerLink editWithForm(AddBeerForm form){
        this.beer.setBrewer(form.getBrewer().toUpperCase());
        this.beer.setName(form.getBeerName().toUpperCase());
        this.beer.setAbv(form.getAbv());
        this.beer.setStyle(form.getStyle().toUpperCase());
        this.beer.setYear(form.getYear());
        this.count = this.count + form.getCount();
        return this;
    }

    @Override
    public String toString() {
        return "UserBeerLink{" +
                "userBeerLinkId=" + userBeerLinkId +
                ", user=" + user +
                ", beer=" + beer +
                ", count=" + count +
                '}';
    }
}
