<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\User;
use Illuminate\Support\Facades\DB;
 use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Storage;
class UserController extends Controller
{
   
    

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function getPerfil()
    {
       return User::find(Auth::user()->id);
    }

    public function getFeedbacks(){
$user = User::find(Auth::user()->id);
      $suggestions =   DB::select("SELECT S.*, U.name, U.photo
        FROM ((`suggestions` AS S) INNER JOIN (`users` AS U) ON S.user_id = U.id) WHERE U.id =". $user->id);

     return $suggestions;
    }

    public function getUser()
     {
      
        $user = User::find(Auth::user()->id);
       // $user->photo = Storage::get('public/image/Fresh-Fruit.png');
        return $user;
       }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
     
       $user = User::find(Auth::user()->id);
       $request->merge(['name' => str_replace('"','',request('name'))]);

        if($request->hasFile('image') && $request->file('image')->isValid()){
       
        $name = $user->id;

        $extension = $request->image->extension();
        $nameFile = "{$name}.{$extension}";
        
        request('image')->storeAs('public/users', $nameFile);
       
       $request->request->add(['photo' => Storage::url('public/users/'.$nameFile)]);
    }
      
       $user->update($request->all());
       return response()->json($user, 200);
    }
    
}