package com.zinkworks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Atm entity represents the database model/table
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "ATM")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Atm implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer atmAmount;
    @Column
    private Integer fifty;
    @Column
    private Integer twenty;
    @Column
    private Integer ten;
    @Column
    private Integer five;
    @Column
    private String description;

    /**
     *
     * @param atmAmount the new Atm entity
     * @param fifty number of fifties banknotes
     * @param twenty number of twenties banknotes
     * @param ten number of tens banknotes
     * @param five number of fives banknotes
     */
    public Atm(Integer atmAmount, Integer fifty, Integer twenty, Integer ten, Integer five) {
        this.atmAmount = atmAmount;
        this.fifty = fifty;
        this.twenty = twenty;
        this.ten = ten;
        this.five = five;
    }

    public Atm(Integer atmAmount, Integer fifty, Integer twenty, Integer ten, Integer five, String description) {
        this.atmAmount = atmAmount;
        this.fifty = fifty;
        this.twenty = twenty;
        this.ten = ten;
        this.five = five;
        this.description = description;
    }

    /**
     *
     * @param o Atm object
     * @return  true/false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Atm atm = (Atm) o;
        return id != null && Objects.equals(id, atm.id);
    }

    /**
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
