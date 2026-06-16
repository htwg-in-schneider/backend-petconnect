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

    private String firstName;
    private String lastName;
    private String address;

    private String oauthId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Ausschreibung> ausschreibungen;

    @OneToMany(mappedBy = "meldenderUser")
    @JsonIgnore
    private List<Meldung> erstellteMeldungen;

    @OneToMany(mappedBy = "gemeldeterUser")
    @JsonIgnore
    private List<Meldung> empfangeneMeldungen;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "requester")
@JsonIgnore
private List<Betreuungsanfrage> betreuungsanfragen;

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
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public List<Meldung> getErstellteMeldungen() {
        return erstellteMeldungen;
    }
    public void setErstellteMeldungen(List<Meldung> erstellteMeldungen) {
        this.erstellteMeldungen = erstellteMeldungen;
    }
    public List<Meldung> getEmpfangeneMeldungen() {
        return empfangeneMeldungen;
    }
    public void setEmpfangeneMeldungen(List<Meldung> empfangeneMeldungen) {
        this.empfangeneMeldungen = empfangeneMeldungen;
    }
    public List<Message> getSentMessages() {
        return sentMessages;
    }
    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }
    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
}