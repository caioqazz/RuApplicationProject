<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\ViewNotice;
use Illuminate\Support\Facades\Auth;
class ViewNoticeController extends Controller
{
 
    public function store(Request $request)
    {
       $view = ViewNotice::create($request->all() + ['user_id' => Auth::user()->id]);
        return response()->json($view, 201);
    }

}