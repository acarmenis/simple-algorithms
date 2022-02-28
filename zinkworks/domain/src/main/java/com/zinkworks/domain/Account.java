package com.zinkworks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Account entity represents the database model/table
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account implements Serializable {

    private static final long serialVersionUID = -141210178699318L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Formula(
            "round(" +
                    "   (interestRate::numeric / 100) * " +
                    "   cents * " +
                    "   date_part('month', age(now(), createdOn)" +
                    ") " +
                    "/ 12) " +
                    "/ 100::numeric")
    private Long accountNumber;
    @Column
    @NotEmpty
    @Size(min = 4, max = 4)
    private String pin;
    @Column
    private Integer balance;
    @Column
    private Integer overDraft;
    @Column
    private String description;

    /**
     *
     * @param accountNumber the new account entity
     * @param pin the user's pin number
     * @param balance the account balance
     * @param overDraft the overdraft restrict dispense
     */
    public Account(Long accountNumber, String pin, Integer balance, Integer overDraft, String description) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.overDraft = overDraft;
        this.description = description;
    }

    /**
     *
     * @param o account
     * @return true/false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
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
