var account=angular.module("account",[]).factory("Account",Account);

Account.$inject=['$resource'];

function Account($resource){
  var resourceUrl='register/:id';
  return $resource(resourceUrl, {}, {
    'update':{
      method: PUT
    }
  });
}
