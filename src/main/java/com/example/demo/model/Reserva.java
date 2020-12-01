package com.example.demo.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Reserva {
    private int numero;
    private Cliente cliente;
    private Veiculo veiculo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate inicio;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fim;

   
    public Reserva()
    {

    }
    public Reserva(int numero, Cliente cliente, Veiculo veiculo, LocalDate inicio, LocalDate fim)
    {
        this.numero = numero;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.inicio = inicio;
        this.fim = fim;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) { 
            this.fim = fim;
    }

    public double getTotalReserva()
    {
        double valor;
        int diferencaDatas;
        valor = this.getVeiculo().getValorDiaria();
        diferencaDatas = this.getFim().getDayOfYear();
        diferencaDatas -= this.getInicio().getDayOfYear();
        return valor*diferencaDatas;
    }
    @Override
    public String toString() {
        return "Reserva [cliente=" + cliente + ", fim=" + fim + ", inicio=" + inicio + ", numero=" + numero
                + ", veiculo=" + veiculo + "]";
    }
    
    
}
