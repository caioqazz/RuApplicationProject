<?php

use Illuminate\Database\Seeder;
use App\User;
class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
     {
        // User::create([
        //     'name' => 'Cliente',
        //     'email' => 'client@gmail.com',
        //     'cpf' => '000.000.000-00',
        //     'password' => bcrypt('12345678'),
        //     'type' => '1',
        //     'photo' => '',
        // ]);
    
        // User::create([
        //     'name' => 'Coordenador',
        //     'email' => 'manager@gmail.com',
        //     'cpf' => '000.000.001-00',
        //     'password' => bcrypt('12345678'),
        //     'type' => '0',
        //     'photo' => ''
        // ]);

    
        factory('App\User', 10)->create();
    }
}