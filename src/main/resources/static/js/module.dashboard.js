// Main Module
var main = angular.module("main",['ngRoute']);

// Main Configuration
main.config(function ($routeProvider, $locationProvider){

  // Assign Routes
  $routeProvider
  // Welcome
  .when("/dashboard", {
    templateUrl: "pages/profile.html",
    controller: "profileController"
  })
  .when("/contacts", {
    templateUrl: "pages/contacts.html",
    controller: "contactsController"
  })
  .when("/explore", {
    templateUrl: "pages/explore.html",
    controller: "exploreController"
  })

  // Enable html5Mode
  $locationProvider.html5Mode(true);

})
