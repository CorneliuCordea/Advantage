#include "pebble.h"
static Window *window;
static TextLayer *up_text_layer;
static TextLayer *up_text_layer_option;
static TextLayer *select_text_layer;
static TextLayer *down_text_layer;
static TextLayer *down_text_layer_option;
static TextLayer *game_score_text_layer;
static TextLayer *set1_score_text_layer;
static TextLayer *set2_score_text_layer;
static TextLayer *set3_score_text_layer;
static TextLayer *player_serving_text_layer;
static TextLayer *match_type_text_layer;

#define pebble_user_points_KEY 1
#define opponent_points_KEY 2
#define game_type_KEY 3
#define serving_KEY 4
#define currentPointWon_KEY 5
#define match_type_KEY 6
#define pebble_user_sets_KEY 7
#define opponent_sets_KEY 8
#define set1_text_KEY 9
#define set2_text_KEY 10
#define set3_text_KEY 11
#define current_set_KEY 12
#define pebble_user_games_KEY 13
#define opponent_games_KEY 14
#define page_KEY 15

//bit masks
//10=Unforced;9=Unforced;8=AtNet;7=Ace;6=Double;5=Win;4=FirstServe;3=Forced;2=Winner;1=Hand;0=Serving;
int const UnforcedPassiveOrMask = 512;
int const UnforcedActiveInopportuneOrMask = 1024;
int const UnforcedActiveOpportuneOrMask = 1536;
int const AtNetOrMask = 256;
int const EndGameOrMask = 1984;
int const AceOrMask = 128;
int const NotAceAndMask = 1919;
int const DoubleOrMask = 64;
int const NotDoubleAndMask = 1983;
int const WinOrMask = 32;
int const LoseAndMask = 2015;
int const FirstOrMask = 16;
int const SecondAndMask = 2031;
int const ForcedOrMask = 8;
int const UnforcedAndMask = 247;
int const WinnerOrMask = 4;
int const NotWinnerAndMask = 2043;
int const ForehandOrMask = 2;
int const BackhandAndMask = 2045;
int const PebbleUserServingOrMask = 1;
int const OpponentServingAndMask = 2046;

// static int ace=0;
// static int doubleFault=0;
// static int win=0;
// static int firstServe=0;
// static int forced=0;
// static int winner=0;
// static int forehand=0;
//variable use to construct point
static int returnPoint=0;
static int persist=1;
static int pebble_user_points=0;
static int opponent_points=0;
//0=Normal()15-15 1=Tie break
static int game_type=0;

//0=PebbleUserServing 1=Opponent Serving
static int serving=0;
static int servingFirstInTieBreak=0;

//0=PebbleUserWonPoint 1=Opponent WonPoint
static int currentPointWon=1;

//0= 2 out of 3; 1=2 out of three wit SuperTieBreak
static int match_type=0;

static int pebble_user_sets=0;
static int opponent_sets=0;

static int pebble_user_games=0;
static int opponent_games=0;

static char set1_text[5]="0 - 0";
static char set2_text[5]="0 - 0";
static char set3_text[10]="0 - 0";

