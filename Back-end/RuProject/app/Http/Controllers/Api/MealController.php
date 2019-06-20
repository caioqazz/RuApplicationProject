<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Meal;
use App\Schedule;
use Illuminate\Support\Facades\DB;
class MealController extends Controller
{
    
     public function weekIndex() { 
       
        $weeks = DB::select("SELECT DISTINCT MONTH(meals.date) AS month, (SELECT FLOOR((DAYOFMONTH(meals.date) - 1) / 7) + 1) AS week_of_month, YEAR(meals.date)
        AS year, (SELECT GROUP_CONCAT( DATE_FORMAT(meals.date, ' %d-%c' )) FROM (SELECT DISTINCT meals.date FROM `meals` ORDER BY meals.date) AS meals
        WHERE MONTH(meals.date) = month AND FLOOR((DAYOFMONTH(meals.date) - 1) / 7) + 1 = week_of_month AND YEAR(meals.date) = year)
        AS days FROM `meals` WHERE meals.date>=CURRENT_DATE()");

        return response()->json( $weeks, 200, [], JSON_NUMERIC_CHECK);
    }

      public function daysByWeekIndex(Request $request)
    {
       $days = DB::select("SELECT  D.date, DATE_FORMAT(D.date, '%d' ) AS day, DAYOFWEEK(D.date) AS day_of_week, DATE_FORMAT(D.date, '%c' ) AS month  
       FROM (SELECT DISTINCT meals.date FROM `meals`) AS D WHERE FLOOR((DAYOFMONTH(D.date) - 1) / 7) + 1 =".request('week')." AND MONTH(D.date)=".request('month')." AND YEAR(D.date)=".request('year')." ORDER BY D.date");
        return $days;
    }

    
    public function daysClosedIndex()
    {   
        $days = DB::select("SELECT D.date, D.id, DATE_FORMAT(D.date, '%d' ) AS day, DAYOFWEEK(D.date) AS day_of_week, DATE_FORMAT(D.date, '%c' ) AS
        month, D.status, D.reason, S.type FROM `meals` AS D , `schedules` AS S WHERE D.status = FALSE AND S.id = D.schedule_id ORDER
        BY D.date");
        
        return $days;
    }
    public function setStatus(Request $request){

    $schedule = Schedule::where('type', request('type'))->get()->first();
   return $meal = Meal::where('date', request('date'))->where('schedule_id', $schedule->id)->get()->first();
    $meal->update($request->all());

    return response()->json($meal, 200);
    }
    
   

}