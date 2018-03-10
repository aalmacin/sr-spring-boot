package com.raidrin.spacedrepetition.website.domain.study;

import com.raidrin.spacedrepetition.website.domain.study.rating.Rating;
import com.raidrin.spacedrepetition.website.domain.topic.Topic;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    private Long startTime;
    private Long endTime;

    @Lob
    private String comment;

    @OneToOne
    @NonNull
    private Topic topic;
}
