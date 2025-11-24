package com.devPetter.usuario.business.converter;

import com.devPetter.usuario.business.dto.EnderecoDTO;
import com.devPetter.usuario.business.dto.TelefoneDTO;
import com.devPetter.usuario.business.dto.UsuarioDTO;
import com.devPetter.usuario.infrastructure.entity.Endereco;
import com.devPetter.usuario.infrastructure.entity.Telefone;
import com.devPetter.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    //DTO -> ENTITY
    public Usuario paraUsuario(UsuarioDTO dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(paraListaEndereco(dto.getEnderecos()))
                .telefones(paraListaTelefones(dto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> dto) {
            return dto.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> dto){
        return dto.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO dto){
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }


    //ENTITY -> DTO
    public UsuarioDTO paraUsuarioDTO(Usuario dto) {
        return UsuarioDTO.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(paraListaEnderecoDTO(dto.getEnderecos()))
                .telefones(paraListaTelefonesDTO(dto.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> dto) {
        return dto.stream().map(this::paraEndereco).toList();
    }

    public EnderecoDTO paraEndereco(Endereco dto) {
        return EnderecoDTO.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> dto){
        return dto.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone dto){
        return TelefoneDTO.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }
}
