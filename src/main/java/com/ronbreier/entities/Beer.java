package com.ronbreier.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ronbreier.forms.AddBeerForm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ron Breier on 4/13/2017.
 */

@Entity
@Table(name="beers")
public class Beer implements Serializable, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "beer_id", nullable=false)
    @JsonProperty("beerId")
    private Long beerId;

    @Column(name = "beer_api_ref")
    @JsonProperty("beerApiRef")
    private String beerApiRef;

    @Column(name = "abv")
    @JsonProperty("abv")
    private Double abv;

    @Column(name = "beer_name", nullable=false)
    @JsonProperty("name")
    private String name;

    @Column(name = "brewer", nullable=false)
    @JsonProperty("brewer")
    private String brewer;

    @Column(name = "style")
    @JsonProperty("style")
    private String style;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "glassware")
    @JsonProperty("glassware")
    private String glassware;

    @Column(name = "beer_year")
    @JsonProperty("year")
    private String year;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "beer")
    private List<UserBeerLink> userBeerLinks = new ArrayList<>();

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<UserBeerLink> getUserBeerLinks() {
        return userBeerLinks;
    }

    public void setUserBeerLinks(List<UserBeerLink> userBeerLinks) {
        this.userBeerLinks = userBeerLinks;
    }

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(Long beerId) {
        this.beerId = beerId;
    }

    public String getBeerApiRef() {
        return beerApiRef;
    }

    public void setBeerApiRef(String beerApiRef) {
        this.beerApiRef = beerApiRef;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGlassware() {
        return glassware;
    }

    public void setGlassware(String glassware) {
        this.glassware = glassware;
    }

    public Beer(){
        // Zero Arg Constructor to satisfy JPA
    }

    public Beer(AddBeerForm form){
        this.brewer = form.getBrewer();
        this.name = form.getBeerName();
        this.abv = form.getAbv();
        this.style = form.getStyle();
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((Beer)o).getName());
    }

    @Override
    public String toString() {
        return "Beer{" +
                "beerId=" + beerId +
                ", beerApiRef='" + beerApiRef + '\'' +
                ", abv=" + abv +
                ", name='" + name + '\'' +
                ", brewer='" + brewer + '\'' +
                ", style='" + style + '\'' +
                ", description='" + description + '\'' +
                ", glassware='" + glassware + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
