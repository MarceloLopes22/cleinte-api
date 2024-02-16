package com.cliente.cliente.service;

import com.cliente.cliente.domain.Cliente;
import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.repository.ClienteRepository;
import com.cliente.cliente.utils.Validar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;


    public Cliente cadastrarCliente(ClienteDTO clienteDTO) {

        validarCPFCNPJ(clienteDTO);

        Cliente cliente = Cliente
                .builder()
                .CNPJ(clienteDTO.getCNPJ())
                .CPF(clienteDTO.getCPF())
                .idade(clienteDTO.getIdade())
                .nome(clienteDTO.getNome())
                .build();
        return repository.save(cliente);
    }

    private static void validarCPFCNPJ(ClienteDTO clienteDTO) {
        if (clienteDTO.getCPF() != null && !Validar.isCPF(clienteDTO.getCPF())){
            throw new RuntimeException("CPF Invalido");
        }

        if (clienteDTO.getCNPJ() != null && !Validar.isCNPJ(clienteDTO.getCNPJ())){
            throw new RuntimeException("CNPJ Invalido");
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = repository.findAll();
        return clientes;
    }

    public Optional<Cliente> recuperarClientes(Integer id) {
        Optional<Cliente> cliente = repository.findById(id);
        return cliente;
    }

    public void excluirClientes(Integer id) {
        Optional<Cliente> cliente = repository.findById(id);

        if (cliente.isPresent()) {
            repository.deleteById(id);
        } else if(cliente.isEmpty()){
            throw new RuntimeException("Não existe um cliente com esse id cadastrado.");
        }
    }

    public Cliente atualizarCliente(ClienteDTO clienteDTO, Integer id) {
        Cliente cliente = null;
        validarCPFCNPJ(clienteDTO);

        Optional<Cliente> optionalCliente = repository.findById(id);

        if (optionalCliente.isPresent()){
            cliente = optionalCliente.get();
            cliente = Cliente
                    .builder()
                    .CNPJ(clienteDTO.getCNPJ())
                    .CPF(clienteDTO.getCPF())
                    .idade(clienteDTO.getIdade())
                    .nome(clienteDTO.getNome())
                    .build();
            repository.save(cliente);
        } else if (optionalCliente.isEmpty()){
            throw new RuntimeException("Não existe um cliente com esse id cadastrado.");
        }
        return cliente;
    }
}
