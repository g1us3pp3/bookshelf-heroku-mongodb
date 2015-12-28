var AssetController = ['$scope','$state','Asset',function($scope,$state,Asset) {
	$scope.$on('$viewContentLoaded', function(event){
		$('html, body').animate({
		    scrollTop: $("#assets").offset().top
		}, 1000);
	});

	$scope.assets = Asset.query();
}];

var AssetDetailsController = ['$scope','$rootScope','$stateParams','Asset', function($scope,$rootScope,$stateParams,Asset) {

	var currentId = $stateParams.id;
	//var nextId = parseInt($stateParams.id) + 1;
	//var prevId = parseInt($stateParams.id) - 1;

	//$scope.prevAsset = Asset.get({id:prevId});
	//$scope.nextAsset = Asset.get({id:nextId});
	$scope.currentAsset = Asset.get($stateParams);
	
	$scope.saveAsset = function(){
		asset = $scope.currentAsset;
		Asset.save(asset);
	}
	
	$scope.addAsset = function() {
		$scope.assetFormHeader = "Add a new Asset";
		$scope.currentAsset = {type:{}};
	}
	
	$scope.editAsset = function(id) {
		$scope.assetFormHeader = "Edit Asset";
		for(i = 0;i < $scope.currentAsset.assets.length; i++) {
			if($scope.currentAsset.assets[i].id == id) {
				$scope.currentAsset = $scope.currentAsset.assets[i];
				break;
			}
		}
	};

}];

var AddAssetController = ['$scope','Asset', function($scope,Asset) {

	$scope.asset={id:0,assets:[]};

	$scope.addAssets = function(){
		Asset.save($scope.asset);
	}
}];