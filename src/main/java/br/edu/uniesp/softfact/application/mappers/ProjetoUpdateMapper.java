package br.edu.uniesp.softfact.application.mappers;

import br.edu.uniesp.softfact.application.projeto.ProjetoUpdateRequest;
import br.edu.uniesp.softfact.domain.projeto.Projeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjetoUpdateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "stacks", ignore = true)
    Projeto toDomain(ProjetoUpdateRequest request);
}
