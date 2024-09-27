package br.com.bcourse.config;

import br.com.bcourse.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User usuario;

    // Construtor para passar o modelo Usuario
    public CustomUserDetails(User usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte os roles do modelo Usuario em GrantedAuthority
        return usuario.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Customizar se o modelo tiver esse campo
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customizar se o modelo tiver esse campo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customizar se o modelo tiver esse campo
    }

    @Override
    public boolean isEnabled() {
        return true; // Customizar se o modelo tiver esse campo
    }
}
