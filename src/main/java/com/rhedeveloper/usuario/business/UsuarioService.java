package com.rhedeveloper.usuario.business;

import com.rhedeveloper.usuario.business.converter.UsuarioConverter;
import com.rhedeveloper.usuario.business.dto.UsuarioDTO;
import com.rhedeveloper.usuario.infrastructure.entity.Usuario;
import com.rhedeveloper.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    //Método para salvar o usuário no banco
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){

        //Convertendo dados do usuarioDTO para dados usuarioEntity
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);


        //Salvamos no banco e Retornamos esse usuario convertido para DTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