static int current_set=1;
//there are 4 pages see draw_page()
static int page=1;
static void resetPoint(){
	returnPoint=0;
	returnPoint=returnPoint|FirstOrMask;
	returnPoint=returnPoint&NotAceAndMask;
	returnPoint=returnPoint&NotDoubleAndMask;
	returnPoint=returnPoint&NotWinnerAndMask;
	if(serving==0)
	returnPoint=returnPoint|PebbleUserServingOrMask;
	else
	returnPoint=returnPoint&OpponentServingAndMask;
}
static void draw_page(int page){
  if(page==1){    
		text_layer_set_text(up_text_layer, "Lose");
		text_layer_set_text(select_text_layer, "FO|Ace");
		text_layer_set_text(down_text_layer, "Win");
	}else  
	if(page==2){    
		text_layer_set_text(up_text_layer, "Lose");
		text_layer_set_text(select_text_layer, "SO|Ace");
		text_layer_set_text(down_text_layer, "Win");
	}else
	if(page==3){
		text_layer_set_text(up_text_layer, "Frcd");
		text_layer_set_text(select_text_layer, "    Wnnr");
		text_layer_set_text(down_text_layer, "UnFr");
	}else
	if(page==4){
		text_layer_set_text(up_text_layer, "A+");
		text_layer_set_text(select_text_layer, "    A-");
		text_layer_set_text(down_text_layer, " P");
	}else
	if(page==5){
		text_layer_set_text(up_text_layer, "FH");
		text_layer_set_text(select_text_layer, "");
		text_layer_set_text(down_text_layer, "BH");
	}
 
  
}
static void refresh_set_score(){
	switch( current_set ) 
	{
	case 1:
		snprintf(set1_text, sizeof(set1_text), "%d %d", pebble_user_games ,opponent_games);
		text_layer_set_text(set1_score_text_layer, set1_text);
		break;
	case 2:
		snprintf(set2_text, sizeof(set2_text), "%d %d", pebble_user_games ,opponent_games);
		text_layer_set_text(set2_score_text_layer, set2_text);
		break;
	case 3:
		snprintf(set3_text, sizeof(set3_text), "%d %d", pebble_user_games ,opponent_games);  
		text_layer_set_text(set3_score_text_layer, set3_text);  
		break;
	} 
}
void setCurrentGameScore(void)
{
	static char body_text[10];
	if(opponent_sets==2)
	snprintf(body_text, sizeof(body_text), "LOSE");
	else
	if(pebble_user_sets==2)
	snprintf(body_text, sizeof(body_text), "WIN");
	else
	if(pebble_user_points>=40 && opponent_points>=40){
		if(pebble_user_points==50){
			snprintf(body_text, sizeof(body_text), "AD-40");
		}
		else
		if(opponent_points==50){
			snprintf(body_text, sizeof(body_text), "40-AD");
		}
		else
		snprintf(body_text, sizeof(body_text), "40-40");
	}   
	else{
		snprintf(body_text, sizeof(body_text), "%d-%d", pebble_user_points ,opponent_points);
	}
	text_layer_set_text(game_score_text_layer, body_text);
}

static void changeServe(){
  
	if ( (match_type==1 && current_set==3 && (((pebble_user_games+opponent_games) % 2 ==1) || (pebble_user_games+opponent_games==0) ) )
			|| (game_type==1 && (((pebble_user_points+opponent_points) % 2 == 1) || (pebble_user_points+opponent_points==0)))
			|| (match_type==0 && game_type!=1) 
			|| (match_type==1 && current_set!=3))
	{
		if(serving==0){
			serving=1;
			text_layer_set_text(player_serving_text_layer, "Opponent");
		}
		else{
			text_layer_set_text(player_serving_text_layer, "PebbleUser");
			serving=0;
		}
	}
  
}
static void displayServing(){
	if(serving==1){
		text_layer_set_text(player_serving_text_layer, "Opponent");
	}
	else{
		text_layer_set_text(player_serving_text_layer, "PebbleUser");
	}
}


static void displayMatchType(){
	if(match_type==1){
		text_layer_set_text(match_type_text_layer, "2/3 ST");
	}
	else{
		text_layer_set_text(match_type_text_layer, "2/3");
	}
}

