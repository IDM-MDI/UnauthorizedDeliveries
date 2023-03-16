package by.a1.unauthorizeddeliveries.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "application")
    private String application;
    @Column(name = "job")
    private String job;
    @Column(name = "department")
    private String department;
    @Column(name = "active")
    private Boolean active;
}
