<?php

use Illuminate\Database\Seeder;
use App\Dish;
class HasDishTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        /*
        App\HasDish::create([
        'menu_id' => 1,
        'dish_id' => 1,
        'dish_type' => 1,
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);
      */
        for ($i=0; $i < 20 ; $i++) { 
        $dish = Dish::find(rand(1,23));
        
        App\HasDish::create([
        'menu_id' => 143,
        'dish_id' => $dish->id,
        'dish_type' => $dish->type,
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);
        }
    }
}