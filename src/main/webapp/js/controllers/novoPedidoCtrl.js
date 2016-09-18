angular.module("wineshop").controller("novoPedidoCtrl", function($scope, $cookies, KEYS, vinhoService) {

	var loadPedido = function () {
		$scope.pedido = $cookies.getObject(KEYS.pedidoAtual);
		if (!$scope.pedido) {
			$scope.pedido = {};
			$scope.pedido.itens = [];
		}
	}
	
	loadPedido();
	
	$scope.item = {};
	$scope.item.qtde = 1;
	
	$scope.adicionarItem = function(item) {
		if (verificaDuplicado(item)) {
			bootbox.alert("Este vinho já está inserido em seu pedido!");
			return;
		}
		$scope.pedido.itens.push(item);
		$scope.item = {};
		$scope.item.qtde = 1;
		$scope.formCadastro.$setPristine();
		storePedido();
	}
	
	$scope.removerItem = function(index) {
		$scope.pedido.itens.splice(index, 1);
		storePedido();
	}
	
	$scope.limparPedido = function() {
		$cookies.remove(KEYS.pedidoAtual);
		loadPedido();
	}
	
	var verificaDuplicado = function(item) {
		for (var i = 0; i < $scope.pedido.itens.length; i++) {
			if ($scope.pedido.itens[i].vinho.id === item.vinho.id) {
				return true;
			}
		}
		return false;
	}
	
	var getVinhos = function() {
		return vinhoService.get().success(function(data) {
			$scope.vinhos = data;
		});
	}
	
	var storePedido = function() {
		$cookies.putObject(KEYS.pedidoAtual, $scope.pedido);
	}
	
	getVinhos();

});