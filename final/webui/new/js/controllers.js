"use strict";

angular.module('apersci.controllers', [])
.controller('HomeCtrl', ['$scope', function($scope) {
    $scope.items = [];
    $scope.add = function() {
        $scope.items.push($scope.newItem);
        $scope.newItem = '';
    };
    $scope.remove = function(index) {
        $scope.items.splice(index, 1);
    };
    $scope.clear = function() {
        $scope.items = [];
    };
}])
.controller('BulkCtrl', ['$scope', function($scope) {
    $scope.$watch('remoteHost', function(newVal) {
        window.REMOTE_HOST = newVal;
    });
}])
.controller('OpCtrl', ['$scope', '$routeParams', '$http', 'operations', function($scope, $routeParams, $http, operations) {
    $http.defaults.headers.common = JSON.parse('{"Content-Type":"text/plain"}');
    $http.defaults.headers.post = $http.defaults.headers.common;
    $scope.op = $routeParams.op;
    $scope.opData = _.clone(operations[$routeParams.op]);
    $scope.showXML = false;

    $scope.requestXML = '';
    function updateXML() {
        if ($scope.showXML) {
            $scope.requestXML = APERSCI.encodeSOAP($scope.opData);
        }
    }

    function traverseOp(desc) {
        if (_.isPlainObject(desc) && _.isUndefined(desc.__value)) {
            return _.map(desc, function(subtag) { return traverseOp(subtag); });
        }
        if (_.isArray(desc)) {
            return _.map(desc, function(elem) { return traverseOp(elem); });
        }
        if (_.isNull(desc)) {
            return [];
        }
        return desc;
        /*
           if (_.isArray(desc)) {
           return _.map(desc, function(multitag) { return traverseOp(subtag); });
           }
           */
    }

    $scope.opFields = _.flatten(traverseOp($scope.opData));
    for (var i = 0; i < $scope.opFields.length; i++) {
        $scope.$watch('opFields['+i+'].__value', updateXML);
    }
    $scope.$watch('showXML', updateXML);

    $scope.responseXML = '';
    $scope.performRequest = function() {
        if (!$scope.remoteHost || $scope.remoteHost.match(/https?:\/\//) === null) {
            $scope.responseStatus = 'A full remote host must be set! (eg http://yoursite.com)';
        } else {
            $http.post($scope.remoteHost + '/inventoryService/', APERSCI.encodeSOAP($scope.opData)).
                success(function(data, status) {
                    $scope.responseXML = data;
                    $scope.responseStatus = status;
                    console.log(status);
                }).
                error(function(data, status) {
                    $scope.responseXML = data;
                    $scope.responseStatus = status;
                    console.log(status);
                });
        }
    };
}]);
