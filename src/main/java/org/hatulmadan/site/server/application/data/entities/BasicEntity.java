package org.hatulmadan.site.server.application.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Version
    private int version = 1;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;
    private Long sortOrder = 0L;
}
