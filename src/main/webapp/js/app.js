//Usar ui-navbar
angular.module("wineshop", ["ui.bootstrap", "ui.router", "ui.navbar", "ngCookies"]);

angular.module("wineshop")
.constant("KEYS", {
	user: "userKey",
	pedidoAtual: "wineshopPedidoAtualKey"
})
.constant("config", {
  baseUrl: "http://localhost:8080/wineshop/api"
})
.run(function($rootScope, $state, KEYS) {
  $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams, options) {
    console.log(fromState.name+" -> "+toState.name);
    if (window.localStorage.getItem(KEYS.user)) {
      if (toState.name === "login") {
        console.log("Tentando acessar login estando logado!");
        event.preventDefault();
        $state.go("main.home");
      }
    }else {
      if (toState.authenticated) {
        console.log("Redirecionando para login");
        event.preventDefault();
        $state.go("login");
      }
    }
  });
});
