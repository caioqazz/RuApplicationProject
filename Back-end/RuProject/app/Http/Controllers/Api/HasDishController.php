<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\HasDish;
use App\Schedule;
use App\Meal;
use Illuminate\Support\Facades\DB;

class HasDishController extends Controller
{
  
    public function store(Request $request)
    {
       
        $schedule = Schedule::where('type', request('type'))->get()->first();
        $meal = Meal::where('date', request('date'))->where('schedule_id', $schedule->id)->get()->first();
        $hasDish = HasDish::create($request->all() + ['menu_id' => $meal->menu()->id]);
        return response()->json($hasDish, 201);
    }

    

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
        $schedule = Schedule::where('type', request('type'))->get()->first();
        $meal = Meal::where('date', request('date'))->where('schedule_id', $schedule->id)->get()->first();
        $hasDish = HasDish::where('menu_id',$meal->menu()->id)->where('dish_type',request('dish_type'))->get()->first();
        
        $hasDish->update($request->all());

        return response()->json($hasDish, 200);
    }

 
}