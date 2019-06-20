<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Dish extends Model
{
    public $timestamps = true;
    protected $fillable = [
        'type', 'name','photo','description', 'created_at','photo', 'updated_at'
    ];
    protected $hidden = [ 'created_at', 'updated_at' ];
    public function classification() { return $this->hasMany('App\ClassificationDish', 'dish_id', 'id')->get(); } 

}