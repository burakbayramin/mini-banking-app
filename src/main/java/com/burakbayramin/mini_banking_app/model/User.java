package com.burakbayramin.mini_banking_app.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    /**
     * Accounts owned by the user.
     *
     * - @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true):
     *   - Defines a one-to-many relationship between User and Account.
     *     This means that one user can own multiple accounts.
     *
     *   - mappedBy = "user":
     *     - Indicates that the "user" field in the Account entity owns the relationship.
     *     - Prevents the creation of an additional join table by specifying that the relationship is managed on the Account side.
     *
     *   - cascade = CascadeType.ALL:
     *     - Specifies that all persistence operations (persist, merge, remove, refresh, detach) should be cascaded from the User entity to the associated Accounts.
     *     - For example, deleting a user will also delete all their associated accounts.
     *
     *   - fetch = FetchType.LAZY:
     *     - Specifies that the accounts collection should be loaded lazily.
     *     - The accounts will only be fetched from the database when accessed for the first time.
     *     - Optimizes performance by avoiding unnecessary data retrieval.
     *
     *   - orphanRemoval = true:
     *     - Ensures that any Account entities removed from the accounts set are also removed from the database.
     *     - Prevents orphaned accounts that are no longer associated with a user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Account> accounts;
}
