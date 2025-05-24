package com.rhedeveloper.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * Classe tabela de Usuário
 * @author  Renan H.
 * @version 1.0
 * @since 08/05/2025
 */
@Getter //Métodos Getters
@Setter //Métodos Setters
@AllArgsConstructor //Passa todos os argumentos no Construtor
@RequiredArgsConstructor
@ToString //Transforma Objeto em String
//@NoArgsConstructor //Sem argumentos no construtor
@Entity //Aponta para o Spring que essa classe é uma tabela para o banco de dados
@Table(name = "usuario") //Nome da Tabela, se não colocar por default vai ser o nome da classe
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Id gerado automaticamente gerado pelo código
    private Long id;

    //@Column essa anotação defini nomes das colunas
    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "email", length = 100)
    private String email;

    //Quando definir a senha incriptografada deixei default sem quantidade caracter
    @Column(name = "senha")
    private String senha;

    //Relacionamento entre tabelas onde 1 usuário pode ter vários endereços
    @OneToMany(cascade = CascadeType.ALL) //Faz o Relacionamento entres as entidades em cascata
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> enderecos;

    //Relacionamento entre a tabela usuario e telefone
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }


}