static void increment_pebble_user_games(){

	if(match_type==0 ||(match_type==1 && (current_set==1 || current_set==2)) ){
		pebble_user_games=pebble_user_games+1;
		refresh_set_score();
		if(pebble_user_games==6 && opponent_games==6 ){
			game_type=1;
      if(serving==1)
        servingFirstInTieBreak=0;
      else
        servingFirstInTieBreak=1;
		}
		if((pebble_user_games-opponent_games>=2 && pebble_user_games>=6)||(pebble_user_games==7 && opponent_games==6)){
			opponent_games=0;
			pebble_user_games=0;
			current_set=current_set+1;
			game_type=0;
			pebble_user_sets=pebble_user_sets+1;
      serving=servingFirstInTieBreak;
		}
	}else{
		if(pebble_user_games<10 ||(pebble_user_games>=10 && pebble_user_games-opponent_games<2)){
			pebble_user_games=pebble_user_games+1;
			refresh_set_score();
			if(pebble_user_games>=10 && pebble_user_games-opponent_games>=2){
				pebble_user_sets=pebble_user_sets+1;
			}
		} 
	}
	changeServe();

}
static void increment_opponent_games(){
	if(match_type==0 ||(match_type==1 && (current_set==1 || current_set==2))){
		opponent_games=opponent_games+1;
		refresh_set_score();
		if(pebble_user_games==6 && opponent_games==6 ){
			game_type=1;
      if(serving==1)
        servingFirstInTieBreak=0;
      else
        servingFirstInTieBreak=1;
		}
		if((opponent_games-pebble_user_games>=2 && opponent_games>=6)||(pebble_user_games==6 && opponent_games==7)){
			opponent_games=0;
			pebble_user_games=0;
			current_set=current_set+1;
			game_type=0;
			opponent_sets=opponent_sets+1;
      serving=servingFirstInTieBreak;
		}
	}else{
		if(opponent_games<10 ||(opponent_games>=10 && opponent_games-pebble_user_games<2)){
			opponent_games=opponent_games+1;
			refresh_set_score();
			if(opponent_games>=10 && opponent_games-pebble_user_games>=2){
				opponent_sets=opponent_sets+1;
				
			}
		}
	}
	changeServe();
}
static void increment_pebble_user_points(){
	returnPoint=returnPoint|WinOrMask;
	if (current_set==3 && match_type==1){
		if(opponent_sets==pebble_user_sets)
		increment_pebble_user_games();
	}
	else
	if(game_type==0){
		if(pebble_user_points==0 || pebble_user_points==15)
		pebble_user_points = pebble_user_points+15;
		else 
		if(pebble_user_points==30 || (pebble_user_points==40 && opponent_points==40))
		pebble_user_points = pebble_user_points+10;
		else
		if(pebble_user_points==40 && opponent_points<40){
			//pebble user game
			pebble_user_points = 0;
			opponent_points = 0;
      increment_pebble_user_games();
		}else
		if(opponent_points==50){
			opponent_points=opponent_points-10;
		}else if(pebble_user_points==50)
		{  //pebble user game
			pebble_user_points = 0;
			opponent_points = 0;
      increment_pebble_user_games();
		}
	}else{
		pebble_user_points=pebble_user_points+1;
    changeServe();
		if(pebble_user_points>=7 && pebble_user_points-opponent_points>=2){
			//opponent game
			pebble_user_points = 0;
			opponent_points = 0;
      increment_pebble_user_games();
		}
	}
}

static void increment_opponent_points(){
	returnPoint=returnPoint&LoseAndMask;
	if (current_set==3 && match_type==1){
		if(opponent_sets==pebble_user_sets)
		increment_opponent_games();
	}
	else
	if(game_type==0){
		if(opponent_points==0 || opponent_points==15)
		opponent_points = opponent_points+15;
		else 
		if(opponent_points==30 || (pebble_user_points==40 && opponent_points==40))
		opponent_points = opponent_points+10;
		else
		if(opponent_points==40 && pebble_user_points<40){
			//opponent game
			opponent_points = 0;
			pebble_user_points = 0;
      increment_opponent_games();
		}else
		if(pebble_user_points==50){
			pebble_user_points=pebble_user_points-10;
		}else if(opponent_points==50)
		{  //opponent game
      opponent_points = 0;
			pebble_user_points = 0;
			increment_opponent_games();
		}
	}else{
		opponent_points=opponent_points+1;
    changeServe();
		if(opponent_points>=7 && opponent_points-pebble_user_points>=2){
			//opponent game
			opponent_points = 0;
			pebble_user_points = 0;
      increment_opponent_games();
		}
	}
}

static void sendPoint(){
	DictionaryIterator *iter;
	if (app_message_outbox_begin(&iter) != APP_MSG_OK) {
		return;
	}
	if (dict_write_uint32(iter, 1, returnPoint) != DICT_OK) {
		return;
	}
	app_message_outbox_send();

}
static void up_click_handler(ClickRecognizerRef recognizer, void *context) {

	if(page==1){
		//Lose Point
		currentPointWon=1;
		page=3;
		draw_page(page);
	}else
	if(page==2){
		//Lose point
		currentPointWon=1;
		page=3;
		draw_page(page);
	}else
	if(page==3){
		//forced error
		returnPoint=returnPoint|ForcedOrMask;
		page=5;
		draw_page(page);
	} else 
  if(page==4){
    //unforced passive
    returnPoint=returnPoint|UnforcedActiveOpportuneOrMask;
    page=5;
    draw_page(page);
  }else
	if(page==5){
		//Forehand
		returnPoint=returnPoint|ForehandOrMask;
		if(currentPointWon==0)    {
			increment_pebble_user_points();
			setCurrentGameScore();
		}else      {
			increment_opponent_points();
			setCurrentGameScore();
		}
		sendPoint();
		resetPoint();
		page=1;
		draw_page(page);
	}
	//increment_opponent_points();
	//setCurrentGameScore();
}

