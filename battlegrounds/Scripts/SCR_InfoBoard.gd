extends Control

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	show_bgm_name()
	pass

func show_wait():
	create_tween().tween_property($Control/VBoxContainer,"position:y",-37,0.2).set_trans(Tween.TRANS_BOUNCE)
	pass

func show_player_turn():
	create_tween().tween_property($Control/VBoxContainer,"position:y",-95,0.2).set_trans(Tween.TRANS_BOUNCE)
	pass

func show_enemy_turn():
	create_tween().tween_property($Control/VBoxContainer,"position:y",21,0.2).set_trans(Tween.TRANS_BOUNCE)
	pass

func show_bgm_name():
	$HBOX/Control/BGMName.position.x = 150
	create_tween().tween_property($HBOX/Control/BGMName,"position:x",-$HBOX/Control/BGMName.size.x-20,10)
	await get_tree().create_timer(10.1).timeout
	show_bgm_name()
