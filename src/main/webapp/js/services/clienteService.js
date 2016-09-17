angular.module("wineshop").service("clienteService", function($http, config) {

	this.get = function() {
		return $http.get(config.baseUrl + "/cliente");
	};
	
	this.save = function(cliente) {
		return $http.put(config.baseUrl + "/cliente", cliente);
	}
	
	this.remove = function(cliente) {
		return $http.delete(config.baseUrl + "/cliente/" + cliente.id);
	}
});
