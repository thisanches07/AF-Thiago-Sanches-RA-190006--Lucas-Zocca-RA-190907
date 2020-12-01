package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.model.Reserva;
import com.example.demo.service.ReservaService;

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
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    public List<Reserva> getReservas(){
        return service.getReservas();
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Reserva> getReservaByNumero(@PathVariable int numero) {
        Reserva reserva = service.getReservaByNumero(numero);
        return ResponseEntity.ok(reserva);
    }
 
@DeleteMapping("/{numero}")
public ResponseEntity<Void> remover(@PathVariable int numero)
{
    service.removerByNumero(numero);
    return ResponseEntity.noContent().build();
}
    @PutMapping("/{numero}")
    public ResponseEntity<Reserva> atualizar(@RequestBody ReservaDTO reservaDTO,
                                             @PathVariable int numero)
{
    Reserva reserva = service.fromDTO(reservaDTO);
    reserva.setNumero(numero);
    reserva = service.update(reserva);
    return ResponseEntity.ok(reserva);   
}


@PostMapping("/{numero}/reservas")
public ResponseEntity<Void> salvar(@PathVariable int numero, @RequestBody Reserva reserva, HttpServletRequest request, UriComponentsBuilder builder)
{
        UriComponents uriComponents = builder.path(request.getRequestURI() + "/" + reserva.getNumero()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();  
}
}
