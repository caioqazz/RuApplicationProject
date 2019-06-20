<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Schedule extends Model
{
    public $timestamps = true;
    protected $fillable = [
         'type','open','close','created_at', 'updated_at'
    ];
    protected $hidden = [ 'created_at', 'updated_at' ];
}