package com.rhedeveloper.usuario.infrastructure.repository;

import com.rhedeveloper.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Verificando se email existe no BD
     boolean existsByEmail(String email);

     //Optional evita o retorno de informações nulas
     Optional<Usuario> findByEmail(String email);

     @Transactional
     void deleteByEmail(String email);
}