static void up_up_long_click_handler(ClickRecognizerRef recognizer, void *context) {
	changeServe();
}
static void up_down_long_click_handler(ClickRecognizerRef recognizer, void *context) {

}

static void up_multi_click_handler(ClickRecognizerRef recognizer, void *context) {
	resetPoint();
	page=1;
	draw_page(page);

}


static void down_multi_click_handler(ClickRecognizerRef recognizer, void *context) {
	if(match_type==0){
		match_type=1;
		displayMatchType();
	}
	else{
		match_type=0;
		displayMatchType();
	}
}

static void down_click_handler(ClickRecognizerRef recognizer, void *context) {
	if(page==1){
		//WIN point
		currentPointWon=0;
		page=3;
		draw_page(page);
	}else
	if(page==2){
		//WIN point
		currentPointWon=0;
		page=3;
		draw_page(page);
	}else
	if(page==3){
		//Unforced error
		returnPoint=returnPoint&UnforcedAndMask;
    if(currentPointWon==0)
		  page=5;
    else
      page=4;
		draw_page(page);
	}else
  if(page==4){
    //unforced passive
    returnPoint=returnPoint|UnforcedPassiveOrMask;
    page=5;
    draw_page(page);
  }else
	if(page==5){
		//backhand
		returnPoint=returnPoint&BackhandAndMask;
		if(currentPointWon==0)    {
			increment_pebble_user_points();
			setCurrentGameScore();
		}else      {
			increment_opponent_points();
			setCurrentGameScore();
		}
		sendPoint();
		resetPoint();
		page=1;
		draw_page(page);
	}
}

static void down_up_long_click_handler(ClickRecognizerRef recognizer, void *context) {

}
static void down_down_long_click_handler(ClickRecognizerRef recognizer, void *context) {
  text_layer_set_text(game_score_text_layer, "RESET");
	persist=0;
}

static void select_click_handler(ClickRecognizerRef recognizer, void *context) {

	if(page==1){
		//First serve out
		returnPoint=returnPoint&SecondAndMask;
		page=2;
		draw_page(page);
	}else
	if(page==2){
		//Second serve out
		returnPoint=returnPoint|DoubleOrMask;
		if(serving==1)
		currentPointWon=0;
		else
		currentPointWon=1;
		
		if(currentPointWon==0)    {
			increment_pebble_user_points();
			setCurrentGameScore();
		}else      {
			increment_opponent_points();
			setCurrentGameScore();
		}
		sendPoint();
		resetPoint();
		page=1;
		draw_page(page);
	}else
	if(page==3){
		//Winner
		returnPoint=returnPoint|WinnerOrMask;
		page=5;
		draw_page(page);
	}else if(page==4){
    //unforced active innoportune
    returnPoint=returnPoint|UnforcedActiveInopportuneOrMask;
    page=5;
    draw_page(page);
  }  
}
static void select_up_long_click_handler(ClickRecognizerRef recognizer, void *context) {
	if(page==1 || page==2){
		returnPoint=returnPoint|AceOrMask; 
		if(serving==0){
			currentPointWon=0;
			increment_pebble_user_points();
			setCurrentGameScore();
			sendPoint();
			resetPoint();	
			page=1;
			draw_page(page);
		}
		else
		if(serving==1){
			currentPointWon=1;
			increment_opponent_points();
			setCurrentGameScore();
			sendPoint();
			resetPoint();	
			page=1;
			draw_page(page);
		}
	}
}
static void select_down_long_click_handler(ClickRecognizerRef recognizer, void *context) {

}

static void click_config_provider(void *context) {
	//single clicks
	window_single_click_subscribe(BUTTON_ID_UP, (ClickHandler) up_click_handler);
	window_single_click_subscribe(BUTTON_ID_DOWN, (ClickHandler) down_click_handler);
	window_single_click_subscribe(BUTTON_ID_SELECT, (ClickHandler) select_click_handler);
	//long clicks
	window_long_click_subscribe(BUTTON_ID_UP, 1000, up_down_long_click_handler, up_up_long_click_handler); 
	window_long_click_subscribe(BUTTON_ID_DOWN, 4000, down_down_long_click_handler, down_up_long_click_handler); 
	window_long_click_subscribe(BUTTON_ID_SELECT, 400, select_down_long_click_handler, select_up_long_click_handler);
	window_multi_click_subscribe(BUTTON_ID_UP, 2, 2, 0, true, up_multi_click_handler);
	window_multi_click_subscribe(BUTTON_ID_DOWN, 2, 2, 0, true, down_multi_click_handler);

}

