var app = angular.module('hackerpins', ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider.
        when('/', {
            templateUrl: 'views/main.html',
            controller: StoriesCtrl
        }).
        when('/stories/new', {
            templateUrl: 'views/new.html',
            controller: SubmitStoryCtrl
        }).
        otherwise({
            redirectTo: '/'
        })
});


function StoriesCtrl($scope, $http) {

    $http.get('api/v1/stories').success(function (data, status, header, config) {
        $scope.stories = data;
    }).error(function (data, status, header, config) {
        alert("Error " + status);
        console.log(data);
    });

}

function SubmitStoryCtrl($scope, $http, $location) {

    $scope.submitStory = function () {
        $http.post('api/v1/stories', $scope.story).success(function (data, status, header, config) {
            alert("Created story with id " + data.id);
            $location.path('/');
        }).error(function (data, status, header, config) {
            alert("Error " + status);
            console.log(data);
        });
    }

    $scope.suggest = function () {
        if ($scope.story && $scope.story.url) {
            $http.get('api/v1/stories/suggest?url=' + $scope.story.url).success(function (data, status, header, config) {
                $scope.story.title = data.title;

                $scope.story.description = data.description;
                if (data.image) {
                    $scope.story.image = data.image;
                }
            }).error(function (data, status, header, config) {
                $scope.isFetchingDetails = false;
                alert(status);
                console.log(data);
            });
        }
    }

}