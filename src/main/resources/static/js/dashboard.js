// Main Module
var main = angular.module("main",['ngRoute']);
var URL = "http://localhost:8083";

// Main Configuration
main.config(function ($routeProvider, $locationProvider){

  // Assign Routes
  $routeProvider
  // Welcome
  .when("/dashboard", {
    templateUrl: "pages/profile.html",
    controller: "mainController"
  })
  .when("/contacts", {
    templateUrl: "pages/contacts.html",
    controller: "contactsController"
  })

  // Enable html5Mode
  $locationProvider.html5Mode(true);

})
// End Main Configuration


// Class BaseAccount: containing all account data
class BaseAccount {
  constructor(first_name="", last_name="", phone="", email="", user_id="") {
	var data = {
		first_name: first_name,
		last_name: last_name,
		phone: phone,
		email: email,
		user_id: user_id
	}
    this.data = data;
  }

  clear() {
	var data = {
		first_name: "",
		last_name: "",
		phone: "",
		email: "",
		user_id: ""
	}
    return data;
  }

}

// Main Controller
main.controller("mainController", function($scope, $http, $location) { //, $window, $routeParams, $location){
	$scope.scene="profile";
	$scope.data = new BaseAccount().data;

  // Get URL Params
	var user_id = $location.search().user;
	$scope.user_id = user_id
	$http.post(URL+"/dashboard", user_id).then(function(response){
		//console.log(response.data);
		$scope.user = response.data;
	})
})

// Contacts Controller
main.controller("contactsController", function($scope, $http, $location, $window) { //, $window, $routeParams, $location){
	$scope.scene="contacts";
	$scope.data = new BaseAccount().data;

	var user_id = $location.search().user;
	$scope.user_id = user_id;
	$scope.data.user_id = $scope.user_id;
	$scope.editing = {1 : false};
	
	$http.get(URL+"/contacts?user="+$scope.user_id).then(function(response){
		$scope.contacts=response.data;
	})
	
	$scope.update=function(){
		$window.setTimeout(function () {console.log("Start");
			$http.get(URL+"/contacts?user="+$scope.user_id).then(function(results){
				$scope.contacts=results.data;
				//console.log(results.data.values());
				//console.log($scope.contacts);
			})}
		,1000)
	}
	
	$scope.create=function(){
		$http.post(URL+"/contacts?user="+$scope.user_id, $scope.data).then(function(response){
			//console.log(response.data);
		})
		$scope.update();
	}
	
	$scope.delete=function(cid){
		//console.log(cid);
		$http.delete(URL+"/contacts/"+cid, $scope.data).then(function(response){});
		$scope.update();
	}
	
	$scope.edit=function(cid){
		$scope.editing.cid = true;
		//console.log($scope.editing.cid);
	}
	$scope.ifedit=function(cid, expect = true){
		if (expect == false){
			console.log($scope.editing.cid);
			console.log("show?")
			console.log($scope.editing.cid != expect);
			return $scope.editing.cid != expect;
		}
		return $scope.editing.cid == expect;
	}
})
