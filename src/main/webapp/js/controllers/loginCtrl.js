angular.module("wineshop").controller("loginCtrl", function ($rootScope, $scope, $http, $state, KEYS) {

	$scope.usuario = {};
	$scope.invalidUser = false;
	$rootScope.auth = null;

	$scope.login = function(user) {
		if (user.email === 'test@test.com' && user.senha === '123') {
			$scope.invalidUser = false;
			$rootScope.auth = "Basic "+btoa(user.email+":"+user.senha);
			window.localStorage.setItem(KEYS.user, $rootScope.auth);
			$state.go("main.home");
		}else {
			$scope.invalidUser = true;
			$rootScope.auth = null;
		}
	};

});
