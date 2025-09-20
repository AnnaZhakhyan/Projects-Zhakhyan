extends Control


func anim_start(pos_y : float, text : String):
	visible = true
	position = Vector2(0, pos_y - 40)
	$Bar.size.x = 0;
	$Bar.position.x =0;
	$TEXT.visible_characters = 0
	$TEXT.position.x = 280.0
	$TEXT.text = "[shake rate=20.0 level=10]" + text
	create_tween().tween_property($TEXT,"visible_characters",10,0.4)
	create_tween().tween_property($Bar,"size:x",1000,0.3)
	await get_tree().create_timer(0.7).timeout 
	create_tween().tween_property($TEXT,"position:x",1000,0.2)
	create_tween().tween_property($Bar,"position:x",1000,0.3)
	await get_tree().create_timer(0.3).timeout 
	visible = false
	
