package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.ReservaDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.Reserva;
import com.example.demo.model.Veiculo;
import com.example.demo.service.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private ReservaService serviceReserva;
    
    @Autowired
    private VeiculoService serviceVeiculo;



    @GetMapping()
    public List<Cliente> getClientes(){
        return service.getClientes();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cliente> getClienteByCodigo(@PathVariable int codigo) {
        Cliente cliente = service.getClienteByCodigo(codigo);
        return ResponseEntity.ok(cliente);
    }
    @PostMapping()
    public ResponseEntity<Cliente> salvar(@Valid @RequestBody ClienteDTO clienteDTO,
                                            HttpServletRequest request,
                                            UriComponentsBuilder builder
                                            ) {
    Cliente cliente = service.fromDTO(clienteDTO);
    Cliente novoCliente = service.save(cliente);
    UriComponents uriComponents = builder.path(request.getRequestURI()+ "/" +novoCliente.getCodigo()).build();
    return ResponseEntity.created(uriComponents.toUri()).build(); 
}
@DeleteMapping("/{codigo}")
public ResponseEntity<Void> remover(@PathVariable int codigo)
{
    service.removerByCodigo(codigo);
    return ResponseEntity.noContent().build();
}
    @PutMapping("/{codigo}")
    public ResponseEntity<Cliente> atualizar(@RequestBody ClienteDTO veiculoDTO,
                                             @PathVariable int codigo)
{
    Cliente cliente = service.fromDTO(veiculoDTO);
    cliente.setCodigo(codigo);
    cliente = service.update(cliente);
    return ResponseEntity.ok(cliente);   
}

@PostMapping("/{codCliente}/veiculos/{codVeiculo}/reservas")
public ResponseEntity<Reserva> salvar(@PathVariable int codCliente,@PathVariable int codVeiculo, @RequestBody ReservaDTO reservaDTO,
        HttpServletRequest request, UriComponentsBuilder builder

) {

    Reserva reserva = serviceReserva.fromDTO(reservaDTO);
    Reserva novaReserva = serviceReserva.save(reserva,codVeiculo,codCliente);
    UriComponents uriComponents = builder.path(request.getRequestURI() + "/" + novaReserva.getNumero()).build();
    return ResponseEntity.created(uriComponents.toUri()).build();
}
@GetMapping("/{codCliente}/veiculos/{codVeiculo}/reservas")
public List<ReservaDTO> getVeiculosCliente(@PathVariable int codCliente, @PathVariable int codVeiculo) {
    Veiculo veiculo = serviceVeiculo.getVeiculoByCodigo(codVeiculo);
    return serviceReserva.toListDTO(veiculo.getReservas());
}

@GetMapping("/{codCliente}/reservas")
public List<ReservaDTO> getReservasVeiculo(@PathVariable int codCliente) {
    Cliente cliente = service.getClienteByCodigo(codCliente);
    return serviceReserva.toListDTO(cliente.getReservas());
}
}
    
