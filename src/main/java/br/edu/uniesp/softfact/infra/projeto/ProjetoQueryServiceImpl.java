package br.edu.uniesp.softfact.infra.projeto;

import br.edu.uniesp.softfact.application.projeto.AlunoResumo;
import br.edu.uniesp.softfact.application.projeto.ProjetoResponse;
import br.edu.uniesp.softfact.domain.projeto.ProjetoQueryService;
import br.edu.uniesp.softfact.zo.old.stack.dto.StackResumo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjetoQueryServiceImpl implements ProjetoQueryService {

    private final ProjetoRepository repo;

    @Transactional(readOnly = true)
    @Override
    public ProjetoResponse buscarPorId(Long id) {
        return repo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProjetoResponse> listar(String termo, Pageable pageable) {
        Page<ProjetoEntity> page;
        if (termo == null || termo.isBlank()) {
            page = repo.findAll(pageable);
        } else {
            page = repo.buscarPorTermo(termo, pageable);
        }
        return page.map(this::toResponse);
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
