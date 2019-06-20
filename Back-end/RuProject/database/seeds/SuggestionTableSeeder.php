<?php

use Illuminate\Database\Seeder;

class SuggestionTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        App\Suggestion::create([
        'user_id' => 2,
        'subject' => 'Almoço de 14/05/2019',
        'content' => 'Gostaria de informar que o almoço foi perfeito na minha opnião, continuem assim!',
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Suggestion::create([
        'user_id' => 3,
        'subject' => 'Omelete',
        'content' => 'Peço por gentileza a retirada do omelete do cardápio.',
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);

        
    }
}