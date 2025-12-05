package br.edu.uniesp.softfact.application.projeto;

import br.edu.uniesp.softfact.shared.enums.StatusProjeto;
import br.edu.uniesp.softfact.zo.old.stack.dto.StackResumo;

import java.time.LocalDate;
import java.util.Set;

public record ProjetoResponse(
        Long id,
        String nome,
        String descricao,
        StatusProjeto status,
        LocalDate dataInicio,
        LocalDate dataFim,
        String urlRepositorio,
        Set<AlunoResumo> alunos,
        Set<StackResumo> stacks
) {}
