package Foodchoose.lcc.foodpick.lcc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "vote_records")
@NoArgsConstructor
@AllArgsConstructor
public class VoteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_option_id")
    private VoteOption voteOption;

    @Column(name = "voter_name", nullable = false)
    private String voterName;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
