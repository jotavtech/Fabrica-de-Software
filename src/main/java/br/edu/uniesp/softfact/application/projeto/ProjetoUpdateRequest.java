package br.edu.uniesp.softfact.application.projeto;

import br.edu.uniesp.softfact.shared.enums.StatusProjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record ProjetoUpdateRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull StatusProjeto status,
        LocalDate dataInicio,
        LocalDate dataFim,
        String urlRepositorio,
        Set<Long> alunosIds,
        Set<Long> stacksIds
) {}
