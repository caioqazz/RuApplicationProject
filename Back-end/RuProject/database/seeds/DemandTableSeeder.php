<?php

use Illuminate\Database\Seeder;

class DemandTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      
        
        for ($i=2; $i < 20; $i++) { 
        App\Demand::create([
        'user_id' => rand(1,30),
        'option' => rand(0,1),
        'meal_id' => 3,
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);
        }
    }
}