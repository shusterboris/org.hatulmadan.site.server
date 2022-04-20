package org.hatulmadan.site.server.application.data.entities.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"USERNAME"})})
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 3428483824158752544L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACCOUNT_EXPIRED", columnDefinition = "boolean default 1")
    private boolean accountExpired = true;

    @Column(name = "ACCOUNT_LOCKED", columnDefinition = "boolean default 1")
    private boolean accountLocked = true;

    @Column(name = "CREDENTIALS_EXPIRED")
    private boolean credentialsExpired = true;

    @Column(name = "ENABLED", columnDefinition = "boolean default 1")
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_AUTHORITIES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
    @OrderBy
    private Collection<Authority> authorities = new ArrayList<Authority>();

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User() {
        // default constructor for Spring
    }

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public void activate() {
        accountExpired = true;
        accountLocked = true;
        credentialsExpired = true;
        enabled = true;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }
    
    @ManyToMany
    @JoinTable(name = "USERGROUPS_USERS",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups = new ArrayList<Group>();

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Long getId(){return id;}
}
