package br.edu.uniesp.softfact.infra.projeto;

import br.edu.uniesp.softfact.infra.aluno.AlunoEntity;
import br.edu.uniesp.softfact.shared.enums.StatusProjeto;
import br.edu.uniesp.softfact.zo.old.stack.StackTecnologia;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "tb_softfact_projeto")
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProjetoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProjeto status;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "url_repositorio")
    private String urlRepositorio;

    @ManyToMany
    @JoinTable(name = "tb_softfact_projeto_aluno",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id"))
    private Set<AlunoEntity> alunos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_softfact_projeto_stack",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "stack_id"))
    private Set<StackTecnologia> stacks = new HashSet<>();
}
