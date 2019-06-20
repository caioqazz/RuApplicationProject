<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
 */

Route::post('register', 'Api\Auth\RegisterController@register');
Route::post('login', 'Api\Auth\LoginController@login');
Route::post('refresh', 'Api\Auth\LoginController@refresh');


Route::middleware(['auth:api', 'admin'])->group(function () {

  

  Route::post('notices', 'Api\NoticeController@store');
  Route::put('notices/{id}', 'Api\NoticeController@update');
  Route::delete('notices/{id}', 'Api\NoticeController@destroy');

  Route::post('meals/status', 'Api\MealController@setStatus');

  Route::post('dishes', 'Api\DishController@store');
  Route::post('dishes/{id}', 'Api\DishController@update');
  Route::delete('dishes/{id}', 'Api\DishController@destroy');

  Route::get('demands', 'Api\DemandController@show');

  Route::post('menus/dishes', 'Api\HasDishController@store');
  Route::put('menus/dishes', 'Api\HasDishController@update');
  Route::get('classifications/report', 'Api\ClassificationDishController@report');
  Route::get('demands/report', 'Api\DemandController@report');
});

Route::middleware('auth:api')->group(function () {

  Route::post('logout', 'Api\Auth\LoginController@logout');

  Route::get('dishes/type', 'Api\DishController@indexByType');
  Route::get('dishes', 'Api\DishController@index');

  Route::get('users', 'Api\UserController@getUser');

  Route::get('menus', 'Api\MenuController@getMenu');

  Route::get('users/demand', 'Api\DemandController@showByUser');
  Route::post('demands', 'Api\DemandController@insert');
  Route::put('demands', 'Api\DemandController@destroy');
  Route::get('demands/days', 'Api\DemandController@daysIndex');

  Route::post('users/update', 'Api\UserController@update');
  Route::get('users/feedbacks', 'Api\UserController@getFeedbacks');

  Route::get('weeks', 'Api\MealController@weekIndex');
  Route::get('meals/days/opened', 'Api\MealController@daysByWeekIndex');
  Route::get('meals/days/closed', 'Api\MealController@daysClosedIndex');

  Route::post('notices/viewed', 'Api\ViewNoticeController@store');

  Route::get('notices', 'Api\NoticeController@index');

  Route::post('classifications', 'Api\ClassificationDishController@store');
  Route::get('classifications', 'Api\ClassificationDishController@show');

  Route::get('feedbacks', 'Api\SuggestionController@index');
  Route::post('feedbacks', 'Api\SuggestionController@store');
  Route::put('feedbacks/{id}', 'Api\SuggestionController@update');
  Route::delete('feedbacks/{id}', 'Api\SuggestionController@destroy');
});