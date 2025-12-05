package br.edu.uniesp.softfact.boundaries.rest.projeto;

import br.edu.uniesp.softfact.application.mappers.ProjetoCreateMapper;
import br.edu.uniesp.softfact.application.mappers.ProjetoUpdateMapper;
import br.edu.uniesp.softfact.application.projeto.ProjetoCreateRequest;
import br.edu.uniesp.softfact.application.projeto.ProjetoResponse;
import br.edu.uniesp.softfact.application.projeto.ProjetoUpdateRequest;
import br.edu.uniesp.softfact.domain.projeto.UpdateProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projetos")
@RequiredArgsConstructor
public class ProjetoCommandController {

    private final UpdateProjetoService service;
    private final ProjetoCreateMapper createMapper;
    private final ProjetoUpdateMapper updateMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetoResponse criar(@RequestBody @Valid ProjetoCreateRequest request) {
        var dominio = createMapper.toDomain(request);
        return service.criar(dominio, request.alunosIds(), request.stacksIds());
    }

    @PutMapping("/{id}")
    public ProjetoResponse atualizar(@PathVariable Long id, @RequestBody @Valid ProjetoUpdateRequest request) {
        var dominio = updateMapper.toDomain(request);
        return service.atualizar(id, dominio, request.alunosIds(), request.stacksIds());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
