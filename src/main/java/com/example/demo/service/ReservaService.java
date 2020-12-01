package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.Reserva;
import com.example.demo.model.Veiculo;
import com.example.demo.repository.ReservaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private VeiculoService serviceVeiculo;

    @Autowired
    private ClienteService serviceCliente;
    public Reserva fromDTO(ReservaDTO objDTO){
        Reserva reserva = new Reserva();
        reserva.setInicio(objDTO.getInicio());
        reserva.setFim(objDTO.getFim());
        return reserva;
    }
    public List<Reserva> getReservas(){
        return repository.getReservas();
    }
    public Reserva getReservaByNumero(int numero)
    {
        Optional<Reserva> op = repository.getReservaByNumero(numero);
        return op.orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não cadastrada!"));
    }
    public Reserva save(Reserva reserva, int codVeiculo, int codCliente)
    {
        Veiculo veiculo = serviceVeiculo.getVeiculoByCodigo(codVeiculo);
        Cliente cliente = serviceCliente.getClienteByCodigo(codCliente);
        reserva.setCliente(cliente);
        reserva.setVeiculo(veiculo);
        System.out.println(reserva);
        if(reserva.getInicio().getDayOfWeek().equals(DayOfWeek.SUNDAY))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossível alugar um veiculo começando no Domingo");


        else if(reserva.getInicio().isBefore(LocalDate.now()))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossivel alugar um veiculo em uma data passada");

        else if(reserva.getFim().getDayOfWeek().equals(DayOfWeek.SUNDAY))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossível devolver um veiculo no Domingo");

        else if(reserva.getFim().isBefore(reserva.getInicio()))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data de fim menor que a data de inicio!");
        
        else 
        {
            for(Reserva r : reserva.getVeiculo().getReservas())
            {
                if(reserva.getInicio().isAfter(r.getInicio()) && reserva.getInicio().isBefore( r.getFim()))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Carro já alugado neste período");
                }
                if(reserva.getFim().isAfter(r.getInicio()) && reserva.getFim().isBefore(r.getFim()))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Carro já alugado neste período");
                }
                if(reserva.getInicio().isEqual(r.getInicio()) || reserva.getFim().isEqual(r.getFim()))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Carro já alugado neste período");
                }
            }
            veiculo.addReserva(reserva);
            cliente.addReserva(reserva);
            return repository.save(reserva);
        }
        
        
    }
    public void removerByNumero(int numero)
    {
            repository.delete(getReservaByNumero(numero));
    }      
    public Reserva update(Reserva reserva)
    {
        getReservaByNumero(reserva.getNumero());
        return repository.update(reserva);
    }

    public ReservaDTO toDTO(Reserva reserva){
        ReservaDTO dto = new ReservaDTO();

        dto.setInicio(reserva.getInicio());
        dto.setFim(reserva.getFim());
        dto.setNumero(reserva.getNumero());
        dto.setTotalReserva(reserva.getTotalReserva());
        return dto;
    }

    public List<ReservaDTO> toListDTO(List<Reserva> reservas){
        ArrayList<ReservaDTO> listDTO = new ArrayList<ReservaDTO>();

        for(Reserva cont: reservas){
            listDTO.add(toDTO(cont));
        }
        return listDTO;
    }
}
