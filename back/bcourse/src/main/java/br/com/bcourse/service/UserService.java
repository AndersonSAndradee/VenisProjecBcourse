package br.com.bcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.bcourse.repository.UserRepository;
import br.com.bcourse.handler.BusinessException;
import br.com.bcourse.model.User;
import br.com.bcourse.util.PasswordValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
//Service
@Service
public class UserService {
    //Usando framework de logging para exibir mensagens como Logs
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    //Framework de codificar password
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Garantidad e dados Transactional
    //Cadastro de usuários
    @Transactional
    public User cadastrarUser(User usuario) {
        try {
            //Blocos de validação de criação de usuário
            if (userRepository.existsByEmail(usuario.getEmail())) {
                throw new BusinessException("O email já está em uso.");
            }

            if (!PasswordValidator.isSenhaSegura(usuario.getPassword())) {
                throw new BusinessException("A senha não é segura.");
            }

            String codificacaoSenha = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(codificacaoSenha);
            usuario.setCreated_at(LocalDate.now());

            return userRepository.save(usuario);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao tentar cadastrar usuário: {}", e.getMessage());
            throw new RuntimeException("Erro ao tentar cadastrar usuário.", e);
        }
    }

    //de visualização de usuário por id
    public Optional<User> buscarUsuarioPorID(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            logger.error("Erro ao tentar buscar usuário por id: {}", e.getMessage());
            throw e;
        }
    }
    //Visualizar todos os usuários
    public List<User> listarUsuarios() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Erro ao tentar listar todos os usuários: {}", e.getMessage());
            return null;
        }
    }

    //Deletar usuários
    @Transactional
    public void deletarUsuario(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new BusinessException("O usuário selecionado não existe!");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Erro ao tentar deletar usuário: {}", e.getMessage());
        }
    }

    //Atualizar usuários
    @Transactional
    public User atualizarDadosUser(Long id, User usuario) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<User> userWithSameEmail = userRepository.findByEmail(usuario.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
            throw new RuntimeException("Email já em uso por outro usuário");
        }

        existingUser.setUsername(usuario.getUsername());
        existingUser.setEmail(usuario.getEmail());

        if (!usuario.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return userRepository.save(existingUser);
    }
}
