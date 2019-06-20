<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Suggestion extends Model
{
    public $timestamps = true;
    protected $fillable = [
        'user_id','subject','content', 'reply','manager_view','user_view'
    ];
    protected $hidden = [ 'created_at', 'updated_at' ];
    
    public function user() {
        return $this->belongsTo('App\User', 'user_id', 'id')->get();
    }
}