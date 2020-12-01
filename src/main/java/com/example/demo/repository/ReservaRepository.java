package com.example.demo.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.demo.model.Reserva;

import org.springframework.stereotype.Component;

@Component
public class ReservaRepository {
    private List<Reserva> reservas;
    private int nextNumero;
    @PostConstruct
    public void init()
    {
        nextNumero = 1;
        reservas = new ArrayList<Reserva>();
    }
     public Reserva save(Reserva reserva)
     {
        reserva.setNumero(nextNumero);
        reserva.setCliente(reserva.getCliente());
        reserva.setVeiculo(reserva.getVeiculo());
        reserva.setInicio(reserva.getInicio());
        reserva.setFim(reserva.getFim());
        reservas.add(reserva);
        nextNumero++;    
        return reserva;    
     }
     public List<Reserva> getReservas()
    {
        return reservas;
    }
    public Optional<Reserva> getReservaByNumero(int numero)
    {
        for(Reserva cont : reservas)
            if(cont.getNumero() == numero)
                return Optional.of(cont);
                
        return Optional.empty();        
    }
    public void delete(Reserva reserva)
    {
            reserva.getCliente().getReservas().remove(reserva);
            reserva.getVeiculo().getReservas().remove(reserva);
            reservas.remove(reserva);
    }
    public Reserva update(Reserva reserva)
    {
        Reserva aux = getReservaByNumero(reserva.getNumero()).get();

        if(aux!=null)
        {
            aux.setInicio(reserva.getInicio());
            aux.setFim(reserva.getFim());
        }
        return aux;
    }
}