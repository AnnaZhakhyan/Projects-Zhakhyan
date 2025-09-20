extends Control

func show_dmg(dmg : int):
	modulate.a = 1.0
	visible = true
	position.y = 660.0
	$TEXT.text = "[wave amp=100.0 freq=10.0 connected=1]" + str(dmg)
	create_tween().tween_property(self,"position:y",position.y-100,0.2)
	await get_tree().create_timer(0.2).timeout
	create_tween().tween_property(self,"position:y",position.y+100,0.2)
	await get_tree().create_timer(0.8).timeout
	create_tween().tween_property(self,"position:y",position.y+200,0.2)
	create_tween().tween_property(self,"modulate:a",0.0,0.2)
	await get_tree().create_timer(0.2).timeout
	visible = false
