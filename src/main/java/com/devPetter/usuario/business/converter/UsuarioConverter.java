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
    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endreco) {
        return EnderecoDTO.builder()
                .id(endreco.getId())
                .rua(endreco.getRua())
                .numero(endreco.getNumero())
                .cidade(endreco.getCidade())
                .complemento(endreco.getComplemento())
                .cep(endreco.getCep())
                .estado(endreco.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefones){
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO dto, Usuario entity ){
        return Usuario.builder()
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .build();

    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario){
        return Endereco.builder()
                .rua(dto.getRua())
                .cidade(dto.getCidade())
                .cep(dto.getCep())
                .complemento(dto.getComplemento())
                .estado(dto.getEstado())
                .numero(dto.getNumero())
                .usuarioId(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario){
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuarioId(idUsuario)
                .build();
    }
}
