# Fabrica-de-Software

Sistema de gestão para a Fábrica de Software da UNIESP.

## Tecnologias

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- Flyway (migrations)
- MapStruct
- Lombok

## Módulos

- **Aluno** - Gerenciamento de alunos participantes
- **Projeto** - CRUD de projetos com relacionamentos
- **Stack** - Tecnologias utilizadas nos projetos

## Como executar

```bash
./mvnw spring-boot:run
```

## API Endpoints

### Alunos
- `GET /api/alunos` - Listar alunos
- `GET /api/alunos/{id}` - Buscar aluno
- `POST /api/alunos` - Criar aluno
- `PUT /api/alunos/{id}` - Atualizar aluno
- `DELETE /api/alunos/{id}` - Excluir aluno

### Projetos
- `GET /api/projetos` - Listar projetos
- `GET /api/projetos/{id}` - Buscar projeto
- `POST /api/projetos` - Criar projeto
- `PUT /api/projetos/{id}` - Atualizar projeto
- `DELETE /api/projetos/{id}` - Excluir projeto
