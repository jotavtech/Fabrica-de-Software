package br.edu.uniesp.softfact.infra.projeto;

import br.edu.uniesp.softfact.shared.enums.StatusProjeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Long> {

    boolean existsByNome(String nome);

    Page<ProjetoEntity> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<ProjetoEntity> findByStatus(StatusProjeto status, Pageable pageable);

    @Query("""
           SELECT DISTINCT p FROM ProjetoEntity p
           LEFT JOIN p.alunos a
           LEFT JOIN p.stacks s
           WHERE (:termo IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')))
              OR (:termo IS NULL OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :termo, '%')))
           """)
    Page<ProjetoEntity> buscarPorTermo(@Param("termo") String termo, Pageable pageable);
}
