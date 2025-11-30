package ar.edu.unrn.chamorro.minitwitter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unrn.chamorro.minitwitter.model.Usuario;
import ar.edu.unrn.chamorro.minitwitter.repository.UsuarioRepository;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    static final String ERROR_USERNAME_DUPLICADO = "Ya existe un usuario con ese userName";
    static final String ERROR_USUARIO_NO_ENCONTRADO = "Usuario no encontrado";

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(String userName) {
        if (usuarioRepository.existsByUserName(userName.trim())) {
            throw new RuntimeException(ERROR_USERNAME_DUPLICADO);
        }
        var usuario = new Usuario(userName);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuario(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ERROR_USUARIO_NO_ENCONTRADO));
        usuarioRepository.delete(usuario); // cascada borra tweets
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ERROR_USUARIO_NO_ENCONTRADO));
    }
}
