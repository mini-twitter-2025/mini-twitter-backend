package ar.edu.unrn.chamorro.minitwitter.web.dto;

import ar.edu.unrn.chamorro.minitwitter.model.Usuario;

public record UsuarioResponse(
    Long id,
    String userName
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(usuario.id(), usuario.userName());
    }
}
