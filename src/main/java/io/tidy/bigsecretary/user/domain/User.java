package io.tidy.bigsecretary.user.domain;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE BS_USER SET deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "BS_USER")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "name", length = 128)
  private String name;

  @Column(name = "phone", length = 32, unique = true)
  private String phone;

  @Column(name = "password", nullable = false)
  private String password;

  @ColumnDefault("false")
  @Column(name = "deleted")
  private boolean deleted = false;

  @ColumnDefault("false")
  @Column(name = "locked")
  private boolean locked = false;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "role", length = 16, nullable = false)
  private ROLE role;

  @Builder
  public User(String name, String phone, String password) {
    this.name = name;
    this.phone = phone;
    this.password = password;
    this.uuid = UUID.randomUUID().toString();
    this.role = ROLE.ROLE_USER;
  }

  public void delete() {
    this.deleted = true;
  }

  public void lock() {
    this.locked = true;
  }

  public void unlock() {
    this.locked = false;
  }
}
