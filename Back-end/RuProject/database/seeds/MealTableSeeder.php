<?php

use Illuminate\Database\Seeder;
use Carbon\Carbon;
class MealTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        App\Meal::create([
            'schedule_id' => 1,
            'date' => Carbon::now(),
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
          App\Meal::create([
            'schedule_id' => 2,
            'date' => Carbon::now(),
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        /*
        for ($i=0; $i < 100; $i++) { 
            $date = Carbon::now();

            App\Meal::create([
            'status' => TRUE,
            'schedule_id' => rand(1,2),
            'date'=> $date->addDays(rand(1, 180))->format('Y-m-d'),
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        } */
        /*
         for ($i=0; $i < 100; $i++) { 
            $date = Carbon::create(2019, 2, 12, 0, 0, 0);

            App\Meal::create([
            'status' => FALSE,
            'reason' => str_random(40),
            'schedule_id' => rand(1,2),
            'date'=> $date->addDays(rand(1, 30))->format('Y-m-d'),
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        
        }*/
    }
}