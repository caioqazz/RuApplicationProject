<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Demand extends Model
{
    public $timestamps = true;
    protected $fillable = [
         'user_id','meal_id','option','created_at', 'updated_at'
    ];
    public function user() { return $this->belongsTo('App\User','user_id','id')->get(); } 
    public function meal() { return $this->belongsTo('App\Meal','meal_id', 'id')->get(); }

}