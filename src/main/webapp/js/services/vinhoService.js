angular.module("wineshop").service("vinhoService", function($http, config) {

	this.get = function() {
		return $http.get(config.baseUrl + "/vinho");
	};
	
});
