<?php

namespace App\Http\Controllers\Api;

use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Dish;
use Carbon\Carbon;
use Illuminate\Support\Facades\Storage;
class DishController extends Controller
{
    
      /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
      public function index()
    {   
        $dishes =Dish::all();
        return response()->json( $dishes, 200, [], JSON_NUMERIC_CHECK);

    
    }


     public function indexByType(Request $request)
    {   
        $type = request('type');
        $dishes = DB::select("SELECT dishes.*, (SELECT AVG(classification_dishes.rating) FROM `classification_dishes` WHERE dishes.id = classification_dishes.dish_id)
        AS average FROM `dishes` WHERE dishes.type = $type ORDER BY dishes.updated_at");
       

        return response()->json( $dishes, 200, [], JSON_NUMERIC_CHECK);

        //
    }

    
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {   
         $request->merge([
        'name' => str_replace('"','',request('name')),
        'description' => str_replace('"','',request('description'))]);

     if($request->hasFile('image') && $request->file('image')->isValid()){
       
        $name = request('name');

        $extension = $request->image->extension();
        $nameFile = "{$name}.{$extension}";
        
        request('image')->storeAs('public/dishes', $nameFile);
        
        $request->request->add(['photo' => Storage::url('public/dishes/'.$nameFile)]);
       
    }
       else{
        $request->request->add(['photo' => '']);
       }
      
       
      
        $dish = Dish::create($request->all());
       
        return response()->json($dish, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($type)
    {
        return $dish;
    }
   
   
    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {   
        $dish = Dish::find($id);
        
        $request->merge([
        'name' => str_replace('"','',request('name')),
        'description' => str_replace('"','',request('description'))]);

        if($request->hasFile('image') && $request->file('image')->isValid()){
       
        $name = $dish->id;

        $extension = $request->image->extension();
        $nameFile = "{$name}.{$extension}";
        
        request('image')->storeAs('public/dishes', $nameFile);
        
        $request->request->add(['photo' => Storage::url('public/dishes/'.$nameFile)]);
       
    }
        $dish->update($request->all());

        return response()->json($dish, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $dish = Dish::find($id);
        $dish->delete();
        return response()->json(null, 204);
    }

     
}