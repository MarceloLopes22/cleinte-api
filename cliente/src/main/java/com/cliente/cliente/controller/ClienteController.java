package com.cliente.cliente.controller;

import com.cliente.cliente.domain.Cliente;
import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping(value = "cadastrar-cliente")
    private ResponseEntity cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = service.cadastrarCliente(clienteDTO);
        return new ResponseEntity(cliente, HttpStatus.CREATED);
    }

    @PutMapping(value = "atualizar-cliente")
    private ResponseEntity atualizarCliente(@RequestBody ClienteDTO clienteDTO, Integer id) {
        Cliente cliente = service.atualizarCliente(clienteDTO, id);
        return new ResponseEntity(cliente, HttpStatus.OK);
    }

    @GetMapping(value = "listar-cliente")
    private ResponseEntity listarClientes() {
        List<Cliente> clientes = service.listarClientes();
        return new ResponseEntity(clientes, HttpStatus.OK);
    }

    @GetMapping(value = "recuperar-cliente")
    private ResponseEntity recuperarClientes(Integer id) {
        Optional<Cliente> cliente = service.recuperarClientes(id);
        return new ResponseEntity(cliente, HttpStatus.OK);
    }

    @DeleteMapping(value = "excluir-cliente")
    private ResponseEntity excluirClientes(Integer id) {
        service.excluirClientes(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
