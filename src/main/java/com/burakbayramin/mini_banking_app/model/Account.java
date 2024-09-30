package com.burakbayramin.mini_banking_app.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    private String number;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    /**
     * The user who owns this account.
     *
     * - @ManyToOne: Defines a many-to-one relationship between Account and User.
     *   This means that many accounts can be associated with a single user.
     *
     * - fetch = FetchType.LAZY: Specifies that the User entity should be loaded lazily.
     *   The User data will only be fetched from the database when it is accessed for the first time.
     *   This helps in optimizing performance by avoiding unnecessary data retrieval.
     *
     * - @JoinColumn(name = "user_id", nullable = false):
     *   - Specifies the foreign key column name in the "accounts" table that references the "users" table.
     *   - nullable = false: Ensures that every account must be associated with a user.
     *     The "user_id" column cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Transactions where this account is the source (fromAccount).
     *
     * - @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY):
     *   - Defines a one-to-many relationship between Account and Transaction.
     *     This means that one account can be associated with multiple outgoing transactions.
     *
     *   - mappedBy = "fromAccount": Indicates that the "fromAccount" field in the Transaction entity owns the relationship.
     *     This prevents the creation of a separate join table and ensures proper bidirectional mapping.
     *
     *   - cascade = CascadeType.ALL:
     *     - Specifies that all persistence operations (persist, merge, remove, refresh, detach) should be cascaded
     *       from the Account entity to the associated outgoing Transactions.
     *     - For example, deleting an account will also delete all its outgoing transactions.
     *
     *   - fetch = FetchType.LAZY:
     *     - Specifies that the outgoingTransactions collection should be loaded lazily.
     *     - The transactions will only be fetched from the database when accessed for the first time.
     *     - This optimizes performance by avoiding unnecessary data retrieval.
     */
    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> outgoingTransactions;

    /**
     * Transactions where this account is the destination (toAccount).
     *
     * - @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY):
     *   - Defines a one-to-many relationship between Account and Transaction.
     *     This means that one account can be associated with multiple incoming transactions.
     *
     *   - mappedBy = "toAccount": Indicates that the "toAccount" field in the Transaction entity owns the relationship.
     *     This prevents the creation of a separate join table and ensures proper bidirectional mapping.
     *
     *   - cascade = CascadeType.ALL:
     *     - Specifies that all persistence operations (persist, merge, remove, refresh, detach) should be cascaded
     *       from the Account entity to the associated incoming Transactions.
     *     - For example, deleting an account will also delete all its incoming transactions.
     *
     *   - fetch = FetchType.LAZY:
     *     - Specifies that the incomingTransactions collection should be loaded lazily.
     *     - The transactions will only be fetched from the database when accessed for the first time.
     *     - This optimizes performance by avoiding unnecessary data retrieval.
     */
    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> incomingTransactions;
}
