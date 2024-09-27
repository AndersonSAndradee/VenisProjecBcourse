    package br.com.bcourse.model;

    import java.time.LocalDate;
    import java.util.Set;

    import org.springframework.data.annotation.CreatedDate;

    import jakarta.persistence.CascadeType;
    import jakarta.persistence.Column;

    import jakarta.persistence.Entity;
    import jakarta.persistence.FetchType;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.JoinTable;
    import jakarta.persistence.ManyToMany;
    import jakarta.persistence.PrePersist;
    import jakarta.persistence.Table;



    @Entity
    @Table(name = "usuario")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Long id;

        @Column(nullable = false, length = 45, unique = true)
        private String username;

        @Column(unique = true, nullable = false, length = 100)
        private String email;

        @Column(nullable =  false, length = 100)
        private String password;

        @Column(nullable = false, name ="data_nascimento")
        private LocalDate dataNascimento;

        @CreatedDate
        @Column(nullable = false, updatable = false)
        private LocalDate created_at;
        
        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles;

        public User() {
        }

        public User(String username){
            this.username = username;
        }


        public Set<Role> getRoles() {
            return roles;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

        @PrePersist
        public void prePersist() {
            if (created_at == null) {
                created_at = LocalDate.now();
            }
        }

        public Long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public LocalDate getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public LocalDate getCreated_at() {
            return created_at;
        }

        public void setCreated_at(LocalDate created_at) {
            this.created_at = created_at;
        }

    
    }
