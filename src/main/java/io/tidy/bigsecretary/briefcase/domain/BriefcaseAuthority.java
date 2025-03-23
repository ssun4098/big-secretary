package io.tidy.bigsecretary.briefcase.domain;

import io.tidy.bigsecretary.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "bs_briefcase_authority ")
@Entity
public class BriefcaseAuthority {

  @Id private Long id;

  @Column(name = "action")
  private Authority authority;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "briefcase_id")
  private Briefcase briefcase;

  @Builder
  public BriefcaseAuthority(User user, Briefcase briefcase) {
    this.user = user;
    this.briefcase = briefcase;
    this.authority = Authority.ALL;
  }

  public boolean canRead() {
    return authority.equals(Authority.ALL) || authority.equals(Authority.READ);
  }

  public boolean canWrite() {
    return authority.equals(Authority.ALL) || authority.equals(Authority.WRITE);
  }

  private enum Authority {
    ALL,
    READ,
    WRITE
  }
}
