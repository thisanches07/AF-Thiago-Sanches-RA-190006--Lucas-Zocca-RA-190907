package com.example.demo.dto;

import java.util.ArrayList;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import com.example.demo.model.Reserva;

import org.hibernate.validator.constraints.Length;

public class VeiculoDTO {

    @NotBlank(message = "Modelo obrigatório!")
    @Length(min=2,max=40, message = "Modelo deve ter no mínimo 4 e no máximo 40 caracteres!")
    private String modelo;

    @DecimalMin(value = "0.0", inclusive = false, message="O Valor da diária deve ser obrigatório e maior que ZERO!")  
    private double valorDiaria;

    private int codigo;

    private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
