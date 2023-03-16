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
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "document_header")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "posting_number", nullable = false)
    private Long postingNumber;
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User username;
    @Column(name = "authorize_delivery", nullable = false)
    private Boolean authorizeDelivery;
    @Column(name = "contract_date", nullable = false)
    private LocalDate contractDate;
    @Column(name = "posting_date", nullable = false)
    private LocalDate postingDate;
}