static void window_load(Window *me) {
	window_set_click_config_provider(me, click_config_provider);

	Layer *layer = window_get_root_layer(me);

	up_text_layer = text_layer_create(GRect(110, 0, 34, 18));
	text_layer_set_font(up_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_18_BOLD));
	text_layer_set_background_color(up_text_layer, GColorClear);
	layer_add_child(layer, text_layer_get_layer(up_text_layer));
  
  up_text_layer_option = text_layer_create(GRect(97, 20, 50, 30));
	text_layer_set_font(up_text_layer_option, fonts_get_system_font(FONT_KEY_GOTHIC_14));
	text_layer_set_background_color(up_text_layer_option, GColorClear);
  text_layer_set_text(up_text_layer_option, "Undo|Srv");
	layer_add_child(layer, text_layer_get_layer(up_text_layer_option));

	select_text_layer = text_layer_create(GRect(95,60, 50, 20));
	text_layer_set_font(select_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_18_BOLD));
	text_layer_set_background_color(select_text_layer, GColorClear);
	layer_add_child(layer, text_layer_get_layer(select_text_layer));

	down_text_layer = text_layer_create(GRect(110, 110, 34, 20));
	text_layer_set_font(down_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_18_BOLD));
	text_layer_set_background_color(down_text_layer, GColorClear);
	layer_add_child(layer, text_layer_get_layer(down_text_layer));
  
  down_text_layer_option = text_layer_create(GRect(85, 130, 70, 30));
	text_layer_set_font(down_text_layer_option, fonts_get_system_font(FONT_KEY_GOTHIC_14));
	text_layer_set_background_color(down_text_layer_option, GColorClear);
  text_layer_set_text(down_text_layer_option, "MType|Rst");
	layer_add_child(layer, text_layer_get_layer(down_text_layer_option));

	draw_page(1);

	game_score_text_layer = text_layer_create(GRect(0, 0, 110, 60));
	text_layer_set_font(game_score_text_layer, fonts_get_system_font(FONT_KEY_BITHAM_30_BLACK));
	text_layer_set_background_color(game_score_text_layer, GColorClear);
	text_layer_set_text(game_score_text_layer, "0-0");
	layer_add_child(layer, text_layer_get_layer(game_score_text_layer));
	setCurrentGameScore();
	
  
  match_type_text_layer = text_layer_create(GRect(0, 40, 110, 60));
	text_layer_set_font(match_type_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_18_BOLD));
	text_layer_set_background_color(match_type_text_layer, GColorClear);
  text_layer_set_text(match_type_text_layer, "2/3 ST");
	layer_add_child(layer, text_layer_get_layer(match_type_text_layer));
  displayMatchType();
  
	player_serving_text_layer = text_layer_create(GRect(0, 70, 110, 60));
	text_layer_set_font(player_serving_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_18_BOLD));
	text_layer_set_background_color(player_serving_text_layer, GColorClear);
	layer_add_child(layer, text_layer_get_layer(player_serving_text_layer));
	displayServing();

	set1_score_text_layer = text_layer_create(GRect(4, 95, 20, 88));
	text_layer_set_font(set1_score_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_28_BOLD));
	text_layer_set_background_color(set1_score_text_layer, GColorClear);
	text_layer_set_text(set1_score_text_layer, "0  0");
	layer_add_child(layer, text_layer_get_layer(set1_score_text_layer));

	set2_score_text_layer = text_layer_create(GRect(34, 95, 20, 88));
	text_layer_set_font(set2_score_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_28_BOLD));
	text_layer_set_background_color(set2_score_text_layer, GColorClear);
	text_layer_set_text(set2_score_text_layer, "0  0");
	layer_add_child(layer, text_layer_get_layer(set2_score_text_layer));
	
	set3_score_text_layer = text_layer_create(GRect(64, 95, 25, 88));
	text_layer_set_font(set3_score_text_layer, fonts_get_system_font(FONT_KEY_GOTHIC_28_BOLD));
	text_layer_set_background_color(set3_score_text_layer, GColorClear);
	text_layer_set_text(set3_score_text_layer, "0  0");
	layer_add_child(layer, text_layer_get_layer(set3_score_text_layer));

	refresh_set_score();

}

