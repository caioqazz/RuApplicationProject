<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Day extends Model
{
     public $timestamps = true;
    protected $fillable = [
        'date','name','week_id', 
    ];
    protected $hidden = [ 'created_at', 'updated_at' ];
    public function week() { return $this->belongsTo('App\Week','week_id','id')->get(); }
    public function meals() { return $this->hasMany('App\Meal', 'day_id', 'id')->get(); }

}