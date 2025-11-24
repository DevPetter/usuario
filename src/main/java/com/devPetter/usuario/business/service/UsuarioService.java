package com.devPetter.usuario.business.service;


import com.devPetter.usuario.business.converter.UsuarioConverter;
import com.devPetter.usuario.business.dto.UsuarioDTO;
import com.devPetter.usuario.infrastructure.entity.Usuario;
import com.devPetter.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO dto){
        //Converte o DTO em entity
        Usuario usuario = usuarioConverter.paraUsuario(dto);

        //Salva a entity no banco
        usuario = usuarioRepository.save(usuario);

        //Converte a entity em DTO novamente e retorna
        return usuarioConverter.paraUsuarioDTO(usuario);
    }
}
