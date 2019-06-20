<?php

namespace App\Http\Controllers\Api;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Notice;
use Illuminate\Support\Facades\Auth;
use Carbon\Carbon;
use App\User;

class NoticeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
       
        $notices= DB::select("SELECT notices.*, users.name, (SELECT COUNT(view_notices.id)
        FROM `view_notices` WHERE view_notices.notice_id = notices.id) AS views, 
       IF((SELECT  view_notices.id FROM `view_notices` WHERE view_notices.notice_id = notices.id AND view_notices.user_id=".Auth::user()->id." ) IS
        NULL,0,1) AS visualized
         FROM `notices`, `users` WHERE notices.user_id = users.id ORDER BY notices.updated_at");
        
        
        return response()->json($notices, 200, [], JSON_NUMERIC_CHECK);
    }

   
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $notice = Notice::create($request->all() + ['user_id' => Auth::user()->id]);
        return response()->json($notice, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show(Notice $notice)
    {
        //adicionar visualizacao
        return $notice;
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
        $notice = Notice::find($id);
        $notice->update($request->all());

        return response()->json($notice, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $notice = Notice::find($id);
        $notice->delete();
        return response()->json($notice, 204);
    }

    public function getViews($id)
    {
        $view = Notice::find($id)->view();
        return response()->json(['data' => $view], 200, [], JSON_NUMERIC_CHECK); ;
    }
 
}