angular.module('crudApp').controller("GeneralController", GeneralController);

GeneralController.$inject=['$scope','Account'];

function GeneralController($scope,Account){
  $scope.accounts=Account.query();
  $scope.account={};
  $scope.buttonText="Submit";

  $scope.saveAccount = function() {
   if($scope.employee.id!==undefined){
     Account.update($scope, account, function(){
       $scope.accounts=Account.query();
       $scope.account={};
       $scope.buttonText="Submit";
     })
   }else{
     Account.update($scope, account, function(){
       $scope.accounts=Account.query();
       $scope.account={};
     })
   }

   $scope.updateAccountInit=function(account){
     $scope.buttonText="Update";
     $scope.account=account;
   }

   $scope.deleteAccount=function(account){
     account.$delete({id:account.id}, function(){
       $scope.accounts=Account.query();
     });
   }


  }
}
