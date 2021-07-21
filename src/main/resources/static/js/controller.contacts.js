
// Contacts Controller
main.controller("contactsController", function($scope, $http, $location, $window) { //, $window, $routeParams, $location){
	$scope.scene="contacts";
	Base = new BaseData("Jack", "Septiceye", "645-08554", "spedicey@msn.com");
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
		//$window.setTimeout(function () {
			$http.get(URL+"/contacts?user="+$scope.user_id).then(function(results){
				$scope.contacts=results.data;})
		//},500)
	}
	
	// Create Contact
	$scope.create=function(){
		//console.log($scope.data);
		$http.post(URL+"/contacts?user="+$scope.user_id, $scope.data).then(function(response){
			//console.log(response.data);
			$scope.update();
			$scope.data = Base.clear();
			$scope.data.user_id = $scope.user_id;
		})
	}
	
	// Check If Editing a Contact
	$scope.ifedit=function(cid, expect = null){
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
			$scope.update();
			$scope.unedit(contact.contact_id);
		});
	}
	
	// Delete Contact
	$scope.delete=function(cid){
		$scope.unedit(cid);
		$http.delete(URL+"/contacts/"+cid).then(function(response){
			$scope.update();
		});
	}
	
	$scope.isempty = function(val=true){
		return $scope.contacts.length < 1 == val;
	}
	
	$scope.logout = function(){
		$window.location.href = URL+"/main";
	}
	
	
})
