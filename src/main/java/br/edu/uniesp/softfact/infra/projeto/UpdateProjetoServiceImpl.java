package br.edu.uniesp.softfact.infra.projeto;

import br.edu.uniesp.softfact.application.projeto.AlunoResumo;
import br.edu.uniesp.softfact.application.projeto.ProjetoResponse;
import br.edu.uniesp.softfact.domain.projeto.Projeto;
import br.edu.uniesp.softfact.domain.projeto.UpdateProjetoService;
import br.edu.uniesp.softfact.infra.aluno.AlunoEntity;
import br.edu.uniesp.softfact.infra.aluno.AlunoRepository;
import br.edu.uniesp.softfact.zo.old.stack.StackTecRepository;
import br.edu.uniesp.softfact.zo.old.stack.StackTecnologia;
import br.edu.uniesp.softfact.zo.old.stack.dto.StackResumo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateProjetoServiceImpl implements UpdateProjetoService {

    private final ProjetoRepository repo;
    private final AlunoRepository alunoRepo;
    private final StackTecRepository stackRepo;

    @Override
    public ProjetoResponse criar(Projeto domain, Set<Long> alunosIds, Set<Long> stacksIds) {
        ProjetoEntity entity = ProjetoEntity.builder()
                .nome(domain.getNome())
                .descricao(domain.getDescricao())
                .status(domain.getStatus())
                .dataInicio(domain.getDataInicio())
                .dataFim(domain.getDataFim())
                .urlRepositorio(domain.getUrlRepositorio())
                .alunos(buscarAlunos(alunosIds))
                .stacks(buscarStacks(stacksIds))
                .build();

        return toResponse(repo.save(entity));
    }

    @Override
    public ProjetoResponse atualizar(Long id, Projeto domain, Set<Long> alunosIds, Set<Long> stacksIds) {
        ProjetoEntity existente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));

        existente.setNome(domain.getNome());
        existente.setDescricao(domain.getDescricao());
        existente.setStatus(domain.getStatus());
        existente.setDataInicio(domain.getDataInicio());
        existente.setDataFim(domain.getDataFim());
        existente.setUrlRepositorio(domain.getUrlRepositorio());
        existente.setAlunos(buscarAlunos(alunosIds));
        existente.setStacks(buscarStacks(stacksIds));

        return toResponse(existente);
    }

    @Override
    public void excluir(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Projeto não encontrado: " + id);
        }
        repo.deleteById(id);
    }

    private Set<AlunoEntity> buscarAlunos(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        return ids.stream()
                .map(id -> alunoRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado: " + id)))
                .collect(Collectors.toSet());
    }

    private Set<StackTecnologia> buscarStacks(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        return ids.stream()
                .map(id -> stackRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Stack não encontrada: " + id)))
                .collect(Collectors.toSet());
    }

    private ProjetoResponse toResponse(ProjetoEntity p) {
        return new ProjetoResponse(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getStatus(),
                p.getDataInicio(),
                p.getDataFim(),
                p.getUrlRepositorio(),
                p.getAlunos().stream()
                        .map(a -> new AlunoResumo(a.getId(), a.getNome(), a.getEmail()))
                        .collect(Collectors.toSet()),
                p.getStacks().stream()
                        .map(s -> new StackResumo(s.getId(), s.getNome(), s.getCategoria()))
                        .collect(Collectors.toSet())
        );
    }
}
