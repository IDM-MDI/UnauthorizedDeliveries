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

@Entity
@Table(name = "posting_item_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(name = "item_position", nullable = false)
    private Long itemPosition;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "measurement_unit", nullable = false)
    private String measurementUnit;
}
