package by.a1.unauthorizeddeliveries.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "postings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "username")
    private User username;
    @Column(name = "item_position")
    private long itemPosition;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "contract_date")
    private LocalDate contractDate;
    @Column(name = "posting_date")
    private LocalDate postingDate;
}