static void window_unload(Window *window) {
	text_layer_destroy(up_text_layer);
	text_layer_destroy(select_text_layer);
	text_layer_destroy(down_text_layer);


}

static void init(void) {
	app_comm_set_sniff_interval(SNIFF_INTERVAL_REDUCED);
	app_message_open(64, 16);

	pebble_user_points = persist_exists(pebble_user_points_KEY) ? persist_read_int(pebble_user_points_KEY) : pebble_user_points;
	opponent_points = persist_exists(opponent_points_KEY) ? persist_read_int(opponent_points_KEY) : opponent_points;
	game_type = persist_exists(game_type_KEY) ? persist_read_int(game_type_KEY) : game_type;
	serving = persist_exists(serving_KEY) ? persist_read_int(serving_KEY) : serving;
	currentPointWon = persist_exists(currentPointWon_KEY) ? persist_read_int(currentPointWon_KEY) : currentPointWon;
	match_type = persist_exists(match_type_KEY) ? persist_read_int(match_type_KEY) : match_type;
	pebble_user_sets = persist_exists(pebble_user_sets_KEY) ? persist_read_int(pebble_user_sets_KEY) : pebble_user_sets;
	opponent_sets = persist_exists(opponent_sets_KEY) ? persist_read_int(opponent_sets_KEY) : opponent_sets;

	persist_read_string(set1_text_KEY,set1_text,5);
	persist_read_string(set2_text_KEY,set2_text,5);
	persist_read_string(set3_text_KEY,set3_text,10);

	current_set = persist_exists(current_set_KEY) ? persist_read_int(current_set_KEY) : current_set;
	pebble_user_games = persist_exists(pebble_user_games_KEY) ? persist_read_int(pebble_user_games_KEY) : pebble_user_games;
	opponent_games = persist_exists(opponent_games_KEY) ? persist_read_int(opponent_games_KEY) : opponent_games;
	//page = persist_exists(page_KEY) ? persist_read_int(page_KEY) : page;

	resetPoint();
	window = window_create();
	window_set_window_handlers(window, (WindowHandlers) {
		.load = window_load,
		.unload = window_unload,
	});


	window_stack_push(window, true /* Animated */);
}

static void deinit(void) {
	if(persist==1){
		persist_write_int(pebble_user_points_KEY, pebble_user_points);
		persist_write_int(opponent_points_KEY, opponent_points);
		persist_write_int(game_type_KEY, game_type);
		persist_write_int(serving_KEY, serving);
		persist_write_int(currentPointWon_KEY, currentPointWon);
		persist_write_int(match_type_KEY, match_type);
		persist_write_int(pebble_user_sets_KEY, pebble_user_sets);
		persist_write_int(opponent_sets_KEY, opponent_sets);
		persist_write_string(set1_text_KEY, set1_text);
		persist_write_string(set2_text_KEY, set2_text);
		persist_write_string(set3_text_KEY, set3_text);
		persist_write_int(current_set_KEY, current_set);
		persist_write_int(pebble_user_games_KEY, pebble_user_games);
		persist_write_int(opponent_games_KEY, opponent_games);
		persist_write_int(page_KEY, page);
	}else
	{
		persist_delete(pebble_user_points_KEY);
		persist_delete(opponent_points_KEY);
		persist_delete(game_type_KEY);
		persist_delete(serving_KEY);
		persist_delete(currentPointWon_KEY);
		persist_delete(match_type_KEY);
		persist_delete(pebble_user_sets_KEY);
		persist_delete(opponent_sets_KEY);
		persist_delete(set1_text_KEY);
		persist_delete(set2_text_KEY);
		persist_delete(set3_text_KEY);
		persist_delete(current_set_KEY);
		persist_delete(pebble_user_games_KEY);
		persist_delete(opponent_games_KEY);
		persist_delete(page_KEY);
	}
//   if (
//     (match_type==1 && current_set==3 && ((pebble_user_games+opponent_games) % 2 ==1))
//    || (( (pebble_user_games==opponent_games) && (pebble_user_games==6)) &&	((pebble_user_points+opponent_points) % 2 == 1) || (pebble_user_points+opponent_points==0)) 
// 	 ||(match_type==1 && current_set!=3)
// 	 ||(pebble_user_games==opponent_games && pebble_user_games==0))
  	window_destroy(window);
}

int main(void) {
	init();
	app_event_loop();
	deinit();
}
