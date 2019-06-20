<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Meal extends Model
{
    public $timestamps = true;
    protected $fillable = [
        'status','reason','schedule_id', 'created_at', 'updated_at'
    ];
    protected $hidden = [ 'created_at', 'updated_at' ];

    public function schedule() { return $this->belongsTo('App\Schedule', 'schedule_id', 'id')->get(); }
    public function menu() { return $this->hasOne('App\Menu', 'meal_id', 'id')->get()->first(); }
    public function classification() { return $this->hasMany('App\ClassificationMeal', 'meal_id', 'id')->get(); }

}