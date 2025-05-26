package com.rhedeveloper.usuario.business;

import com.rhedeveloper.usuario.business.converter.UsuarioConverter;
import com.rhedeveloper.usuario.business.dto.UsuarioDTO;
import com.rhedeveloper.usuario.infrastructure.entity.Usuario;
import com.rhedeveloper.usuario.infrastructure.exceptions.ConflictException;
import com.rhedeveloper.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.rhedeveloper.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    //Método para salvar o usuário no banco
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){


        emailExiste(usuarioDTO.getEmail());

        //Criptografando a senha
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        //Convertendo dados do usuarioDTO para dados usuarioEntity
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);


        //Salvamos no banco e Retornamos esse usuario convertido para DTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


    //Tratamento de erro
    public void emailExiste(String email){

        try{
            boolean existe = verificaEmailExistente(email);

            if (existe) {
                throw new ConflictException("Email já cadastrado!" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado!", e.getCause());
        }
    }

    //retorna um boolean se o email já existe ou não no banco de dados
   public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
   }

   //Buscando usuário por email
    public Usuario buscaUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Email não encontrado" + email));
    }

    //Excluir usuário
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
