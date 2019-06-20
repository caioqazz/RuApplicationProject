<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Menu extends Model
{
    public $timestamps = true;
    protected $fillable = [
         'meal_id','created_at', 'updated_at'
    ];
    protected $hidden = [ 'created_at', 'updated_at' ];
    public function meal() { return $this->belongsTo('App\Meal', 'meal_id', 'id')->get(); }
    public function dishes() { return $this->hasMany('App\HasDish', 'menu_id', 'id')->get(); }

}