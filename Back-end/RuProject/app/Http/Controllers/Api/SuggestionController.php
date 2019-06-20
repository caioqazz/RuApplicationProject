<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Suggestion;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;

class SuggestionController extends Controller
{
           /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
     public function index()
    {

        $suggestions =   DB::select('SELECT S.*, U.name, U.photo
        FROM ((`suggestions` AS S) INNER JOIN (`users` AS U) ON S.user_id = U.id)');

        return response()->json($suggestions);
    }


    
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $suggestion = Suggestion::create($request->all()+ ['user_id' => Auth::user()->id]);
        return response()->json($suggestion, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $suggestion = Suggestion::find($id);
        // DB::table('sugestoes')->join('users', 'user_id', '=', 'users.id')->where('sugestoes.id', $id)->select('name')->get(); 
        return $suggestion;
    }

 
    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $suggestion = Suggestion::find($id);
        $suggestion->update($request->all());

        return response()->json($suggestion, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $suggestion = Suggestion::find($id);
        $suggestion->delete();
        return response()->json(null, 204);
    }
}