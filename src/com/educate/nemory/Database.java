package com.educate.nemory;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends Constants{
	
	// USED TO DETERMINE IN WHAT CLASS WE ARE USING THIS DATABASE CLASS
	private final Context context; 
	
	// FOR BUILT IN DATABASE METHODS
	private databaseHelper dbHelper;
	
	// THE DATABASE OBJECT ITSELF
	private SQLiteDatabase db;
	
	private class databaseHelper extends SQLiteOpenHelper{
	
		public databaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		// CREATE TABLES IF IT DOES NOT EXIST ONLY
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			// QUESTIONS TABLE
			db.execSQL("CREATE TABLE IF NOT EXISTS " + 
	        		TABLE_QUESTIONS + " (" 			+ 
	        		COLUMN_QUESTION_ID   			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	        		COLUMN_QUESTION		 			+ " TEXT NOT NULL, " 	 + 
	        		COLUMN_QUESTION_CATEGORY 	  + " INTEGER NOT NULL, " + 
	        		COLUMN_QUESTION_SUB_CATEGORY  + " INTEGER NOT NULL, " + 
	        		COLUMN_QUESTION_STATUS 		  + " INTEGER NOT NULL, " + 
	        		COLUMN_QUESTION_ANSWER 		  + " TEXT NOT NULL, " 	  +
	        		COLUMN_QUESTION_A 			  + " TEXT, " 	 		  + 
	        		COLUMN_QUESTION_B 			  + " TEXT, " 	 		  + 
	        		COLUMN_QUESTION_C 			  + " TEXT, " 	 		  +
	        		COLUMN_QUESTION_IMAGE 		  + " TEXT, " 	 		  +
	        		COLUMN_QUESTION_SOUND 		  + " TEXT, " 	 		  +
	        		COLUMN_QUESTION_SCORE_POINTS  + " INTEGER NOT NULL, " +
	        		COLUMN_QUESTION_CORRECTED  	  + " INTEGER, " +
	        		COLUMN_QUESTION_TIMER_SECS    + " INTEGER NOT NULL);");
			
			// PLAYERS TABLE
			db.execSQL("CREATE TABLE IF NOT EXISTS "+ 
	        		TABLE_PLAYERS + " (" + 
	        		COLUMN_PLAYER_ID 	 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	        		COLUMN_PLAYER_NAME 	 + " TEXT, " + 
	        		COLUMN_PLAYER_AVATAR + " TEXT, " + 
	        		COLUMN_PLAYER_SCORE  + " INTEGER NOT NULL);");
			
			// SCORE BOARD TABLE
			db.execSQL("CREATE TABLE IF NOT EXISTS "	+ 
	        		TABLE_SCORE_BOARD + " (" 			+ 
	        		COLUMN_SCORE_BOARD_PLAYER_ID 	   	+ " INTEGER PRIMARY KEY, " +
	        		COLUMN_SCORE_BOARD_PLAYER_NAME 	   	+ " TEXT, " +
	        		COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE + " INTEGER NOT NULL);");
		}
		
		// CALL IF YOU WANT TO UPGRADE THE DATABASE
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE_BOARD);
			onCreate(db); // re-create database again
		}
	}
	
	public Database (Context c){
		context = c; // RETURN THE PASSED CONTEXT ON WHAT CLASS IT INSTANTIATED
	}
	
	// OPEN THE DATABASE CONNECTION
	public Database open(){
		dbHelper = new databaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return Database.this;
	}
	
	// CLOSE THE DATABASE CONNECTION
	public void close(){
		dbHelper.close();
	}
	
	// CREATE ALL TABLES
	public void CreateTables(){
		dbHelper.onCreate(db);
	}
	
	// DROP A SINGLE TABLE
	public void DropTable(String tableName){
		try{
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
		}catch (Exception e) {}
	}
	
	/**---------------------------------------------------QUESTIONS-----------------------------------------------------------**/
	
	// METHOD FOR ADDING QUESTIONS WITH ANSWERS
	public void AddQuestion(
			String question, String correctAnswer, String A, String B, String C, String imageFileName, String soundFile,
			int scorePoints, int category, int subCategory, int timerSecs){
		
		// STORAGE USED FOR INSERTING MULTIPLE DATA TO BE INSERTED IN THE DATABASE
		ContentValues content = new ContentValues();
		
		// FILL CONTENTS
		content.put(COLUMN_QUESTION, question);
		content.put(COLUMN_QUESTION_ANSWER, correctAnswer);
		content.put(COLUMN_QUESTION_SOUND, soundFile);
		content.put(COLUMN_QUESTION_A, A);
		content.put(COLUMN_QUESTION_B, B);
		content.put(COLUMN_QUESTION_C, C);
		content.put(COLUMN_QUESTION_IMAGE, imageFileName);
		content.put(COLUMN_QUESTION_SCORE_POINTS, scorePoints);
		content.put(COLUMN_QUESTION_STATUS, Constants.STATUS_UNANSWERED);
		content.put(COLUMN_QUESTION_CATEGORY, category);
		content.put(COLUMN_QUESTION_SUB_CATEGORY, subCategory);
		content.put(COLUMN_QUESTION_TIMER_SECS, timerSecs);
		
		// INSERT THE CONTENST TO THE TABLE_QUESTIONS
		db.insert(TABLE_QUESTIONS, null, content);
	}
	
	// METHOD FOR UPDATING QUESTIONS STATUS
	// 1 = Answered; 0 = Un-answered
	public void UpdateQuestionStatus(int questionID, int status, int corrected){
		ContentValues content = new ContentValues();
		content.put(COLUMN_QUESTION_STATUS, status);
		content.put(COLUMN_QUESTION_CORRECTED, corrected);
		
		//UPDATE Question and SET it's Status WHERE it's QuestionID = the passed ID in this method
		db.update(TABLE_QUESTIONS, content, COLUMN_QUESTION_ID + "=" + questionID, null);
	}
	
	public String[] GetQuestionsData(int category, int subCategory){		
		String[] whereArgs = {Integer.toString(category), Integer.toString(subCategory), 
				Integer.toString(STATUS_UNANSWERED)};

		String columns[] = {COLUMN_QUESTION, COLUMN_QUESTION_ANSWER, COLUMN_QUESTION_A, COLUMN_QUESTION_B, COLUMN_QUESTION_C, 
				COLUMN_QUESTION_ID, COLUMN_QUESTION_SCORE_POINTS, COLUMN_QUESTION_TIMER_SECS, COLUMN_QUESTION_IMAGE, COLUMN_QUESTION_SOUND};

		Cursor cursor = db.query(TABLE_QUESTIONS, columns, 
				COLUMN_QUESTION_CATEGORY + "=? AND " + COLUMN_QUESTION_SUB_CATEGORY + "=? AND " + COLUMN_QUESTION_STATUS + "=?", 
				whereArgs, null, null, "RANDOM()");

		String questionData[] = new String[10];
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			questionData[0] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION));
			questionData[1] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_A));
			questionData[2] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_B));
			questionData[3] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_C));
			questionData[4] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_ANSWER));
			questionData[5] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_ID));
			questionData[6] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_SCORE_POINTS));
			questionData[7] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_TIMER_SECS));
			questionData[8] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_IMAGE));
			questionData[9] = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_SOUND));
			cursor.close();
			return questionData;
		}
		cursor.close();
		return null;
	}

	public int GetAnsweredQuestionsCount(int category, int subCategory){
		String query = "SELECT COUNT(" + COLUMN_QUESTION + ") AS AnsweredQuestionsCount FROM " +
				TABLE_QUESTIONS + " WHERE " + COLUMN_QUESTION_CATEGORY + "=" + category + " AND " +
				COLUMN_QUESTION_SUB_CATEGORY + "=" + subCategory + " AND " + COLUMN_QUESTION_STATUS + "=" + STATUS_ANSWERED;
		
	    Cursor cursor = db.rawQuery(query, null);
	    
	    cursor.moveToFirst();
		int answeredQuestionsCount = cursor.getInt(cursor.getColumnIndex("AnsweredQuestionsCount"));
		cursor.close();
		return answeredQuestionsCount;
	}
	
	public int GetCorrectlyAnsweredCount(int category, int subCategory){
		String query = "SELECT COUNT(" + COLUMN_QUESTION + ") AS AnsweredQuestionsCount FROM " +
				TABLE_QUESTIONS + " WHERE " + COLUMN_QUESTION_CATEGORY + "=" + category + " AND " +
				COLUMN_QUESTION_SUB_CATEGORY + "=" + subCategory + " AND " + COLUMN_QUESTION_STATUS + "=" + STATUS_ANSWERED  + " AND " + COLUMN_QUESTION_CORRECTED + "=" + CORRECTED;
		
	    Cursor cursor = db.rawQuery(query, null);
	    
	    cursor.moveToFirst();
		int answeredQuestionsCount = cursor.getInt(cursor.getColumnIndex("AnsweredQuestionsCount"));
		cursor.close();
		return answeredQuestionsCount;
	}
	
	public int GetQuestionsCount(int category, int subCategory){
		String query = "SELECT COUNT(" + COLUMN_QUESTION + ") AS QuestionsCount FROM " +
				TABLE_QUESTIONS + " WHERE " + COLUMN_QUESTION_CATEGORY + "=" + category + " AND " +
				COLUMN_QUESTION_SUB_CATEGORY + "=" + subCategory;
		
	    Cursor cursor = db.rawQuery(query, null);
	    
	    cursor.moveToFirst();
		int questionsCount = cursor.getInt(cursor.getColumnIndex("QuestionsCount"));
		cursor.close();
		return questionsCount;
	}
	
	// METHOD FOR UPATING THE PLAYER'S SCORE
	public void ResetQuestions(){
		ContentValues content = new ContentValues();
		// SET ALL QUESTIONS STATUS SCORE TO 0
		content.put(COLUMN_QUESTION_STATUS, STATUS_UNANSWERED);
		db.update(TABLE_QUESTIONS, content, null, null);
	}
	
	/**---------------------------------------------------PLAYERS-----------------------------------------------------------**/
	
	//METHOD FOR ADDING A NEW PLAYER
	public void AddPlayer(String playerName){
		ContentValues content = new ContentValues();
		content.put(COLUMN_PLAYER_NAME, playerName);
		content.put(COLUMN_PLAYER_SCORE, 0);
		db.insert(TABLE_PLAYERS, null, content);
	}
	
	// METHOD FOR DELETING A PLAYER
	public void DeletePlayer(int playerID){
		// DELETE THE PLAYER IF THE playerID that was passed is equal to the PlayerID in the Database
		db.delete(TABLE_PLAYERS, COLUMN_PLAYER_ID + "=" + playerID, null);
		db.delete(TABLE_SCORE_BOARD, COLUMN_SCORE_BOARD_PLAYER_ID + "=" + playerID, null);
	}
	
	// METHOD FOR DELETING ALL PLAYERS
	public void DeleteAllPlayers(){
		// DELETE THE PLAYER IF THE playerID that was passed is equal to the PlayerID in the Database
		db.delete(TABLE_PLAYERS, null, null);
		db.delete(TABLE_SCORE_BOARD, null, null);
	}
	
	// METHOD FOR UPATING THE PLAYER'S SCORE
	public void UpdatePlayerScore(int playerID, int scorePoints, int previousScore){
		ContentValues content = new ContentValues();
		// SET PLAYER'S SCORE = THE PREVIOUS SCORE + THE NEW SCORE THAT WAS PASSED
		content.put(COLUMN_PLAYER_SCORE, (previousScore + scorePoints));
		db.update(TABLE_PLAYERS, content, COLUMN_PLAYER_ID + "=" + playerID , null);
	}
	
	// METHOD FOR CHECKING IF THE PLAYER EXISTS
	public boolean thisPlayerExists(String playerName){
		String playerColumns[] = {COLUMN_PLAYER_NAME};
		// PREVENT TO ADD PLAYER WITH THE SAME NAME.
		Cursor cursor = db.query(TABLE_PLAYERS, playerColumns, "LOWER(" + COLUMN_PLAYER_NAME + ") = LOWER('" + playerName + "')", null, null, null, null);
		
		if(cursor.getCount() > 0){ // IF CURSOR RETURNED 1 : THE PLAYER EXISTS
			cursor.close();
			return PLAYER_EXIST;
		}else{ // IF CURSOR RETURNED 0 : THE PLAYER DOES NOT EXISTS
			cursor.close();
			return PLAYER_DOES_NOT_EXIST;
		}
	}
	
	// METHOD FOR GETTING THE PLAYER NAME BY USING IT's PlayerID
	public String GetPlayerName(int playerID){
		String playerColumns[] = {COLUMN_PLAYER_NAME};
		Cursor cursor = db.query(TABLE_PLAYERS, playerColumns, COLUMN_PLAYER_ID + "=" + playerID, null, null, null, null);
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			String playerName = cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_NAME));
			cursor.close();
			return playerName;
			
		}else{
			cursor.close();
			return null;
		}
	}
	
	// METHOD FOR GETTING THE PLAYER ID BY USING IT's PlayerName
	public int GetPlayerIDByName(CharSequence playerName){ // CharSequence is the same as string
		String playerColumns[] = {COLUMN_PLAYER_ID};
		Cursor cursor = db.query(TABLE_PLAYERS, playerColumns, COLUMN_PLAYER_NAME + "='" + playerName + "' ", null, null, null, null);
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			int playerID = cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_ID));
			cursor.close();
			return playerID;
		}else{
			cursor.close();
			return 0;
		}
	}
	
	public ArrayList<String> GetPlayers(){
		String playerColumns[] = {COLUMN_PLAYER_NAME};
		Cursor cursor = db.query(TABLE_PLAYERS, playerColumns, null, null, null, null, null);
		
		ArrayList<String> playerNames = new ArrayList<String>();

		if(cursor.getCount() > 0){
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
				playerNames.add(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_NAME)));
			}
			cursor.close();
			return playerNames;
		}else{
			cursor.close();
			return null;
		}
	}
	
	public int GetPlayerScore(int playerID){
		String columns[] = {COLUMN_PLAYER_SCORE};
		Cursor cursor = db.query(TABLE_PLAYERS, columns, COLUMN_PLAYER_ID + "=" + playerID, null, null, null, null);
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			int playerScore = cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_SCORE));
			cursor.close();
			return playerScore;
		}else{
			cursor.close();
			return 0;
		}
	}
	
	public int GetPlayersCount(){
		String playerColumns[] = {COLUMN_PLAYER_ID};
		Cursor cursor = db.query(TABLE_PLAYERS, playerColumns, null, null, null, null, null);
		int playersCount = cursor.getCount();
		cursor.close();
		return playersCount;
	}
	
	// METHOD FOR UPATING THE PLAYER'S SCORE
	public void ResetPlayersScore(){
		ContentValues content = new ContentValues();
		// SET ALL PLAYER'S SCORE TO 0
		content.put(COLUMN_PLAYER_SCORE, 0);
		db.update(TABLE_PLAYERS, content, null, null);
	}

	/**---------------------------------------------------SCORE BOARD-----------------------------------------------------------**/
	
	// METHOD FOR CHECKING IF THE PLAYER EXISTS
	public boolean IsPlayerInScoreBoard(int playerID){
		String playerColumns[] = {COLUMN_SCORE_BOARD_PLAYER_ID};
		Cursor cursor = db.query(TABLE_SCORE_BOARD, playerColumns, COLUMN_SCORE_BOARD_PLAYER_ID + "=" + playerID , null, null, null, null);
		if(cursor.getCount() > 0){ 
			cursor.close();
			return PLAYER_EXIST;
		}else{ 
			cursor.close();
			return PLAYER_DOES_NOT_EXIST;
		}
	}
	
	//METHOD FOR ADDING A NEW PLAYER IN SCORE BOARD
	public void AddPlayerInScoreBoard(int playerID, String playerName, int playerScore){
		ContentValues content = new ContentValues();
		content.put(COLUMN_SCORE_BOARD_PLAYER_ID, playerID);
		content.put(COLUMN_SCORE_BOARD_PLAYER_NAME, playerName);
		content.put(COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE, playerScore);
		db.insert(TABLE_SCORE_BOARD, null, content);
	}
	
	// METHOD FOR UPDATING A PLAYER'S INFO
	public void UpdatePlayerScoreInScoreBoard(int playerID, int newHighScore){
		ContentValues content = new ContentValues();
		content.put(COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE, newHighScore);
		//UPDATE The Player SET it's name and gender WHERE it's PlayerID = the passed ID in this method
		db.update(TABLE_SCORE_BOARD, content, COLUMN_PLAYER_ID + "=" + playerID , null);
	}
	
	public int GetPlayerHighScore(int playerID){
		String columns[] = {COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE};
		
		Cursor cursor = db.query(TABLE_SCORE_BOARD, columns, 
				COLUMN_SCORE_BOARD_PLAYER_ID + "=" + playerID, null, null, null, null, null);
		
		int highScore = 0;
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			highScore = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE));
		}
		cursor.close();
		return highScore;
	}
	
	public int GetHighestScore(){
		String query = "SELECT MAX(" + COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE + ") FROM " + TABLE_SCORE_BOARD;
	    Cursor cursor = db.rawQuery(query, null);

		cursor.moveToFirst();
		int highScore = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE));
		cursor.close();
		return highScore;
	}
	
	public Cursor GetPlayersInScoreBoard(){
		String columns[] = {COLUMN_SCORE_BOARD_PLAYER_ID, COLUMN_SCORE_BOARD_PLAYER_NAME, COLUMN_SCORE_BOARD_PLAYER_HIGHSCORE};
		Cursor cursor = db.query(TABLE_SCORE_BOARD, columns, null, null, null, null, null, null);

		if(cursor.getCount() > 0){
			return cursor;
		}else{
			cursor.close();
			return null;
		}
	}
}
