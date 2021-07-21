angular.module('crudApp').factory('Account', account);

Account.$inject=['$resource'];

function Account($resource){
  var resourceURL = 'api/accounts/:id';

  return $resource(resourceURL, {, {
    'update':{
      method: 'PUT'
    }
  }});
}
