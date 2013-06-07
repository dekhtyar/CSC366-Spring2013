"use strict";

angular.module('apersci.filters', [])
.filter('escape', ['escape', function(escape) {
    return function(input) {
        return escape(input);
    };
}])
.filter('prettify', function() {
    return function(input) {
        return prettyPrintOne(input);
    };
});
