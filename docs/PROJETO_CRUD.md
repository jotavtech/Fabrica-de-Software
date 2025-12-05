# Módulo de Projetos - CRUD

## Visão Geral

Este módulo implementa as operações de CRUD (Create, Read, Update, Delete) para a entidade **Projeto**, seguindo a arquitetura em camadas já estabelecida no sistema SoftFact.

O Projeto representa um trabalho desenvolvido por alunos, podendo estar associado a múltiplos alunos participantes e às tecnologias (stacks) utilizadas.

---

## Estrutura de Arquivos

```
src/main/java/br/edu/uniesp/softfact/
├── application/
│   ├── mappers/
│   │   ├── ProjetoCreateMapper.java
│   │   └── ProjetoUpdateMapper.java
│   └── projeto/
│       ├── AlunoResumo.java
│       ├── ProjetoCreateRequest.java
│       ├── ProjetoResponse.java
│       └── ProjetoUpdateRequest.java
├── boundaries/rest/projeto/
│   ├── ProjetoCommandController.java
│   └── ProjetoQueryController.java
├── domain/projeto/
│   ├── Projeto.java
│   ├── ProjetoQueryService.java
│   └── UpdateProjetoService.java
├── infra/projeto/
│   ├── ProjetoEntity.java
│   ├── ProjetoQueryServiceImpl.java
│   ├── ProjetoRepository.java
│   └── UpdateProjetoServiceImpl.java
└── shared/enums/
    └── StatusProjeto.java
```

---

## Modelo de Dados

### Entidade Projeto

| Campo           | Tipo           | Descrição                              |
|-----------------|----------------|----------------------------------------|
| id              | Long           | Identificador único (auto-incremento)  |
| nome            | String         | Nome do projeto (obrigatório)          |
| descricao       | String (TEXT)  | Descrição detalhada do projeto         |
| status          | StatusProjeto  | Status atual do projeto                |
| dataInicio      | LocalDate      | Data de início do projeto              |
| dataFim         | LocalDate      | Data de conclusão do projeto           |
| urlRepositorio  | String         | URL do repositório (GitHub, GitLab)    |
| alunos          | Set<Aluno>     | Alunos participantes do projeto        |
| stacks          | Set<Stack>     | Tecnologias utilizadas no projeto      |

### Enum StatusProjeto

- `EM_ANDAMENTO` - Projeto em desenvolvimento
- `CONCLUIDO` - Projeto finalizado
- `PAUSADO` - Projeto temporariamente pausado
- `CANCELADO` - Projeto cancelado

### Relacionamentos

- **Projeto ↔ Aluno**: ManyToMany (tabela `tb_softfact_projeto_aluno`)
- **Projeto ↔ Stack**: ManyToMany (tabela `tb_softfact_projeto_stack`)

---

## Endpoints da API

Base URL: `/api/projetos`

### Criar Projeto

```http
POST /api/projetos
Content-Type: application/json
```

**Request Body:**
```json
{
  "nome": "Sistema de Gestão Acadêmica",
  "descricao": "Sistema para gerenciamento de notas e frequência",
  "status": "EM_ANDAMENTO",
  "dataInicio": "2024-03-01",
  "dataFim": null,
  "urlRepositorio": "https://github.com/usuario/projeto",
  "alunosIds": [1, 2, 3],
  "stacksIds": [1, 4, 7]
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nome": "Sistema de Gestão Acadêmica",
  "descricao": "Sistema para gerenciamento de notas e frequência",
  "status": "EM_ANDAMENTO",
  "dataInicio": "2024-03-01",
  "dataFim": null,
  "urlRepositorio": "https://github.com/usuario/projeto",
  "alunos": [
    {"id": 1, "nome": "João Silva", "email": "joao@email.com"},
    {"id": 2, "nome": "Maria Santos", "email": "maria@email.com"}
  ],
  "stacks": [
    {"id": 1, "nome": "Java", "categoria": "Backend"},
    {"id": 4, "nome": "Spring Boot", "categoria": "Framework"}
  ]
}
```

---

### Buscar Projeto por ID

```http
GET /api/projetos/{id}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "Sistema de Gestão Acadêmica",
  "descricao": "Sistema para gerenciamento de notas e frequência",
  "status": "EM_ANDAMENTO",
  "dataInicio": "2024-03-01",
  "dataFim": null,
  "urlRepositorio": "https://github.com/usuario/projeto",
  "alunos": [...],
  "stacks": [...]
}
```

---

### Listar Projetos (Paginado)

```http
GET /api/projetos?query=gestao&page=0&size=10&sort=nome,asc
```

**Parâmetros:**
- `query` (opcional): Termo de busca por nome ou descrição
- `page`: Número da página (começa em 0)
- `size`: Quantidade de itens por página
- `sort`: Campo e direção de ordenação

**Response:** `200 OK`
```json
{
  "content": [...],
  "pageable": {...},
  "totalElements": 15,
  "totalPages": 2,
  "size": 10,
  "number": 0
}
```

---

### Atualizar Projeto

```http
PUT /api/projetos/{id}
Content-Type: application/json
```

**Request Body:**
```json
{
  "nome": "Sistema de Gestão Acadêmica v2",
  "descricao": "Versão atualizada com novos recursos",
  "status": "CONCLUIDO",
  "dataInicio": "2024-03-01",
  "dataFim": "2024-06-15",
  "urlRepositorio": "https://github.com/usuario/projeto",
  "alunosIds": [1, 2],
  "stacksIds": [1, 4, 7, 8]
}
```

**Response:** `200 OK`

---

### Excluir Projeto

```http
DELETE /api/projetos/{id}
```

**Response:** `204 No Content`

---

## Validações

- `nome`: Obrigatório, não pode ser vazio
- `status`: Obrigatório, deve ser um valor válido do enum
- `alunosIds`: IDs devem corresponder a alunos existentes
- `stacksIds`: IDs devem corresponder a stacks existentes

## Tratamento de Erros

| Código | Situação                                    |
|--------|---------------------------------------------|
| 400    | Dados inválidos na requisição               |
| 404    | Projeto, Aluno ou Stack não encontrado      |
| 500    | Erro interno do servidor                    |

---

## Exemplos de Uso com cURL

### Criar projeto
```bash
curl -X POST http://localhost:8080/api/projetos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "App Mobile",
    "descricao": "Aplicativo para controle de tarefas",
    "status": "EM_ANDAMENTO",
    "dataInicio": "2024-01-15",
    "alunosIds": [1],
    "stacksIds": [2, 5]
  }'
```

### Listar projetos
```bash
curl http://localhost:8080/api/projetos?page=0&size=5
```

### Buscar por ID
```bash
curl http://localhost:8080/api/projetos/1
```

### Atualizar projeto
```bash
curl -X PUT http://localhost:8080/api/projetos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "App Mobile v2",
    "descricao": "Versão com melhorias",
    "status": "CONCLUIDO",
    "dataInicio": "2024-01-15",
    "dataFim": "2024-04-20",
    "alunosIds": [1, 3],
    "stacksIds": [2, 5, 6]
  }'
```

### Excluir projeto
```bash
curl -X DELETE http://localhost:8080/api/projetos/1
```

---

## Considerações Técnicas

- Utiliza MapStruct para mapeamento entre DTOs e entidades
- Transações gerenciadas pelo Spring (@Transactional)
- Paginação implementada com Spring Data Pageable
- Validação de entrada com Bean Validation (Jakarta)
- Arquitetura em camadas: Controller → Service → Repository
