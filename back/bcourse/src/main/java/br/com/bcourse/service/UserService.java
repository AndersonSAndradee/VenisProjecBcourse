package br.com.bcourse.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.bcourse.repository.UserRepository;
import br.com.bcourse.handler.BusinessException;
import br.com.bcourse.model.User;

import java.time.LocalDate;

import java.util.Optional;
import java.util.List;

import br.com.bcourse.util.PasswordValidator;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User cadastrarUser(User usuario) {
        try {
            // Verificação de email duplicado
            if (userRepository.existsByEmail(usuario.getEmail())) {
                throw new BusinessException("O email já está em uso.");
            }
            
            // Validação da senha
            if (!PasswordValidator.isSenhaSegura(usuario.getPassword())) {
                throw new BusinessException("A senha não é segura.");
            }
            //Codificando a senha
            String codificacaoSenha =  passwordEncoder.encode(usuario.getPassword());

            usuario.setPassword(codificacaoSenha);
            
            // Definindo a data atual para created_at
            usuario.setCreated_at(LocalDate.now());
    
            // Salvando o usuário
            return userRepository.save(usuario);
        } catch (BusinessException e) {
            // Lançar exceção de negócio
            throw e;
        } catch (Exception e) {
            // Log de erro e lançamento de exceção
            System.out.println("Erro ao tentar cadastrar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao tentar cadastrar usuário.", e);
        }
    }

    //Método para buscar usuário por id;
    public Optional<User> buscarUsuarioPorID(Long id){
        try{          
            return userRepository.findById(id);
        } catch (Exception e) {
            System.out.println("Erro ao tentar buscar usuário por id" + e.getMessage());
            throw e;
        }
    }

    // Método para listar todos os usuários
    public List<User> listarUsuarios() {
        try {
            return userRepository.findAll(); 
        } catch (Exception e) {
            System.out.println("Erro ao tentar listar todos os usuários " + e.getMessage());
            return null;
        }
    }

    // Método para deletar um usuário
    public void deletarUsuario(Long id) {
        try {
            if (!userRepository.existsById(id)){
                throw new BusinessException("O usuário selecionado não existe!");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Erro ao tentar deletar usuário " + e.getMessage());
        }
    }

    // Método para atualizar os dados de um usuário
    public User atualizarDadosUser(Long id,User usuario) {
         // Verificar se o usuário existe
         User existingUser = userRepository.findById(id)
         .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar se o email já está em uso, exceto para o próprio usuário
        Optional<User> userWithSameEmail = userRepository.findByEmail(usuario.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
         throw new RuntimeException("Email já em uso por outro usuário");
        }

        // Atualizar os dados do usuário
        existingUser.setUsername(usuario.getUsername());
        existingUser.setEmail(usuario.getEmail());
        existingUser.setPassword(usuario.getPassword());

        return userRepository.save(existingUser);
        
    }

}
