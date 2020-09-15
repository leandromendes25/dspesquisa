package com.devsuperior.dspesquisa.repositories;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.dspesquisa.entities.Record;
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
	/*O valor depois dos : deve ser o mesmo dos de baixo, ja pega automaticamente*/
	/*Para trazer os valores mesmo com ou sem as especificações do min e max*/
	@Query("SELECT obj from Record obj where"
			+ " (coalesce(:min, null) is null OR obj.moment >= :min) AND "
			+ " (coalesce(:max, null) is null OR obj.moment <= :max)")
	Page<Record> findByMoments(Instant min, Instant max,Pageable pageable);

}
