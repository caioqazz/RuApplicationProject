<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\ClassificationDish;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;
use App\User;
class ClassificationDishController extends Controller
{
    public function store(Request $request) {
     
        $user = User::find(Auth::user()->id);
      
      $classification =  ClassificationDish::where('dish_id', request('dish_id'))->where('user_id', Auth::user()->id)->get()->first();
     if($classification == null){
      $classification =  ClassificationDish::create(($request->all() + ['user_id' => Auth::user()->id]));
     }else{
         $classification->fill($request->all() + ['user_id' => Auth::user()->id]);
         $classification->save();
     }
       return response()->json($classification, 201);
    }

    public function show(Request $request){
    
   $classification =  ClassificationDish::where('user_id',Auth::user()->id)->where('dish_id',request('dish_id'))->get()->first();
   return $classification;
}

public function report(Request $request){
   
  $dishes = DB::select("SELECT 
  COUNT(IF(classification_dishes.rating<=1 , 1 , NULL)) AS ruim,
   COUNT(IF(classification_dishes.rating> 1 AND classification_dishes.rating <=2 , 1 , NULL)) AS baixo,
    COUNT(IF(classification_dishes.rating> 2 AND classification_dishes.rating<=3 , 1 , NULL)) AS medio,
     COUNT(IF(classification_dishes.rating> 3 AND classification_dishes.rating<=4 , 1 , NULL)) AS alto,
      COUNT(IF(classification_dishes.rating> 4 AND classification_dishes.rating<=5 , 1 , NULL)) AS excelente,
       COUNT(classification_dishes.dish_id) AS total,
       SUM(classification_dishes.option=TRUE) AS rejeicao
        FROM `classification_dishes` WHERE classification_dishes.dish_id=".request('dish_id'));
    return response()->json($dishes[0], 200);
}
}