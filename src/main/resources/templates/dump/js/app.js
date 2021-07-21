
var user=angular.module("user",[]);

user.controller("controller", function($scope, $http, $log){

  var name="noname"
  var params = [
    str= {name: "str", value: 10},
    agi= {name: "agi", value: 120},
    vit= {name: "vit", value: 60}
  ];
  var sortby="name";
  var asc=0;

  $scope.name=name;
  $scope.params=params;
  $scope.sortby=sortby;
  $scope.asc=asc;

  var sort_img="nil.png";
  $scope.sort_img=sort_img;

  var reverse=false;
  $scope.sort=function(obj){
    $scope.reverse = ($scope.sortby==obj) ? !$scope.reverse : false;
    $scope.sortby = obj;
  };

  $scope.getsortclass=function(obj){
    if ($scope.sortby!=obj) {
      $scope.sort_img = $scope.reverse==false ? "up.png" : "down.png";
      return 'unselectable'
    }
    $scope.sort_img = 'nil.png';
    return 'unselectable'
  };
  // $scope.sort=sort();
  // $scope.getsortclass=getsortclass();
  $scope.reverse=reverse;

  var search="";
  $scope.searchtext=search;

  var records=[];
  $scope.records=records;
  $scope.get_records=function(){
    var curl="http://localhost:8184/req/1";
    console.log("connecting 1")
    var x = $http({
      method: 'GET',
      url: curl,
    })
      .then(function(data, status) {
        // console.log(data.headers('Content-Length'));
        // console.log(data.headers('Content-type'));
        $log.info(data);
        $scope.records = data.data;

      });

  }

  $scope.newrecord={
    title: "",
    description: "",
  }

  $scope.post_records=function(){
    var curl="http://localhost:8184/req/submit";
    console.log("connecting 12");
    $http({
      method: 'POST',
      url: curl,
      data: {
        title: $scope.newrecord.title,
        description: $scope.newrecord.description
      }
    })
    .then(function successCallback(response){
      console.log("Successfully submitted data");
      console.log(response);
    }),
    function errorCallback(response) {
      console.log('error');
    }};

}

);


/// # sourceMappingURL=0.0.0.0:8081/req/
