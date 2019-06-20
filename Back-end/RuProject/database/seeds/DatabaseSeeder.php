<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
       //  $this->call(WeekTableSeeder::class);
        // $this->call(DayTableSeeder::class);
         $this->call(UsersTableSeeder::class);
         $this->call(ScheduleTableSeeder::class);
         $this->call(MealTableSeeder::class);
         $this->call(NoticeTableSeeder::class);
         $this->call(DishTableSeeder::class);
         $this->call(ClassificationDishTableSeeder::class);
        // $this->call(ClassificationMealTableSeeder::class);
         $this->call(SuggestionTableSeeder::class);
         $this->call(DemandTableSeeder::class);
         $this->call(ViewNoticeTableSeeder::class);
         $this->call(MenuTableSeeder::class);
         $this->call(HasDishTableSeeder::class);
         
        
         
    }
}