package io.tidy.bigsecretary.briefcase.domain;

import io.tidy.bigsecretary.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE BS_BRIEFCASE SET deleted = true WHERE id = ?")
@Table(name = "BS_BRIEFCASE")
@Entity
public class Briefcase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 512)
  private String name;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ColumnDefault("false")
  @Column(name = "deleted")
  private boolean deleted = false;

  @Builder
  private Briefcase(String name, User user) {
    this.name = name;
    this.user = user;
  }

  public void update(String name) {
    this.name = name;
  }

  public void delete() {
    this.deleted = true;
  }
}
