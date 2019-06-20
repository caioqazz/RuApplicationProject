<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->string('email','50');
            $table->timestamp('email_verified_at')->nullable();
            $table->string('cpf','15');
            $table->string('photo')->default('');
            $table->tinyInteger('type')->default(1);
            $table->string('password');
            $table->rememberToken();
            $table->timestamps();
            $table->unique(array('email', 'cpf'));
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}