package ar.edu.unrn.chamorro.minitwitter.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ar.edu.unrn.chamorro.minitwitter.model.Usuario;
import ar.edu.unrn.chamorro.minitwitter.service.UsuarioService;
import ar.edu.unrn.chamorro.minitwitter.web.dto.CrearUsuarioRequest;
import ar.edu.unrn.chamorro.minitwitter.web.dto.UsuarioResponse;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@RequestBody CrearUsuarioRequest request) {
        // Para simplificar devolvemos el dominio. Si querés, podés mapear a DTO también.
        return usuarioService.crearUsuario(request.userName());
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listarUsuarios().stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}
