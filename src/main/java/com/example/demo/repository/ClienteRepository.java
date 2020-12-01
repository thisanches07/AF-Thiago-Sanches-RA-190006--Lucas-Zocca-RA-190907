package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.demo.model.Cliente;

import org.springframework.stereotype.Component;


@Component
public class ClienteRepository {
    private List<Cliente> clientes;
    private int nextCodigo;

    @PostConstruct
    public void init()
    {
        nextCodigo = 1;
        clientes = new ArrayList<Cliente>();
    }
    public Cliente save(Cliente cliente)
    {
        cliente.setCodigo(nextCodigo);
        cliente.setNome(cliente.getNome());
        cliente.setEndereco(cliente.getEndereco());
        cliente.setCPF(cliente.getCPF());
        clientes.add(cliente);
        nextCodigo++;
        return cliente;
    }
    public List<Cliente> getClientes()
    {
        return clientes;
    }
    public Optional<Cliente> getClienteByCodigo(int codigo)
    {
        for(Cliente cont : clientes)
            if(cont.getCodigo() == codigo)
                return Optional.of(cont);
        return Optional.empty();        
    }
    public void delete(Cliente cliente)
    {
        if(cliente.getTotalReservas(cliente) == 0)
            clientes.remove(cliente);
    }
    public Cliente update(Cliente cliente)
    {
        Cliente aux = getClienteByCodigo(cliente.getCodigo()).get();

        if(aux!=null)
        {
            aux.setEndereco(cliente.getEndereco());
            aux.setNome(cliente.getNome());
        }
        return aux;
    }
    
}
