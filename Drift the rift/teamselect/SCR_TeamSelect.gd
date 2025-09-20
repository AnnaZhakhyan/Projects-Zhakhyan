extends Control

func _ready():
	$HBOX/C1/CharaSelect.chara_select = 1
	$HBOX/C2/CharaSelect.chara_select = 0
	$HBOX/C3/CharaSelect.chara_select = 2
	
	var EmptyItemArray : Array[ItemRes] 
	
	$HBOX/C1/CharaSelect.setup(CharaSet.new(API.current_team[1],EmptyItemArray))
	$HBOX/C2/CharaSelect.setup(CharaSet.new(API.current_team[0],EmptyItemArray))
	$HBOX/C3/CharaSelect.setup(CharaSet.new(API.current_team[2],EmptyItemArray))
	
func _on_start_pressed():
	get_tree().change_scene_to_file("res://scenes/misc/NDE_LoadingScreen.tscn")
	queue_free()
	pass # Replace with function body.


func _on_back_pressed():
	queue_free()
