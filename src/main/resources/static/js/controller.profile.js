// Profile Controller
main.controller("profileController", function($scope, $http, $location, $window) { 
	$scope.scene="profile";
	$scope.data = new BaseData().data;
	// Get User
	var user_id = $location.search().user;

	$scope.get_user=function(){
		$http.get(URL+"/dashboard?profile="+user_id).then(function(response){
			var data = response.data;
			$scope.contact = data;
			$scope.user_id=user_id;
		})
	}
	$scope.get_user();
	
	$scope.update_user=function(){
		$http.post(URL+"/dashboard",$scope.contact).then(function(response){
			
		})
	}

		
	$scope.editing={
		user: null,
		phone: null,
		email: null
	}
	
	// Check If Editing
	$scope.ifedit=function(item=null, expect = null){
		//console.log($scope.editing);
		return $scope.editing[item] == expect;
	}
	//Toggle Editing
	$scope.edit=function(item){
		$scope.editing[item] = true;
	}
	// Toggle Unediting
	$scope.unedit=function(item){
		$scope.editing[item] = null;
	}
	$scope.save=function(item){
		$scope.update_user();
		$scope.unedit(item);
	}
	
	$scope.logout = function(){
		$window.location.href = URL+"/main";
	}
})