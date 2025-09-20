extends Chara
class_name CharaEnemy

func _ready() -> void:
	SignalBus.find_next_enemy.connect(select_next_enemy)
	
	match COLOR:
		Utils.COLOR.AGL: $ColorCircle.modulate = Color("8cd1ff");
		Utils.COLOR.STR: $ColorCircle.modulate = Color("ff8c8c");
		Utils.COLOR.PHY: $ColorCircle.modulate = Color("ffdd8c");
		Utils.COLOR.INT: $ColorCircle.modulate = Color("bc75ff");
		Utils.COLOR.TEQ: $ColorCircle.modulate = Color("b1ff8c");

func _on_button_pressed() -> void:
	Utils.select_enemy(self)
	Utils.create_effect(Utils.EFFECT.CIRCLE,global_position + Vector2(-10,0),2)
	Utils.highlight_car(self)
	show_hp()
	pass # Replace with function body.
	
func deal_dmg(dmg : int, modifier : float):
	HP = HP - dmg;
	show_hp()
	$HPBar.position = Vector2(-106.0,-77.0)
	create_tween().tween_property($HPBar,"value",HP / MAX_HP * 100,1)
	
	if HP < 0 : 
		SignalBus.enemy_died.emit(self)
		play_anim(Utils.ANIM.DEATH,4)
	else:
		play_anim(Utils.ANIM.DAMAGE,0.5)

	$EnemyDamage.set("theme_override_colors/default_color",Color.WHITE)
	$EnemyDamage/CRIT.visible = false
	
	match modifier:
		0.8: # Weak Damage
			$EnemyDamage/Arrow.visible = true
			$EnemyDamage/Arrow.rotation_degrees = 165.0
			$EnemyDamage/Arrow.modulate = Color("ff8c8c")
		1.5: # Super Effective Damage
			$EnemyDamage/Arrow.visible = true
			$EnemyDamage/Arrow.rotation_degrees = -15.0
			$EnemyDamage/Arrow.modulate = Color("9dff8c")
		2.0: # Critical Damage
			$EnemyDamage/CRIT.visible = true
			$EnemyDamage/Arrow.visible = true
			$EnemyDamage/Arrow.rotation_degrees = -15.0
			$EnemyDamage/Arrow.modulate = Color("9dff8c")
	
	$EnemyDamage.modulate.a = 1.0
	$EnemyDamage.visible = true
	$EnemyDamage.text = "[wave amp=100.0 freq=10.0 connected=1]" + str(dmg)
	create_tween().tween_property($EnemyDamage,"position:y",$EnemyDamage.position.y-20,0.2)
	await get_tree().create_timer(0.2).timeout
	create_tween().tween_property($EnemyDamage,"position:y",$EnemyDamage.position.y+20,0.2)
	await get_tree().create_timer(1.8).timeout
	#create_tween().tween_property($EnemyDamage,"position:y",$EnemyDamage.position.y+200,0.2)
	create_tween().tween_property($EnemyDamage,"modulate:a",0.0,0.2)
	await get_tree().create_timer(0.2).timeout
	$EnemyDamage.visible = false
	$EnemyDamage/Arrow.visible = false
	
	if HP < 0 : queue_free()
	
func show_hp():
	$HPBar.visible = true
	create_tween().tween_property($HPBar,"modulate:a",1.0,0.2)
	await get_tree().create_timer(1).timeout
	
	create_tween().tween_property($HPBar,"modulate:a",0.0,1)
	await get_tree().create_timer(1).timeout
	$HPBar.visible = false
	$HPBar.position = Vector2(-19.0,-248.0)
	
func select_next_enemy():
	if HP > 0: Utils.select_enemy(self)
