
// angular.module("Register").controller("Controller", Controller);
// Controller.inject=['$scope', 'Account'];
//
// function Controller($scope, Account){
//
//   $scope.accounts=Account.query();
//
//
// }



// Create Module
var account=angular.module("account",[])

account.controller("controller", function($scope, $http, $log){

  var username="kaixxx";
  var password="98765asd";
  var first_name="Kai";
  var last_name="Kisuke";

  $scope.data = {
    username: username,
    password: password,
    first_name: first_name,
    last_name: last_name,
  }

  function clear(){
    $scope.data = {
      username: "",
      password: "",
      first_name: "",
      last_name: "",
    }
    // console.log("cleared forms");
  }

  $scope.register=function(){
    var url = "http://localhost:8187/register"
    $http.post(url, $scope.data).then(function (response) {
      if (response) {
        console.log(response.data);
        // var n = response.data;
        // console.log(n.toString());
        // console.log($scope.feed)

      };
    });
    // clear();
  }

});
