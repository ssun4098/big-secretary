package io.tidy.bigsecretary.user.domain;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @Builder
  public User(String name, String phone, String password) {
    this.name = name;
    this.phone = phone;
    this.password = password;
    this.uuid = UUID.randomUUID().toString();
  }
}
