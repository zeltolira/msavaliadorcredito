package com.lira.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
@AllArgsConstructor
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;
}
