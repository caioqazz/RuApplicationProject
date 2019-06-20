<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class HasDish extends Model
{
    public $timestamps = true;
    protected $fillable = [
         'menu_id','dish_id','dish_type','created_at', 'updated_at'
    ];
    protected $hidden = [ 'created_at', 'updated_at', ];
    public function menu() { return $this->belongsTo('App\Menu','menu_id','id')->get(); }
    public function dish() { return $this->belongsTo('App\Dish','dish_id','id')->get(); }

}