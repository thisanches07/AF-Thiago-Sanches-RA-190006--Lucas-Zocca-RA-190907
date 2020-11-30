package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.VeiculoDTO;
import com.example.demo.model.Veiculo;
import com.example.demo.repository.VeiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VeiculoService {
       
    @Autowired
    private VeiculoRepository repository;



    public Veiculo fromDTO(VeiculoDTO objDTO){
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(objDTO.getModelo());
        veiculo.setValorDiaria(objDTO.getValorDiaria());
        return veiculo;
    }
    public List<Veiculo> getVeiculos(){
        return repository.getVeiculos();
    }
    public Veiculo getVeiculoByCodigo(int codigo)
    {
        Optional<Veiculo> op = repository.getVeiculoByCodigo(codigo);
        return op.orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Veiculo não cadastrado!"));
    }
    public Veiculo save(Veiculo veiculo)
    {

        return repository.save(veiculo);
        
    }
    public void removerByCodigo(int codigo)
    {
        if(getVeiculoByCodigo(codigo).getReservas().isEmpty() == true)
            repository.delete(getVeiculoByCodigo(codigo));
        else
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossível remover veiculo com reservas cadastradas nele!");    
    }      
    public Veiculo update(Veiculo veiculo)
    {
        getVeiculoByCodigo(veiculo.getCodigo());
        return repository.update(veiculo);
    }

    public VeiculoDTO toDTO(Veiculo veiculo){
        VeiculoDTO dto = new VeiculoDTO();

        dto.setModelo(veiculo.getModelo());
        dto.setReservas(veiculo.getReservas());
        dto.setValorDiaria(veiculo.getValorDiaria());
        dto.setCodigo(veiculo.getCodigo());
        
        return dto;
    }

    public List<VeiculoDTO> toListDTO(List<Veiculo> veiculos){
        ArrayList<VeiculoDTO> listDTO = new ArrayList<VeiculoDTO>();

        for(Veiculo cont: veiculos){
            listDTO.add(toDTO(cont));
        }
        return listDTO;
    }
}
