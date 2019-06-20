<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Notice extends Model
{
    public $timestamps = true;
    protected $fillable = [
        'user_id','type','subject','content', 'created_at', 'updated_at'
    ];
    protected $hidden = [ 'created_at', 'updated_at', ];

    public function administrator() { return $this->belongsTo('App\User', 'user_id', 'id')->get(); }
    public function views() { return $this->hasMany('App\ViewNotice', 'notice_id', 'id')->get(); }

}