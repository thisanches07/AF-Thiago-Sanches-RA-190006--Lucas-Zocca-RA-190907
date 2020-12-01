package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {
       
    @Autowired
    private ClienteRepository repository;


    public Cliente fromDTO(ClienteDTO objDTO){
        Cliente cliente = new Cliente();
        cliente.setNome(objDTO.getNome());
        cliente.setEndereco(objDTO.getEndereco());
        cliente.setCPF(objDTO.getCPF());
        return cliente;
    }
    public List<Cliente> getClientes(){
        return repository.getClientes();
    }
    public Cliente getClienteByCodigo(int codigo)
    {
        Optional<Cliente> op = repository.getClienteByCodigo(codigo);
        return op.orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não cadastrado!"));
    }
    public Cliente save(Cliente cliente)
    {
        return repository.save(cliente);
    }
    public void removerByCodigo(int codigo)
    {
        if(getClienteByCodigo(codigo).getReservas().isEmpty() == true)
            repository.delete(getClienteByCodigo(codigo));
            else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossível remover cliente com reservas cadastradas nele!");        
    }      
    public Cliente update(Cliente cliente)
    {
        getClienteByCodigo(cliente.getCodigo());
        return repository.update(cliente);
    }
}

