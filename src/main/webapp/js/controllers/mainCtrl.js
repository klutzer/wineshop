angular.module("wineshop").controller("mainCtrl", function($scope, $state, KEYS) {

  $scope.logout = function() {
    console.log("Chamou o logout!!");
    window.localStorage.removeItem(KEYS.user);
    $state.go("login");
  };

  $scope.isActive = function(state) {
    return $state.current.name === state;
  };

  $scope.treeMeusDados = [{
    name: "Clientes",
    link: "main.cliente"
  }, {
    name: "Configurações",
    link: "main.configuracoes"
  }];

  $scope.tree = [{
    name: "States",
    link: "#",
    subtree: [{
      name: "state 1",
      link: "state1"
    }, {
      name: "state 2",
      link: "state2",
      subtree: [{
        name: "state unknown",
        link: "state unknown 2",
      }]
    }]
  }, {
    name: "No states",
    link: "#",
    subtree: [{
      name: "no state connected",
      link: "#"
    }]
  }, {
    name: "divider",
    link: "#"

  }, {
    name: "State has not been set up",
    link: "#"
  }, {
    name: "divider",
    link: "#"
  }, {
    name: "Here again no state set up",
    link: "#"
  }];
});
