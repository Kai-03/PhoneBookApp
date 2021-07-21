
// Main Module
var main = angular.module("main",['ngRoute']);
var URL = "http://localhost:8084";

// Main Configuration
main.config(function ($routeProvider, $locationProvider){

  // Assign Routes
  $routeProvider
  // Login
  .when("/login", {
    templateUrl: "pages/login.html",
    controller: "loginController"
  })
  // Register
  .when("/register", {
    templateUrl: "pages/register.html",
    controller: "registerController"
  })
  // Home
  .when("/home", {
    templateUrl: "pages/home.html",
    controller: "homeController"
  })

  // Enable html5Mode
  $locationProvider.html5Mode(true);

})
// End Main Configuration

// Class BaseAccount: containing all account data
class BaseAccount {
  constructor(username="kai03", password="", first_name="", last_name="") {
    var data = {
      username: username,
      password: password,
      first_name: first_name,
      last_name: last_name
    }
    this.data = data;
  }

  clear() {
    var data = {
      username: "",
      password: "",
      first_name: "",
      last_name: ""
    }
    return data;
  }

}

/* Define Controllers */

// Login
main.controller("loginController", function($scope, $http, $window){
  $scope.scene="login";
  $scope.data = new BaseAccount().data;

  $scope.login = function() {
    var url = URL+"/login";
    $http.post(url, $scope.data)
    .then(function (response) {
        if (response.data) {
          console.log(response.data.user_id);
          $scope.login_mes=response.data.msg;
          // $window.location.href
          // $routeProvider =
          $window.location.href = URL+"/dashboard?user="+response.data.user_id;
        }
    })
  }

});

// Register
main.controller("registerController", function($scope, $http){
  $scope.scene="register";
  BaseAcc = new BaseAccount("kai03", "","Kai", "Kisuke");
  $scope.data = BaseAcc.data;

  function clear() {
    $scope.data = BaseAcc.clear();
  }

  $scope.register = function() {
    var url = URL+"/register";
    $http.post(url, $scope.data).then(function (response) {
        if (response.data) {
          $scope.register_mes=response.data.msg;
        }
    })
  }

});

// Home | Dashboard
main.controller("homeController", function($scope, $http){
  $scope.message="Welcome"
});
