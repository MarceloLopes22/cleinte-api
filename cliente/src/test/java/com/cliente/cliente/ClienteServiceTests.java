package com.cliente.cliente;

import com.cliente.cliente.domain.Cliente;
import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.repository.ClienteRepository;
import com.cliente.cliente.service.ClienteService;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class ClienteServiceTests {

	@Mock
	ClienteRepository repository;

	@InjectMocks
	ClienteService service;
	@Test
	@DisplayName("Cadastrar cliente teste")
	void cadastrarClienteTest() {
		ClienteDTO cliente = cliente();

		service.cadastrarCliente(cliente);
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Cliente.class));
	}

	@Test
	@DisplayName("Cadastrar cliente com CPF invalido teste")
	void cadastrarClienteCPFInvalidoTest() {
		ClienteDTO cliente = cliente();
		cliente.setCPF("73875317252");
		Assertions.assertThatThrownBy(() -> service.cadastrarCliente(cliente))
				.hasMessage("CPF Invalido");
	}

	@Test
	@DisplayName("Cadastrar cliente com CNPJ invalido teste")
	void cadastrarClienteCNPJInvalidoTest() {
		ClienteDTO cliente = cliente();
		cliente.setCPF(null);
		cliente.setCNPJ("97759014000166");
		Assertions.assertThatThrownBy(() -> service.cadastrarCliente(cliente))
				.hasMessage("CNPJ Invalido");
	}

	@Test
	@DisplayName("listar clientes")
	void listarClientesTest() {
		Mockito.when(repository.findAll()).thenReturn(mockListaClientes());
		List<Cliente> clientes = service.listarClientes();
		assertNotNull(clientes);
	}

	@Test
	@DisplayName("recuperar clientes")
	void recuperarClientesTest() {
		Mockito.when(repository.findById(anyInt())).thenReturn(mockCliente());
		Optional<Cliente> cliente = service.recuperarClientes(1);
		assertNotNull(cliente.get());
	}

	@Test
	@DisplayName("excluir clientes")
	void excluirClientesTest() {
		Mockito.when(repository.findById(anyInt())).thenReturn(mockCliente());
		service.excluirClientes(1);
		Mockito.verify(repository, Mockito.times(1)).deleteById(anyInt());
	}

	@Test
	@DisplayName("excluir clientes inesistente")
	void excluirClientesInesistenteTest() {
		Mockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());
  		Assertions.assertThatThrownBy(() -> service.excluirClientes(1))
				.hasMessage("Não existe um cliente com esse id cadastrado.");
	}

	@Test
	@DisplayName("atualizar cliente")
	void atualizarClienteTest() {
		Mockito.when(repository.findById(anyInt())).thenReturn(mockCliente());
		ClienteDTO cliente = cliente();
		service.atualizarCliente(cliente, 1);
		Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Cliente.class));
	}

	@Test
	@DisplayName("atualizar cliente inesistente")
	void atualizarClienteInesistenteTest() {
		Mockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());
		ClienteDTO cliente = cliente();
		Assertions.assertThatThrownBy(() -> service.atualizarCliente(cliente,1))
				.hasMessage("Não existe um cliente com esse id cadastrado.");
	}

	private List<Cliente> mockListaClientes() {
		Cliente cliente1 = Cliente
				.builder()
				.id(1)
				.nome("Test 1")
				.idade(31)
				.CNPJ("17124665000100")
				.build();

		Cliente cliente2 = Cliente
				.builder()
				.id(2)
				.nome("Test 2")
				.idade(30)
				.CPF("37502213244")
				.build();

		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

		return clientes;
	}

	private Optional<Cliente> mockCliente() {

		return Optional.of(Cliente
				.builder()
				.id(1)
				.nome("Test 1")
				.idade(31)
				.CNPJ("17124665000100")
				.build());
	}

	private ClienteDTO cliente() {
		return ClienteDTO
				.builder()
				.CNPJ(null)
				.CPF("73875317220")
				.idade(30)
				.nome("Marcelo")
				.build();
	}
}
