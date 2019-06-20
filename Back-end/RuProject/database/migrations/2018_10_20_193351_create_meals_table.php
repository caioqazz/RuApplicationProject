<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateMealsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::disableForeignKeyConstraints();
         Schema::create('meals', function (Blueprint $table) {
            $table->increments('id');
            $table->boolean('status')->default(TRUE); // 0 - Aberto / 1-Fechado
            $table->string('reason')->default('');
            $table->date('date');           
            $table->unsignedInteger('schedule_id');
            $table->foreign('schedule_id')->references('id')->on('schedules')->onDelete('cascade');;
            $table->unique(array('schedule_id','date'));
            $table->timestamps();
        });
        Schema::enableForeignKeyConstraints();
       
        DB::statement('ALTER TABLE `ru`.`meals` DROP PRIMARY KEY,
         ADD PRIMARY KEY ( `id`,`schedule_id`,`date`)
        USING BTREE');
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('meals');
    }
}