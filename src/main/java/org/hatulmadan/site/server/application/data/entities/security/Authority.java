package org.hatulmadan.site.server.application.data.entities.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "AUTHORITY", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = -4994405279423981455L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name != null ? name : "";
    }
}
