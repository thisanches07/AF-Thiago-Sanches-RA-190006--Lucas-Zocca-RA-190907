package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.demo.model.Veiculo;

import org.springframework.stereotype.Component;

@Component
public class VeiculoRepository {
    private List<Veiculo> veiculos;
    private int nextCodigo;

    @PostConstruct
    public void init()
    {
        nextCodigo = 1;
        veiculos = new ArrayList<Veiculo>();
    }
    public Veiculo save(Veiculo veiculo)
    {
        veiculo.setCodigo(nextCodigo);
        veiculo.setModelo(veiculo.getModelo());
        veiculo.setValorDiaria(veiculo.getValorDiaria());
        veiculos.add(veiculo);
        nextCodigo++;
        return veiculo;
    }
    public List<Veiculo> getVeiculos()
    {
        return veiculos;
    }
    public Optional<Veiculo> getVeiculoByCodigo(int codigo)
    {
        for(Veiculo cont : veiculos)
            if(cont.getCodigo() == codigo)
                return Optional.of(cont);
        return Optional.empty();        
    }
    public void delete(Veiculo veiculo)
    {
        if(veiculo.getTotalReservas(veiculo) == 0)
            veiculos.remove(veiculo);
    }
    public Veiculo update(Veiculo veiculo)
    {
        Veiculo aux = getVeiculoByCodigo(veiculo.getCodigo()).get();

        if(aux!=null)
        {
            aux.setModelo(veiculo.getModelo());
            aux.setValorDiaria(veiculo.getValorDiaria());
        }
        return aux;
    }
}
