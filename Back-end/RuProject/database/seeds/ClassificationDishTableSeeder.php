<?php

use Illuminate\Database\Seeder;

class ClassificationDishTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        for ($i=0; $i < 200 ; $i++) { 
           App\ClassificationDish::create([
        'user_id' => rand(2,30),
        'dish_id' => rand(1,23),
        'comment' => '',
        'rating'=> rand(1,5),
        'option' => rand(0,1),
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);
        }
        


    }
}