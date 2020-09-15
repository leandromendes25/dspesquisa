package com.devsuperior.dspesquisa.resources;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dspesquisa.dto.RecordDTO;
import com.devsuperior.dspesquisa.dto.RecordInsertDTO;
import com.devsuperior.dspesquisa.services.RecordService;

@RestController
@RequestMapping(value = "/records")
public class RecordResource {
	@Autowired
	private RecordService service;

	@PostMapping
	private ResponseEntity<RecordDTO> insert(@RequestBody RecordInsertDTO dto){
		RecordDTO newDTO = service.insert(dto);
		return ResponseEntity.ok().body(newDTO);
	}

	@GetMapping
	private ResponseEntity<Page<RecordDTO>> findAll(
			/*value = nome_parametro, valor 0 representa padrão,
			 * Depois declaração da variavel que vai ser o parametro  do metodo,
			 *  do qual tem q ser o mesmo a do inicio*/
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			/*Linhas por pagina, 0 = todos, o maximo que puder*/
			@RequestParam(value = "linesPerPage", defaultValue = "0") Integer linesPerPage,
			@RequestParam(value = "min", defaultValue = "") String min,
			@RequestParam(value = "max", defaultValue = "") String max,
			@RequestParam(value = "orderBy", defaultValue = "moment") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC")String direction){
/*Trata os valores que veem vazio, como min e max, Se vier null, para
 *  a variavel, caso contrario converte para Instant o(String min)*/	
/*Instrução Condicional ternaria, de(= Instant.parse(min) para)*/
Instant minDate = ("".equals(min))? null: Instant.parse(min); 
Instant maxDate =("".equals(max))? null: Instant.parse(max);
if (linesPerPage == 0){
	linesPerPage = Integer.MAX_VALUE;
}
 PageRequest pageRequest = PageRequest.of(page,linesPerPage ,Direction.valueOf(direction), orderBy);

		Page<RecordDTO>list = service.findByMoments(minDate,maxDate,pageRequest);
		return ResponseEntity.ok().body(list);
	}
}
