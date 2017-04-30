package com.ronbreier.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ron Breier on 4/20/2017.
 */

public class AddBeerForm {

    @NotNull
    @NotEmpty
    @Size(min=1, max=50)
    private String brewer;

    @NotNull
    @NotEmpty
    @Size(min=1, max=50)
    private String style;

    @NotNull
    @NotEmpty
    @Size(min=1, max=50)
    private String beerName;

    @Min(0)
    @Max(25)
    private Double abv;

    @Min(0)
    @Max(100000)
    private Long count;

    @NotNull
    @NotEmpty
    @Size(min=4, max=4)
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
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

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    @Override
    public String toString() {
        return "AddBeerForm{" +
                "brewer='" + brewer + '\'' +
                ", style='" + style + '\'' +
                ", beerName='" + beerName + '\'' +
                ", year=" + year +
                ", abv=" + abv +
                '}';
    }
}
