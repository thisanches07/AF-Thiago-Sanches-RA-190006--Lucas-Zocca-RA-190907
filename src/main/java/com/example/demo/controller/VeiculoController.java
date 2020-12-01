package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.demo.dto.ReservaDTO;

import com.example.demo.dto.VeiculoDTO;

import com.example.demo.model.Veiculo;
import com.example.demo.service.ReservaService;
import com.example.demo.service.VeiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @Autowired
    private ReservaService serviceReserva;
    @GetMapping
    public List<Veiculo> getVeiculos(){
        return service.getVeiculos();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Veiculo> getVeiculoById(@PathVariable int codigo) {
        Veiculo veiculo = service.getVeiculoByCodigo(codigo);
        return ResponseEntity.ok(veiculo);
    }
    @PostMapping()
    public ResponseEntity<Veiculo> salvar(@Valid @RequestBody VeiculoDTO veiculoDTO,
                                            HttpServletRequest request,
                                            UriComponentsBuilder builder
                                            ) {
    Veiculo veiculo = service.fromDTO(veiculoDTO);
    Veiculo novoVeiculo = service.save(veiculo);
    UriComponents uriComponents = builder.path(request.getRequestURI()+ "/" +novoVeiculo.getCodigo()).build();
    return ResponseEntity.created(uriComponents.toUri()).build(); 
}

@DeleteMapping("/{codigo}")
public ResponseEntity<Void> remover(@PathVariable int codigo)
{
    service.removerByCodigo(codigo);
    return ResponseEntity.noContent().build();
}
    @PutMapping("/{codigo}")
    public ResponseEntity<Veiculo> atualizar(@RequestBody VeiculoDTO veiculoDTO,
                                             @PathVariable int codigo)
{
    Veiculo veiculo = service.fromDTO(veiculoDTO);
    veiculo.setCodigo(codigo);
    veiculo = service.update(veiculo);
    return ResponseEntity.ok(veiculo);   
}

@GetMapping("/{codVeiculo}/reservas")
public List<ReservaDTO> getReservasVeiculo(@PathVariable int codVeiculo) {
    Veiculo veiculo = service.getVeiculoByCodigo(codVeiculo);
    return serviceReserva.toListDTO(veiculo.getReservas());
}
}
    
