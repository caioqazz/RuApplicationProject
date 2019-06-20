<?php

namespace App\Http\Controllers\Api\Auth;

use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Laravel\Passport\Passport;
use Laravel\Passport\Client;
use Illuminate\Support\Facades\Route;

class RegisterController extends Controller
{
    use IssueTokenTrait;


    public $client;

    public function __construct()
    {
        $this->client = Client::find(1);
    }

    public function register(Request $request)
    {


        $this->validate($request, [
            'first_name' => 'required',
            'last_name' => 'required',
            'cpf' => 'required|min:11',
            'email' => 'required|email|unique:users,email',
            'password' => 'required|min:8'


        ]);

        $user = User::create([
            'first_name' => request('first_name'),
            'cpf' => request('cpf'),
            'email' => request('email'),
            'password' => bcrypt(request('password'))
        ]);

        return $this->issueToken($request, 'password');

    }
}