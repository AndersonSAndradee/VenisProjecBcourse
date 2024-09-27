    package br.com.bcourse.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;  // Para atualização
    import org.springframework.web.bind.annotation.DeleteMapping; // Para deleção
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import br.com.bcourse.handler.BusinessException;
    import br.com.bcourse.model.User;
    import br.com.bcourse.service.UserService;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/bcourse")
    public class UserController {

        @Autowired
        private UserService userService;

      
        @PostMapping("/cadastrarUsuario")
        public ResponseEntity<?> cadastrarUsuario(@RequestBody User usuario) {
            try {
                User novoUsuario = userService.cadastrarUser(usuario);
                return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
            } catch (BusinessException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar cadastrar usuário.");
            }
        }

        // Endpoint para buscar usuário por id
        @GetMapping("/usuario/{id}")
        public ResponseEntity<Optional<User>> buscarUsuarioPorId(@PathVariable("id") Long idUsuario) {
            Optional<User> usuario  = userService.buscarUsuarioPorID(idUsuario);
            if (usuario.isPresent()) {
                return ResponseEntity.ok().body(usuario);
            }
            return ResponseEntity.notFound().build();
        }

        // Endpoint para listar todos os usuários
        @GetMapping("/usuarios")
        public ResponseEntity<?> listarUsuarios() {
            try {
                List<User> usuarios = userService.listarUsuarios();
                if (usuarios.isEmpty()) {
                    return ResponseEntity.noContent().build(); // Retorna 204 No Content se não houver usuários
                }
                return ResponseEntity.ok().body(usuarios);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar usuários.");
            }
        }

        // Endpoint para atualizar dados do usuário
        @PutMapping("/usuario/{id}")
        public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
            User updatedUser = userService.atualizarDadosUser(id, user);
            return ResponseEntity.ok(updatedUser);
        }
        
        
        // Endpoint para deletar um usuário
        @DeleteMapping("/usuario/{id}")
        public ResponseEntity<?> deletarUsuario(@PathVariable("id") Long id) {
            try {
                userService.deletarUsuario(id);
                return ResponseEntity.noContent().build(); // Retorna 204 No Content após dele  ção bem-sucedida
            } catch (BusinessException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar deletar usuário.");
            }
        }
    }
