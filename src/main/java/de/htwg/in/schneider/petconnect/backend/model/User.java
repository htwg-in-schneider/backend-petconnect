package de.htwg.in.schneider.petconnect.backend.model;
import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String name;

    private String oauthId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Ausschreibung> ausschreibungen;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOauthId() {
        return oauthId;
    }
    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public List<Ausschreibung> getAusschreibungen() {
    return ausschreibungen;
    }
    public void setAusschreibungen(List<Ausschreibung> ausschreibungen) {
    this.ausschreibungen = ausschreibungen;
    }
}