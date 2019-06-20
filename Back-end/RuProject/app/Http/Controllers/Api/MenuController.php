<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Menu;
use App\Schedule;
use App\Meal;
use Illuminate\Support\Facades\DB;
class MenuController extends Controller
{
      /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $menus = Menu::all();
        return response()->json(['data' => $menus], 200, [], JSON_NUMERIC_CHECK);
    }

    public function getByMeal(Request $request)
    {
    $menus = DB::select("SELECT * FROM `has_dishes` WHERE has_dishes.menu_id = (SELECT menus.id FROM `menus` WHERE menus.meal_id=".request('id').")");
        return response()->json( $menus, 200, [], JSON_NUMERIC_CHECK);
    }

    public function getMenu(Request $request){
        $schedule = Schedule::where('type', request('type'))->get()->first();
        $meal = Meal::where('date', request('date'))->where('schedule_id', $schedule->id)->get()->first();
        if($meal == null){
            return response()->json([], 404); 
        }

     $menu=   DB::select("SELECT * FROM `has_dishes` INNER JOIN `dishes` ON has_dishes.dish_id = dishes.id   WHERE has_dishes.menu_id =".$meal->menu()->id);

      return response()->json( ['meal' => $meal, 'dishes' => $menu], 200, [], JSON_NUMERIC_CHECK);
    }
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $menu = Menu::create($request->all());
        return response()->json($menu, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show(Menu $menu)
    {
        return $menu;
    }

   

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Menu $menu)
    {
        $menu->update($request->all());

        return response()->json($menu, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Menu $menu)
    {
        $menu->delete();
        return response()->json(null, 204);
    }
}