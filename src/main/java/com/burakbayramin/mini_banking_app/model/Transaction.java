package com.burakbayramin.mini_banking_app.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The account from which money is being sent.
     *
     * - @ManyToOne: Defines a many-to-one relationship between Transaction and Account.
     *   This means that many transactions can originate from a single account.
     *
     * - fetch = FetchType.LAZY: Specifies that the fromAccount entity should be loaded lazily.
     *   The Account data will only be fetched from the database when it is accessed for the first time.
     *   This helps in optimizing performance by avoiding unnecessary data retrieval.
     *
     * - @JoinColumn(name = "from_account_id", nullable = false):
     *   - Specifies the foreign key column name in the "transactions" table that references the "accounts" table.
     *   - nullable = false: Ensures that every transaction must have a source account.
     *     The "from_account_id" column cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    /**
     * The account to which money is being sent.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
}
