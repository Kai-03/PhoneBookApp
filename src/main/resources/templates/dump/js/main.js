
// Main Module
var main = angular.module("main",['ngRoute'])
// Start Config
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
  // Home | Dashboard
  .when("/home", {
    templateUrl: "pages/home.html",
    controller: "homeController"
  })

  // Enable html5Mode
  $locationProvider.html5Mode(true);

  // End Config
})



/* Define Controllers */

// Login
main.controller("loginController", function($scope, $http){
  $scope.scene="login";
  $scope.message="Welcome";
});

// Register
main.controller("registerController", function($scope, $http){
  $scope.scene="register";


  var username = "kai03";
  var first_name = "Kai";
  var last_name = "Kisuke";
  $scope.data = {
    username: username,
    password: "",
    first_name: first_name,
    last_name: last_name
  }

  function clear() {
    $scope.data = {
      username: "",
      password: "",
      first_name: "",
      last_name: ""
    }
  }

  $scope.register = function() {
    var url = "http://localhost:8187/register";
    $http.post(url, $scope.data).then(function (response) {
        if (response.data) {
          console.log(response.data.msg);
          // $scope.register_mes="Regisration successful!"
          // var n = response.data;
          // console.log(n.toString());
          // console.log($scope.feed)
        }
    })
  }

});

// Home | Dashboard
main.controller("homeController", function($scope, $http){
  $scope.message="Welcome"
});
