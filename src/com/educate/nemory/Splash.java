package com.educate.nemory;

import com.educate.nemory.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Splash extends Activity {

	private ImageView ivSplashImage;
	private Intent mainMenuIntent;
	
	private Database db = new Database(this);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("Educate My Baby");
		
		// FULLSCREEN THE APP
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		
		 ivSplashImage = (ImageView) findViewById(R.id.ivSplashImage);
		 mainMenuIntent = new Intent(this, Main.class);
		 
		 InitializeQuestions();
		//setTimer();
		//setUpProgressBar();
		 startActivity(new Intent(this, Main.class));
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		finish();
		//progressBar.setVisibility(View.GONE);
	}
	
	int progress = 0;
	
	@SuppressWarnings("unused")
	private void setUpProgressBar() {
		int seconds = 12000, interval = 3000;
		new CountDownTimer(seconds, interval) {
			@Override
			public void onTick(long millisUntilFinished) {
				
			}
			@Override
			public void onFinish() {}
		}.start(); // start timer for 5 seconds
	}

	int tick = 0;
	
	@SuppressWarnings("unused")
	private void setTimer(){
		int seconds = 12000, interval = 3000;
		new CountDownTimer(seconds, interval) {
			@Override
			public void onTick(long millisUntilFinished) {
				// for every 3 seconds change splash image
				tick++;
				switch (tick) {
				
				case 1: // 1st second
					ivSplashImage.setImageResource(R.drawable.splash1);
					break;
				case 2: // 4th second
					ivSplashImage.setImageResource(R.drawable.splash2);
					break;
				case 3: // 7th second
					ivSplashImage.setImageResource(R.drawable.splash3);
					break;
				}
			}

			@Override
			public void onFinish() {
				startActivity(mainMenuIntent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				finish(); // kill activity
			}
		}.start(); // start timer for 5 seconds
	}
	
	private void InitializeQuestions(){
		db.open(); // ALWAYS OPEN BEFORE USING DATABASE METHODS
		db.DropTable(Constants.TABLE_QUESTIONS);
		db.CreateTables();
		
		
		final int sec = 20000;
		
		final int  c_ip = Constants.CATEGORY_IMAGE_PINPOINTING;
		final int  c_si = Constants.CATEGORY_SOUND_IDENTIFICATION;
		final int  c_ii = Constants.CATEGORY_IMAGE_IDENTIFICATION;
		
		final int  sc_b = Constants.SUB_CAT_BODY;
		final int  sc_a = Constants.SUB_CAT_ANIMALS;
		final int  sc_s = Constants.SUB_CAT_SHAPES;
		final int  sc_m = Constants.SUB_CAT_MATHEMATICS;
		final int  sc_o = Constants.SUB_CAT_OBJECTS;
		final int  sc_c = Constants.SUB_CAT_CARTOONS;
		final int  sc_co = Constants.SUB_CAT_COLORS;
		final int  sc_n= Constants.SUB_CAT_NURSERY;
		final int  sc_oi= Constants.SUB_CAT_OBJECTS_INSTRUMENTS;
		
		/**-----------------------------------------------------------------------------------------**/
		
		// CARTOONS
		db.AddQuestion("This is a mythical creature and is a half human and half fish, and she lives under the water.", "Mermaid", "Mermaid", "Fairy", "Princess", "c_mermaid.jpg", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("She is a disney princess, and she has 2 step sisters.", "Cinderella", "Snow White", "Cinderella", "Pocahontas", "c_cinderella.gif", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("She likes to go on adventures and explore many places., and her best friend is a monkey named Boots", "Dora", "Dora", "Sponge Bob", "Diego", "c_dora.gif", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("He is adetective, and he his assistant is Robin", "Batman", "Mermaid", "Batman", "Princess", "c_batman.gif", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("He owns the omnitrix and he transforms into an alien", "Ben 10", "Snow White", "Ben 10", "Pocahontas", "c_ben10.gif", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("He is a rabbit and he loves to eat carrots  ", "Bugs Bunny", "Dora", "Sponge Bob", "Bugs Bunny", "c_bugs_bunny.gif", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("He is a dog and he always wears a turtle neck and a vest with white gloves.", "Goofy", "Goofy", "Fairy", "Princess", "c_goofy.gif", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("He is a white duck with yellow orange bill legs and feet and his partner is Daisy Duck", "Donald Duck", "Snow White", "Donald Duck", "Pocahontas", "c_donald_duck.gif", "sound.mp3", 10, c_ii, sc_c, sec);
		db.AddQuestion("He is a mouse. and his partner is minnie mouse", "Mickey Mouse", "Dora", "Sponge Bob", "Mickey Mouse", "c_mickey_mouse.png", "sound.mp3", 10, c_ii, sc_c, sec);

		
		// ANIMALS
		db.AddQuestion("This animal can swim, and lives in the water.", "Fish", "Pig", "Chicken", "Fish", "a_fish.jpg", "sound.mp3", 10, c_ii, sc_a, sec);
		db.AddQuestion("This animal  love's to climb a tree and eat banana.", "Monkey", "Monkey", "Lion", "Tiger", "a_gorilla.jpg", "sound.mp3", 10, c_ii, sc_a, sec);
		db.AddQuestion("This animal is a mammal, it eats meat and has a thick fur.", "Bear", "Lion", "Bear", "Tiger", "a_grizzly_bear.jpg", "sound.mp3", 10, c_ii, sc_a, sec);
		db.AddQuestion("This animal is the king of the jungle.", "Lion", "Lion", "Tiger", "Dog", "a_lion.jpg", "sound.mp3", 10, c_ii, sc_a, sec);
		db.AddQuestion("This animal looks like a big cat, and it has black stripes with orange fur.", "Tiger", "Chicken", "Tiger", "Cat", "a_tiger.jpg", "sound.mp3", 10, c_ii, sc_a, sec);
		db.AddQuestion("This animal can fly, and has 2 wings.", "Bird", "Cat", "Dog", "Bird", "a_bird.jpg", "sound.mp3", 10, c_ii, sc_a, sec);
		/**-----------------------------------------------------------------------------------------**/
		
		// xMiddle = 280, yMiddle = 200, xTo = 550, yTo = 450

		final String square1 = "0,280,0,200";
		final String square2 = "280,550,0,200";
		final String square3 = "0,280,200,450";
		final String square4 = "280,550,200,450";
		
		/**-----------------------------------------GRID BODY------------------------------------------------**/

		db.AddQuestion("Pinpoint the Ear", square1, null, null, null, "b_grid1.jpg", "ear.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Foot", square2, null, null, null, "b_grid1.jpg", "foot.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Eyes", square3, null, null, null, "b_grid1.jpg", "eyes.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Arm", square4, null, null, null, "b_grid1.jpg", "arm.mp3", 10, c_ip, sc_b, sec);

		db.AddQuestion("Pinpoint the Thomb", square1, null, null, null, "b_grid2.jpg", "ear.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Tongue", square2, null, null, null, "b_grid2.jpg", "foot.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Head", square3, null, null, null, "b_grid2.jpg", "eyes.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Nose", square4, null, null, null, "b_grid2.jpg", "arm.mp3", 10, c_ip, sc_b, sec);

		db.AddQuestion("Pinpoint the Mouth", square1, null, null, null, "b_grid3.jpg", "ear.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Head", square2, null, null, null, "b_grid3.jpg", "foot.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Tongue", square3, null, null, null, "b_grid3.jpg", "eyes.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Hands", square4, null, null, null, "b_grid3.jpg", "arm.mp3", 10, c_ip, sc_b, sec);
		
		db.AddQuestion("Pinpoint the Mouth", square1, null, null, null, "b_grid4.jpg", "ear.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Foot", square2, null, null, null, "b_grid4.jpg", "foot.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Ear", square3, null, null, null, "b_grid4.jpg", "eyes.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Eyes", square4, null, null, null, "b_grid4.jpg", "arm.mp3", 10, c_ip, sc_b, sec);

		db.AddQuestion("Pinpoint the Nose", square1, null, null, null, "b_grid5.jpg", "ear.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Mouth", square2, null, null, null, "b_grid5.jpg", "foot.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Teeth", square3, null, null, null, "b_grid5.jpg", "eyes.mp3", 10, c_ip, sc_b, sec);
		db.AddQuestion("Pinpoint the Arm", square4, null, null, null, "b_grid5.jpg", "arm.mp3", 10, c_ip, sc_b, sec);
		
		/**-----------------------------------------GRID OBJECT------------------------------------------------**/

		db.AddQuestion("Pinpoint the Table", square1, null, null, null, "o_grid1.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Teddy Bear", square2, null, null, null, "o_grid1.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Umbrella", square3, null, null, null, "o_grid1.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Computer", square4, null, null, null, "o_grid1.gif", "arm.mp3", 10, c_ip, sc_o, sec);

		db.AddQuestion("Pinpoint the Magnifying Glass", square1, null, null, null, "o_grid2.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Chair", square2, null, null, null, "o_grid2.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Hat", square3, null, null, null, "o_grid2.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Table", square4, null, null, null, "o_grid2.gif", "arm.mp3", 10, c_ip, sc_o, sec);

		db.AddQuestion("Pinpoint the Trophy", square1, null, null, null, "o_grid3.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Computer", square2, null, null, null, "o_grid3.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Soccer Ball", square3, null, null, null, "o_grid3.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Bag", square4, null, null, null, "o_grid3.gif", "arm.mp3", 10, c_ip, sc_o, sec);
		
		db.AddQuestion("Pinpoint the Bag", square1, null, null, null, "o_grid4.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Umbrella", square2, null, null, null, "o_grid4.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Funnel", square3, null, null, null, "o_grid4.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Teddy Bear", square4, null, null, null, "o_grid4.gif", "arm.mp3", 10, c_ip, sc_o, sec);

		db.AddQuestion("Pinpoint the Funnel", square1, null, null, null, "o_grid5.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Ring", square2, null, null, null, "o_grid5.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Pencil", square3, null, null, null, "o_grid5.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Eye Glasses", square4, null, null, null, "o_grid5.gif", "arm.mp3", 10, c_ip, sc_o, sec);

		db.AddQuestion("Pinpoint the Hat", square1, null, null, null, "o_grid6.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Balloons", square2, null, null, null, "o_grid6.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Car", square3, null, null, null, "o_grid6.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Books", square4, null, null, null, "o_grid6.gif", "arm.mp3", 10, c_ip, sc_o, sec);

		db.AddQuestion("Pinpoint the Shoes", square1, null, null, null, "o_grid7.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Chair", square2, null, null, null, "o_grid7.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Bulb", square3, null, null, null, "o_grid7.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Car", square4, null, null, null, "o_grid7.gif", "arm.mp3", 10, c_ip, sc_o, sec);

		db.AddQuestion("Pinpoint the Bulb", square1, null, null, null, "o_grid8.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Pencil", square2, null, null, null, "o_grid8.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Balloons", square3, null, null, null, "o_grid8.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Computer", square4, null, null, null, "o_grid8.gif", "arm.mp3", 10, c_ip, sc_o, sec);
		
		db.AddQuestion("Pinpoint the Chair", square1, null, null, null, "o_grid9.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Trophy", square2, null, null, null, "o_grid9.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Books", square3, null, null, null, "o_grid9.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Microphone", square4, null, null, null, "o_grid9.gif", "arm.mp3", 10, c_ip, sc_o, sec);

		db.AddQuestion("Pinpoint the Soccer Ball", square1, null, null, null, "o_grid10.gif", "ear.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Hat", square2, null, null, null, "o_grid10.gif", "foot.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Phone", square3, null, null, null, "o_grid10.gif", "eyes.mp3", 10, c_ip, sc_o, sec);
		db.AddQuestion("Pinpoint the Shoes", square4, null, null, null, "o_grid10.gif", "arm.mp3", 10, c_ip, sc_o, sec);
		
		/**---------------------------------------------GRID SHAPES--------------------------------------------**/
		
		db.AddQuestion("Pinpoint the Circle", square1, null, null, null, "s_grid1.gif", "ear.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Diamond", square2, null, null, null, "s_grid1.gif", "foot.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Pentagon", square3, null, null, null, "s_grid1.gif", "eyes.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Heart", square4, null, null, null, "s_grid1.gif", "arm.mp3", 10, c_ip, sc_s, sec);
		
		db.AddQuestion("Pinpoint the Recangle", square1, null, null, null, "s_grid2.gif", "ear.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Star", square2, null, null, null, "s_grid2.gif", "foot.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Oblong", square3, null, null, null, "s_grid2.gif", "eyes.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Square", square4, null, null, null, "s_grid2.gif", "arm.mp3", 10, c_ip, sc_s, sec);
		
		db.AddQuestion("Pinpoint the Diamond", square1, null, null, null, "s_grid3.gif", "ear.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Heart", square2, null, null, null, "s_grid3.gif", "foot.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Triangle", square3, null, null, null, "s_grid3.gif", "eyes.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Oblong", square4, null, null, null, "s_grid3.gif", "arm.mp3", 10, c_ip, sc_s, sec);
		
		db.AddQuestion("Pinpoint the Circle", square1, null, null, null, "s_grid4.gif", "ear.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Triangle", square2, null, null, null, "s_grid4.gif", "foot.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Star", square3, null, null, null, "s_grid4.gif", "eyes.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Rectangle", square4, null, null, null, "s_grid4.gif", "arm.mp3", 10, c_ip, sc_s, sec);
		
		db.AddQuestion("Pinpoint the Heart", square1, null, null, null, "s_grid5.gif", "ear.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Square", square2, null, null, null, "s_grid5.gif", "foot.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Star", square3, null, null, null, "s_grid5.gif", "eyes.mp3", 10, c_ip, sc_s, sec);
		db.AddQuestion("Pinpoint the Pentagon", square4, null, null, null, "s_grid5.gif", "arm.mp3", 10, c_ip, sc_s, sec);
		
		/**---------------------------------------------SOUNDS--------------------------------------------**/
		
		db.AddQuestion("Listen and identify what sound is being produced.", "Bird", "Dog", "Cat", "Bird", null, "bird.mp3", 10, c_si, sc_a, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Dog", "Horse", "Cat", "Dog", null, "dog.mp3", 10, c_si, sc_a, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Horse", "Dog", "Horse", "Cat", null, "horse.mp3", 10, c_si, sc_a, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Frog", "Dog", "Cat", "Frog", null, "frog.mp3", 10, c_si, sc_a, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Pig", "Pig", "Frog", "Chicken", null, "pig.mp3", 10, c_si, sc_a, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Monkey", "Dog", "Monkey", "Pig", null, "monkey.mp3", 10, c_si, sc_a, sec);
		
		db.AddQuestion("Listen and identify what sound is being produced.", "Ambulance", "Airplane", "Motorcycle", "Ambulance", null, "ambulance.mp3", 10, c_si, sc_oi, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Baby Crying", "Dog Barking", "Old man laughing", "Baby Crying", null, "baby_cry.mp3", 10, c_si, sc_oi, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Clapping Audience", "Shouting People", "Crying People", "Clapping Audience", null, "clapping_audience.mp3", 10, c_si, sc_oi, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Drums", "Violin", "Drums", "Flute", null, "drum.mp3", 10, c_si, sc_oi, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Flute", "Flute", "Drums", "Violin", null, "flute.mp3", 10, c_si, sc_oi, sec);
		db.AddQuestion("Listen and identify what sound is being produced.", "Guitar", "Drums", "Flute", "Guitar", null, "guitar.mp3", 10, c_si, sc_oi, sec);
		
		db.AddQuestion("Listen and identify the title of the song.", "London bridge is falling down", "London bridge is falling down", "Mary had a little lamb", "Row row row your Boat", null, "n_london.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Humpty Dumpty", "Rain rain go away", "Humpty Dumpty", "Jack n Jill",null, "n_humptydumpty.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Mary had a little lamb", "Mary had a little lamb", "London bridge is falling down", "Jack n Jill", null, "n_mary.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "twinkle twinkle little star", "Mary had a little lamb", "Itsy Bitsy Spider", "twinkle twinkle little star", null, "n_twinkle.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Row row row your Boat", "twinkle twinkle little star", "Row row row your Boat", "Old McDonald Had A Farm", null, "n_rowyourboat.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Rain rain go away", "Ten Little Indains", "Itsy Bitsy Spider", "Rain rain go away", null, "guitar.mp3", 10, c_si, 0, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Ten Little Indains", "Ten Little Indains", "Old McDonald Had A Farm", "twinkle twinkle little star", null, "n_10littleindians.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Jack n Jill", "Row row row your Boat", "Ten Little Indains", "Jack n Jill", null, "n_jackandjill.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Old McDonald Had A Farm", "twinkle twinkle little star", "Old McDonald Had A Farm", "London bridge is falling down", null, "n_oldmacdonald.mp3", 10, c_si, sc_n, 40000);
		db.AddQuestion("Listen and identify the title of the song.", "Itsy Bitsy Spider", "Itsy Bitsy Spider", "twinkle twinkle little star", "Ten Little Indains", null, "n_eencyweencyspider.mp3", 10, c_si, sc_n, 40000);
		
		/**-----------------------------------------------SHAPES------------------------------------------**/
		
		db.AddQuestion("Identify the shape name of the shape.", "Circle", "Heart", "Rectangle", "Circle", "s_circle.gif", "a_bird.mp3", 10, c_ii, sc_s, sec);
		db.AddQuestion("Identify the shape name of the shape.", "Diamond", "Circle", "Diamond", "Heart", "s_diamond.gif", "a_dog.mp3", 10, c_ii, sc_s, sec);
		db.AddQuestion("Identify the shape name of the shape.", "Heart", "Heart", "Pentagon", "Diamond", "s_heart.gif", "a_horse.mp3", 10, c_ii, sc_s, sec);
		db.AddQuestion("Identify the shape name of the shape.", "Square", "Diamond", "Circle", "Square", "s_square.gif", "a_frog.mp3", 10, c_ii, sc_s, sec);
		db.AddQuestion("Identify the shape name of the shape.", "Rectangle", "Square", "Rectangle", "Pentagon", "s_rectangle.gif", "a_pig.mp3", 10, c_ii, sc_s, sec);
		db.AddQuestion("Identify the shape name of the shape.", "Pentagon", "Pentagon", "Rectangle", "Square", "s_pentagon.gif", "a_monkey.mp3", 10, c_ii, sc_s, sec);
		
		/**------------------------------------------------MATH-----------------------------------------**/
		
		db.AddQuestion("Count the objects in the image given.", "Two", "Three", "Two", "Four", "math1.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("Count the objects in the image given.", "Four", "Five", "Six", "Four", "math2.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("Count the objects in the image given.", "Nine", "Six", "Nine", "Seven", "math3.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("Count the objects in the image given.", "Four", "Three", "Four", "Five", "math4.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("Count the objects in the image given.", "Five", "One", "Eight", "Five", "math5.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("Count the objects in the image given.", "Three", "Three", "Five", "Four", "math6.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("Count the objects in the image given.", "Nine", "Eight", "Nine", "Seven", "math7.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("Count the objects in the image given.", "Two", "Ten", "Two", "Four", "math8.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		db.AddQuestion("How many apples are in the image?", "Two", "Two", "Five", "Nine", "math9.png", "a_bird.mp3", 10, c_ii, sc_m, sec);
		
		/**------------------------------------------------COLORS-----------------------------------------**/
		
		db.AddQuestion("Identify the color of the image.", "Red", "Blue", "Red", "Black", "co_bag.gif", "a_bird.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Green", "Green", "Red", "Gray", "co_banana.gif", "a_dog.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Green", "Pink", "Orange", "Green", "co_basket.gif", "a_horse.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Red", "Yellow", "Red", "Violet", "co_bowl.gif", "a_frog.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Blue", "Blue", "White", "Brown", "co_butterfly.gif", "a_pig.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Pink", "Blue", "Black", "Pink", "co_cup.gif", "a_monkey.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Red", "Yellow", "Red", "Blue", "co_dice.gif", "a_dog.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Yellow", "Yellow", "Brown", "Orange", "co_duck.gif", "a_horse.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Violet", "Orange", "Violet", "White", "co_ribbon.gif", "a_frog.mp3", 10, c_ii, sc_co, sec);
		db.AddQuestion("Identify the color of the image.", "Yellow", "Blue", "Red", "Yellow", "co_tomato.gif", "a_pig.mp3", 10, c_ii, sc_co, sec);
	
		db.CreateTables();
		db.close();

	}
}
