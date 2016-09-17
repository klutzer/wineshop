angular.module("wineshop").controller("novoPedidoCtrl", function($scope, $cookies, KEYS) {

	$scope.pedido = $cookies.getObject(KEYS.pedidoAtual);
	console.log($scope.pedido);
	
	$scope.item = {};
	$scope.item.qtde = 1;

});