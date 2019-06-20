<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class ViewNotice extends Model
{
  public $timestamps = true;
    protected $fillable = [
        'user_id','notice_id', 'created_at', 'updated_at'
    ];
    protected $hidden = [ 'created_at', 'updated_at' ];

    public function user() { return $this->belongsTo('App\User', 'user_id', 'id')->get(); }
    public function notice() { return $this->belongsTo('App\Notice', 'notice_id', 'id')->get(); }

}