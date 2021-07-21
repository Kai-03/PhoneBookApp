// Main Module
var main = angular.module("main",['ngRoute']);
var URL = "http://localhost:8084";

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
class BaseData {
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
	$scope.data = new BaseData().data;

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
	Base = new BaseData("Vina", "Trinitipakdee", "09050805", "email@email.com");
	$scope.data = Base.data; 

	var user_id = $location.search().user;
	$scope.user_id = user_id;
	$scope.data.user_id = $scope.user_id;
	$scope.editing = {trigger : false};
	
	// Retrieve current Contacts
	$scope.retrieve = function () {
		$http.get(URL+"/contacts?user="+$scope.user_id).then(function(response){
			$scope.contacts=response.data;
		})
	}
	$scope.retrieve();
	
	// Define Update function
	$scope.update=function(){
		//console.log($scope.data);
		$window.setTimeout(function () {
			$http.get(URL+"/contacts?user="+$scope.user_id).then(function(results){
				$scope.contacts=results.data;})
		},500)
	}
	
	// Create Contact
	$scope.create=function(){
		//console.log($scope.data);
		$http.post(URL+"/contacts?user="+$scope.user_id, $scope.data).then(function(response){
			//console.log(response.data);
		})
		$scope.update();
		$scope.data = Base.clear();
		$scope.data.user_id = $scope.user_id;
	}
	
	// Check If Editing a Contact
	$scope.ifedit=function(cid, expect = null){
		var open = $scope.editing[cid];
		//console.log(open);
		//console.log($scope.editing);
		return $scope.editing[cid] == expect;
	}
	
	// Enable Editing
	$scope.edit=function(cid){
		$scope.editing[cid] = true;
	}
	// Disable Editing
	$scope.unedit=function(cid){
		$scope.editing[cid] = null;
	}
	
	// Modify Contact: Save()
	$scope.save=function(contact){
		var data = contact
		data.user_id = $scope.user_id; // Bind to user_id
		$http.put(URL+"/contacts?user="+$scope.user_id, data).then(function(response){
			console.log(reponse);
		});
		$scope.update();
		console.log("Saved");
		console.log($scope.editing);
		$scope.unedit(contact.contact_id);
	}
	
	// Delete Contact
	$scope.delete=function(cid){
		$scope.unedit(cid);
		console.log(cid);
		$http.delete(URL+"/contacts/"+cid).then(function(response){});
		$scope.update();
	}
	
	$scope.logout = function(){
		$window.location.href = URL+"/main";
	}
	
	
})
