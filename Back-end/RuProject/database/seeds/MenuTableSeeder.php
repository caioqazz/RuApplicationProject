<?php

use Illuminate\Database\Seeder;

class MenuTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        
        // App\Menu::create([
        // 'meal_id' => 1,
        // 'created_at' => date('Y-m-d H:i:s'),
        // 'updated_at' => date('Y-m-d H:i:s'),
        // ]);

        for ($i=0; $i < 61 ; $i++) { 
        App\Menu::create([
        'meal_id' => $i,
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);
        }
      
    
       
    }
}