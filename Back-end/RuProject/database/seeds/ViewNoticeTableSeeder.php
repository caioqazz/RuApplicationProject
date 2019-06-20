<?php

use Illuminate\Database\Seeder;

class ViewNoticeTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
         App\ViewNotice::create([
            'user_id' => 1,
            'notice_id' => 1,
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        for ($i=0; $i < 30 ; $i++) { 
             App\ViewNotice::create([
            'user_id' => rand(0,21),
            'notice_id' => rand(0,20),
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        }
    }
}