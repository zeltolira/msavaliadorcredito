package com.lira.msavaliadorcredito.application;

import com.lira.msavaliadorcredito.application.ex.DadosClientesNotFoundException;
import com.lira.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.lira.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import com.lira.msavaliadorcredito.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClientesNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
        @PostMapping
        public ResponseEntity realizaAvaliacao(@RequestBody DadosAvaliacao dados){
            try {
                RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
                return ResponseEntity.ok(retornoAvaliacaoCliente);
            } catch (DadosClientesNotFoundException e) {
                return ResponseEntity.notFound().build();
            } catch (ErroComunicacaoMicroservicesException e) {
                return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
            }

        }

        @PostMapping("solicitacoes-cartao")
        public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
        try {
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService
                    .solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        }catch (ErroSolicitacaoCartaoException e){
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }
        }
    }
