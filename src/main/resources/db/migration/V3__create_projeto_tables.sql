-- Tabela de projetos e suas associações com alunos e stacks

create table if not exists tb_softfact_projeto (
    id              bigserial primary key,
    nome            varchar(150) not null,
    descricao       text,
    status          varchar(30) not null,
    data_inicio     date,
    data_fim        date,
    url_repositorio varchar(500)
);

create index if not exists idx_projeto_nome   on tb_softfact_projeto (nome);
create index if not exists idx_projeto_status on tb_softfact_projeto (status);

-- Associação Projeto <-> Aluno (ManyToMany)
create table if not exists tb_softfact_projeto_aluno (
    projeto_id bigint not null,
    aluno_id   bigint not null,
    primary key (projeto_id, aluno_id),
    constraint fk_projeto_aluno__projeto
        foreign key (projeto_id) references tb_softfact_projeto(id)
        on delete cascade,
    constraint fk_projeto_aluno__aluno
        foreign key (aluno_id) references tb_softfact_aluno(id)
        on delete cascade
);

-- Associação Projeto <-> Stack (ManyToMany)
create table if not exists tb_softfact_projeto_stack (
    projeto_id bigint not null,
    stack_id   bigint not null,
    primary key (projeto_id, stack_id),
    constraint fk_projeto_stack__projeto
        foreign key (projeto_id) references tb_softfact_projeto(id)
        on delete cascade,
    constraint fk_projeto_stack__stack
        foreign key (stack_id) references tb_softfact_stack(id)
        on delete cascade
);
