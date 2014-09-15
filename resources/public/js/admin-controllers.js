app.controller('AdminCtrl', function($scope, $controller, $location, Page, $templateCache){
    $scope.notify = function(code, message){
	centipair.components.notify(code, message);
    }
    
    
    $scope.notify(102, "Loading Page");
    $scope.page = Page;
    $scope.page.title = 'Admin';
    $scope.editUrl = '';
    $scope.$on('$routeChangeStart', function() {
	$scope.notify(102, "Loading Page");
	//$templateCache.removeAll();
    });
    $scope.$on('$routeChangeSuccess', function() {
	$scope.notify(200);
    });
    $scope.$on('$routeChangeError', function(data, status) {
	console.log(data.status);
	$scope.notify(404, "Page not found");
    });
    $scope.editData = function(object){
	$location.url($scope.editUrl+object.id);
    };
    
});

app.controller('MenuCtrl', function($scope, $location){
    $scope.getClass = function(path) {
	if ($location.path().substr(0, path.length) == path) {
	    return "active"
	} else {
	    return ""
	}
    }
});


app.controller('Admin404Ctrl', function($scope, $controller){
    $controller('AdminCtrl', {$scope:$scope});
    $scope.page.title = "Page not found"
    $scope.callback = function(data){
	console.log(data);
    }
});




app.factory("Page", function(){
    return {title: "Site Administration"};
});
