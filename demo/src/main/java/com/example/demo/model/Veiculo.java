package com.example.demo.model;



import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Veiculo {
    private int codigo;
    private String modelo;
    private double valorDiaria;
    @JsonIgnore
    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
    public int getTotalReservas(Veiculo veiculo)
    {
        return this.getReservas().size();
    }

    public boolean addReserva(Reserva reserva)
    {       
                return reservas.add(reserva); 
    }
}
