<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\DB;
use Carbon\Carbon;
use Illuminate\Support\Facades\Auth;
use App\Demand;
use App\Schedule;
use App\Meal;
class DemandController extends Controller
{
     public function daysIndex()
    {
        $date = Carbon::now()->addDays(21)->format('Y-m-d');
       $days = DB::select("SELECT M.date, DATE_FORMAT(M.date, '%d' ) AS day, DAYOFWEEK(M.date) AS day_of_week, DATE_FORMAT(M.date, '%c' ) AS month FROM
        (SELECT DISTINCT meals.date FROM `meals`) AS M WHERE M.date>=CURRENT_DATE AND M.date<'".$date."' ORDER BY M.date");
        
         return response()->json( $days, 200, [], JSON_NUMERIC_CHECK);
    }

    public function show(Request $request){
      
        $demand = DB::select("SELECT COUNT(IF(demands.option=0,1,NULL)) AS principal, COUNT(IF(demands.option=1,1,NULL)) AS vegetariano, COUNT(demands.id)
            AS total FROM `demands` WHERE demands.meal_id =
             (SELECT meals.id FROM `meals` INNER JOIN `schedules` ON meals.schedule_id=schedules.id
            WHERE meals.date='".request('date')."' AND schedules.type=".request('type').")");

        return response()->json( $demand[0], 200, [], JSON_NUMERIC_CHECK);


    }

    public function showByUser(Request $request){
       
        $demand = DB::select("SELECT IF (EXISTS (SELECT 1 FROM `demands` WHERE demands.user_id =". Auth::user()->id ." AND demands.meal_id =
             (SELECT meals.id FROM `meals` INNER JOIN `schedules` ON meals.schedule_id=schedules.id
            WHERE meals.date='".request('date')."' AND schedules.type=".request('type')." )
        ),1,0) AS parcipation");

        return response()->json( $demand[0], 200, [], JSON_NUMERIC_CHECK);
    }

    public function insert(Request $request){
        $schedule = Schedule::where('type', request('type'))->get()->first();
      $meal = Meal::where('date', request('date'))->where('schedule_id', $schedule->id)->get()->first();
   
      $demand =  Demand::create($request->all()+ [
          'user_id' => Auth::user()->id,
          'meal_id' => $meal->id
      ]);       
      return response()->json( $demand, 200, [], JSON_NUMERIC_CHECK);
    }

    public function destroy(Request $request){
          $schedule = Schedule::where('type', request('type'))->get()->first();
         $meal = Meal::where('date', request('date'))->where('schedule_id', $schedule->id)->get()->first();
        $demand= Demand::where('user_id', Auth::user()->id)->where('meal_id', $meal->id)->get()->first();
        $demand->delete();
         return response()->json($demand, 204);
     }

     public function report(Request $request){
      
//WHERE MONTH(M.date) =".request('month')." AND YEAR(M.date)='".request('year')."'"

         $demands = DB::select("SELECT DISTINCT M.date, DAY(M.date) AS day,
(SELECT COUNT(meals.id)  FROM `demands` INNER JOIN `meals` ON demands.meal_id=meals.id WHERE meals.date = M.date) AS total
FROM `demands` AS D INNER JOIN `meals` AS M ON D.meal_id=M.id WHERE MONTH(M.date)=".request('month')." AND YEAR(M.date)='".request('year')."' ORDER BY day");

         return response()->json(['demands'=> $demands], 200, [], JSON_NUMERIC_CHECK);
     }

}