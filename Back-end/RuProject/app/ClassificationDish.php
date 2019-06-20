<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ClassificationDish extends Model
{
    public $timestamps = true;
    protected $fillable = [ 
        'user_id','dish_id','comment','rating','option','created_at', 'updated_at' ];
    
    protected $hidden = [ 'created_at', 'updated_at' ];

    public function user() { return $this->belongsTo('App\User','user_id', 'id')->get(); }
     public function dish() { return  $this->belongsTo('App\Dish','dish_id', 'id')->get(); }
}