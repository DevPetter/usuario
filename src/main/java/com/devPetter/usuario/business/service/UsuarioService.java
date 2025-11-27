package com.devPetter.usuario.business.service;


import com.devPetter.usuario.business.converter.UsuarioConverter;
import com.devPetter.usuario.business.dto.EnderecoDTO;
import com.devPetter.usuario.business.dto.TelefoneDTO;
import com.devPetter.usuario.business.dto.UsuarioDTO;
import com.devPetter.usuario.infrastructure.entity.Endereco;
import com.devPetter.usuario.infrastructure.entity.Telefone;
import com.devPetter.usuario.infrastructure.entity.Usuario;
import com.devPetter.usuario.infrastructure.exceptions.ConflictException;
import com.devPetter.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.devPetter.usuario.infrastructure.repository.EnderecoRepository;
import com.devPetter.usuario.infrastructure.repository.TelefoneRepository;
import com.devPetter.usuario.infrastructure.repository.UsuarioRepository;
import com.devPetter.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository  telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado: " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado, " + e.getCause());
        }
    }

    public UsuarioDTO salvaUsuario(UsuarioDTO dto) {
        emailExiste(dto.getEmail());

        //Ecnripta a senha do usuario
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));

        //Converte o DTO em entity
        Usuario usuario = usuarioConverter.paraUsuario(dto);

        //Salva a entity no banco
        usuario = usuarioRepository.save(usuario);

        //Converte a entity em DTO novamente e retorna
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Usuário com email '" + email + "' não encontrado.")
            ));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Usuário com email '" + email + "' não encontrado.");
        }
    }

    @Transactional
    public void deletarUsuarioPorEmail(String email) {
        boolean existe = verificaEmailExistente(email);

        if (existe) {
            usuarioRepository.deleteByEmail(email);
        } else {
            throw new ResourceNotFoundException("Usuário com email '" + email + "' não encontrado.");
        }
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO dto) {
        //Busca o email do usuario atraves do token
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //Busca o usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado."));

        //Mesclou os dados do DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Salvou os dados do usuario convertido e pegou o retorno e converteu para DTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizarEndereco(Long idEndereco, EnderecoDTO dto){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("ID não encontrado."));

        Endereco endereco = usuarioConverter.updateEndereco(dto, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizarTelefone(Long idTelefone, TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("ID não encontrado."));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
