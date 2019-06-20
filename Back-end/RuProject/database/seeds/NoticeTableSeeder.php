<?php

use Illuminate\Database\Seeder;

class NoticeTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        App\Notice::create([
        'user_id' => 1,
        'subject' => 'FUNCIOMAMENTO DO RU 07/09/2018',
        'content' => 'Prezado usuário do ru, Informamos a todos que o restaurante não funcionará no dia 16/05/2019 devido ao feriado!',
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Notice::create([
        'user_id' => 1,
        'type'=>1,
        'subject' => 'FUNCIOMAMENTO DO RU DURANTE RECESSO ACADÊMICO',
        'content' => 'Prezado usuário do ru, Informamos a todos que já está disponível no site da Ufop, o calendário de funcionamento dos Restaurantes
        universitários para 2018. O restaurante funcionará no período de recesso acadêmico (26/02 a 09/03),apenas no período do almoço,
        no horário de 12 h as 13 h. Para facilitar demanda e saber o número de refeições a ser enviada neste período, deixarei na
        saída do ru, uma planilha, sendo assim, peço que assinem ou enviem e-mail para lelis.viviane@yahoo.com.br. Atencionamente,',
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);

        App\Notice::create([
        'user_id' => 1,
        'type'=>2,
        'subject' => 'Venda de Tickets do RU',
        'content' => 'Prezados usuários do Restaurante Universitário, Na próxima semana, a venda dos tickets acontecerá somente segunda(26/02),
        terça(27/02) e quinta(01/03), de 12h às 13h. Atenciosamente,',
        'created_at' => date('Y-m-d H:i:s'),
        'updated_at' => date('Y-m-d H:i:s'),
        ]);

        // for ($i=0; $i < 20 ; $i++) { 
        // App\Notice::create([
        // 'user_id' => rand(1,21),
        // 'type'=>rand(0,2),
        // 'subject' => str_random(12),
        // 'content' => str_random(120),
        // 'created_at' => date('Y-m-d H:i:s'),
        // 'updated_at' => date('Y-m-d H:i:s'),
        // ]);
        // }
     
    }
}