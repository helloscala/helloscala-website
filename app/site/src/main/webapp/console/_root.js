angular.module('console', [
  'ui.router',
  'ngMd5'
])
  .constant('Const', {
    default_limit: 15,
    HELLOWORLD_SESSION_TOKEN: 'hello-session',
    event: {
      AUTH_LOGIN_REQUEST: 'auth:loginRequest',
      AUTH_FORBIDDEN: 'auth:forbidden',
      DATA_NOT_FOUND: 'data:notFound',
      SERVER_ERROR: 'server:error'
    }
  })
  .factory('accountService', ['Const', function (Const) {
    var _account = {};
    return {
      getToken: function () {
        return jQuery.cookie(Const.HELLOWORLD_SESSION_TOKEN);
      },
      setToken: function (v) {
        jQuery.cookie(Const.HELLOWORLD_SESSION_TOKEN, v, {path: '/'});
      },
      getAccount: function () {
        return _account;
      },
      setAccount: function (v) {
        _account = v;
      }
    };
  }])
  .factory('authService', ['$http', 'md5', 'accountService', function ($http, md5, accountService) {
    var baseUrl = '/api/session';
    return {
      /**
       *
       * @param params "{account: '', password: ''}"
       * @returns {HttpPromise}
       */
      login: function (params) {
        var auth = {account: params.account, md5_pass: md5.createHash(params.password)};
        //var auth = {account: params.account, md5_pass: params.password};
        return $http.post(baseUrl + '/login', auth);
      },
      logout: function (token) {
        return $http.delete(baseUrl + '/logout/' + token);
      },
      query: function (token) {
        return $http.get(baseUrl + '/query/' + token);
      }
    };
  }])
  .controller('LoginCtrl', ['$scope', '$timeout', 'accountService', 'authService', function ($scope, $timeout, accountService, authService) {
    $scope.auth = {};
    $scope.loginSuccess = '';
    $scope.loginError = '';

    $scope.onLogin = function () {
      authService.login($scope.auth)
        .success(function (resp, status, headers) {
          if (resp.error.errcode == 0) {
            accountService.setToken(resp.data.token);
            accountService.setAccount(resp.data.account);
            $scope.loginError = '';
            $scope.loginSuccess = '登陆成功，3秒后自动跳转';
            $timeout(function () {
              $scope.$state.go('index');
            }, 3000);
          } else {
            $scope.loginSuccess = '';
            $scope.loginError = resp.error.errmsg;
            $scope.login_form.account.$pristine = true;
            $scope.login_form.password.$pristine = true;
            $scope.auth.password = '';
          }
        })
        .error(function (reason) {

        });
    };

    $scope.onLogout = function () {
      authService.logout(accountService.getToken()).success(function (resp) {
        if (resp.status.code == 0) {
          accountService.setToken(undefined);
          accountService.setAccount(undefined);
          $scope.status = {code: 0, msg: '退出登录成功，返回登录页面。'};
          $scope.$state.go('login');
        } else {
          $scope.status = resp.status;
        }
      });
    };
  }])
  .controller('HeaderCtrl', ['$scope', 'accountService', function ($scope, accountService) {
    $scope.account = accountService.getAccount();
  }])
  .config(['$httpProvider', '$locationProvider', '$stateProvider', '$urlRouterProvider',
    function ($httpProvider, $locationProvider, $stateProvider, $urlRouterProvider) {
      $locationProvider.hashPrefix('!');
      $urlRouterProvider.when('', 'index');

      $stateProvider
        .state('index', {
          url: '/index',
          views: {
            'main': {
              templateUrl: '_index.html'
            }
          }
        })
        .state('login', {
          url: '/login',
          views: {
            //'main': {
            //  templateUrl: '_none.html'
            //},
            'main': {
              templateUrl: '_login.html',
              controller: 'LoginCtrl'
            }
          }
        });

      $httpProvider.interceptors.push(['$q', '$rootScope', 'Const', 'accountService', function ($q, $rootScope, Const, accountService) {
        return {
          'responseError': function (rejection) {
            switch (rejection.status) {
              case 401:
                if ($rootScope.$state.current.name != 'login') {
                  $rootScope.$broadcast(Const.event.AUTH_LOGIN_REQUEST);
                }
                return;
              case 403:
                $rootScope.$broadcast(Const.event.AUTH_FORBIDDEN);
                break;
              case 404:
                $rootScope.$broadcast(Const.event.DATA_NOT_FOUND);
                break;
              case 500:
                $rootScope.$broadcast(Const.event.SERVER_ERROR);
            }
          }
        }
      }]);
    }
  ])
  .run(['$rootScope', '$state', '$stateParams', 'Const', 'accountService', function ($rootScope, $state, $stateParams, Const, accountService) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.Const = Const;

    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
      console.log(toState);
      console.log(fromState);
      var token = accountService.getToken();
      if (!token) {
        if (toState.name != 'login') {
          event.preventDefault();
          console.log('$state.go("login")');
          $state.go('login');
        }
      }
    });

    $rootScope.$on(Const.event.AUTH_LOGIN_REQUEST, function () {
      $state.go('login');
    });

    //var token = accountService.getToken();
    //if (!token) {
    //  console.log('需要登陆');
    //  $rootScope.$broadcast(Const.event.AUTH_LOGIN_REQUEST);
    //}
  }]);

angular.element(document).ready(function () {
  angular.bootstrap(document, ['console']);
});
