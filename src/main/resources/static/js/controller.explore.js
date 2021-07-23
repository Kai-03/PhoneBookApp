



main.controller("exploreController", function($scope, $http, $location, $window) {
	// Get User ID
	var user_id = $location.search().user;
	$scope.user_id = user_id;
	$scope.search={
		user: "",
		contact: ""
	}
	
	// Retrieve current Contacts
	$scope.retrieve = function () {
		$http.get(URL+"/explore?user="+$scope.user_id).then(function(response){
			$scope.contacts=response.data.contacts;
			$scope.users=response.data.users;
			$scope.uniq_contacts=response.data.uniq_contacts;
			for (i in $scope.contacts) {
				console.log(i);
			}
		})
	}
	$scope.retrieve();
	
	// Contacts include?
	$scope.contacts_include = function(obj, expected=false){
		let include = $scope.contacts.includes(obj);
		console.log(include);
		return false; // Test mode
		return include == expected;
	}
	
	// Add to Contacts DB
	$scope.add_to_contacts=function(obj, index){
		var data = {
			user_id: $scope.user_id,
			target_contact_id: obj.contact_id
		}
		$http.put(URL+"/explore?user="+$scope.user_id, data).then(function(response){
			console.log(response);
			
		})
	}
	
	// Logout
	$scope.logout = function(){
		$window.location.href = URL+"/main";
	}
	
})