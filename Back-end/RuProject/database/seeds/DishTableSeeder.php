<?php

use Illuminate\Database\Seeder;

class DishTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        App\Dish::create([
            'type'=> 0,
            'name' => 'Fricasse de frango',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
          App\Dish::create([
            'type'=> 0,
            'name' => 'Carne ao forno',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
          App\Dish::create([
            'type'=> 0,
            'name' => 'Bife de porco acebolado',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        //Arroz
         App\Dish::create([
            'type'=> 3,
            'name' => 'Simples',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

          App\Dish::create([
            'type'=> 3,
            'name' => 'À Grega',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        //Feijao
          App\Dish::create([
            'type'=> 4,
            'name' => 'Simples',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

           App\Dish::create([
            'type'=> 4,
            'name' => 'Tutu',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
           App\Dish::create([
            'type'=> 4,
            'name' => 'Batido',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        //Guarnição
        
        App\Dish::create([
            'type'=> 2,
            'name' => 'Macarrão à Primavera',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 2,
            'name' => 'Moranga Acebolada',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 2,
            'name' => 'Angu',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        //Salada
         App\Dish::create([
            'type'=> 7,
            'name' => 'Alface com tomate',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 7,
            'name' => 'Salada de Almeirão',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 7,
            'name' => 'Beterraba cozida',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        //Sobremesa
         App\Dish::create([
            'type'=> 6,
            'name' => 'Laranja',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 6,
            'name' => 'Pudim de chocalate',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 6,
            'name' => 'Salada de Frutas',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
        //Suco
            App\Dish::create([
            'type'=> 5,
            'name' => 'Laranja',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 5,
            'name' => 'Uva',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 5,
            'name' => 'Abacaxi',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

         //Vegetariano
            App\Dish::create([
            'type'=> 1,
            'name' => 'Bolinho de batata',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 1,
            'name' => 'Legumes sautê',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Dish::create([
            'type'=> 1,
            'name' => 'Quiche de espinafre e queijo',
            'photo' => '',
            'description' => '',
            'created_at' => date('Y-m-d H:i:s'),
            'updated_at' => date('Y-m-d H:i:s'),
        ]);
    }
}