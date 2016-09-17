angular.module("wineshop").controller("clienteCtrl", function($scope, clienteService) {

	$scope.clientes = [];
	
	var carregarClientes = function() {
		clienteService.get().success(function(data) {
			$scope.clientes = data;
		}).error(function(data) {
			console.log("Erro: "+response.statusText);
		});
	}
	
	$scope.salvarCliente = function(cliente) {
		clienteService.save(cliente).success(function(data) {
			bootbox.alert("Cliente adicionado!");
			reset();
		});
	}
	
	$scope.removerCliente = function(cliente) {
		clienteService.remove(cliente).success(function(data) {
			bootbox.alert("Cliente removido!");
			reset();
		});
	}
	
	$scope.editar = function(cliente) {
		$scope.cliente = angular.copy(cliente);
	}
	
	var reset = function() {
		delete $scope.cliente;
		$scope.formCadastro.$setPristine();
		carregarClientes();
	}
	
	carregarClientes();

});